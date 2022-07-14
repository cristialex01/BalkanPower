package sk.best.newtify.web.connector;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.best.newtify.api.ArticlesApi;
import sk.best.newtify.api.dto.ArticleDTO;
import sk.best.newtify.api.dto.CreateArticleDTO;

import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * @author Marek Urban
 * Copyright © 2022 BEST Technická univerzita Košice.
 * All rights reserved.
 */
@Slf4j
@Service
public class ArticleConnectorService implements ArticlesApi {

    private static final String ARTICLES_API_URL = "http://localhost:8081/v1/articles";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<ArticleDTO> createArticle(@NonNull CreateArticleDTO createArticleDTO) {
        try {
            return restTemplate.postForEntity(ARTICLES_API_URL, createArticleDTO, ArticleDTO.class);
        } catch (Exception e) {
            log.error("ERROR createArticle", e);
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteArticle(@NonNull String articleUuid) {
        try {
            restTemplate.delete(ARTICLES_API_URL + "/" + articleUuid);
            return ResponseEntity
                    .ok()
                    .build();
        } catch (Exception e) {
            log.error("ERROR deleteArticle", e);
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @Override
    public ResponseEntity<ArticleDTO> retrieveArticle(@NonNull String articleUuid) {
        try {
            return restTemplate.getForEntity(ARTICLES_API_URL + "/" + articleUuid, ArticleDTO.class);
        } catch (Exception e) {
            log.error("ERROR retrieveArticle", e);
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    /**
     * @return remapped response entity with list of articles or an empty list
     */
    @Override
    public ResponseEntity<List<ArticleDTO>> retrieveArticles(@Nullable String topic) {
        try {
            ResponseEntity<ArticleDTO[]> articlesResponse;
            if (topic == null) {
                articlesResponse = restTemplate.getForEntity(ARTICLES_API_URL, ArticleDTO[].class);
            } else {
                articlesResponse = restTemplate.getForEntity(ARTICLES_API_URL + "?topic={topic}", ArticleDTO[].class, topic);
            }

            ArticleDTO[] data = articlesResponse.getBody();
            if (data == null) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            return ResponseEntity
                    .status(articlesResponse.getStatusCode())
                    .body(List.of(data));
        } catch (Exception e) {
            log.error("ERROR retrieveArticles", e);
            return ResponseEntity
                    .internalServerError()
                    .body(Collections.emptyList());
        }
    }

    @Override
    public ResponseEntity<Void> updateArticle(@NonNull String articleUuid,
                                              @NonNull CreateArticleDTO createArticleDTO) {
        try {
            restTemplate.put(URI.create(ARTICLES_API_URL + "/" + articleUuid), createArticleDTO);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("ERROR updateArticle", e);
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

}
