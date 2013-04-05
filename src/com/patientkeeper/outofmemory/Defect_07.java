/*
 * @author tkain 
 */

package com.patientkeeper.outofmemory;

import java.util.LinkedHashMap;

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
	class_2.setQuestion("Does <span class=\"class\">" + CACHEPOPULATOR + "</span> exist within the thread details of <span class=\"class\">pool-[#]-thread-[#]</span>?");
	
	BadClass class_3 = new BadClass();
	class_3.setName("CHEERS");
	class_3.setNumber(200000);
	class_3.setContext("Objects");
		
	LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
	classList.put(class_1.getName(), class_1);
	classList.put(class_2.getName(), class_2);
	classList.put(class_3.getName(), class_3);
	
	setName("DEV-36990");
	setDescription("Fill this in later.");
	setFixVersion("Dev To Manage");
	setBlurb("Identify the number of objects in the class below, and review thread details via Java Basics | Thread Details:");
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
