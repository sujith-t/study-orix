
package aix.study.orix.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
public class RestConnector {
    
    private static final int TIMEOUTS = 30000;
    
    protected TrustManagerFactory trustManagerFactory;
    protected String host = "";
    
    /**
     * Handles the HTTP <code>GET Calls</code> method.
     *
     * @param url
     * @param headers
     * @return HttpResponse
     * @throws ConnectionException
     */    
    public HttpResponse get(String url, Map<String, String> headers) throws ConnectionException {
        try {
            return this.call(url, "GET", headers, null);
        } catch(IOException | KeyManagementException | NoSuchAlgorithmException e) {
            throw new ConnectionException(e);
        }
    }
    
    /**
     * Handles the HTTP <code>POST Calls</code> method.
     *
     * @param url
     * @param headers
     * @param content
     * @return HttpResponse
     * @throws ConnectionException
     */    
    public HttpResponse post(String url, Map<String, String> headers, byte[] content) throws ConnectionException {
        try {
            return this.call(url, "POST", headers, content);
        } catch(IOException | KeyManagementException | NoSuchAlgorithmException e) {
            throw new ConnectionException(e);
        }        
    }
    
    /**
     * Handles the HTTP <code>PUT Calls</code> method.
     *
     * @param url
     * @param headers
     * @param content
     * @return HttpResponse
     * @throws ConnectionException
     */    
    public HttpResponse put(String url, Map<String, String> headers, byte[] content) throws ConnectionException {
        try {
            return this.call(url, "PUT", headers, content);
        } catch(IOException | KeyManagementException | NoSuchAlgorithmException e) {
            throw new ConnectionException(e);
        }        
    }
    
    /**
     * Handles the HTTP <code>DELETE Calls</code> method.
     *
     * @param url
     * @param headers
     * @param content
     * @return HttpResponse
     * @throws ConnectionException
     */    
    public HttpResponse delete(String url, Map<String, String> headers, byte[] content) throws ConnectionException {
        try {
            return this.call(url, "DELETE", headers, content);
        } catch(IOException | KeyManagementException | NoSuchAlgorithmException e) {
            throw new ConnectionException(e);
        }        
    }    

    /**
     * Handles the HTTP calls
     */    
    private HttpResponse call(String path, String method, Map<String, String> headers, byte[] content) 
            throws MalformedURLException, IOException, NoSuchAlgorithmException, KeyManagementException {
		
        URL url = new URL(this.host + path);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        conn.setReadTimeout(TIMEOUTS);
        conn.setConnectTimeout(TIMEOUTS);
        
        //setting mutual SSL
        if(this.trustManagerFactory != null) {
            SSLContext ctx = SSLContext.getInstance("TLS");       
            ctx.init(null, this.trustManagerFactory.getTrustManagers(), null);
            SSLSocketFactory factory = ctx.getSocketFactory();
            conn.setSSLSocketFactory(factory);
        }
        
        //setting header
        if(headers != null) {
            for(Map.Entry<String, String> entry: headers.entrySet()) {
                conn.addRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        
        //setting body content if available
        if(content != null && content.length > 0) {
            OutputStream outStream = conn.getOutputStream();
            outStream.write(content);
        }
        
        int responseCode = conn.getResponseCode();
        InputStream stream = null;
        
        if(responseCode == HttpsURLConnection.HTTP_OK) {
            stream = conn.getInputStream();
        } else if(HttpsURLConnection.HTTP_INTERNAL_ERROR >= responseCode && HttpsURLConnection.HTTP_BAD_REQUEST <= responseCode) {
            stream = conn.getErrorStream();
        }
        
        HttpResponse response = new HttpResponse(stream, responseCode);
        response.setResponseMessage(conn.getResponseMessage());
        //conn.disconnect();
        
        return response;
    }
}
