package sk.best.newtify.web.gui.component.widget;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import javax.annotation.PostConstruct;
import lombok.val;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class KanyeWidget extends VerticalLayout {
    private static final long serialVersionUID = 1414727226194592872L;
    private final RestTemplate restTemplate = new RestTemplate();
    public KanyeWidget(){

    }

    @PostConstruct
    private void init(){
        val quote = restTemplate.getForEntity("https://api.kanye.rest/", String.class);
        createQuotePart(quote.getBody().replace("{\"quote\":\"","").replace("\"}",""));
        this.setWidthFull();

        this.getStyle()
                .set("backround", "var(--lumo-contrast-10pct)")
                .set("border-radius", "1em");
        this.setAlignItems(Alignment.AUTO);
        this.setWidthFull();
    }

    private void createQuotePart(String quote) {
        H4 todayDateTitle = new H4(quote);
        todayDateTitle.getStyle()
                .set("color", "var(--lumo-contrast-color)");
        H3 ronSwanson = new H3("- Kanye West");
        ronSwanson.getStyle()
                .set("color", "var(--lumo-contrast-color)")
                .set("align-text", "center");

        this.add(todayDateTitle, ronSwanson);
    }


}