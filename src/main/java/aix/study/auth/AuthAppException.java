
package aix.study.auth;


/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
public class AuthAppException extends Exception {
    
    private String description;
    
	/**
	 * default constructor
	 */
	public AuthAppException() {

	}
    
	/**
	 * constructor
     * @param msg
	 */
	public AuthAppException(String msg) {
        super(msg);
	}    
	
	/**
	 * constructor
     * @param e
	 */
	public AuthAppException(Exception e) {
		super(e);
	}
	
	/**
	 * constructor
     * @param description
     * @param e
	 */
	public AuthAppException(String description, Exception e) {
		super(e);
		this.description = description;
	}
    
	/**
     * Returns Description
     * @return String
	 */
     public String getDescription() {
         return this.description;
     }    
}
