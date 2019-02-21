
package aix.study.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDomain implements AuthDomain {

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Date createdDate;
    private String password;
    
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
        this.id = id;
    }
        
    /**
     * Returns UserDomain Name
     * 
     * @return String
     */    
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets UserDomain Name
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
     * Returns Created Date
     * 
     * @return Date
     */    
    public Date getCreatedDate() {    
        return this.createdDate;
    }

    /**
     * Sets Created Date
     * 
     * @param date
     */     
    public void setCreatedDate(Date date) {
        this.createdDate = date;
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
}
