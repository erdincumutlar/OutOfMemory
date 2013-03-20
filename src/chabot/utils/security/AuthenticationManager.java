package chabot.utils.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.WeakHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public abstract class AuthenticationManager {

	private static final String ATTRIBUTE_NAME = "authentication_tokens";

	public abstract Authentication authenticate(String username, String password);

	public synchronized static void save(ServletContext context, HttpSession session, Authentication auth) {
		String shared_context = context.getInitParameter("authenticationContext");
		if (shared_context == null) {
			shared_context = context.getContextPath();
		}

		ServletContext sharedContext = context.getContext(shared_context);

		Map<String, Object> data = (Map<String, Object>) sharedContext.getAttribute(ATTRIBUTE_NAME);

	    // if not yet available, create a new one
	    if (data == null) {
	        data = new WeakHashMap<String, Object>();
	    }

	    if (auth != null) {
	    	data.put(session.getId(), serialize(auth));
	    }
	    else {
	    	data.remove(session.getId());
	    }

	    sharedContext.setAttribute(ATTRIBUTE_NAME, data);
	}

	public static Authentication read(ServletContext context, HttpSession session) {
		String shared_context = context.getInitParameter("authenticationContext");
		if (shared_context == null) {
			shared_context = context.getContextPath();
		}

		ServletContext sharedContext = context.getContext(shared_context);

		Authentication auth = null;

		Map<String, Object> data = (Map<String, Object>) sharedContext.getAttribute(ATTRIBUTE_NAME);

	    if (data != null && data.get(session.getId()) != null) {
	    	byte[] bytes = (byte[]) data.get(session.getId());
	    	auth = deserialize(bytes);
	    }

	    return auth;
	}

	private static byte[] serialize(Authentication auth) {
	    byte[] bytes = null;
		try {
	    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    ObjectOutputStream out = new ObjectOutputStream(bos);
		    out.writeObject(auth);
		    bytes = bos.toByteArray();
	    }
	    catch (Exception e) {
	    	System.err.println(e);
	    }
		return bytes;
	}

	private static Authentication deserialize(byte[] bytes) {
	    Authentication auth = null;

		try {
	    	ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		    ObjectInputStream in = new ObjectInputStream(bis);
		    auth = (Authentication) in.readObject();
	    }
	    catch (Exception e) {
	    	System.err.println(e);
	    }

	    return auth;
	}
}
