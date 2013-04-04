<%@ page language="java"
	import="com.patientkeeper.outofmemory.Signature"
%>
<% Signature dev = (Signature) request.getAttribute("dev");
if(dev != null) { %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="styles/home.css" media="screen" />
<link href="http://fonts.googleapis.com/css?family=Press Start 2P" rel="stylesheet" type="text/css">
<title><%=dev.getName()%></title>
</head>
<body>
<div id="header">
	<div id="devTitle"><%=dev.getName()%></div> 
	<div class="right">
	<b>Fix Version:</b> <%=dev.getFixVersion()%>
	</div>
	<div style="clear: both"></div>
</div>
<%=dev.getInstructions()%>
</body>
</html>
 <% } %>
 