/*
 * @author tkain 
 */

package com.patientkeeper.outofmemory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import com.patientkeeper.tools.ToHTML;

/*
 * DEV-36990
 * https://jira/browse/DEV-36990
 */

public class Defect_07 extends Signature {

	final String CDSENCOUNTER = "CdsEncounter";
	final String CACHEPOPULATOR = "ProviderCachePopulatorTask.run()";

	public Defect_07() {

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
		instructions.add("In the Histogram, search for <strong>CdsEncounter</strong>.");
		instructions.add("Enter the object count into the <em>CdsEncounter Objects</em> field.");
		instructions.add("Right click on the CdsEncounter class and select \"Merge Shortest Path to GC roots with all references.\"");
		instructions.add("Identify a thread root named with this syntax: pool-NN-thread-Y (ex. pool-27-thread-2). Right-click on it and select Java Basics -> Thread Details.");
		instructions.add("Determine if com.patientkeeper.recurringtask.providerpopulator.ProviderCachePopulatorTask.run() exists in the Thread details.");
		instructions.add("Update the <em>ProviderCachePopulatorTask.run()</em> field appropriately.");
		String ordered = ToHTML.getOrderedList(instructions);
		
		setName("DEV-36990");
		setDescription("Fill this in later.");
		setInstructions(ordered);
		setFixVersion("Dev To Manage");
		setBlurb("Identify the number of objects in the class below, and review thread details via Java Basics | Thread Details:");
		setClassList(classList);
		
	}

	public boolean evaluate(AnalyzedHeap analyzedHeap) {
		if (analyzedHeap.getNumber(this.getName() + "_" + CDSENCOUNTER) >= classList
				.get(CDSENCOUNTER).getNumber()) {
			return (analyzedHeap.getBoolean(this.getName() + "_"
					+ CACHEPOPULATOR));
		}
		return false;
	}
}
