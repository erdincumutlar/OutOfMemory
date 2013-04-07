<%@ page language="java" %>
<jsp:include page="/header.jsp"/>
<%
	String error = (String) request.getAttribute("error");
%>
<div align="center">
<form action="login" method="POST">
<input type="hidden" name="retURL" value="index.jsp">
<div id="login">
<fieldset>
<legend>Login</legend>
<div align="left" class="tip">Use VIRTMED credentials.</div>
<label class="inblock">Login:</label> <input class="inblock" type="text" name="username">
<label class="inblock">Password:</label> <input class="inblock" type="password" name="password" autocomplete="off"/>
<label class="inblock">Remember Me:</label> <input class="inblock" type="checkbox" name="remember" />
</fieldset>
</div>
<div class="submit">
	<input type="submit" value="Login"><br>
<%	if(error != null && error.length() > 0) { %>
	<span class="error">Invalid credentials.</span>
<%	} %>
</div>
</form>
</div>
</body>
</html>
