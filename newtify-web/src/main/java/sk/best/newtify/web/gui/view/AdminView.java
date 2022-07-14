package sk.best.newtify.web.gui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.http.ResponseEntity;
import sk.best.newtify.api.ArticlesApi;
import sk.best.newtify.api.dto.ArticleDTO;
import sk.best.newtify.web.gui.component.article.ArticleEditor;
import sk.best.newtify.web.util.ArticleMapper;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author Marek Urban
 * Copyright © 2022 BEST Technická univerzita Košice.
 * All rights reserved.
 */
@PageTitle("Administration")
@Route("admin")
public class AdminView extends SplitLayout {

    private static final long              serialVersionUID = -5284513347869397044L;
    private static final DateTimeFormatter DATE_FORMATTER   = DateTimeFormatter.ofPattern("dd. MM. uuuu");

    private final ArticlesApi                  articlesApi;
    private final ObjectFactory<ArticleEditor> articleEditorObjectFactory;

    private final HorizontalLayout topLayout    = new HorizontalLayout();
    private final VerticalLayout   topRightPane = new VerticalLayout();
    private final VerticalLayout   bottomLayout = new VerticalLayout();

    public AdminView(ArticlesApi articlesApi,
                     ObjectFactory<ArticleEditor> articleEditorObjectFactory) {
        this.articlesApi                = articlesApi;
        this.articleEditorObjectFactory = articleEditorObjectFactory;
    }

    @PostConstruct
    protected void init() {
        this.setOrientation(Orientation.VERTICAL);

        topLayout.removeAll();
        topRightPane.removeAll();
        bottomLayout.removeAll();

        ArticleEditor articleEditor = articleEditorObjectFactory.getObject();
        ListBox<ArticleDTO> articleSelector = createArticleSelector(articleEditor);
        Button fetchArticlesButton = createArticlesFetchButton(articleSelector);
        Button createArticlesButton = createArticlesCreateButton(articleSelector);
        Button updateArticlesButton = createArticleUpdateButton(articleEditor, articleSelector);
        Button deleteArticlesButton = createArticleDeleteButton(articleEditor, articleSelector);

        topRightPane.add(fetchArticlesButton, createArticlesButton, updateArticlesButton, deleteArticlesButton);
        topRightPane.setSizeFull();
        topRightPane.getStyle()
                .set("border", "var(--lumo-contrast-5pct) 5px solid");

        topLayout.add(articleSelector, topRightPane);
        topLayout.setSizeFull();

        bottomLayout.add(articleEditor.getFormLayout(), articleEditor.getContentTextArea());
        bottomLayout.getStyle().set("overflow", "hidden");
        bottomLayout.setSizeFull();

        this.addToPrimary(topLayout);
        this.addToSecondary(bottomLayout);
        this.setSplitterPosition(25);
        this.setSizeFull();
    }

    private ListBox<ArticleDTO> createArticleSelector(ArticleEditor articleEditor) {
        ListBox<ArticleDTO> selector = new ListBox<>();
        selector.setSizeFull();
        selector.setRenderer(createSelectorRenderer());
        fetchArticles(selector);

        selector.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                return;
            }
            articleEditor.getArticleBinder().setBean(event.getValue());
            articleEditor.getContentTextArea().setValue(event.getValue().getText());
            articleEditor.getArticleBinder().getBean().setUuid(event.getValue().getUuid());
        });

        return selector;
    }

    private Button createArticleDeleteButton(ArticleEditor articleEditor,
                                             ListBox<ArticleDTO> articleSelector) {
        Button button = new Button("Delete article", VaadinIcon.DEL.create());
        button.addThemeVariants(ButtonVariant.LUMO_ERROR);

        button.addClickListener(event -> {
            if (!articleEditor.getArticleBinder().validate().isOk()) {
                return;
            }
            ResponseEntity<Void> response = articlesApi.deleteArticle(
                    articleEditor.getArticleBinder().getBean().getUuid()
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                Notification successNotification = new Notification("Deleted");
                successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                successNotification.setPosition(Notification.Position.TOP_CENTER);
                successNotification.setDuration(5000);
                successNotification.open();
                articleEditor.clear();
                fetchArticles(articleSelector);
                return;
            }

            Notification errorNotification = new Notification("Error");
            errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorNotification.setPosition(Notification.Position.TOP_CENTER);
            errorNotification.setDuration(5000);
            errorNotification.open();
        });

        return button;
    }

    private Button createArticleUpdateButton(ArticleEditor articleEditor,
                                             ListBox<ArticleDTO> articleSelector) {
        Button button = new Button("Update article", VaadinIcon.UPLOAD.create());
        button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        button.addClickListener(event -> {
            if (!articleEditor.getArticleBinder().validate().isOk()) {
                return;
            }
            ResponseEntity<Void> response = articlesApi.updateArticle(
                    articleEditor.getArticleBinder().getBean().getUuid(),
                    ArticleMapper.toCreateArticle(articleEditor.getArticleBinder().getBean())
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                Notification successNotification = new Notification("Saved");
                successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                successNotification.setPosition(Notification.Position.TOP_CENTER);
                successNotification.setDuration(5000);
                successNotification.open();
                articleEditor.clear();
                fetchArticles(articleSelector);
                return;
            }

            Notification errorNotification = new Notification("Error");
            errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorNotification.setPosition(Notification.Position.TOP_CENTER);
            errorNotification.setDuration(5000);
            errorNotification.open();
        });

        return button;
    }

    private Button createArticlesCreateButton(
            ListBox<ArticleDTO> articleSelector) {
        Button button = new Button("Create article", VaadinIcon.PLUS_SQUARE_O.create());
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickListener(event -> {
            ConfirmDialog articleEditorDialog = articleEditorObjectFactory.getObject().getConfirmDialog();
            articleEditorDialog.addConfirmListener(confirmEvent -> fetchArticles(articleSelector));
            articleEditorDialog.open();
        });
        return button;
    }

    private Button createArticlesFetchButton(ListBox<ArticleDTO> articleSelector) {
        Button button = new Button("Reload", VaadinIcon.DOWNLOAD.create());
        button.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        button.addClickListener(event -> fetchArticles(articleSelector));
        return button;
    }

    private void fetchArticles(ListBox<ArticleDTO> articleSelector) {
        articleSelector.setItems(
                articlesApi
                        .retrieveArticles(null)
                        .getBody()
        );
    }

    private static ComponentRenderer<FlexLayout, ArticleDTO> createSelectorRenderer() {
        return new ComponentRenderer<>(article -> {
            FlexLayout wrapper = new FlexLayout();

            Span topicBadge = new Span(article.getTopicType().getValue());
            topicBadge.getElement().getThemeList().add("badge secondary");
            topicBadge.getStyle().set("margin-right", "1em");
            topicBadge.setWidth("8em");

            Div titleAndContent = new Div();
            titleAndContent.setText(article.getTitle());

            Div content = new Div();
            content.add(new Span(article.getAuthor()));
            content.add(new Span(
                    DATE_FORMATTER.format(
                            Instant.ofEpochSecond(article.getCreatedAt())
                                    .atZone(ZoneId.systemDefault())
                    )
            ));

            content.getStyle()
                    .set("font-size", "var(--lumo-font-size-s)")
                    .set("color", "var(--lumo-secondary-text-color)")
                    .set("display", "flex")
                    .set("flex-direction", "column");
            titleAndContent.add(content);

            wrapper.add(topicBadge, titleAndContent);
            return wrapper;
        });
    }

}
