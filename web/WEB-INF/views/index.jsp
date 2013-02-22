<%@ page language="java" 
		 contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="styles/home.css" media="screen" />
<link href="http://fonts.googleapis.com/css?family=Press Start 2P" rel="stylesheet" type="text/css">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="scripts/validate.js"></script>
<title>Support OOM Analysis</title>
</head>
<body>
	<div id="outer">
		<div id="title">
			<span class="title">Support OOM Analysis</span>
		</div>
		<div id="body">
			<div id="content">
				Instructions for determining the values below can be found on
				Support's <a
					href="https://confluence/display/support/Out-of-Memory+Analysis">
					Out-of-Memory Analysis</a> page.
			</div>
			<div id="leftPane">
				<jsp:include page="case.jsp" />				
				<jsp:include page="hits.jsp" />			
				<jsp:include page="defects.jsp" />				
			</div>
		</div>
	</div>
</body>
</html>