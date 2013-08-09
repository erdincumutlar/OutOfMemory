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
 * https://jira/browse/DEV-40106
 */

public class Dev_40106 extends Signature {
	
	final String BATCH = "BillingBatch";
	
	public Dev_40106() {
		
		BadClass class_1 = new BadClass();
		class_1.setName(BATCH);
		class_1.setNumber(150);
		class_1.setContext("MB");
		
		LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		
		List<String> instructions = new ArrayList<String>(0);
		instructions.add("Open the heap dump using the Eclipse Memory Analyzer and open a new histogram tab.");
		instructions.add("Search for the <b>BillingBatch</b> class and calculate the percise retained size (via right-click)");
		instructions.add("Update the <em>BillingBatch</em> question accordingly.");
		String ordered = WebUtil.getOrderedList(instructions);
		
		setName("DEV-40106");
		setDescription("Fill this in later.");
		setInstructions(ordered);
		setFixVersion("Maintenance Backlog");
		setClassList(classList);		
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {
		if (analyzedHeap.getNumber(this.getName() + "_" + BATCH) >= classList.get(BATCH).getNumber()) {
			return true;
		}
		return false;
	}

}
