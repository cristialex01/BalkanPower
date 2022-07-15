package sk.best.newtify.web.gui.component.widget;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.val;
import javax.annotation.PostConstruct;
import java.util.Map;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RandomJokesWidget extends VerticalLayout {
    private static final long serialVersionUID = 1414727226894592872L;
    private final RestTemplate restTemplate = new RestTemplate();

    public RandomJokesWidget() {
    }

    @PostConstruct
    private void init(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        val quote = restTemplate.exchange("https://icanhazdadjoke.com/", HttpMethod.GET, httpEntity, Map.class);
        createQuotePart(quote.getBody().get("joke").toString());
        this.setWidthFull();

        this.getStyle()
                .set("backround", "var(--lumo-contrast-10pct)")
                .set("border-radius", "1em");
        this.setAlignItems(Alignment.AUTO);
        this.setWidthFull();
    }

    private void createQuotePart(String quote) {
        H4 todayDateTitle = new H4("Random Joke:");
        todayDateTitle.getStyle()
                .set("color", "var(--lumo-contrast-color)");
        H3 ronSwanson = new H3(quote);
        ronSwanson.getStyle()
                .set("color", "var(--lumo-contrast-color)");

        this.add(todayDateTitle, ronSwanson);
    }
}
