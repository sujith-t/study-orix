
package aix.study.orix.bean;

import aix.study.auth.AuthAppConnector;
import aix.study.auth.UserLoginSessionDomain;
import aix.study.orix.exception.ConnectionException;
import aix.study.orix.exception.UserIdentityException;
import aix.study.orix.util.DataCrypto;
import aix.study.orix.exception.UtilException;
import aix.study.orix.util.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.ejb.Stateful;
import org.apache.commons.io.IOUtils;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
@Stateful
public class UserIdentity {

    private String authToken;
    private String logSessionId;
    private String username;
    private String location;

    /**
     * constructor
     */    
    public UserIdentity() {
        
    }

    /**
     * constructor
     * 
     * @param token
     * @throws UserIdentityException
     */     
    public UserIdentity(String token) throws UserIdentityException {
        this.authToken = token;
        
        try {
            token = DataCrypto.decrypt(token);
            String[] tokenParts = token.split(":::");
            
            //throw a valid exception if missing
            if(tokenParts.length != 3) {
                throw new UserIdentityException("Constructing UserIdentity Failed: " + this.authToken + "(token)");
            }
            
            this.logSessionId = tokenParts[0];
            this.username = tokenParts[1];
            this.location = tokenParts[2];
        } catch (UtilException ex) {
            throw new UserIdentityException("Constructing UserIdentity Failed: ", ex);
        }
    }

    /**
     * Returns Authentication Token
     * 
     * @return String
     */    
    public String getAuthToken() {
        return this.authToken;
    }

    /**
     * Returns Session Id
     * 
     * @return String
     */    
    public String getLogSessionId() {
        return this.logSessionId;
    }

    /**
     * Returns Username
     * 
     * @return String
     */    
    public String getUsername() {
        return this.username;
    }
    
    /**
     * Returns Whether The Token Returned from Auth is Valid 
     * 
     * @return boolean
     */    
    public boolean isValidToken() {
        if(this.logSessionId == null || this.logSessionId.isEmpty()) {
            return false;
        }
        
        try {
            AuthAppConnector con = new AuthAppConnector();
            String url = "/auth/session?id=" + this.logSessionId;
            Map<String, String> map = new HashMap<>();
            map.put("Content-Type", "application/json");            

            HttpResponse response = con.get(url, map);
            String jsonStr = IOUtils.toString(response.getResponseStream());
            ObjectMapper mapper = new ObjectMapper();
           
            UserLoginSessionDomain session = mapper.readValue(jsonStr, UserLoginSessionDomain.class);
            String decryptedUserName = DataCrypto.decrypt(session.getUsername());
            
            if(!this.username.equals(decryptedUserName) 
                    || !session.getLoggedStatus().equals(UserLoginSessionDomain.LOG_STATUS_ACTIVE) || !this.location.equals(session.getLocation())) {
                return false;
            }
            
        } catch (ConnectionException | IOException | UtilException ex) {
            return false;
        }

        return true;
    }    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.authToken);
        hash = 29 * hash + Objects.hashCode(this.logSessionId);
        hash = 29 * hash + Objects.hashCode(this.username);
        hash = 29 * hash + Objects.hashCode(this.location);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserIdentity other = (UserIdentity) obj;
        if (!Objects.equals(this.authToken, other.authToken)) {
            return false;
        }
        if (!Objects.equals(this.logSessionId, other.logSessionId)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        return true;
    }
}
