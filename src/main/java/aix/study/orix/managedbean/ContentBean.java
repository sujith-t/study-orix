
package aix.study.orix.managedbean;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
public abstract class ContentBean {
    
    protected String id;
    protected String name;
    protected String description;
    protected String tags;
    
    /**
     * Returns Id
     * 
     * @return String
     */    
    public String getId() {
        return id;
    }

    /**
     * Sets Id
     * 
     * @param id
     */    
    public void setId(String id) {
        this.id = id.trim();
    }
    
    /**
     * Returns Name
     * 
     * @return String
     */    
    public String getName() {
        return this.name;
    }

    /**
     * Sets Name
     * 
     * @param name
     */     
    public void setName(String name) {
        this.name = name.trim();
    }

    /**
     * Returns Description
     * 
     * @return String
     */    
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets Name
     * 
     * @param desc
     */     
    public void setDescription(String desc) {
        this.description = desc.trim();
    }
    
    /** 
     * Return Tags
     * 
     * @return String
     */    
    public String getTags() {
        return this.tags;
    }
    
    /**
     * Set Tags
     * 
     * @param tags
     */     
    public void setTags(String tags) {
        this.tags = tags.trim();
    }    
}