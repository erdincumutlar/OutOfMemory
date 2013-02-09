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
	<%! final Logger log = Logger.getLogger(Signature.class); %>	
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
	for(Signature each : signatures) {
		// Retrieve input parameters for each class
		for(BadClass badClass : each.getClassList().values()) {
			if(request.getParameter(each.getName() + "_" + badClass.getName()) != null && request.getParameter(each.getName() + "_" + badClass.getName()).trim().length() > 0) {
				analyzedHeapMap.put(each.getName() + "_" + badClass.getName(), request.getParameter(each.getName() + "_" + badClass.getName()).trim());
			}
			else {
				// Assume null fields are 0 MB. Applies only when javascript is disabled in the browser.
				analyzedHeapMap.put(each.getName() + "_" + badClass.getName(), "0");
			}								
		}				
		analyzedHeap.setAnalyzedHeap(analyzedHeapMap);
		// Evaluate input against defects (e.g. Defect_[#])
		if(each.evaluate(analyzedHeap))	{
			hits.add(each);
		}		

		log.info("Done evaluating " + each.getName() + ", " + (hits.contains(each)));			
	}

	request.setAttribute("hits", hits);
	request.setAttribute("didRun", true);
	log.info("Finished...");	
		
} %>
<jsp:include page="WEB-INF/views/index.jsp" />