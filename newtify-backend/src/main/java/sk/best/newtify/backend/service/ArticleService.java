package sk.best.newtify.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sk.best.newtify.api.dto.ArticleDTO;
import sk.best.newtify.api.dto.CreateArticleDTO;
import sk.best.newtify.api.dto.ETopicType;
import sk.best.newtify.backend.entity.Article;
import sk.best.newtify.backend.entity.enums.TopicType;
import sk.best.newtify.backend.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleDTO createArticle(CreateArticleDTO createArticleDTO) {
        Article article = new Article();
        article.setTitle(createArticleDTO.getTitle());
        article.setShortTitle(createArticleDTO.getShortTitle());
        article.setText(createArticleDTO.getText());
        article.setCreatedAt(createArticleDTO.getCreatedAt());
        article.setAuthor(createArticleDTO.getAuthor());
        article.setTopicType(
                TopicType.fromValue(createArticleDTO
                        .getTopicType().toString()));

        articleRepository.save(article);

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setUuid(article.getUuid());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setShortTitle(article.getShortTitle());
        articleDTO.setText(article.getText());
        articleDTO.setCreatedAt(article.getCreatedAt());
        articleDTO.setAuthor(article.getAuthor());
        articleDTO.setTopicType(
                ETopicType.fromValue(article
                        .getTopicType().toString()));

        return articleDTO;
    }

    public ArticleDTO retrieveArticle(String uuid) {
        Optional<Article> articleOpt = articleRepository.findById(uuid);
        if (articleOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Article article = articleOpt.get();

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setUuid(article.getUuid());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setShortTitle(article.getShortTitle());
        articleDTO.setText(article.getText());
        articleDTO.setCreatedAt(article.getCreatedAt());
        articleDTO.setAuthor(article.getAuthor());
        articleDTO.setTopicType(
                ETopicType.fromValue(article
                        .getTopicType().toString()));
        return articleDTO;
    }

    public List<ArticleDTO> retrieveArticles(String topic) {
        List<Article> articles = new ArrayList<>();

        if (topic != null && !topic.isEmpty()) {
            // we want to retrieve articles by topic name
            TopicType topicType = TopicType.fromValue(topic.toUpperCase());
            articles = articleRepository.findAllByTopicType(topicType);
        } else {
            articles = articleRepository.findAllByOrderByCreatedAt();
        }

        List<ArticleDTO> response = new ArrayList<>();

        for (Article article : articles) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setUuid(article.getUuid());
            articleDTO.setTitle(article.getTitle());
            articleDTO.setShortTitle(article.getShortTitle());
            articleDTO.setText(article.getText());
            articleDTO.setCreatedAt(article.getCreatedAt());
            articleDTO.setAuthor(article.getAuthor());
            articleDTO.setTopicType(
                    ETopicType.fromValue(article
                            .getTopicType().toString()));
            response.add(articleDTO);
        }

        return response;
    }

    public void deleteArticle(String uuid) {
        articleRepository.deleteById(uuid);
    }

    public void updateArticle(String uuid, CreateArticleDTO createArticleDTO) {
        Optional<Article> articleOpt = articleRepository.findById(uuid);
        if (articleOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article was not found");
        }

        Article article = articleOpt.get();
        article.setTitle(createArticleDTO.getTitle() != null ? createArticleDTO.getTitle() : article.getTitle());
        article.setShortTitle(createArticleDTO.getShortTitle() != null ? createArticleDTO.getShortTitle() : article.getShortTitle());
        article.setText(createArticleDTO.getText() != null ? createArticleDTO.getText() : article.getText());
        article.setCreatedAt(createArticleDTO.getCreatedAt() != null ? createArticleDTO.getCreatedAt() : article.getCreatedAt());
        article.setAuthor(createArticleDTO.getAuthor() != null ? createArticleDTO.getAuthor() : article.getAuthor());
        article.setTopicType(createArticleDTO.getTopicType() != null ? TopicType.fromValue(createArticleDTO.getTopicType().getValue()) : article.getTopicType());
        articleRepository.save(article);
    }


}
