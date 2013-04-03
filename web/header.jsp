<%@ page language="java"
    import="com.patientkeeper.security.Authentication"
    import="com.patientkeeper.security.AuthenticationManager"
%>
<%	Authentication auth = AuthenticationManager.read(getServletContext(), session);
	request.setAttribute("authentication", auth);
%>
<jsp:include page="/WEB-INF/views/header.jsp"/>