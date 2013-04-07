<%@ page language="java" 
	import="com.patientkeeper.outofmemory.*"
	import="com.patientkeeper.reflections.*"
	import="com.patientkeeper.tools.*"
	import="java.util.ArrayList"
	import="java.util.Collections"
	import="java.util.List"
%>
<% String tag = (String) request.getAttribute("tag");
if(tag != null && tag.length() > 0) {
	Long versionNormalized = (Long) request.getAttribute("versionNormalized");
	String version = (String) request.getAttribute("version");
	List<Signature> signatures = (List<Signature>) request.getAttribute("signatures");
	AnalyzedHeap analyzedHeap = (AnalyzedHeap) request.getAttribute("analyzedHeap");
%>
<form method="post" action="index.jsp" onSubmit="return validateDefects()">		
	<input type="hidden" name="defectForm" value="defectForm" />
	<input type="hidden" name="tag" value="<%=tag%>" />
	<% 	for(Signature sig : signatures) {
			// Display relevant defects based on version
			if (versionNormalized < sig.getFixVersionNormalized()) {
			 	// Set the width of the fieldset based on whether the defect has question text			 
			 	if(sig.hasQuestion()) { %>
			 		<fieldset class="question">	
			 <%	} else { %>
				 	<fieldset>
			 <% } %>				 							
				<legend><%=sig.getName()%></legend>		 
				<div class="tip">Click icon to view instructions
				<a href="javascript:info('info.jsp?id=<%=sig.getName()%>', 'info')"><img id="icon" src="images/info.png"/></a>
				</div>
<%				for(BadClass badClass : sig.getClassList().values()) {
						 String name = sig.getName() + "_" + badClass.getName();
						// Digisplay radio buttons for questions
						if(badClass.isQuestion()) { %>								
							<label class="question" id="<%=name%>" for="<%=name%>"><%=badClass.getQuestion()%></label><br>
							<input class="radio" type="radio" name="<%=name%>" id="<%=name%>" value="true"
							<%=analyzedHeap != null && analyzedHeap.getBoolean(name) ? "checked" : ""%>> Yes 
							<input class="radio" type="radio" name="<%=name%>" id="<%=name%>" value="false" 
							<%=analyzedHeap != null && !analyzedHeap.getBoolean(name) ? "checked" : ""%>> No					  										
					<%	}
						// Display input fields for megabyte thresholds
						else { %>								
							<label class="inblock" id="<%=name%>" for="<%=name%>"><%=badClass.getName()%></label>&nbsp;
							<input type="text" class="inblock" id="<%=name%>" name="<%=name%>" size="5"
							value="<%=analyzedHeap != null ? WebUtil.out(analyzedHeap.getNumber(name)) : ""%>"/>&nbsp;<%=badClass.getContext()%>			  				  
					 <% } %>
						<br>
					<% } %>
					</fieldset>									
					<div><span class="error" id="<%=sig.getName()%>"></span></div>
			<%  } %>
	<%	}
	%>
	<div class="submit">			
		<input type="submit" value="Submit">		
	</div>
	</form>
 <%
	}
%>
