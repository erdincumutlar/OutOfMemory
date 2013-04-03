<%@ page language="java"
    import="com.patientkeeper.security.Authentication"
    import="com.patientkeeper.security.AuthenticationManager"
%>
<% Authentication auth = (Authentication) request.getAttribute("authentication"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="styles/home.css" media="screen" />
<!--[if IE]><link href="styles/fieldset-ie.css" rel="stylesheet" type="text/css" /><![endif]-->
<link href="http://fonts.googleapis.com/css?family=Press Start 2P" rel="stylesheet" type="text/css">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="scripts/validate.js"></script>
<script type="text/javascript" src="scripts/info.js"></script>
<title>Support OOM Analysis</title>
</head>
<body>
<div id="header">
	<div id="title">
		<span class="title">Support OOM Analysis</span>
	</div>	
	<div id="auth">
		<% if(auth != null) { %>
			<b>Logged in:</b> <%=auth.getFullname()%><br>
			<a href="/logout">Logout</a> 			
		<% } %>
	</div>
	<div style="clear: both"></div>
</div>