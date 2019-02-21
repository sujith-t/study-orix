
package aix.study.orix.util;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
public class RemoteUserRequestInfo {
    
    private final HttpServletRequest request;
    
    private String remoteIp;
    private String city;
    private String region;
    private String country;
    private String location;
    private String postal;
    private String organization;

    /**
     * Constructor
     * @throws aix.study.orix.util.ConnectionException
     */    
    public RemoteUserRequestInfo() throws ConnectionException {
        this.request = (HttpServletRequest)FacesContext.getCurrentInstance()
                .getExternalContext().getRequest(); 
        
        this.readRemoteData();
    }
    
    public RemoteUserRequestInfo(HttpServletRequest request) throws ConnectionException {
        this.request = request;
        this.readRemoteData();
    }
    
    private void readRemoteData() throws ConnectionException {
        String[] ipHeaders = { 
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR" };
        
        for (String header : ipHeaders) {
            String ip = this.request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                this.remoteIp = ip;
                break;
            }
        }
        
        if(this.remoteIp == null) {
            this.remoteIp = this.request.getRemoteAddr();
        }

        RestConnector con = new RestConnector();
        String url = "https://ipinfo.io/";
        HttpResponse response;
        JSONObject json;        
        
        //this is for testing purpose
        if(!this.remoteIp.equals("127.0.0.1")) {
            url = url + this.remoteIp;
        }
        
        try {
            response = con.get(url, null);
            json = new JSONObject(IOUtils.toString(response.getResponseStream()));
        } catch (IOException ex) {
            throw new ConnectionException("Connecting to ipinfo failed through URL: " + url, ex);
        }
        
        if(response.getHttpCode() != HttpsURLConnection.HTTP_OK) {
            throw new ConnectionException("HTTP Code:" + response.getHttpCode() + " " + response.getResponseMessage() + " Server Response:" + json.toString());
        } 
        
        this.remoteIp = (json.has("ip")) ? json.getString("ip") : this.remoteIp;
        this.city = (json.has("city")) ? json.getString("city") : null;
        this.region = (json.has("region")) ? json.getString("region") : null;
        this.country = (json.has("country")) ? json.getString("country") : null;
        this.location = (json.has("loc")) ? json.getString("loc") : null;
        this.postal = (json.has("postal")) ? json.getString("postal") : null;
        this.organization = (json.has("org")) ? json.getString("org") : null;
    }

    /**
     * Returns remote ip address
     * @return String
     */    
    public String getRemoteIpAddr() {
        return this.remoteIp;
    }
    
    /**
     * Returns City
     * @return String
     */    
    public String getCity() {
        return this.city;
    }
    
    /**
     * Returns Region
     * @return String
     */    
    public String getRegion() {
        return this.region;
    }
    
    /**
     * Returns Country
     * @return String
     */    
    public String getCountry() {
        return this.country;
    }

    /**
     * Returns Location
     * @return String
     */    
    public String getLocation() {
        return this.location;
    }
    
    /**
     * Returns Postal
     * @return String
     */    
    public String getPostal() {
        return this.postal;
    }
    
    /**
     * Returns Network Provider
     * @return String
     */    
    public String getOrganization() {
        return this.organization;
    }    
}