<%@ page language="java" 
		 import="chabot.utils.outofmemory.*,
		         chabot.utils.reflections.*,
		         org.apache.log4j.Logger,
		         java.util.ArrayList,
		         java.util.Collections,
		         java.util.List,
		         java.util.HashMap,
		         java.util.Map"
		 contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>

<%  List<Signature> signatures = new ArrayList<Signature>();	
	// Find classes that extend Signature (e.g. Defect_[#].java)
	for(Signature each : Reflection.findSubTypesOf("chabot.utils.outofmemory", Signature.class)) {
		signatures.add(each);							
	}	
 %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="styles/home.css" media="screen"/>
	<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script type="text/javascript" src="scripts/utils.js"></script>
	<link href="http://fonts.googleapis.com/css?family=Press Start 2P" rel="stylesheet" type="text/css">
	<title>OOM Analysis</title>
</head>
<body>		
	<div id="outerContainer">
		<div id="titleContainer">
			<span class="title">OOM Analysis</span>
		</div>
		<div id="innerContainer">
			<div id="bodyContainer">
				Instructions for determining the values below can be found on Support's <a href="https://confluence/display/support/Out-of-Memory+Analysis">Out-of-Memory Analysis</a> page.
			</div>
			<div id="hitsContainer">
				<%  List<Signature> hits =  (List<Signature>) request.getAttribute("hits");
					if(hits != null) { %>
						<span class="error">OOM caused by:</span><br/>	   	
   						<ul> 
   	 			<%		for(Signature hit : hits) { %>
							<span class="black"><li><%=hit.getName()%></li></span>
 				<%	 	} %>
   						</ul> 
	 			<%	}
	 				else { %>
	 				  	Cause unknown, please enter a CI.
	 			<%  }%>					
			</div>
			<div id="defectContainer">
			<form id="oomform" method="post" action="./index.jsp" onSubmit="return validate()">		
			<% 	Collections.sort(signatures);			
				for(Signature eachSig : signatures) {
					String sigName = eachSig.getName();
				 	// Set the width of the fieldset based on whether the defect has question text
					if(eachSig.hasQuestion()) {								
						%><fieldset class="wide"><%
					}
					else {
						%><fieldset class="thin"><%	
					} %> 							
					<legend><%=eachSig.getName()%></legend>
					<%	for(BadClass badClass : eachSig.getClassList().values()) {
							 String className = badClass.getName();
							// Display radio buttons for questions
							if(badClass.isQuestion()) { %>
								<div>
								<label for="<%=sigName%>_<%=className%>" class="question"><%=badClass.getQuestion()%></label> 
								<span class="indent">
								<input type="radio" name="<%=sigName%>_<%=className%>"  id="<%=sigName%>_<%=className%>" value="true"> Yes 
								<input type="radio" name="<%=sigName%>_<%=className%>"  id="<%=sigName%>_<%=className%>" value="false" checked="yes"> No
								</span>									
					  			</div>							
						<%	}
							// Display input fields for megabyte thresholds
							else { %>
								<div>
								<label for="<%=sigName%>_<%=className%>" class="className"><%=className%></label>
								<input type="text" class="user" id="<%=sigName%>_<%=className%>" 
								       name="<%=sigName%>_<%=className%>" maxlength="4" size="5"/> MB <br />			  				  
								</div> <%
							}
						 } %>
						</fieldset>
						<div class="error"><span class="error" id="<%=sigName%>"></span></div>
				<% 
				}
			%>
			<div class="centerText">	
				<div id="submit">
					<input type="submit" value="Submit"><br/>
				</div>
			<i><b>Note:</b> 1,000,000 Bytes = 1 MB</i><br />
			<a href="javascript:document.getElementById('oomform').reset();">reset</a>
			</div>
			</form>
			</div>
		</div>
	</div>
</body>
</html>	