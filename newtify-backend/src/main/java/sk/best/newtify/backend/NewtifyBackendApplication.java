package sk.best.newtify.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sk.best.newtify.api.dto.CreateArticleDTO;
import sk.best.newtify.backend.entity.enums.TopicType;

/**
 * @author Stanislav Blasko
 * Copyright © 2022 BEST Technická univerzita Košice.
 * All rights reserved.
 */
@SpringBootApplication
public class NewtifyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewtifyBackendApplication.class, args);}

}
