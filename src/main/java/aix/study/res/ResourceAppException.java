
package aix.study.res;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
public class ResourceAppException extends Exception {
    
    private String description;
    
	/**
	 * default constructor
	 */
	public ResourceAppException() {

	}
    
	/**
	 * constructor
     * @param msg
	 */
	public ResourceAppException(String msg) {
        super(msg);
	}    
	
	/**
	 * constructor
     * @param e
	 */
	public ResourceAppException(Exception e) {
		super(e);
	}
	
	/**
	 * constructor
     * @param description
     * @param e
	 */
	public ResourceAppException(String description, Exception e) {
		super(e);
		this.description = description;
	}       
}
