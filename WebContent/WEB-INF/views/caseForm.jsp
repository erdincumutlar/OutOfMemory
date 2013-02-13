<%@ page language="java" 
		 import="chabot.utils.tools.WebUtil"

		 contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<%
	String owner = (String) request.getAttribute("owner");
	String caseNumber = (String) request.getAttribute("caseNumber");
	String tag = (String) request.getAttribute("tag");
	String version = (String) request.getAttribute("version");
%>

<form id="case" method="post" action="index.jsp" onSubmit="return validateCase()">
	<fieldset class="case">
		<legend>Case Information</legend>
		<input type="hidden" name="caseForm" value="caseForm"/>
		<label for="owner">Case Owner</label>&nbsp; 
		<input type="text" class="case" name="owner" size="20" value="<%=WebUtil.out(owner)%>"/><br /> 
		<label for="caseNumber">Case Number</label>&nbsp; 
		<input type="text" class="case" name="caseNumber" size="20" value="<%=WebUtil.out(caseNumber)%>"/><br /> 
		<label for="tag">Mobilizer Tag</label>&nbsp;
		<input type="text" class="case" name="tag" size="20" value="<%=WebUtil.out(tag)%>"/><br /> <br />
	</fieldset>
	<div class="center">
		<input type="submit" id="caseSubmit" value="Next">
	</div>
	<div class="error">
		<span class="error" id="caseError"></span>
	</div>
</form>