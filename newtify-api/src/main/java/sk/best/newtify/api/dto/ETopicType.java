package sk.best.newtify.api.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets ETopicType
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-07-14T11:39:24.924332600+02:00[Europe/Budapest]")
public enum ETopicType {
  
  NEWS("NEWS"),
  
  GAMING("GAMING"),
  
  FASHION("FASHION"),
  
  FINANCE("FINANCE"),
  
  MOVIE("MOVIE"),
  
  MUSIC("MUSIC");


  private String value;

  ETopicType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ETopicType fromValue(String value) {
    for (ETopicType b : ETopicType.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

