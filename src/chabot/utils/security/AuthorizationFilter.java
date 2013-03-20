package chabot.utils.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorizationFilter implements Filter {
	private FilterConfig filterConfig = null;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;		
		HttpServletResponse httpResponse = (HttpServletResponse) response;		
		HttpSession session = httpRequest.getSession();
				
		if(httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/login") || httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/login.jsp")) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		}
		
		Authentication auth = AuthenticationManager.read(filterConfig.getServletContext(), session);		
		if (auth == null) {
			String retURL = httpRequest.getRequestURI();
			if (httpRequest.getQueryString() != null) {
				retURL += "?" + httpRequest.getQueryString();
			}
			session.setAttribute("retURL", retURL);
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
			return;
		}
		
		String config;
		String[] values;
		
		// check no authorization
		config = filterConfig.getInitParameter("all-users");
		if ("true".equals(config)) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		}
		
		// check excluded named users
		config = filterConfig.getInitParameter("users-excluded");
		if (config == null) config = "";
		
		values = config.split(",");
		for (String value : values) {
			if (auth.getUsername().equals(value)) {
				httpResponse.sendError(403);
				return;
			}
		}
		
		// check excluded roles
		config = filterConfig.getInitParameter("roles-excluded");
		if (config == null) config = "";
		
		values = config.split(",");
		for (String value : values) {
			if (auth.hasRole(value)) {
				httpResponse.sendError(403);
				return;
			}
		}
		
		// check named users
		config = filterConfig.getInitParameter("users");
		if (config == null) config = "";
		
		values = config.split(",");
		for (String value : values) {
			if (auth.getUsername().equals(value)) {
				chain.doFilter(httpRequest, httpResponse);
				return;
			}
		}
		
		// check roles
		config = filterConfig.getInitParameter("roles");
		if (config == null) config = "";
		
		values = config.split(",");
		for (String value : values) {
			if (auth.hasRole(value)) {
				chain.doFilter(httpRequest, httpResponse);
				return;
			}
		}
		
		// forbidden (not authorized)
		httpResponse.sendError(403);
	}
	
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}
	
	public void destroy() {
		this.filterConfig = null;	
	}
}