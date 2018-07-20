
package aix.study.res;


/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
public interface ResourceService {
    
    public ResourceDomain getResourceByUrlKey(String key) throws ResourceAppException;
}
