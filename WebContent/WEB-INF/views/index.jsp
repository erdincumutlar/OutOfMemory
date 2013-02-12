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

<%  
	RE versionPattern = new RE("([0-9])_([0-9])_([0-9])");
	String version = request.getParameter("version");
	long versionNormalized = 0;
	
	if(version != null && version.length() > 0 && versionPattern.match(version)) {
		version = versionPattern.getParen(1) + "." + versionPattern.getParen(2) + "." + versionPattern.getParen(3);
		versionNormalized = Long.parseLong(version.replace(".", ""));
	}
	
	// Get request attributes if a run was just performed
	AnalyzedHeap analyzedHeap = (AnalyzedHeap) request.getAttribute("analyzedHeap");
	List<Signature> hits =  (List<Signature>) request.getAttribute("hits");	
		
	// Find classes that extend Signature (e.g. Defect_[#].java)
	List<Signature> signatures = new ArrayList<Signature>();		
	for(Signature each : Reflection.findSubTypesOf("chabot.utils.outofmemory", Signature.class)) {
		signatures.add(each);							
	}	
 %>
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
			<div id="inner">
				<div id="info">
					<% if (analyzedHeap != null) {	%>
					<fieldset id="hits" class="thin">
						<legend>Results</legend>
						<ul>
							<% if (!hits.isEmpty()) {
									for (Signature hit : hits) { %>
									<li><span class="black"><%=hit.getName()%></span></li>
							<%		}
								} else { %>
									<li><span class="black">Cause unknown, please enter a CI.</span></li>
							<%	}  %>
						</ul>
					</fieldset>
					<%	} else if (version == null) { %>
							<form id="aux" method="post" action="index.jsp" onSubmit="return validate()">
								<fieldset class="aux">
									<legend>Auxiliary Information</legend>
									<label for="name">Name</label>&nbsp; 
									<input type="text" class="aux" name="name" size="20" /><br /> 
									<label for="case">Case Number</label>&nbsp; 
									<input type="text" class="aux" name="case" size="20" /><br /> 
									<label for="version">Mobilizer Tag</label>&nbsp;
									<input type="text" class="aux" name="version" size="20" /><br /> <br />
									<div class="center">
										<input type="submit" value="Next">
									</div>
								</fieldset>
								<div class="error">
									<span class="error" id="auxError"></span>
								</div>
							</form>
						<% } else { %>							
							<form id="oomform" method="post" action="index.jsp" onSubmit="return validate()">		
							<% 	Collections.sort(signatures);			
								for(Signature sig : signatures) {
									// Based on the version, display the relevant defects
									if (versionNormalized < sig.getFixVersionNormalized()) {
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
													<label for="<%=name%>"><%=badClass.getQuestion()%></label>
													<input type="radio" name="<%=name%>" id="<%=name%>" value="true"
													<%=analyzedHeap != null && analyzedHeap.getBoolean(name) ? "checked" : ""%>> Yes 
													<input type="radio" name="<%=name%>" id="<%=name%>" value="false" 
													<%=analyzedHeap != null && !analyzedHeap.getBoolean(name) ? "checked" : ""%>> No					  										
											<%	}
												// Display input fields for megabyte thresholds
												else { %>								
													<label for="<%=name%>"><%=badClass.getName()%></label>&nbsp;
													<input type="text" class="user" id="<%=name%>" name="<%=name%>" maxlength="4" size="5"
													value="<%=analyzedHeap != null ? WebUtil.out(analyzedHeap.getNumber(name)) : ""%>"/> MB <br />			  				  
													<%
												}
											 } %>
											</fieldset>
											<div class="error"><span class="error" id="<%=sig.getName()%>"></span></div>
									<%  } %>
							<%	}
							%>
							<div class="center">	
								<div id="submit">
									<input type="submit" value="Submit"><br/>
								</div>
							</div>
							<input type="hidden" name="oomFormSubmitted" value="true" />
							</form>
						<div class="center">
							<i><b>Note:</b> 1,000,000 Bytes = 1 MB</i><br />
						<a href="javascript:document.getElementById('oomform').reset();">reset</a>
						</div>
						
					<%	} %>
				</div>
			</div>
		</div>
	</div>
</body>
</html>