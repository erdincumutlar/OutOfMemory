package chabot.utils.security;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class LocalAuthenticationFactory implements ObjectFactory {

	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws NamingException {
		LocalAuthenticationManager auth = new LocalAuthenticationManager();
		
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
			
			if (key.equals("localUsername")) {
				auth.setLocalUsername(value);
			}
			else if (key.equals("localFullname")) {
				auth.setLocalFullname(value);
			}
			else if (key.equals("localPassword")) {
				auth.setLocalPassword(value);
			}
			else if (key.equals("localRoles")) {
				String[] roles = value.split(",");
				for (String role : roles) {
					auth.addLocalRole(role);
				}
			}
		}
		
		return auth;
	}
}
