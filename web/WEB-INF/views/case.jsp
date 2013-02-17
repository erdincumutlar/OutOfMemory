<%@ page language="java" 
		 import="chabot.utils.outofmemory.*,
	     		 chabot.utils.tools.WebUtil,
	     		 java.util.List,
	     		 java.util.ArrayList"

		 contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>

<%	List<Signature> hits =  (List<Signature>) request.getAttribute("hits");	
	
	if(hits == null) {
		String owner = (String) request.getAttribute("owner");
		if (owner == null) {
			owner = "";
		}
		String caseNumber = (String) request.getAttribute("caseNumber");
		String tag = (String) request.getAttribute("tag");
		String version = (String) request.getAttribute("version"); 
%>		<div>
		<form id="case" method="post" action="index.jsp" onSubmit="return validateCase()">
			<fieldset class="case">
				<legend>Case Information</legend>
				<input type="hidden" name="caseForm" value="caseForm"/>
				<label for="owner">Case Owner</label>&nbsp; 
				<select class="case" name="owner" size="1" id="owner">
						<option value=""></option>
						<option value="Akshay Chaand" <%=(owner.equals("Akshay Chaand")) ? "selected" : ""%>>Akshay Chaand</option>
						<option value="Bob Cosman" <%=(owner.equals("Bob Cosman")) ? "selected" : ""%>>Bob Cosman</option>
						<option value="Craig Thonis" <%=(owner.equals("Craig Thonis")) ? "selected" : ""%>>Craig Thonis</option>
						<option value="Jason MacDonald" <%=(owner.equals("Jason MacDonald")) ? "selected" : ""%>>Jason MacDonald</option>
						<option value="Kapil Malhotra" <%=(owner.equals("Kapil Malhotra")) ? "selected" : ""%>>Kapil Malhotra</option>
						<option value="Keith Ivy" <%=(owner.equals("Keith Ivy")) ? "selected" : ""%>>Keith Ivy</option>
						<option value="Mayank Pande" <%=(owner.equals("Mayank Pande")) ? "selected" : ""%>>Mayank Pande</option>
						<option value="Michael Chabot" <%=(owner.equals("Michael Chabot")) ? "selected" : ""%>>Michael Chabot</option>
						<option value="Michael Haverty" <%=(owner.equals("Michael Haverty")) ? "selected" : ""%>>Michael Haverty</option>
						<option value="Pradeep Thorat" <%=(owner.equals("Pradeep Thorat")) ? "selected" : ""%>>Pradeep Thorat</option>
						<option value="Robert Smith" <%=(owner.equals("Robert Smith")) ? "selected" : ""%>>Robert Smith</option>
						<option value="Satyajeet Sugandhi" <%=(owner.equals("Satyajeet Sugandhi")) ? "selected" : ""%>>Satyajeet Sugandhi</option>
						<option value="Tom Kain" <%=(owner.equals("Dr")) ? "Tom Kain" : ""%>>Tom Kain</option>
						<option value="Vinayak Palande" <%=(owner.equals("Vinayak Palande")) ? "selected" : ""%>>Vinayak Palande</option>
				</select>				 
				<label for="caseNumber">Case Number</label>&nbsp; 
				<input type="text" class="case" name="caseNumber" size="20" value="<%=WebUtil.out(caseNumber)%>" id="caseNumber"/><br /> 
				<label for="tag">Mobilizer Tag</label>&nbsp;
				<input type="text" class="case" name="tag" size="20" value="<%=WebUtil.out(tag)%>" id="tag"/><br /> <br />
			</fieldset>
			<div class="center">
				<input type="submit" id="caseSubmit" value="Next">
			</div>
			<div class="error">
				<span class="error" id="caseError"></span>
			</div>
		</form>
		</div><%	
	}
%>

