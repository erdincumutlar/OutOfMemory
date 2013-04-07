<%@ page language="java"
    import="com.patientkeeper.security.Authentication"
    import="com.patientkeeper.security.AuthenticationManager"
%>
<% Authentication auth = (Authentication) request.getAttribute("authentication"); 
   String baseURL = request.getContextPath();
%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/styles/home.css" media="screen" />
<!--[if IE]><link href="styles/fieldset-ie.css" rel="stylesheet" type="text/css" /><![endif]-->
<link href="http://fonts.googleapis.com/css?family=Press Start 2P" rel="stylesheet" type="text/css">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="scripts/utils.js"></script>
<title>Support OOM Analysis</title>
</head>
<body>
<div id="header">
	<div id="title">Support OOM Analysis</div> 
	<div class="right">
		<% if(auth != null) { %>
			Welcome, <b><%=auth.getFullname()%></b>!<br>
			<a href="<%=baseURL%>/logout">Logout</a><br>
			<a href="mailto:mchabot@patientkeeper.com;technicalconsultants@patientkeeper.com">Report An Issue</a> 			
		<% } %>
	</div>
	<div style="clear: both"></div>
</div>