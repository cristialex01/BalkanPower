package sk.best.newtify.web.util;

import lombok.NonNull;
import sk.best.newtify.api.dto.ArticleDTO;
import sk.best.newtify.api.dto.CreateArticleDTO;

import java.time.Instant;

/**
 * @author Marek Urban
 * Copyright © 2022 BEST Technická univerzita Košice.
 * All rights reserved.
 */
public interface ArticleMapper {

    static CreateArticleDTO toCreateArticle(@NonNull ArticleDTO articleDTO) {
        CreateArticleDTO createArticleDTO = new CreateArticleDTO();
        createArticleDTO.setCreatedAt(Instant.now().getEpochSecond());
        createArticleDTO.setAuthor(articleDTO.getAuthor());
        createArticleDTO.setTitle(articleDTO.getTitle());
        createArticleDTO.setText(articleDTO.getText());
        createArticleDTO.setTopicType(articleDTO.getTopicType());
        createArticleDTO.setShortTitle(articleDTO.getShortTitle());
        return createArticleDTO;
    }

}
