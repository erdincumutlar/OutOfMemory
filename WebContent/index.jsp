<%@ page language="java" 
	 import="chabot.utils.outofmemory.*,
	         chabot.utils.reflections.*,
	         org.apache.log4j.Logger,
	         java.util.ArrayList,
	         java.util.List,
	         java.util.Map,
	         java.util.HashMap,
	         org.apache.regexp.RE"		         
	 contentType="text/html; charset=ISO-8859-1"
   	 pageEncoding="ISO-8859-1" %>  

<%! final Logger log = Logger.getLogger(AnalyzedHeap.class); %>	
<%	if (request.getMethod().equals("POST")) {
	
		String caseForm = request.getParameter("caseForm");
		String defectForm = request.getParameter("defectForm");	
		
		if(caseForm != null && caseForm.length() > 0) {
			RE versionPattern = new RE("([0-9]+)_([0-9]+)_([0-9]+)");
			String tag = request.getParameter("tag");
			String version = tag;
			long versionNormalized = 0;
			
			// Parse "MOBILIZER_7_6_2_0_20130118_1157-RELEASE" to "7.6.2" and "762"	
			if(version != null && version.length() > 0 && versionPattern.match(version)) {
				version = versionPattern.getParen(1) + "." + versionPattern.getParen(2) + "." + versionPattern.getParen(3);
				versionNormalized = Long.parseLong(version.replace(".", ""));		
			}
			
			String owner = request.getParameter("owner");
			String caseNumber = request.getParameter("caseNumber");
			
			request.setAttribute("owner", owner);
			request.setAttribute("caseNumber", caseNumber);
			request.setAttribute("tag", tag);
			request.setAttribute("version", version);
			request.setAttribute("versionNormalized", versionNormalized);
			
			log.info(owner + ", " + caseNumber + ", " + tag + " (" + version + ", " + versionNormalized + ")");
			
		}	
	} 
%>
<jsp:include page="WEB-INF/views/index.jsp" />
