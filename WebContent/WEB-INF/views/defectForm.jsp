<%@ page language="java" 
		 import="chabot.utils.outofmemory.*,
		 		 chabot.utils.reflections.*,
		 		 chabot.utils.tools.*,
		 		 java.util.ArrayList,
		         java.util.Collections,
		         java.util.List"
		 contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<%
String tag = (String) request.getAttribute("tag");

if(tag != null && tag.length() > 0) {
	
	AnalyzedHeap analyzedHeap = (AnalyzedHeap) request.getAttribute("analyzedHeap");
	long versionNormalized = (Long) request.getAttribute("versionNormalized");

	List<Signature> signatures = new ArrayList<Signature>();		
	for(Signature each : Reflection.findSubTypesOf("chabot.utils.outofmemory", Signature.class)) {
		signatures.add(each);							
	}
	
	Collections.sort(signatures);		
%>
<form id="oomform" method="post" action="index.jsp" onSubmit="return validate()">		
	<% 	for(Signature sig : signatures) {
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
	<input type="hidden" name="defectForm" value="defectForm" />
	</form> <%
	}
	%>
