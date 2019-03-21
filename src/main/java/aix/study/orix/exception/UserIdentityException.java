
package aix.study.orix.exception;

/**
 * @author Sujith T
 *
 * <!In God We Trust>
 */
public class UserIdentityException extends Exception {
    private String description;

    /**
     * default constructor
     */
    public UserIdentityException() {

    }

    /**
     * default constructor
     *
     * @param description
     */
    public UserIdentityException(String description) {
        this.description = description;
    }

    /**
     * constructor
     *
     * @param e
     */
    public UserIdentityException(Exception e) {
        super(e);
    }

    /**
     * constructor
     *
     * @param description
     * @param e
     */
    public UserIdentityException(String description, Exception e) {
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
