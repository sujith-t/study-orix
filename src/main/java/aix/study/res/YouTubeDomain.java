
package aix.study.res;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
public class YouTubeDomain extends ResourceDomain {
    
    private String url;
    private String presenter;
    
    public YouTubeDomain() {
        this.resourceType = "UTUBE";
    }
    
    /**
     * Returns URL
     * 
     * @return String
     */    
    public String getUrl() {
        return this.url;
    }

    /**
     * Sets URL
     * 
     * @param url
     */     
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * Returns Presenter Name
     * 
     * @return String
     */    
    public String getPresenter() {
        return this.presenter;
    }

    /**
     * Sets URL
     * 
     * @param name
     */     
    public void setPresenter(String name) {
        this.presenter = name;
    }
}