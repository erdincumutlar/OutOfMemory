<%@ page language="java" 
	import="com.patientkeeper.outofmemory.*"
	import="com.patientkeeper.tools.WebUtil"
	import="com.patientkeeper.security.Authentication"
	import="com.patientkeeper.security.AuthenticationManager"
	import="java.util.List"
	import="java.util.ArrayList"	
%>
<%	Authentication auth = (Authentication) request.getAttribute("authentication");
	List<Signature> hits = (List<Signature>) request.getAttribute("hits");	
	
	if(hits == null) {
		String caseNumber = (String) request.getAttribute("caseNumber");
		String tag = (String) request.getAttribute("tag");
		String version = (String) request.getAttribute("version"); 
%>		<div>
		<form id="case" method="post" action="index.jsp" onSubmit="return validateCase()">
			<fieldset class="case">
				<legend>Case Information</legend>
				<div class="instructions">All fields required</div>
				<input type="hidden" name="caseForm" value="caseForm"/>
				<input type="hidden" name="caseOwner" value="<%=auth.getFullname()%>"/>
				<label for="owner">Case Owner</label>&nbsp;
				<input type="text" class="case" size="20" id="caseOwner" value="<%=auth.getFullname()%>" readonly/><br>			 
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
		</div><%	
	}
%>

