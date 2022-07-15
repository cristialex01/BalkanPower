package sk.best.newtify.web.gui.component.widget;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.val;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LuckyNumberWidget extends VerticalLayout {
    private static final long serialVersionUID = 1474737226804597872L;
    private final RestTemplate restTemplate = new RestTemplate();

    public LuckyNumberWidget() {
    }

    @PostConstruct
    private void init(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        val quote = restTemplate.exchange("https://csrng.net/csrng/csrng.php?min=0&max=100", HttpMethod.GET, httpEntity, List.class);
        Map<String, Object> resturnMap = (Map<String, Object>) quote.getBody().get(0);
                createQuotePart(resturnMap.get("random").toString());
        this.setWidthFull();

        this.getStyle()
                .set("backround", "var(--lumo-contrast-10pct)")
                .set("border-radius", "1em");
        this.setAlignItems(Alignment.AUTO);
        this.setWidthFull();
    }

    private void createQuotePart(String quote) {
        H4 todayDateTitle = new H4("Your lucky number is: " + quote);
        todayDateTitle.getStyle()
                .set("color", "var(--lumo-contrast-color)");
        /*
        H4 ronSwanson = new H4(quote);
        ronSwanson.getStyle()
                .set("color", "var(--lumo-contrast-color)");
*/
        this.add(todayDateTitle);
    }
}
