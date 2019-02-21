
package aix.study.orix.managedbean;

import aix.study.auth.AuthAppException;
import aix.study.auth.AuthService;
import aix.study.auth.UserDomain;
import aix.study.orix.util.ConnectionException;
import aix.study.orix.util.DataCrypto;
import aix.study.orix.util.RemoteUserRequestInfo;
import aix.study.orix.util.UtilException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
@Named(value = "registerBean")
@RequestScoped
public class RegisterBean {
    
    private RemoteUserRequestInfo info;
    
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String repassword;
    
    private static final Logger LOGGER = Logger.getLogger(RegisterBean.class.getName());
 
    private final AuthService service;
    
    /**
     * Creates a new instance
     * @param service
     */
    @Inject
    public RegisterBean(AuthService service) {
        this.service = service;
    }
    
    /**
     * Sets Request
     * @param request
     * @throws ConnectionException
     */
    public void setRequest(HttpServletRequest request) throws ConnectionException {
        this.info = new RemoteUserRequestInfo(request);
    }
    
    /**
     * Returns User Name
     * 
     * @return String
     */    
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets User Name
     * 
     * @param name
     */     
    public void setUsername(String name) {
        this.username = name;
    }

    /**
     * Returns First Name
     * 
     * @return String
     */    
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets First Name
     * 
     * @param name
     */     
    public void setFirstName(String name) {
        this.firstName = name;
    }

    /**
     * Returns Last Name
     * 
     * @return String
     */    
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Sets Last Name
     * 
     * @param name
     */     
    public void setLastName(String name) {
        this.lastName = name;
    }

    /**
     * Returns Email
     * 
     * @return String
     */    
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets Email
     * 
     * @param email
     */     
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns Password
     * 
     * @return String
     */    
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets Password
     * 
     * @param password
     */     
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Returns Re-entered Password
     * 
     * @return String
     */    
    public String getRePassword() {
        return this.repassword;
    }

    /**
     * Sets Re-entered Password
     * 
     * @param password
     */     
    public void setRePassword(String password) {
        this.repassword = password;
    } 
    
    /**
     * Validates Username
     * 
     * @param context
     * @param comp
     * @param value
     * @throws aix.study.auth.AuthAppException
     */
    public void validateUsername(FacesContext context, UIComponent comp, Object value) throws AuthAppException {
        String uname = (String) value;
        if(uname == null || uname.trim().equals("")) {
            return;
        }
        
        uname = uname.trim();
        UserDomain domain = this.service.getUserByUsername(uname);

        if(domain != null && !domain.getId().equals("")) {
            ((UIInput) comp).setValid(false);
            FacesMessage message = new FacesMessage("The username is already taken, choose another");
            context.addMessage(comp.getClientId(context), message);
        }      
    }
    
    /**
     * Validates Email
     * 
     * @param context
     * @param comp
     * @param value
     * @throws aix.study.auth.AuthAppException
     */
    public void validateEmail(FacesContext context, UIComponent comp, Object value) throws AuthAppException {
        String dataemail = (String) value;
        if(dataemail == null || dataemail.trim().equals("")) {
            return;
        }
        
        dataemail = dataemail.trim();
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        if(!pattern.matcher(dataemail).matches()) {
            ((UIInput) comp).setValid(false);
            FacesMessage message = new FacesMessage("Invalid email, please enter a proper email");
            context.addMessage(comp.getClientId(context), message);            
            return;
        }
        
        UserDomain domain = this.service.getUserByEmail(dataemail);

        if(domain != null && !domain.getId().equals("")) {
            ((UIInput) comp).setValid(false);
            FacesMessage message = new FacesMessage("The email is already taken, choose another");
            context.addMessage(comp.getClientId(context), message);
        }      
    }

    /**
     * Validates Password
     * 
     * @param context
     * @param comp
     * @param value
     * @throws aix.study.auth.AuthAppException
     */
    public void validatePassword(FacesContext context, UIComponent comp, Object value) throws AuthAppException {
        String passwd = (String) value;

        // Obtain the component and submitted value of the confirm password component.
        UIInput confirmComponent = (UIInput) comp.getAttributes().get("confirm");
        String confirm = (String) confirmComponent.getSubmittedValue();

        if(!passwd.equals(confirm)) {
            ((UIInput) comp).setValid(false);
            FacesMessage message = new FacesMessage("The password re-entered is not the same as the password");
            context.addMessage(comp.getClientId(context), message);
        }      
    }     

    /**
     * Actions to be performed on successful post
     */    
    public void action() {
        
        UserDomain user = new UserDomain();
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        
        try {
            user.setUsername(DataCrypto.encrypt(this.username));
            user.setPassword(DataCrypto.encrypt(this.password));
            this.service.register(user);
        } catch (UtilException ex) {
            LOGGER.log(Level.SEVERE, ex.getDescription(), ex);
        } catch(AuthAppException ex) {
            LOGGER.log(Level.SEVERE, ex.getDescription(), ex);
        }
        
    }
}