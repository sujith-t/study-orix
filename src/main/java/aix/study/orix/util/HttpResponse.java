package aix.study.orix.util;

import java.io.InputStream;

/**
 * @author Sujith T
 *
 * <!In God We Trust>
 */
public class HttpResponse {

    private final InputStream responseStream;
    private final int httpCode;
    private String responseMessage;

    /**
     * Constructor.
     *
     * @param responseStream
     * @param httpCode
     */    
    public HttpResponse(InputStream responseStream, int httpCode) {
        this.responseStream = responseStream;
        this.httpCode = httpCode;
    }

    /**
     * Returns response stream
     *
     * @return InputStream
     */     
    public InputStream getResponseStream() {
        return responseStream;
    }
    
    /**
     * Returns http code
     *
     * @return int
     */    
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * Set Response Message
     * 
     * @param responseMessage
     */     
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    /**
     * Returns http code
     *
     * @return String
     */    
    public String getResponseMessage() {
        return responseMessage;
    }
}