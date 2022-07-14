package sk.best.newtify.backend.entity;

import sk.best.newtify.backend.entity.enums.TopicType;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Article {

    @Id
    private String uuid = UUID.randomUUID().toString();

    private String title;

    private String shortTitle;

    @Lob
    private String text;

    private Long createdAt;

    private String author;

    @Enumerated(EnumType.STRING)
    private TopicType topicType;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public TopicType getTopicType() {
        return topicType;
    }

    public void setTopicType(TopicType topicType) {
        this.topicType = topicType;
    }
}
