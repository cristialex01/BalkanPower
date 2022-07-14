package sk.best.newtify.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import sk.best.newtify.api.ArticlesApi;
import sk.best.newtify.api.dto.ArticleDTO;
import sk.best.newtify.api.dto.CreateArticleDTO;
import sk.best.newtify.backend.entity.Article;
import sk.best.newtify.backend.service.ArticleService;

import java.util.List;

@Controller
public class ArticlesController implements ArticlesApi {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseEntity<ArticleDTO> createArticle(CreateArticleDTO createArticleDTO) {
        ArticleDTO response = articleService.createArticle(createArticleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ArticleDTO> retrieveArticle(String articleUuid) {
        ArticleDTO article = articleService.retrieveArticle(articleUuid);
        return ResponseEntity.status(HttpStatus.OK).body(article);
    }

    @Override
    public ResponseEntity<List<ArticleDTO>> retrieveArticles(String topic) {
        List<ArticleDTO> articleList = articleService.retrieveArticles(topic);
        return ResponseEntity.status(HttpStatus.OK).body(articleList);
    }

    @Override
    public ResponseEntity<Void> updateArticle(String articleUuid, CreateArticleDTO createArticleDTO) {
        articleService.updateArticle(articleUuid, createArticleDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteArticle(String articleUuid) {
        articleService.deleteArticle(articleUuid);
        return ResponseEntity.ok().build();
    }
}
