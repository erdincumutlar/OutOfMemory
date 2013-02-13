<%@ page language="java" 
		 import="chabot.utils.outofmemory.*,
		         chabot.utils.reflections.*,
		         chabot.utils.tools.*,
		         org.apache.log4j.Logger,
		         java.util.ArrayList,
		         java.util.Collections,
		         java.util.List,
		         java.util.HashMap,
		         java.util.Map,
		         org.apache.regexp.RE"
		 contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="styles/home.css" media="screen" />
<link href="http://fonts.googleapis.com/css?family=Press Start 2P" rel="stylesheet" type="text/css">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="scripts/utils.js"></script>
<title>OOM Analysis</title>
</head>
<body>
	<div id="outer">
		<div id="title">
			<span class="title">OOM Analysis</span>
		</div>
		<div id="body">
			<div id="instructions">
				Instructions for determining the values below can be found on Support's 
				<a href="https://confluence/display/support/Out-of-Memory+Analysis">
				Out-of-Memory Analysis</a> page.
			</div>
			<div id="leftPane">
				<div>								
					<jsp:include page="caseForm.jsp" />	
				</div>
				<div>								
					<jsp:include page="defectForm.jsp" />	
				</div>
			</div>
		</div>
	</div>
</body>
</html>