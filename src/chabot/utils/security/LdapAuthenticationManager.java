package chabot.utils.security;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LdapAuthenticationManager extends AuthenticationManager {

	private String connectionURL;
	private String connectionUser;
	private String connectionPassword;
	private String userSearchBase;
	private String userSearchPattern;
	private String userRoleName;

	@Override
	public synchronized Authentication authenticate(String username, String password) {
		if (username == null || username.equals("")) {
			return null;
		}
		if (password == null || password.equals("")) {
			return null;
		}

		Hashtable<String, String> env = new Hashtable<String, String>(0);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.PROVIDER_URL, connectionURL);
        env.put(Context.SECURITY_PRINCIPAL, connectionUser);
        env.put(Context.SECURITY_CREDENTIALS, connectionPassword);

		try {
			// connect to LDAP
			DirContext context = new InitialDirContext(env);

			// search for user
			SearchControls controls = new SearchControls();
			controls.setReturningAttributes(new String[] { "cn", userRoleName });
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			MessageFormat format = new MessageFormat(userSearchPattern);
			String filter = format.format(new String[] { username });

			NamingEnumeration<SearchResult> results = context.search(userSearchBase, filter, controls);

			// if (!found) return null
			if (results == null || !results.hasMore()) {
				return null;
			}
			SearchResult result = results.next();

			// make sure only 1 result available
			if (results.hasMore()) {
				return null;
			}

			// save user's DN
			String dn = result.getNameInNamespace();

			// bind as user
			context.addToEnvironment(Context.SECURITY_PRINCIPAL, dn);
			context.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
			context.getAttributes("", null);

			// authentication successful
			Authentication auth = new Authentication();
			auth.setUsername(username);

			// restore connection
			context.addToEnvironment(Context.SECURITY_PRINCIPAL, connectionUser);
			context.addToEnvironment(Context.SECURITY_CREDENTIALS, connectionPassword);

			// get fullname
			String fullname = (String) result.getAttributes().get("cn").get();
			auth.setFullname(fullname);

			// iterate over roles
			NamingEnumeration values = result.getAttributes().get(userRoleName).getAll();
			while (values.hasMore()) {
				String role = (String) values.next();
				auth.addRoles(getRoles(context, role));
			}

			return auth;
		}
		catch (AuthenticationException e) {
			// could not authenticate
			System.out.println("Could not authenticate");
			e.printStackTrace();
			return null;
		}
		catch (NamingException e) {
			// could not connect
			System.out.println("Error communicating with LDAP server");
			e.printStackTrace();
			return null;
		}
	}

	public void setConnectionURL(String connectionURL) {
		this.connectionURL = connectionURL;
	}

	public void setConnectionUser(String connectionUser) {
		this.connectionUser = connectionUser;
	}

	public void setConnectionPassword(String connectionPassword) {
		this.connectionPassword = connectionPassword;
	}

	public void setUserSearchBase(String userSearchBase) {
		this.userSearchBase = userSearchBase;
	}

	public void setUserSearchPattern(String userSearchPattern) {
		this.userSearchPattern = userSearchPattern;
	}

	public void setUserRoleName(String userRolePattern) {
		this.userRoleName = userRolePattern;
	}

	private ArrayList<String> getRoles(DirContext context, String dn) throws NamingException {
		return getRoles(context, dn, 1);
	}

	private ArrayList<String> getRoles(DirContext context, String dn, int nested) throws NamingException {
		ArrayList<String> roles = new ArrayList<String>(0);

		Attributes attrs = context.getAttributes(dn, new String[] { "cn", userRoleName });

		Attribute attr = attrs.get("cn");
		if (attr == null || attr.size() == 0) {
			return roles;
		}

		String roleName = (String) attr.get();
		roles.add(roleName);

		attr = attrs.get(userRoleName);
		if (attr == null || attr.size() == 0) {
			return roles;
		}

		NamingEnumeration values = attr.getAll();
		while (values.hasMore()) {
			String role = (String) values.next();
			if (nested < 10) roles.addAll(getRoles(context, role, nested+1));
		}

		return roles;
	}
}
