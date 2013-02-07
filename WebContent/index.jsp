<%@ page language="java" 
		 import="chabot.utils.outofmemory.*,
		         chabot.utils.reflections.*,
		         org.apache.log4j.Logger,
		         java.util.ArrayList,
		         java.util.Collections,
		         java.util.Collection,
		         java.util.List,
		         java.util.HashMap,
		         java.util.Map,
		         java.util.Comparator"
		 contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>

<%! final Logger log = Logger.getLogger(AnalyzedHeap.class); %>

<%  List<Signature> signatures = new ArrayList<Signature>();
	
	for(Signature each : Reflection.findSubTypesOf("chabot.utils.outofmemory", Signature.class)) {
		signatures.add(each);							
	}

	if (request.getMethod().equals("POST")) {
	
	AnalyzedHeap analyzedHeap = new AnalyzedHeap();	
	Map<String, String> analyzedHeapMap = new HashMap<String, String>();
	List<Signature> hits = new ArrayList<Signature>();	
	
	log.info("Starting...");
		
	// Cycle through classes that extend Signature (e.g. Defect_[#])
	for(Signature each : signatures) {
		// Retrieve input parameters for each found class
		for(BadClass badClass : each.getClassList().values()) {
			if(request.getParameter(each.getName() + "_" + badClass.getName()) != null && request.getParameter(each.getName() + "_" + badClass.getName()).trim().length() > 0) {
				analyzedHeapMap.put(each.getName() + "_" + badClass.getName(), request.getParameter(each.getName() + "_" + badClass.getName()).trim());
				analyzedHeap.setAnalyzedHeap(analyzedHeapMap);
			}
			else {
				// Assume null fields are 0 MB. Applies only when javascript is disabled in the browser.
				analyzedHeapMap.put(badClass.getName(), "0");
				analyzedHeap.setAnalyzedHeap(analyzedHeapMap);
			}								
		}			
		
		// Evaluate input against signatures
		if(each.evaluate(analyzedHeap))	{
			hits.add(each);
		}		
		
		log.info("Done evaluating " + each.getName() + " " + (hits.contains(each) ? "(match)" : ""));			
	}
	
	request.setAttribute("hits", hits);
	request.setAttribute("signatures", signatures);
	request.setAttribute("analyzedHeapMap", analyzedHeapMap);
	
	log.info("Finished...");	
	
	%><jsp:forward page="completed.jsp" /><%			
}	
 
if (request.getMethod().equals("GET")) { %>
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
				<form id="oomform" method="post" action="index.jsp" onSubmit="return validate()">		
				<% 	Collections.sort(signatures);			
					for(Signature each : signatures) {
					 	// Set the width of the fieldset based on whether the defect has question text
						if(each.hasQuestion()) {								
							%><fieldset class="wide"><%
						}
						else {
							%><fieldset class="thin"><%	
						} %> 							
						<legend><%=each.getName()%></legend>
						<%	for(BadClass badClass : each.getClassList().values()) {
								// Display radio buttons for questions
								if(badClass.isQuestion()) { %>
									<div>
									<label for="<%=each.getName()%>_<%=badClass.getName()%>" class="question"><%=badClass.getQuestion()%></label> 
									<span class="indent">
									<input type="radio" name="<%=each.getName()%>_<%=badClass.getName()%>" 
									       id="<%=each.getName()%>_<%=badClass.getName()%>" value="true"> Yes 
									<input type="radio" name="<%=each.getName()%>_<%=badClass.getName()%>" 
									       id="<%=each.getName()%>_<%=badClass.getName()%>" value="false" checked="yes"> No
									</span>									
						  			</div>							
							<%	}
								// Display input fields for megabyte thresholds
								else { %>
									<div>
									<label for="<%=each.getName()%>_<%=badClass.getName()%>" class="className"><%=badClass.getName()%></label>
									<input type="text" class="userInput" id="<%=each.getName()%>_<%=badClass.getName()%>" 
									       name="<%=each.getName()%>_<%=badClass.getName()%>" maxlength="4" size="5"/> MB <br />			  				  
									</div> <%
								}
							 } %>
							</fieldset>
							<div class="error"><span class="error" id="<%=each.getName()%>"></span></div>
					<% 
					}
				%>
				<div class="centerText">	
					<div id="submit">
						<input type="submit" value="Check It"><br/>
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
<%	}  %>
