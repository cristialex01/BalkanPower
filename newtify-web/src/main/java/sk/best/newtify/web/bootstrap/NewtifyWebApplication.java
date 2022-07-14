package sk.best.newtify.web.bootstrap;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Marek Urban
 * Copyright © 2022 BEST Technická univerzita Košice.
 * All rights reserved.
 */
@ComponentScan(
        basePackages = "sk.best.newtify.web"
)
@EnableVaadin(
        "sk.best.newtify.web.gui"
)
@EnableConfigurationProperties
@EnableAutoConfiguration
@SpringBootConfiguration
public class NewtifyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewtifyWebApplication.class, args);
    }

}
