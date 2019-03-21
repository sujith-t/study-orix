package aix.study.orix.exception;

/**
 * @author Sujith T
 *
 * <!In God We Trust>
 */
public class UtilException extends Exception {

    private String description;

    /**
     * default constructor
     */
    public UtilException() {

    }

    /**
     * default constructor
     *
     * @param description
     */
    public UtilException(String description) {
        this.description = description;
    }

    /**
     * constructor
     *
     * @param e
     */
    public UtilException(Exception e) {
        super(e);
    }

    /**
     * constructor
     *
     * @param description
     * @param e
     */
    public UtilException(String description, Exception e) {
        super(e);
        this.description = description;
    }

    /**
     * Returns Description
     *
     * @return String
     */
    public String getDescription() {
        return this.description;
    }
}
