package sk.best.newtify.web.gui.component.article;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sk.best.newtify.api.dto.ArticleDTO;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author Marek Urban
 * Copyright © 2022 BEST Technická univerzita Košice.
 * All rights reserved.
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ArticlePreviewComponent extends Composite<VerticalLayout> {

    private static final long              serialVersionUID = 6948638691283818028L;
    private static final DateTimeFormatter DATE_FORMATTER   = DateTimeFormatter.ofPattern("d.MM.uuuu");

    private final Image image       = new Image();
    private final H2    title       = new H2();
    private final Span  author      = new Span();
    private final Span  date        = new Span();
    private final H5    previewText = new H5();

    private ArticleDTO article;
    private byte[]     imageData;

    public ArticlePreviewComponent() {
    }

    public void setArticle(@Nullable ArticleDTO article) {
        this.article = article;
        if (article == null) {
            clear();
            return;
        }


        this.imageData = fetchImage();
        setImage(imageData);
        title.setText(article.getTitle());
        previewText.setText(article.getShortTitle());
        author.setText(article.getAuthor());

        date.setText(DATE_FORMATTER.format(
                        Instant.ofEpochSecond(article.getCreatedAt())
                                .atZone(ZoneId.systemDefault())
                )
        );
    }

    private void onPreviewClicked(DomEvent domEvent) {
        if (article == null) {
            return;
        }

        ArticleDialogComponent articleDialog = new ArticleDialogComponent();
        articleDialog.setArticle(article, imageData);
        articleDialog.open();
    }

    private void setImage(@Nullable byte[] imageBytes) {
        if (imageBytes == null) {
            image.removeAll();
            return;
        }

        StreamResource streamResource = new StreamResource(
                UUID.randomUUID() + ".jpeg",
                () -> new ByteArrayInputStream(imageBytes)
        );
        image.setSrc(streamResource);
    }

    private byte[] fetchImage() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("https://picsum.photos/500/250", byte[].class);
    }

    private void clear() {
        title.removeAll();
        previewText.removeAll();
    author.removeAll();
        date.removeAll();

        image.removeAll();
        image.setSrc("");
    }

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout rootLayout = super.initContent();
        styleImage();
        styleTitle();
        styleAuthor();
        stylePreviewText();

        rootLayout.setSpacing(false);
        rootLayout.setPadding(false);
        rootLayout.getStyle()
                .set("border-radius", "1em")
                .set("cursor", "pointer")
                .set("background", "rgba(0, 0, 0, 0.1)");

        Span titleAndAuthor = new Span(title, author, date);
        titleAndAuthor.getStyle()
                .set("margin-left", "0.5em");

        rootLayout.add(image, titleAndAuthor, previewText);
        rootLayout.getElement().addEventListener("click", this::onPreviewClicked);
        return rootLayout;
    }

    private void styleImage() {
        image.getStyle()
                .set("width", "100%")
                .set("height", "auto")
                .set("border-radius", "1em 1em 0 0");
    }

    private void styleTitle() {
        title.getStyle()
                .set("margin", "0");
    }

    private void styleAuthor() {
        Icon icon = VaadinIcon.USER.create();
        icon.getStyle().set("margin-left", "0.5em");
        author.add(icon);
        author.getElement().getThemeList().add("badge primary");
        author.getStyle()
                .set("margin", "0.75em 1.5em 0em auto");
    }

    private void stylePreviewText() {
        previewText.getStyle()
                .set("font-style", "italic")
                .set("color", "gray")
                .set("margin-left", "2em");
    }
}
