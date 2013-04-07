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
 * https://jira/browse/DEV-36990
 */

public class Dev_36990 extends Signature {

	final String CDSENCOUNTER = "CdsEncounter";
	final String CACHEPOPULATOR = "ProviderCachePopulatorTask.run()";

	public Dev_36990() {

		BadClass class_1 = new BadClass();
		class_1.setName(CDSENCOUNTER);
		class_1.setNumber(200000);
		class_1.setContext("Objects");

		BadClass class_2 = new BadClass();
		class_2.setName(CACHEPOPULATOR);
		class_2.setQuestion("Does <span class=\"class\">"
				+ CACHEPOPULATOR
				+ "</span> exist within the thread details of <span class=\"class\">pool-[#]-thread-[#]</span>?");

		LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);

		List<String> instructions = new ArrayList<String>(0);
		instructions.add("Open the heap dump using Eclipse Memory Analyzer.");
		instructions.add("Search for <strong>CdsEncounter</strong> in the class histogram.");
		instructions.add("Enter the object count into the <em>CdsEncounter Objects</em> field.");
		instructions.add("Right click on the CdsEncounter class and select \"Merge Shortest Path to GC roots with all references.\"");
		instructions.add("Identify a thread root named with this syntax: <strong>pool-NN-thread-Y</strong> (e.g. pool-27-thread-2). Right-click on it and select Java Basics | Thread Details.");
		instructions.add("Determine if <strong>com.patientkeeper.recurringtask.providerpopulator.ProviderCachePopulatorTask.run()</strong> exists in the Thread details.");
		instructions.add("Update the <em>ProviderCachePopulatorTask.run()</em> field appropriately.");
		String ordered = WebUtil.getOrderedList(instructions);
		
		setName("DEV-36990");
		setDescription("Fill this in later.");
		setInstructions(ordered);
		setFixVersion("Dev To Manage");
		setClassList(classList);		
	}

	public boolean evaluate(AnalyzedHeap analyzedHeap) {
		if (analyzedHeap.getNumber(this.getName() + "_" + CDSENCOUNTER) >= classList.get(CDSENCOUNTER).getNumber() && 
			analyzedHeap.getBoolean(this.getName() + "_" + CACHEPOPULATOR)) {
			return true;
		}
		return false;
	}
}
