<%@ page language="java" 
		 import="chabot.utils.outofmemory.*,
		         chabot.utils.reflections.*,
		         chabot.utils.tools.*,
		         org.apache.log4j.Logger,
		         java.util.ArrayList,
		         java.util.Collections,
		         java.util.List,
		         java.util.HashMap,
		         java.util.Map"
		 contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>

<%  // Get request attributes if a run was just performed
	AnalyzedHeap analyzedHeap = (AnalyzedHeap) request.getAttribute("analyzedHeap");
	List<Signature> hits =  (List<Signature>) request.getAttribute("hits");	
		
	// Find classes that extend Signature (e.g. Defect_[#].java)
	List<Signature> signatures = new ArrayList<Signature>();		
	for(Signature each : Reflection.findSubTypesOf("chabot.utils.outofmemory", Signature.class)) {
		signatures.add(each);							
	}
	request.setAttribute("sigs", signatures);
	System.out.println(request.getAttribute("sigs"));
	
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
			<div id="defectContainer">
			<div id="hitsContainer">
			<% 	if(analyzedHeap != null) { %>
					<fieldset id="hits" class="thin">
					<legend>Results</legend>	   	
   					<ul> <%
					if(!hits.isEmpty()) {
						for(Signature hit : hits) { %>
							<li><span class="black"><%=hit.getName()%></span></li> <%
						}
					}
					else { %>
						<li><span class="black">Cause unknown, please enter a CI.</span></li> <%
					} %>
   					</ul>
   					</fieldset>
			<%	}  %>
			</div>				
			<form id="oomform" method="post" action="./index.jsp" onSubmit="return validate()">		
			<% 	Collections.sort(signatures);			
				for(Signature sig : signatures) {
				 	// Set the width of the fieldset based on whether the defect has question text
					if(sig.hasQuestion()) {								
						%><fieldset class="wide"><%
					}
					else {
						%><fieldset class="thin"><%	
					} %> 							
					<legend><%=sig.getName()%></legend>
					<%	for(BadClass badClass : sig.getClassList().values()) {
							 String name = sig.getName() + "_" + badClass.getName();
							// Display radio buttons for questions
							if(badClass.isQuestion()) { %>
								<div>
								<label for="<%=name%>" class="question"><%=badClass.getQuestion()%></label>
								<input type="radio" name="<%=name%>" id="<%=name%>" value="true"
								<%=analyzedHeap != null && analyzedHeap.getBoolean(name) ? "checked" : ""%>> Yes 
								<input type="radio" name="<%=name%>" id="<%=name%>" value="false" 
								<%=analyzedHeap != null && !analyzedHeap.getBoolean(name) ? "checked" : ""%>> No
					  			</div>							
						<%	}
							// Display input fields for megabyte thresholds
							else { %>
								<div>
								<label for="<%=name%>" class="className"><%=badClass.getName()%></label>
								<input type="text" class="user" id="<%=name%>" name="<%=name%>" maxlength="4" size="5"
								value="<%=analyzedHeap != null ? WebUtil.out(analyzedHeap.getNumber(name)) : ""%>"/> MB <br />			  				  
								</div> <%
							}
						 } %>
						</fieldset>
						<div class="error"><span class="error" id="<%=sig.getName()%>"></span></div>
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