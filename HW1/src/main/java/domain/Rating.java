package domain;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
public enum Rating {
    HIGH("high"), MID("mid"), LOW("low");

    private String value;

    Rating(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
