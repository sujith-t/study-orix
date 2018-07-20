
package aix.study.orix;

/**
 * @author Sujith T
 *
 * <!In God We Trust>
 */
public class ConnectionException extends Exception {

    private String description;
    
	/**
	 * default constructor
	 */
	public ConnectionException() {
		
	}
	
	/**
	 * constructor
     * @param e
	 */
	public ConnectionException(Exception e) {
		super(e);
	}
	
	/**
	 * constructor
     * @param description
     * @param e
	 */
	public ConnectionException(String description, Exception e) {
		super(e);
		this.description = description;
	}    
}
