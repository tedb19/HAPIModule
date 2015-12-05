package hapimodule.core.constants;

/**
 *
 * @author Teddy Odhiambo
 */
public enum Sex {

    MALE("MALE"),
    FEMALE("FEMALE"),
    MISSING("MISSING"),
    UNKOWN("UNKNOWN");

    private final String st;

    private Sex(String st) {
        this.st = st;
    }

    public String getValue() {
        return st;
    }

}
