package sk.best.newtify.web.gui.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import sk.best.newtify.api.dto.ETopicType;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Marek Urban
 * Copyright © 2022 BEST Technická univerzita Košice.
 * All rights reserved.
 */
@Push
@Theme(value = Lumo.class, variant = Lumo.DARK)
@JsModule("./styles/custom-styles.js")
@CssImport(value = "./styles/navbar/newtify-navbar.css", themeFor = "vaadin-app-layout")
public class MainLayout extends AppLayout {

    private static final long serialVersionUID = 4107656392983873277L;

    private VerticalLayout navigationBar = new VerticalLayout();

    public MainLayout() {
    }

    @PostConstruct
    protected void init() {
        navigationBar = new VerticalLayout();

        createTitle();
        createTabs();

        navigationBar.setPadding(false);
        navigationBar.setSpacing(false);

        addToNavbar(true, navigationBar);
    }

    private void createTabs() {
        Tabs tabs = new Tabs(true);

        Map<ETopicType, Tab> topic2Tab = new HashMap<>();

        for (ETopicType topic : ETopicType.values()) {
            Tab topicTab = new Tab();
            addIconToTab(topic, topicTab);
            topicTab.setId(topic.getValue());
            topicTab.add(topic.getValue());
            topicTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
            tabs.add(topicTab);
            topic2Tab.put(topic, topicTab);
        }

        UI.getCurrent().getPage().fetchCurrentURL(url -> {
            Optional<ETopicType> topicOpt = Arrays.stream(ETopicType.values())
                    .filter(topicType -> url.getPath().matches(topicType.getValue().toLowerCase()))
                    .findFirst();

            topicOpt.ifPresent(topicType -> tabs.setSelectedTab(topic2Tab.get(topicType)));
        });

        tabs.addSelectedChangeListener(this::onTabChanged);

        tabs.addThemeVariants(
                TabsVariant.LUMO_CENTERED,
                TabsVariant.LUMO_ICON_ON_TOP
        );
        tabs.getStyle()
                .set("width", "100%")
                .set("min-width", "150px")
        ;

        navigationBar.add(tabs);
    }

    private void onTabChanged(Tabs.SelectedChangeEvent selectedChangeEvent) {
        String tabId = selectedChangeEvent
                .getSelectedTab()
                .getId()
                .orElse("");

        if (tabId.equals(ETopicType.NEWS.getValue())) {
            UI.getCurrent().navigate(ETopicType.NEWS.getValue().toLowerCase());
            return;
        }

        if (tabId.equals(ETopicType.GAMING.getValue())) {
            UI.getCurrent().navigate(ETopicType.GAMING.getValue().toLowerCase());
            return;
        }

        UI.getCurrent().navigate(""); // default
    }

    private void addIconToTab(ETopicType topic, Tab topicTab) {
        switch (topic) {
            case NEWS:
                topicTab.add(VaadinIcon.GLOBE.create());
                break;
            case GAMING:
                topicTab.add(VaadinIcon.GAMEPAD.create());
                break;
            default:
                topicTab.setEnabled(false);
                // no-op
        }
    }

    private void createTitle() {
        Div titleDiv = new Div();

        H1 title = new H1("Newtify");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0 0 0 0.5em");
        Icon titleIcon = VaadinIcon.NEWSPAPER.create();

        titleDiv.add(titleIcon, title);
        titleDiv.getStyle()
                // margin
                .set("margin", "0 0 0 0.5em")
                // flexbox
                .set("display", "flex")
                .set("flex-direction", "row")
                .set("flex-wrap", "nowrap")
                .set("align-items", "center")
                .set("height", "100%");

        navigationBar.add(titleDiv);
    }

}