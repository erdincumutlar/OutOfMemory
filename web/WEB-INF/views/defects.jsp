<%@ page language="java" 
		 import="com.patientkeeper.outofmemory.*,
		         com.patientkeeper.reflections.*,
		         com.patientkeeper.tools.*,
		 		 java.util.ArrayList,
		         java.util.Collections,
		         java.util.List"
		 contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>

<% String tag = (String) request.getAttribute("tag");
if(tag != null && tag.length() > 0) {
	long versionNormalized = (Long) request.getAttribute("versionNormalized");
	String version = (String) request.getAttribute("version");
	List<Signature> signatures = (List<Signature>) request.getAttribute("signatures");
	AnalyzedHeap analyzedHeap = (AnalyzedHeap) request.getAttribute("analyzedHeap");
%>
<div>
<form id="oomform" name="oomform" method="post" action="index.jsp" onSubmit="return validateDefects()">		
	<input type="hidden" name="defectForm" value="defectForm" />
	<input type="hidden" name="tag" value="<%=tag%>" />
	<input type="hidden" name="versionNormalized" value="<%=versionNormalized%>" />
	<% 	for(Signature sig : signatures) {
			// Display relevant defects based on version
			if (versionNormalized < sig.getFixVersionNormalized()) {
			 	// Set the width of the fieldset based on whether the defect has question text
				if(sig.hasQuestion()) { %>
					<fieldset class="wide">
			<%	} else { %>
					<fieldset class="thin">
			<%	} %> 							
				<legend><%=sig.getName()%></legend>		 
				<div class="instructions"><%=sig.getInstructions()%>
				<a href="javascript:popup('info.jsp', 'info','10','300')"><img id="info" src="/images/info.png"/></a>
				</div>
<%				List<BadClass> classes = new ArrayList<BadClass>(sig.getClassList().values());
				Collections.sort(classes);
				for(BadClass badClass : classes) {
						 String name = sig.getName() + "_" + badClass.getName();
						// Display radio buttons for questions
						if(badClass.isQuestion()) { %>								
							<label class="question" for="<%=name%>"><%=badClass.getQuestion()%></label>
							<input type="radio" name="<%=name%>" id="<%=name%>" value="true"
							<%=analyzedHeap != null && analyzedHeap.getBoolean(name) ? "checked" : ""%>> Yes 
							<input type="radio" name="<%=name%>" id="<%=name%>" value="false" 
							<%=analyzedHeap != null && !analyzedHeap.getBoolean(name) ? "checked" : ""%>> No					  										
					<%	}
						// Display input fields for megabyte thresholds
						else { %>								
							<label class="class" for="<%=name%>"><%=badClass.getName()%></label>&nbsp;
							<input type="text" class="user" id="<%=name%>" name="<%=name%>" size="5"
							value="<%=analyzedHeap != null ? WebUtil.out(analyzedHeap.getNumber(name)) : ""%>"/>&nbsp;<%=badClass.getContext()%>			  				  
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
	</form>
	</div>
 <%
	}
%>
