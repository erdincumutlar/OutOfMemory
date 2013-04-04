<%@ page language="java"
	import="com.patientkeeper.reflections.*"
	import="com.patientkeeper.outofmemory.Signature"
	import="java.util.ArrayList"
	import="java.util.List" 
%>
<%  String id = request.getParameter("id");	
	if(id != null && id.length() > 0) {
		Signature temp = null;
		for(Signature each : Reflection.findSubTypesOf("com.patientkeeper.outofmemory", Signature.class)) {
			if(each.getName().equals(id)) {
				temp = each;
			}
		}
		request.setAttribute("dev", temp);
   } else { %>
   		   		
<% } %> 
<jsp:include page="/WEB-INF/views/info.jsp" />