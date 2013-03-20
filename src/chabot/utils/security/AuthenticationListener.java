package chabot.utils.security;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class AuthenticationListener implements HttpSessionListener {

	/*
	 * Clean up AuthenticationManager after each session expires
	 */

	public void sessionCreated(HttpSessionEvent se) {
		// do nothing
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		if (session != null) {
			ServletContext context = session.getServletContext();
			if(context != null) AuthenticationManager.save(context, session, null);
		}
	}

}