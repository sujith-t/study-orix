
package aix.study.orix.managedbean;

import aix.study.res.ResourceAppException;
import aix.study.res.ResourceService;
import aix.study.res.YouTubeDomain;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
@Named(value = "utcontentBean")
@RequestScoped
public class YouTubeContentBean extends ContentBean {

    private String url;
    private String presenter;
    private final ResourceService resourceService;
    private String contentMode;
    private static final Logger LOGGER = Logger.getLogger(YouTubeContentBean.class.getName());
    
    private static final String CREATE = "cr_ut";
    private static final String EDIT = "ed_ut";    
    
    /**
     * Sets Id
     * 
     * @param id
     */
    @Override
    public void setId(String id) {
        if(id == null || id.trim().equals("")) {
            this.id = null;
            return;
        }
        
        id = id.trim();
        this.id = id;
        YouTubeDomain domain = null;
        
        try {
            domain = (YouTubeDomain)this.resourceService.getResourceById(id);
        } catch (ResourceAppException ex) {
            LOGGER.log(Level.SEVERE, "ResourceService #getResourceById failure (param:id " + id + ")" , ex);
        }
        
        if(domain == null) {
            return;
        }
        
        this.name = domain.getName();
        this.description = domain.getDescription();
        this.presenter = domain.getPresenter();
        this.url = domain.getUrl();
        this.tags = String.join(",", domain.getTags());
    }
    
    /**
     * Sets Content Mode
     * 
     * @param mode
     */
    public void setContentMode(String mode) {
        this.contentMode = mode;
    }    
    
    /**
     * Validates whether the content is available if it's update
     * 
     * @param context
     * @param comp
     * @param value
     * @throws aix.study.res.ResourceAppException
     */
    public void validateContentOnUpdate(FacesContext context, UIComponent comp, Object value) throws ResourceAppException {
        String contentId = (String) value;
        if(contentId == null || contentId.trim().equals("")) {
            return;
        }
        
        contentId = contentId.trim();
        YouTubeDomain ytr = (YouTubeDomain)this.resourceService.getResourceById(contentId);
        if(ytr == null || ytr.getId().equals("")) {
            ((UIInput) comp).setValid(false);

            FacesMessage message = new FacesMessage("You are trying to update a YouTube Content which is not available");
            context.addMessage(comp.getClientId(context), message);
        }        
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
        this.url = url.trim();
    }
    
    /**
     * Validates whether URL is Already Available 
     * https://www.journaldev.com/7035/jsf-validation-example-tutorial-validator-tag-custom-validator
     * 
     * @param context
     * @param comp
     * @param value
     * @throws aix.study.res.ResourceAppException
     */
    public void validateUrlAvailable(FacesContext context, UIComponent comp, Object value) throws ResourceAppException {
        String utUrl = (String) value;
        YouTubeDomain ytr = this.resourceService.getYouTubeByUrl(utUrl);
        
        //validate on adding
        if(this.contentMode.equals(CREATE) && ytr != null && !ytr.getId().equals("")) {
            ((UIInput) comp).setValid(false);
            FacesMessage message = new FacesMessage("YouTube Content is already available, can't add again");
            context.addMessage(comp.getClientId(context), message);
        }
        
        //validate on updating
        if(this.contentMode.equals(EDIT) && ytr != null && !ytr.getId().equals(this.id)) {
            ((UIInput) comp).setValid(false);
            FacesMessage message = new FacesMessage("YouTube Content URL you are trying to modify is already available");
            context.addMessage(comp.getClientId(context), message);
        }
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
        this.presenter = name.trim();
    }    
    
    /**
     * Creates a new instance of YouTubeContentBean
     * @param resourceService
     */
    @Inject
    public YouTubeContentBean(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
    
    public void action() throws IOException {
        
        YouTubeDomain domain = new YouTubeDomain();
        if(this.id != null) {
            domain.setId(this.id);
        }
        
        domain.setAuthor("pixle01");
        domain.setDescription(this.description);
        domain.setName(this.name);
        domain.setPresenter(this.presenter);
        domain.setUrl(this.url);
        
        if(this.tags != null && !this.tags.equals("")) {
            String[] contentTags = this.tags.split(",");
            Set<String> tagSet = new HashSet<>();
            for (String contentTag : contentTags) {
                tagSet.add(contentTag.trim());
            }
            
            domain.setTags(tagSet);
        }

        try {
            
            if(this.id == null) {
                this.resourceService.addYouTubeContent(domain);
            } else {
                this.resourceService.updateYouTubeContent(domain);
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect("../content");
        } catch (ResourceAppException e) {
            LOGGER.log(Level.SEVERE, "ResourceService #addYouTubeContent failure", e);
        }
    }     
    
}
