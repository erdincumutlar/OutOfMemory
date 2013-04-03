<%@ page language="java" %>
<jsp:include page="/header.jsp"/>
<div align="center">
<form action="login" method="POST">
<input type="hidden" name="retURL" value="/index.jsp">
<div id="login">
<fieldset class="case">
<legend>Login</legend>
<div align="left" class="instructions">Use VIRTMED credentials.</div>
<label>Login:</label> <input class="user" type="text" name="username">
<label>Password:</label> <input class="user" type="password" name="password" autocomplete="off"/>
<label>Remember Me:</label> <input type="checkbox" name="remember" />
</fieldset>
</div>
<input type="submit" value="Submit" />
</form>
</div>
</body>
</html>
