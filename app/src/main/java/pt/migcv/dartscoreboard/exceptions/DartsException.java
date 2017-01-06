package pt.migcv.dartscoreboard.exceptions;

/**
 * Created by Miguel on 06/01/2017.
 */

public abstract class DartsException extends RuntimeException {
    protected String element;

    public DartsException(String element) {
        this.element = element;
    }

    public abstract String getMessage();

    public String getElement() {
        return element;
    }
}
