package com.patientkeeper.outofmemory.defects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.patientkeeper.outofmemory.AnalyzedHeap;
import com.patientkeeper.outofmemory.BadClass;
import com.patientkeeper.outofmemory.Signature;
import com.patientkeeper.tools.WebUtil;

/**
 * @author tkain
 * https://jira/browse/DEV-36991
 */

public class Dev_36991 extends Signature {

	final String POOL27 = "Pool-27-Thread-1";
	final String PRELOADCACHE = "preloadcache()V ";
	final String RUN = "run()V";
	
	public Dev_36991() {
		
		BadClass class_1 = new BadClass();
		class_1.setName(POOL27);
		class_1.setQuestion("Is <span class=\"class\">" + POOL27 + "</span> the largest thread in the dominator tree and using greater than 30% of the heap?");
		
		BadClass class_2 = new BadClass();
		class_2.setName(PRELOADCACHE);
		class_2.setQuestion("Do both <span class=\"class\">" + PRELOADCACHE + "</span> and <span class=\"class\">" + RUN + "</span> exist in the thread details?");
		
		LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);
		
		List<String> instructions = new ArrayList<String>(0);
		instructions.add("Open the heap dump using Eclipse Memory Analyzer.");
		instructions.add("Open the Dominator Tree and sort by percent descending.");
		instructions.add("Determine if <strong> Pool-27-Thread-1 </strong> (also seen as <strong>Pool-27-Thread01) is at the top and using at least 30% of the heap; update the <em>Pool-27</em> question accordingly.");
		instructions.add("Right-click on the thread and select Java Basics -> Thread Details");
		instructions.add("Determine if the <strong>preloadcache()v</strong> and <strong>run()V</strong> methods are in the detailed thread stack; update the <em>preloadcache()V)</em> question accordingly.");
		String ordered = WebUtil.getOrderedList(instructions);
		
		setName("DEV-36991");
		setDescription("Fill this in later.");
		setInstructions(ordered);
		setFixVersion("7.6.6");
		setClassList(classList);
		
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {
		if (analyzedHeap.getBoolean(this.getName() + "_" + POOL27) 
				&& analyzedHeap.getBoolean(this.getName() + "_" + PRELOADCACHE)) {
			return true;
		}
		return false;
	}
	
}
