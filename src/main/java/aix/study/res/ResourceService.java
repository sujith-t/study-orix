
package aix.study.res;


/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
public interface ResourceService {
    
    public ResourceDomain getResourceByUrlKey(String key) throws ResourceAppException;
    
    public void addYouTubeContent(YouTubeDomain domain) throws ResourceAppException;
    
    public void updateYouTubeContent(YouTubeDomain domain) throws ResourceAppException;
    
    public YouTubeDomain getYouTubeByUrl(String url) throws ResourceAppException;
    
    public ResourceDomain getResourceById(String id) throws ResourceAppException;
}
