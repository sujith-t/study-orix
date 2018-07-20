package aix.study.res;

import aix.study.orix.ConnectionException;
import aix.study.orix.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.DependsOn;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.JAXBException;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

/**
 * @author Sujith T
 *
 * <!In God We Trust>
 */
@DependsOn("ResourceAppConnector")
@Local(ResourceService.class)
@Stateless
public class ResourceServiceImpl implements ResourceService {

    private ResourceAppConnector resAppConnector;

    /**
     * Constructor
     */     
    protected ResourceServiceImpl() {
        
    }

    /**
     * Injection Constructor
     * 
     * @param connector
     */    
    @Inject
    public ResourceServiceImpl(ResourceAppConnector connector) {
        this.resAppConnector = connector;
    }

    /**
     * Return Resource By UrlKey
     * 
     * @param key
     * @return ResourceDomain
     * @throws aix.study.res.ResourceAppException
     */     
    @Override
    public ResourceDomain getResourceByUrlKey(String key) throws ResourceAppException {
        String url = "/resource?key=" + key;

        try {
            Map<String, String> map = new HashMap();
            map.put("Content-Type", "application/json");
            
            HttpResponse response = this.resAppConnector.get(url, map);
            this.throwExceptionIfServerError(response);
            String jsonStr = IOUtils.toString(response.getResponseStream());
            
            return jsonToObject(jsonStr);
            
        } catch (ConnectionException | IOException | JAXBException ex) {
            throw new ResourceAppException(ex);
        }
    }

    /**
     * Constructs Domain Object
     * 
     * @param strJson
     * @return ResourceDomain
     */    
    private ResourceDomain jsonToObject(String strJson) throws JAXBException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        ResourceDomain domain = (ResourceDomain)mapper.readValue(strJson, ResourceDomain.class);
        
        if(domain != null && domain.getResourceType().equalsIgnoreCase("UTUBE")) {
            domain = (YouTubeDomain)mapper.readValue(strJson, YouTubeDomain.class);
        }
        
        return domain;
    }
    
    private void throwExceptionIfServerError(HttpResponse response) throws ResourceAppException, IOException {
        if(response.getHttpCode() != HttpsURLConnection.HTTP_OK) {
            throw new ResourceAppException("HTTP Code:" + response.getHttpCode() + " " + response.getResponseMessage() + " Server Response:" + IOUtils.toString(response.getResponseStream()));
        }        
    }
}
