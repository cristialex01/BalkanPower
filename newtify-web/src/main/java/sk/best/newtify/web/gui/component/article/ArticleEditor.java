package sk.best.newtify.web.gui.component.article;

import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.best.newtify.api.ArticlesApi;
import sk.best.newtify.api.dto.ArticleDTO;
import sk.best.newtify.api.dto.CreateArticleDTO;
import sk.best.newtify.api.dto.ETopicType;
import sk.best.newtify.web.util.ArticleMapper;

import javax.annotation.PostConstruct;

/**
 * @author Marek Urban
 * Copyright © 2022 BEST Technická univerzita Košice.
 * All rights reserved.
 */
@Getter
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ArticleEditor extends VerticalLayout {

    private static final long serialVersionUID = -404447830994215437L;

    private final ArticlesApi articlesApi;

    private final FormLayout    formLayout    = new FormLayout();
    private final ConfirmDialog confirmDialog = new ConfirmDialog();

    private final TextField          titleField      = new TextField();
    private final TextField          shortTitleField = new TextField();
    private final TextField          authorField     = new TextField();
    private final Select<ETopicType> topicSelector   = new Select<>(ETopicType.values());
    private final TextArea           contentTextArea = new TextArea();
    private final Binder<ArticleDTO> articleBinder   = new Binder<>();

    public ArticleEditor(ArticlesApi articlesApi) {
        this.articlesApi = articlesApi;

        titleField.setSizeFull();
        shortTitleField.setSizeFull();
        authorField.setSizeFull();
        topicSelector.setSizeFull();
        contentTextArea.setSizeFull();

        confirmDialog.setHeader("Create new article");
        confirmDialog.setHeight("600px");
    }

    @PostConstruct
    protected void init() {
        topicSelector.setTextRenderer(ETopicType::getValue);

        formLayout.addFormItem(titleField, "Title");
        formLayout.addFormItem(shortTitleField, "Short intro");
        formLayout.addFormItem(authorField, "Author");
        formLayout.addFormItem(topicSelector, "Topic");

        contentTextArea.setLabel("Content");
        formLayout.add(contentTextArea);

        createBinder();

        confirmDialog.setCancelable(true);
        confirmDialog.addConfirmListener(this::onConfirmAction);
        confirmDialog.addCancelListener(this::onCancelAction);
        confirmDialog.addAttachListener(event -> {
            formLayout.setSizeFull();
            contentTextArea.setHeight("200px");
            contentTextArea.setMaxHeight("200px");
            confirmDialog.add(formLayout);
        });

        this.setSizeFull();
        this.add(formLayout);
    }

    private void onCancelAction(ConfirmDialog.CancelEvent cancelEvent) {
        confirmDialog.close();
    }

    private void onConfirmAction(ConfirmDialog.ConfirmEvent confirmEvent) {
        if (!articleBinder.validate().isOk()) {
            confirmDialog.open();
            return;
        }
        CreateArticleDTO createArticleDTO = ArticleMapper.toCreateArticle(articleBinder.getBean());
        articlesApi.createArticle(createArticleDTO);
        confirmDialog.close();
    }

    private void createBinder() {
        articleBinder.setBean(new ArticleDTO());

        articleBinder
                .forField(titleField)
                .withValidator((value, context) -> {
                    if (StringUtils.isBlank(value)) {
                        return ValidationResult.error("Title is missing!");
                    }
                    return ValidationResult.ok();
                })
                .bind(ArticleDTO::getTitle, ArticleDTO::setTitle);

        articleBinder
                .forField(shortTitleField)
                .withValidator((value, context) -> {
                    if (StringUtils.isBlank(value)) {
                        return ValidationResult.error("Short title is missing!");
                    }
                    return ValidationResult.ok();
                })
                .bind(ArticleDTO::getShortTitle, ArticleDTO::setShortTitle);

        articleBinder
                .forField(authorField)
                .withValidator((value, context) -> {
                    if (StringUtils.isBlank(value)) {
                        return ValidationResult.error("Author name is missing!");
                    }
                    return ValidationResult.ok();
                })
                .bind(ArticleDTO::getAuthor, ArticleDTO::setAuthor);

        articleBinder
                .forField(topicSelector)
                .withValidator((value, context) -> {
                    if (value == null) {
                        return ValidationResult.error("Topic must be selected!");
                    }
                    return ValidationResult.ok();
                })
                .bind(ArticleDTO::getTopicType, ArticleDTO::setTopicType);

        articleBinder
                .forField(contentTextArea)
                .withValidator((value, context) -> {
                    if (StringUtils.isBlank(value)) {
                        return ValidationResult.error("Content can't be empty!");
                    }
                    return ValidationResult.ok();
                })
                .bind(ArticleDTO::getText, ArticleDTO::setText);

        articleBinder.addValueChangeListener(event -> articleBinder.validate());
    }

    public void clear() {
        articleBinder.removeBean();

        titleField.clear();
        shortTitleField.clear();
        authorField.clear();
        topicSelector.clear();
        contentTextArea.clear();
    }

}
