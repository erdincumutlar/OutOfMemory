package chabot.utils.security;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String defaultPage = "index.jsp";
	private String loginPage = "login.jsp";
	private String authKey  = "authentication";
	private int authDuration =  60 * 60 * 24;
	private int rememberDuration = 365 * 60 * 60 * 24;
	private String cookieDomain = null;
	private String cookiePath = null;
	private boolean cookieSecure = false;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		if(config.getInitParameter("defaultPage") != null) {
			defaultPage = config.getInitParameter("defaultPage");
		}

		if(config.getInitParameter("loginPage") != null) {
			loginPage = config.getInitParameter("loginPage");
		}

		if(config.getInitParameter("authKey") != null) {
			authKey = config.getInitParameter("authKey");
		}

		if(config.getInitParameter("authDuration") != null) {
			authDuration = Integer.parseInt(config.getInitParameter("authDuration"));
		}

		if(config.getInitParameter("rememberDuration") != null) {
			rememberDuration = Integer.parseInt(config.getInitParameter("rememberDuration"));
		}

		cookieDomain = config.getInitParameter("cookieDomain");
		cookiePath = config.getInitParameter("cookiePath");
		cookieSecure = Boolean.parseBoolean(config.getInitParameter("cookieSecure"));
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String retURL = request.getParameter("retURL");
		if (retURL == null || retURL.equals("")) {
			retURL = (String) session.getAttribute("retURL");
		}
		if (retURL == null || retURL.equals("")) {
			retURL = defaultPage;
		}

		// check for existing session authentication
		Authentication auth = AuthenticationManager.read(getServletContext(), session);
		if (auth != null) {
			session.removeAttribute("retURL");
			response.sendRedirect(retURL);
			return;
		}

		// check for existing cookie authentication
		String authValue = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (authKey.equals(cookie.getName())) {
				authValue = cookie.getValue();
				break;
			}
		}

		// no cookie set, send to login page
		if (authValue == null) {
			response.sendRedirect(loginPage);
			return;
		}

		// initialize the AuthenticationManager
		AuthenticationManager authManager = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			authManager = (AuthenticationManager) envContext.lookup("security/auth");
		}
		catch (NamingException e) {
			e.printStackTrace();
			response.sendError(500);
			return;
		}

		// decrypt cookie
		Cookie cookie = new Cookie(authKey, null);
		AuthenticationCookie authCookie = null;
		try {
			authCookie = new AuthenticationCookie();
			authCookie.decode(authValue);
		}
		catch (Exception e) {
			e.printStackTrace();
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			response.sendRedirect(loginPage);
			return;
		}

		// setup credentials
		String username = authCookie.getUsername();
		String password = authCookie.getPassword();

		// validate credentials
		auth = authManager.authenticate(username, password);
		if (auth == null) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			response.sendRedirect(loginPage);
			return;
		}

		// setup cookie
		try {
			cookie.setValue(authCookie.encode());
			if(cookieDomain != null) cookie.setDomain(cookieDomain);
			if(cookiePath != null) cookie.setPath(cookiePath);
			cookie.setSecure(cookieSecure);
			cookie.setValue(authCookie.encode());
			cookie.setMaxAge(authDuration);
		}
		catch (Exception e) {
			e.printStackTrace();
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			response.sendRedirect(loginPage);
			return;
		}

		// store authentication
		response.addCookie(cookie);
		AuthenticationManager.save(getServletContext(), session, auth);

		// send redirect
		if (retURL == null || retURL.equals("")) {
			retURL = defaultPage;
		}
		session.removeAttribute("retURL");
		response.sendRedirect(retURL);
		return;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String retURL = request.getParameter("retURL");
		if (retURL == null || retURL.equals("")) {
			retURL = (String) session.getAttribute("retURL");
		}
		if (retURL == null || retURL.equals("")) {
			retURL = defaultPage;
		}

		// check for existing session authentication
		Authentication auth = AuthenticationManager.read(getServletContext(), session);
		if (auth != null) {
			AuthenticationManager.save(getServletContext(), session, null);
		}

		// initialize the AuthenticationManager
		AuthenticationManager authManager = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			authManager = (AuthenticationManager) envContext.lookup("security/auth");
		}
		catch (NamingException e) {
			e.printStackTrace();
			response.sendError(500);
			return;
		}

		// setup credentials;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");

		// validate credentials
		auth = authManager.authenticate(username, password);
		if (auth == null) {
			response.sendError(401);
			return;
		}

		// set cookie authentication
		Cookie cookie = new Cookie(authKey, null);
		if (remember != null && remember.equals("y")) {
			try {
				AuthenticationCookie authCookie = new AuthenticationCookie();
				authCookie.setUsername(username);
				authCookie.setPassword(password);
				if(cookieDomain != null) cookie.setDomain(cookieDomain);
				if(cookiePath != null) cookie.setPath(cookiePath);
				cookie.setSecure(cookieSecure);
				cookie.setValue(authCookie.encode());
				cookie.setMaxAge(authDuration);
			}
			catch (Exception e) {
				cookie.setMaxAge(0);
				e.printStackTrace();
			}
		}
		else {
			cookie.setMaxAge(0);
		}
		response.addCookie(cookie);

		// set username cookie (remember me)
		cookie = new Cookie("username", null);
		if (remember != null && remember.equals("y")) {
			cookie.setValue(username);
			if(cookieDomain != null) cookie.setDomain(cookieDomain);
			if(cookiePath != null) cookie.setPath(cookiePath);
			cookie.setSecure(cookieSecure);
			cookie.setMaxAge(rememberDuration);
		}
		else {
			cookie.setMaxAge(0);
		}
		response.addCookie(cookie);

		// set session authentication
		AuthenticationManager.save(getServletContext(), session, auth);

		// send redirect
		if (retURL == null || retURL.equals("")) {
			retURL = defaultPage;
		}
		session.removeAttribute("retURL");
		response.sendRedirect(retURL);
	}
}