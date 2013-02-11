	<%@ page language="java" 
		 import="chabot.utils.outofmemory.*,
		         chabot.utils.reflections.*,
		         org.apache.log4j.Logger,
		         java.util.ArrayList,
		         java.util.List,
		         java.util.Map,
		         java.util.HashMap"		         
		 contentType="text/html; charset=ISO-8859-1"
    	 pageEncoding="ISO-8859-1" %>  
	<%! final Logger log = Logger.getLogger(AnalyzedHeap.class); %>	
	<%	
	if (request.getMethod().equals("POST")) {

	AnalyzedHeap analyzedHeap = new AnalyzedHeap();	
	
	List<Signature> hits = new ArrayList<Signature>();		
	List<Signature> signatures = new ArrayList<Signature>();
	Map<String, String> analyzedHeapMap = new HashMap<String, String>();
	
	for(Signature each : Reflection.findSubTypesOf("chabot.utils.outofmemory", Signature.class)) {
		signatures.add(each);							
	}
	
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
				// Assume null fields are 0 MB. Applies only when javascript is disabled in the browser.
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
		
} %>
<jsp:include page="WEB-INF/views/index.jsp" />
