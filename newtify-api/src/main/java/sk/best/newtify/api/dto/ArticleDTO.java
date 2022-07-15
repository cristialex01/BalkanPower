package sk.best.newtify.api.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import sk.best.newtify.api.dto.ETopicType;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * ArticleDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-07-14T11:39:24.924332600+02:00[Europe/Budapest]")
public class ArticleDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("uuid")
  private String uuid;

  @JsonProperty("title")
  private String title;

  @JsonProperty("shortTitle")
  private String shortTitle;

  @JsonProperty("text")
  private String text;

  @JsonProperty("createdAt")
  private Long createdAt = null;

  @JsonProperty("author")
  private String author;

  @JsonProperty("topicType")
  private ETopicType topicType;

  public ArticleDTO uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  /**
   * Get uuid
   * @return uuid
  */
  
  @Schema(name = "uuid", required = false)
  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public ArticleDTO title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */
  
  @Schema(name = "title", required = false)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ArticleDTO shortTitle(String shortTitle) {
    this.shortTitle = shortTitle;
    return this;
  }

  /**
   * Get shortTitle
   * @return shortTitle
  */
  
  @Schema(name = "shortTitle", required = false)
  public String getShortTitle() {
    return shortTitle;
  }

  public void setShortTitle(String shortTitle) {
    this.shortTitle = shortTitle;
  }

  public ArticleDTO text(String text) {
    this.text = text;
    return this;
  }

  /**
   * Get text
   * @return text
  */
  
  @Schema(name = "text", required = false)
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public ArticleDTO createdAt(Long createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
  */
  
  @Schema(name = "createdAt", required = false)
  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }

  public ArticleDTO author(String author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
  */
  
  @Schema(name = "author", required = false)
  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public ArticleDTO topicType(ETopicType topicType) {
    this.topicType = topicType;
    return this;
  }

  /**
   * Get topicType
   * @return topicType
  */
  @Valid 
  @Schema(name = "topicType", required = false)
  public ETopicType getTopicType() {
    return topicType;
  }

  public void setTopicType(ETopicType topicType) {
    this.topicType = topicType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ArticleDTO articleDTO = (ArticleDTO) o;
    return Objects.equals(this.uuid, articleDTO.uuid) &&
        Objects.equals(this.title, articleDTO.title) &&
        Objects.equals(this.shortTitle, articleDTO.shortTitle) &&
        Objects.equals(this.text, articleDTO.text) &&
        Objects.equals(this.createdAt, articleDTO.createdAt) &&
        Objects.equals(this.author, articleDTO.author) &&
        Objects.equals(this.topicType, articleDTO.topicType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, title, shortTitle, text, createdAt, author, topicType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ArticleDTO {\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    shortTitle: ").append(toIndentedString(shortTitle)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    topicType: ").append(toIndentedString(topicType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

