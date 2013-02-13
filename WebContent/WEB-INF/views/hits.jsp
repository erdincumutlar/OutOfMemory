<%@ page language="java" 
		 import="chabot.utils.outofmemory.*,
		 		 chabot.utils.reflections.*,
		 		 chabot.utils.tools.*,
		 		 java.util.ArrayList,
		         java.util.List"
		 contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>

<% AnalyzedHeap analyzedHeap =  (AnalyzedHeap) request.getAttribute("analyzedHeap");

if (analyzedHeap != null) {	

	List<Signature> signatures = (List<Signature>) request.getAttribute("signatures"); 
	List<Signature> hits =  (List<Signature>) request.getAttribute("hits");  
%>	
	<fieldset id="hits" class="thin">
		<legend>Results</legend>
		<ul>
<% 		if (!hits.isEmpty()) {
			for (Signature hit : hits) { %>
			<li><span class="black"><%=hit.getName()%></span></li>
<%			}
		} 
else { 
%>
	<li><span class="black">Cause unknown, please enter a CI.</span></li>
<%
} 
%>
</ul>
</fieldset>
<%
}
%>
 