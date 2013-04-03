<%@ page language="java"
	import="com.patientkeeper.security.*"
%>
<%
Authentication auth = AuthenticationManager.read(getServletContext(), session);

if (auth != null) {
	response.sendRedirect("index.jsp");
	return;
}

boolean error = false;
String username = request.getParameter("username");
String remember = request.getParameter("remember");

if (username == null || username.equals("")) {
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if ("username".equals(cookie.getName())) {
				username = cookie.getValue();
				remember = "y";
			}
		}
	}
}
request.setAttribute("username", username);
request.setAttribute("remember", remember);
%>
<jsp:include page="/WEB-INF/views/login.jsp" />