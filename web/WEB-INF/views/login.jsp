<%@ page language="java" %>
<jsp:include page="/header.jsp"/>
<%
	String error = (String) request.getAttribute("error");
%>
<div align="center">
<form action="login" method="POST">
<input type="hidden" name="retURL" value="/index.jsp">
<div id="login">
<fieldset class="case">
<legend><span class="left">Login</span></legend>
<div align="left" class="instructions">Use VIRTMED credentials.</div>
<label>Login:</label> <input class="user" type="text" name="username">
<label>Password:</label> <input class="user" type="password" name="password" autocomplete="off"/>
<label>Remember Me:</label> <input type="checkbox" name="remember" />
</fieldset>
</div>
<div class="center">
	<input type="submit" value="Login"><br>
<%	if(error != null && error.length() > 0) { %>
	<span class="error">Invalid credentials.</span>
<%	} %>
</div>
</form>
</div>
</body>
</html>
