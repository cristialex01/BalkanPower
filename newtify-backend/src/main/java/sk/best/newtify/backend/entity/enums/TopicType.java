package sk.best.newtify.backend.entity.enums;

public enum TopicType {

    NEWS("NEWS"),

    GAMING("GAMING"),

    FASHION("FASHION"),

    FINANCE("FINANCE"),

    MOVIE("MOVIE"),

    MUSIC("MUSIC");

    private String value;

    TopicType(String value) {
        this.value = value;
    }

    public static TopicType fromValue(String value) {
        return valueOf(value);
    }


}
