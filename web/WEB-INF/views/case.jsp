<%@ page language="java" 
	import="com.patientkeeper.outofmemory.*"
	import="com.patientkeeper.tools.WebUtil"
	import="java.util.List"
	import="java.util.ArrayList"	
%>
<%	List<Signature> hits = (List<Signature>) request.getAttribute("hits");	
	
	if(hits == null) {
		String caseNumber = (String) request.getAttribute("caseNumber");
		String tag = (String) request.getAttribute("tag");
		String version = (String) request.getAttribute("version"); 
	%>			
		<div>
		<form id="case" method="post" action="index.jsp" onSubmit="return validateCase()">
			<fieldset class="case">
				<legend>Case Information</legend>
				<div class="instructions">All fields required</div>
				<input type="hidden" name="caseForm" value="caseForm"/>
				<label for="caseNumber">Case Number</label>&nbsp; 
				<input type="text" class="case" name="caseNumber" size="20" value="<%=WebUtil.out(caseNumber)%>" id="caseNumber" maxlength="7"/><br> 
				<label for="tag">Mobilizer Tag</label>&nbsp;
				<input type="text" class="case" name="tag" size="20" value="<%=WebUtil.out(tag)%>" id="tag"/><br><br>
			</fieldset>
			<div class="center">
				<input type="submit" id="caseSubmit" value="Generate Signature List">
			</div>
			<div class="error">
				<span class="error" id="caseError"></span>
			</div>
		</form>
		</div>
<%	} %>

