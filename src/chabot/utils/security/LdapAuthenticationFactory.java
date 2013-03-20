package chabot.utils.security;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class LdapAuthenticationFactory implements ObjectFactory {

	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws NamingException {
		LdapAuthenticationManager auth = new LdapAuthenticationManager();
		
		Reference ref = (Reference) obj;
		Enumeration<RefAddr> attrs = ref.getAll();
		while (attrs.hasMoreElements()) {
			RefAddr attr = attrs.nextElement();
			String key = attr.getType();
			String value = (String) attr.getContent();
			
			if (key == null || key.equals("")) {
				continue;
			}
			if (value == null || value.equals("")) {
				continue;
			}
			
			if (key.equals("connectionURL")) {
				auth.setConnectionURL(value);
			}
			else if (key.equals("connectionUser")) {
				auth.setConnectionUser(value);
			}
			else if (key.equals("connectionPassword")) {
				auth.setConnectionPassword(value);
			}
			else if (key.equals("userSearchBase")) {
				auth.setUserSearchBase(value);
			}
			else if (key.equals("userSearchPattern")) {
				auth.setUserSearchPattern(value);
			}
			else if (key.equals("userRoleName")) {
				auth.setUserRoleName(value);
			}
		}
		
		return auth;
	}
}
