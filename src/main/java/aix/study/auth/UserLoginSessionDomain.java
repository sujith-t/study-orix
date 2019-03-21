
package aix.study.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginSessionDomain implements AuthDomain {

    public static final String LOG_STATUS_ACTIVE = "ACTIVE";
    
    private String username;
    
    private String email;
    
    private String password;
    
    private String ipAddress;
    
    private String userAgent;
    
    private String city;
    
    private String region;    
    
    private String countryCode;
    
    private String location;
    
    private String sid;
    
    private String organization;
    
    private String clientOs;
    
    private String logStatus;

    /**
     * Returns Username
     * 
     * @return String
     */ 
    public String getUsername() {
        return username;
    }

    /**
     * Sets User Name
     * 
     * @param username
     */     
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns IPAddress
     * 
     * @return String
     */    
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Sets ipAddress
     * 
     * @param ipAddress
     */    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Returns User Agent
     * 
     * @return String
     */    
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Sets User Agent
     * 
     * @param userAgent
     */    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Returns City
     * 
     * @return String
     */    
    public String getCity() {
        return city;
    }

    /**
     * Sets City
     * 
     * @param city
     */    
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns Region
     * 
     * @return String
     */    
    public String getRegion() {
        return this.region;
    }

    /**
     * Sets Region
     * 
     * @param region
     */     
    public void setRegion(String region) {
        this.region = region;
    }    
    
    /**
     * Returns Country Code
     * 
     * @return String
     */    
    public String getCountryCode() {
        return this.countryCode;
    }

    /**
     * Sets Country Code
     * 
     * @param code
     */     
    public void setCountryCode(String code) {
        this.countryCode = code;
    }
    
    /**
     * Returns Location Logitude,Latitude
     * 
     * @return String
     */    
    public String getLocation() {
        return this.location;
    }

    /**
     * Sets Country Code
     * 
     * @param loc
     */     
    public void setLocation(String loc) {
        this.location = loc;
    }

    /**
     * Returns Session Id
     * 
     * @return String
     */    
    public String getSid() {
        return this.sid;
    }

    /**
     * Sets Session Id
     * 
     * @param id
     */     
    public void setSid(String id) {
        this.sid = id;
    }

    /**
     * Returns Organization
     * 
     * @return String
     */     
    public String getOrganization() {
        return organization;
    }

    /**
     * Sets Organization
     * 
     * @param organization
     */    
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * Returns ClientOS
     * 
     * @return String
     */    
    public String getClientOs() {
        return clientOs;
    }

    /**
     * Sets ClientOS
     * 
     * @param clientOs
     */ 
    public void setClientOs(String clientOs) {
        this.clientOs = clientOs;
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
     * Returns Logged Status
     * 
     * @return String
     */    
    public String getLoggedStatus() {
        return this.logStatus;
    }

    /**
     * Sets Logged Status
     * 
     * @param status
     */     
    public void setLoggedStatus(String status) {
        this.logStatus = status;
    }    
}