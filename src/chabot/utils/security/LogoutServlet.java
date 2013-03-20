package chabot.utils.security;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String defaultPage = "index.jsp";
	private String authKey  = "authentication";
	private String cookieDomain = null;
	private String cookiePath = null;
	private boolean cookieSecure = false;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		if(config.getInitParameter("defaultPage") != null) {
			defaultPage = config.getInitParameter("defaultPage");
		}

		if(config.getInitParameter("authKey") != null) {
			authKey = config.getInitParameter("authKey");
		}

		cookieDomain = config.getInitParameter("cookieDomain");
		cookiePath = config.getInitParameter("cookiePath");
		cookieSecure = Boolean.parseBoolean(config.getInitParameter("cookieSecure"));
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// invalidate session
		HttpSession session = request.getSession(false);

		String retURL = request.getParameter("retURL");
		if (retURL == null || retURL.equals("")) {
			retURL = (String) session.getAttribute("retURL");
		}
		if (retURL == null || retURL.equals("")) {
			retURL = defaultPage;
		}

		if (session != null) {
			session.invalidate();
			AuthenticationManager.save(getServletContext(), session, null);			
		}

		// clear cookie
		Cookie cookie = new Cookie(authKey, null);
		if(cookieDomain != null) cookie.setDomain(cookieDomain);
		if(cookiePath != null) cookie.setPath(cookiePath);
		cookie.setSecure(cookieSecure);
		cookie.setMaxAge(0);
		response.addCookie(cookie);

		// redirect browser to home page
		response.sendRedirect(retURL);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}