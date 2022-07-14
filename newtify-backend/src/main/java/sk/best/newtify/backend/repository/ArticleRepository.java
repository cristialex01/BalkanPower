package sk.best.newtify.backend.repository;

import org.springframework.data.repository.CrudRepository;
import sk.best.newtify.backend.entity.Article;
import sk.best.newtify.backend.entity.enums.TopicType;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, String> {

    List<Article> findAllByOrderByCreatedAt();

    List<Article> findAllByTopicType(TopicType topicType);
}
