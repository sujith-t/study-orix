
package aix.study.auth;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
public interface AuthService {
    
    public void register(UserDomain domain) throws AuthAppException;
    
    public void login(UserLoginSessionDomain domain) throws AuthAppException;
    
    public UserDomain getUserByUsername(String username) throws AuthAppException;
    
    public UserDomain getUserByEmail(String email) throws AuthAppException;
}
