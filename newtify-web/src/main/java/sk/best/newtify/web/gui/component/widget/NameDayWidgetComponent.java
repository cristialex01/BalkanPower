package sk.best.newtify.web.gui.component.widget;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.best.newtify.api.NamedaysApi;
import sk.best.newtify.api.dto.NameDayDTO;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Marek Urban
 * Copyright © 2022 BEST Technická univerzita Košice.
 * All rights reserved.
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NameDayWidgetComponent extends FlexLayout {

    //Get the suffix
    private static String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    //Put the suffix in a variable
    public static String dayNumberSuffix = getDayNumberSuffix(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    private static final long              serialVersionUID    = 1414727226197592073L;

    //Date format for month and year
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("LLLL uuuu", Locale.ENGLISH);

    //Date format for the day of the year
    private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("d", Locale.ENGLISH);

    private final NamedaysApi namedaysApi;

    public NameDayWidgetComponent(NamedaysApi namedaysApi) {
        this.namedaysApi = namedaysApi;
    }

    @PostConstruct
    private void init() {
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);
        NameDayDTO data = namedaysApi.retrieveNameDay(currentMonth, currentDay).getBody();

        createWidgetIcon();
        createDatePart(currentYear, currentMonth, currentDay);
        createNameDayPart(data);

        this.getStyle()
                .set("background", "var(--lumo-contrast-10pct)")
                .set("border-radius", "1em");
        this.setFlexDirection(FlexDirection.COLUMN);
        this.setAlignItems(Alignment.CENTER);
        this.setWidthFull();
    }

    private void createWidgetIcon() {
        Icon calendarIcon = VaadinIcon.CALENDAR_USER.create();
        calendarIcon.setSize("5em");
        calendarIcon.setColor("var(--lumo-contrast-color)");

        this.add(calendarIcon);
    }

    private void createDatePart(int currentYear, int currentMonth, int currentDay) {
        H4 todayDateTitle = new H4("Today is");
        todayDateTitle.getStyle()
                .set("color", "var(--lumo-contrast-color)");

        //Write the day, the suffix and the rest of the date
        H3 todayDateValue = new H3(DAY_FORMATTER.format(
                LocalDate.of(currentYear, currentMonth, currentDay)) + dayNumberSuffix + " of " +
                DATE_TIME_FORMATTER.format(LocalDate.of(currentYear, currentMonth, currentDay))
        );
        todayDateValue.getStyle()
                .set("color", "white")
                .set("font-style", "italic")
                .set("margin", "0");

        this.add(todayDateTitle, todayDateValue);
    }

    private void createNameDayPart(NameDayDTO data) {
        Label nameDayLabel = new Label("Name day has");
        nameDayLabel.getStyle()
                .set("color", "var(--lumo-contrast-color)")
                .set("margin", "1em 0 0 0");

        H3 nameDayValue = new H3(data != null ? data.getName() : "Unknown");
        nameDayValue.getStyle()
                .set("color", "white")
                .set("font-style", "italic")
                .set("margin", "0 0 1em 0");

        this.add(nameDayLabel, nameDayValue);
    }
}