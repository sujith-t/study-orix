
package aix.study.orix.util;

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
	 * default constructor
     * @param description
	 */
	public ConnectionException(String description) {
		this.description = description;
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
    
	/**
     * Returns Description
     * @return String
	 */
     public String getDescription() {
         return this.description;
     }
}
