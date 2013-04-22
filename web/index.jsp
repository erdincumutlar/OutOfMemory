<%@ page language="java" 
	import="com.patientkeeper.outofmemory.*"
	import="com.patientkeeper.reflections.*"
	import="com.patientkeeper.security.Authentication"
    import="com.patientkeeper.security.AuthenticationManager"
	import="org.apache.log4j.Logger"
	import="java.util.ArrayList"
	import="java.util.List"
	import="java.util.Map"
	import="java.util.HashMap"
	import="java.util.Collections"
	import="org.apache.regexp.RE"
%>  
<%! final Logger log = Logger.getLogger(AnalyzedHeap.class); %>	
<%  Authentication auth = AuthenticationManager.read(getServletContext(), session);
    request.setAttribute("authentication", auth);
	
	if (request.getMethod().equals("POST")) {			
		// Used to identify which form was submitted
		String caseForm = request.getParameter("caseForm");
		String defectForm = request.getParameter("defectForm");
		
		// Get the Mobilizer tag
		String tag = request.getParameter("tag");
		String version = tag;
		long versionNormalized = 0;
		RE versionPattern = new RE("([0-9]+)_([0-9]+)_([0-9]+)");		
				
		// Parse "MOBILIZER_7_6_2_0_20130118_1157-RELEASE" to "7.6.2"	
		if(version != null && version.length() > 0 && versionPattern.match(version)) {
			long major = Integer.parseInt(versionPattern.getParen(1));
			long minor = Integer.parseInt(versionPattern.getParen(2));
			long maint = Integer.parseInt(versionPattern.getParen(3));
			versionNormalized = (major * 1000000) + (minor * 1000) + (maint);
			version = major + "." + minor + "." + maint;
		}
				
		// Find all classes that extend Signature (e.g. Defect_[#].java)
		List<Signature> signatures = new ArrayList<Signature>();		
		for(Signature each : Reflection.findSubTypesOf("com.patientkeeper.outofmemory.defects", Signature.class)) {
			signatures.add(each);							
		}
		
		// Sort the signatures in ascending order, placing questions at the end
		Collections.sort(signatures);		
		log.debug("Found " + signatures.size() + " signatures");
		
		// If the Case form was submitted...
		if(caseForm != null && caseForm.length() > 0) {						
			String caseNumber = request.getParameter("caseNumber");			
			request.setAttribute("tag", tag);
			request.setAttribute("version", version);
			request.setAttribute("versionNormalized", versionNormalized);
			request.setAttribute("caseNumber", caseNumber);
			request.setAttribute("signatures", signatures);
			log.info(auth.getFullname() + ", " + "Case #" + caseNumber + ", " + tag + " (" + version + ")");
		}	
		
		// If the Defect form was submitted...
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
			
			request.setAttribute("tag", tag);
			request.setAttribute("hits", hits);			
			request.setAttribute("version", version);
			request.setAttribute("versionNormalized", versionNormalized);
			request.setAttribute("signatures", signatures);
			request.setAttribute("analyzedHeap", analyzedHeap);			
			log.info("Finished...");	
		}
}	 
%>
<jsp:include page="WEB-INF/views/index.jsp" />