
package aix.study.auth;

import aix.study.orix.util.Config;
import aix.study.orix.util.ConnectionException;
import aix.study.orix.util.RestConnector;
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
 * <!In God We Trust>
 */
@Stateless
public class AuthAppConnector extends RestConnector {

    /**
     * Constructor
     * 
     * @throws aix.study.orix.util.ConnectionException
     */    
    public AuthAppConnector() throws ConnectionException {
        
        this.host = Config.get("auth.host");
        String certFile = Config.get("auth.server.cert");

        try {

            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            InputStream is = new FileInputStream(certFile);
            Certificate certificate = certFactory.generateCertificate(is);

            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(null);
            keyStore.setCertificateEntry("auth_server", certificate);
            
            this.trustManagerFactory = TrustManagerFactory.getInstance("X.509");
            this.trustManagerFactory.init(keyStore);
            
        } catch(CertificateException | IOException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new ConnectionException("Auth SSL certificat error: ", e);
        }     
    }    
}
