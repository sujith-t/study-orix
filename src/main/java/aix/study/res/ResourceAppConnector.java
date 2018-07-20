
package aix.study.res;

import aix.study.orix.Config;
import aix.study.orix.ConnectionException;
import aix.study.orix.RestConnector;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.ejb.Stateless;
import javax.net.ssl.TrustManagerFactory;


/**
 * @author Sujith T
 * 
 * reference : http://blog.palominolabs.com/2011/10/18/java-2-way-tlsssl-client-certificates-and-pkcs12-vs-jks-keystores/index.html
 * 
 * <!In God We Trust>
 */
@Stateless
public class ResourceAppConnector extends RestConnector {
    
    /**
     * Constructor
     * 
     * @throws aix.study.orix.ConnectionException
     */    
    public ResourceAppConnector() throws ConnectionException {
        
        this.host = Config.get("resource.host");
        String certFile = Config.get("resource.server.cert");

        try {

            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            InputStream is = new FileInputStream(certFile);
            Certificate certificate = certFactory.generateCertificate(is);

            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(null);
            keyStore.setCertificateEntry("MyServer", certificate);
            
            this.trustManagerFactory = TrustManagerFactory.getInstance("X.509");
            this.trustManagerFactory.init(keyStore);
            
        } catch(CertificateException | IOException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new ConnectionException(e);
        }     
    }
}
