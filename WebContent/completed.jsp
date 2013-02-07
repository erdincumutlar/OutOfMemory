<%@ page language="java" 
		 import="chabot.utils.outofmemory.*,
		         chabot.utils.reflections.*,
		         org.apache.log4j.Logger,
		         java.util.ArrayList,
		         java.util.List"
		 contentType="text/html; charset=ISO-8859-1"
    	 pageEncoding="ISO-8859-1" %>    	 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="/styles/home.css" media="screen"/>
	<title>OOM Analysis</title>
</head>
<body>	
	
	<div class="container">
	<% List<Signature> hits = new ArrayList<Signature>();
	   hits = (List<Signature>) request.getAttribute("hits");
	   	%> You've hit: <br/>	   	
	   	<ul> <%
		 for(Signature hit : hits) { %>
			<li><%=hit.getName()%></li>	
	 <%	 }  %>	
		</ul>

	</div>
</body>
</html>