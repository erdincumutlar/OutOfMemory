<%@ page language="java" %>
<%
String retURL = request.getParameter("retURL");
if (retURL == null || retURL.equals("")) {
	retURL = request.getHeader("Referer");
}
response.sendRedirect("logout" + (retURL != null ? "?retURL="+retURL : ""));
%>