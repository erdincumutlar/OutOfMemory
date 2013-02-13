<%@ page language="java" 
	 import="chabot.utils.outofmemory.*,
	         chabot.utils.reflections.*,
	         org.apache.log4j.Logger,
	         java.util.ArrayList,
	         java.util.List,
	         java.util.Map,
	         java.util.HashMap,
	         java.util.Collections,
	         org.apache.regexp.RE"		         
	 contentType="text/html; charset=ISO-8859-1"
   	 pageEncoding="ISO-8859-1" %>  

<%! final Logger log = Logger.getLogger(AnalyzedHeap.class); %>	
<%	if (request.getMethod().equals("POST")) {
	
		String caseForm = request.getParameter("caseForm");
		String defectForm = request.getParameter("defectForm");
		
		// Find all classes that extend Signature (e.g. Defect_[#].java)
		List<Signature> signatures = new ArrayList<Signature>();		
		for(Signature each : Reflection.findSubTypesOf("chabot.utils.outofmemory", Signature.class)) {
			signatures.add(each);							
		}
		
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
			
			Collections.sort(signatures);
			
			request.setAttribute("owner", owner);
			request.setAttribute("caseNumber", caseNumber);
			request.setAttribute("tag", tag);
			request.setAttribute("version", version);
			request.setAttribute("versionNormalized", versionNormalized);
			request.setAttribute("signatures", signatures);
			
			log.info(owner + ", " + caseNumber + ", " + tag + " (" + version + ", " + versionNormalized + ")");
			
		}	
		
		if(defectForm != null && defectForm.length() > 0) {
			
			AnalyzedHeap analyzedHeap = new AnalyzedHeap();	
			Map<String, String> analyzedHeapMap = new HashMap<String, String>();	
			List<Signature> hits = new ArrayList<Signature>();	
			
			log.info("Starting...");

			// Cycle through classes that extend Signature (e.g. Defect_[#])
			for(Signature sig : signatures) {
				// Retrieve input parameters for each class
				for(BadClass badClass : sig.getClassList().values()) {
				String name = sig.getName() + "_" + badClass.getName();
				String input = request.getParameter(name);
					if(input != null && input.trim().length() > 0) {
					log.debug("Adding " + name + " with value " + input.trim());
					analyzedHeapMap.put(name, input.trim());	
					}
					else {
					// Assume null fields are 0 MB. Should only apply when Javascript has been disabled.
					analyzedHeapMap.put(name, "0");
					}	
				}	
				analyzedHeap.setAnalyzedHeap(analyzedHeapMap);
				// Evaluate input against defects (e.g. Defect_[#])
				if(sig.evaluate(analyzedHeap))	{
				hits.add(sig);
				}	
	
				log.info("Done evaluating " + sig.getName() + ", " + (hits.contains(sig)));	
			}

			request.setAttribute("analyzedHeap", analyzedHeap);
			request.setAttribute("hits", hits);
			log.info("Finished...");	

			}
}
	 
%>
<jsp:include page="WEB-INF/views/index.jsp" />
