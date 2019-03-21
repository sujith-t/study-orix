
package aix.study.auth;

import aix.study.orix.bean.UserIdentity;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
public interface AuthService {
    
    public void register(UserDomain domain) throws AuthAppException;
    
    public UserIdentity login(UserLoginSessionDomain domain) throws AuthAppException;
    
    public UserDomain getUserByUsername(String username) throws AuthAppException;
    
    public UserDomain getUserByEmail(String email) throws AuthAppException;
}
