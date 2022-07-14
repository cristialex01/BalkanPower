package sk.best.newtify.web.gui.component.article;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.StreamResource;
import org.springframework.lang.Nullable;
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
public class ArticleDialogComponent extends Dialog {

    private static final long              serialVersionUID = -8124048656164601926L;
    private static final DateTimeFormatter DATE_FORMATTER   = DateTimeFormatter.ofPattern("d.MM.uuuu");

    private final Image    image       = new Image();
    private final H2       title       = new H2();
    private final Span     author      = new Span();
    private final Span     date        = new Span();
    private final H5       previewText = new H5();
    private final TextArea content     = new TextArea();

    public ArticleDialogComponent() {
        init();
    }

    protected void init() {
        styleImage();
        styleTitle();
        styleAuthor();
        stylePreviewText();
        styleContent();

        Span titleAndAuthor = new Span(title, author, date);
        titleAndAuthor.getStyle()
                .set("margin-left", "0.5em");

        add(image, titleAndAuthor, previewText, content);
    }

    public void setArticle(@Nullable ArticleDTO article,
                           @Nullable byte[] imageData) {
        if (article == null) {
            clear();
            return;
        }

        if (imageData != null && imageData.length != 0) {
            setImage(imageData);
        }


        title.setText(article.getTitle());
        previewText.setText(article.getShortTitle());
        author.setText(article.getAuthor());
        content.setValue(article.getText());

        date.setText(DATE_FORMATTER.format(
                        Instant.ofEpochSecond(article.getCreatedAt())
                                .atZone(ZoneId.systemDefault())
                )
        );
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

    private void clear() {
        title.removeAll();
        previewText.removeAll();
        author.removeAll();
        date.removeAll();
        content.clear();

        image.removeAll();
        image.setSrc("");
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

    private void styleContent() {
        content.setReadOnly(true);
        content.setWidthFull();
    }
}
