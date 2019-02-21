
package aix.study.auth;

import aix.study.orix.util.ConnectionException;
import aix.study.orix.util.DataCrypto;
import javax.ejb.DependsOn;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import aix.study.orix.util.HttpResponse;
import aix.study.orix.util.UtilException;
import aix.study.res.ResourceAppException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
@DependsOn("AuthAppConnector")
@Stateless
@Local(AuthService.class)
public class AuthServiceImpl implements AuthService {

    private AuthAppConnector authConnector;

    public AuthServiceImpl() {
        
    }
    
    @Inject
    public AuthServiceImpl(AuthAppConnector con) {
        this.authConnector = con;
    }
    
    /**
     * Retrieve User By Username
     * @param username
     * @return UserDomain
     * @throws aix.study.auth.AuthAppException
     */    
    @Override
    public UserDomain getUserByUsername(String username) throws AuthAppException {
        
        String url = "/auth?username=";
        try {
            username = DataCrypto.encrypt(username).replace("+", "%2B");
            url = url + username;
        } catch (UtilException ex) {
            throw new AuthAppException(ex.getDescription(), ex);
        }
        
        return (UserDomain) this.fetchOne(url, UserDomain.class);
    }
    
    
    /**
     * Retrieve User By Email
     * @param email
     * @return UserDomain
     * @throws aix.study.auth.AuthAppException
     */    
    @Override
    public UserDomain getUserByEmail(String email) throws AuthAppException {
        
        String url = "/auth?email=" + email;
        return (UserDomain) this.fetchOne(url, UserDomain.class);
    }    
    
    private void throwExceptionIfServerError(HttpResponse response) throws AuthAppException, IOException {
        if(response.getHttpCode() != HttpsURLConnection.HTTP_OK) {
            throw new AuthAppException("HTTP Code:" + response.getHttpCode() + " " + response.getResponseMessage() + " Server Response:" + IOUtils.toString(response.getResponseStream()));
        }        
    }
    
    private AuthDomain fetchOne(String url, Class clazz) throws AuthAppException {
        
        try {
            Map<String, String> map = new HashMap();
            map.put("Content-Type", "application/json");            

            HttpResponse response = this.authConnector.get(url, map);
            this.throwExceptionIfServerError(response);
            String jsonStr = IOUtils.toString(response.getResponseStream());
            
            ObjectMapper mapper = new ObjectMapper();
            if(clazz.equals(UserDomain.class)) {
                return (UserDomain)mapper.readValue(jsonStr, UserDomain.class);
            }
            
            return null;
            
        } catch (ConnectionException | IOException ex) {
           throw new AuthAppException("Unable to fetch from auth URL " + url + ": ", ex);
        }    
    }

    /**
     * Register a user
     * @param domain
     * @throws aix.study.auth.AuthAppException
     */     
    @Override
    public void register(UserDomain domain) throws AuthAppException {
        ObjectWriter objWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        HttpResponse response;
        String url = "/auth";
        
        try {
            Map<String, String> map = new HashMap();
            map.put("Content-Type", "application/json");            
            String jsonStr = objWriter.writeValueAsString(domain);
            
            response = this.authConnector.post(url, map, jsonStr.getBytes());
            this.throwExceptionIfServerError(response);
            
            JSONObject jsonResp = new JSONObject(IOUtils.toString(response.getResponseStream()));
            String code = jsonResp.getString("code");
            
            if(!code.equals("AUTH_NO_ERROR")) {
                throw new AuthAppException("Auth Application error: " + IOUtils.toString(response.getResponseStream()));
            }
        } catch (ConnectionException | IOException ex) {
           throw new AuthAppException("Registering user failed: ", ex);
        }
    }

    /**
     * Login a user
     * @param domain
     * @throws aix.study.auth.AuthAppException
     */     
    @Override    
    public void login(UserLoginSessionDomain domain) throws AuthAppException {
    
    }
}