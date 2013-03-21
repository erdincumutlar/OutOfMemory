/*
 * @author tkain
 */

package com.patientkeeper.outofmemory;

import java.util.HashMap;
import java.util.Map;

/*
 * DEV-9752
 * https://jira/browse/DEV-9752
 */

public class Defect_09 extends Signature {
	
	final String ACCOUNT = "Account";
	final String CLASSLOADER = "WebappClassLoader";
	final String MAPACCOUNT = "StandardMap.Account";
	final String MAPPKPERSONNEL = "PKPersonnel";
	
	public Defect_09() {
		
	BadClass class_1 = new BadClass();
	class_1.setName(ACCOUNT);
	class_1.setNumber(400);
	class_1.setContext("MB");
	
	BadClass class_2 = new BadClass();
	class_2.setName(CLASSLOADER);
	class_2.setNumber(300);
	class_2.setContext("MB");
	
	BadClass class_3 = new BadClass();
	class_3.setName(MAPACCOUNT);
	class_3.setNumber(1000);
	class_3.setContext("Objects");
	
	BadClass class_4 = new BadClass();
	class_4.setName(MAPPKPERSONNEL);
	class_4.setNumber(1000);
	class_4.setContext("Objects");
	
	Map<String, BadClass> classList = new HashMap<String, BadClass>();
	classList.put(class_1.getName(), class_1);
	classList.put(class_2.getName(), class_2);
	classList.put(class_3.getName(), class_3);
	classList.put(class_4.getName(), class_4);
	
	setName("DEV-9752");
	setDescription("Fill this in later.");
	setInstructions("Calculate the retained heap sizes of the classes below:");
	setFixVersion("4.3.3");
	setClassList(classList);
		
	}//end Defect
	
	/* 
	 * Unable to locate any prior heaps to reference, 
	 * so I'm interpreting the below line as 
	 * "continue to next steps if WebappClassLoader is > 300 MB"
	 * 
	 * "Check if org.apache.catalina.loader.WebappClassLoader is over 300MB"
	 * https://na2.salesforce.com/50140000000HuCl
	 */

	public boolean evaluate(AnalyzedHeap analyzedHeap) {
		Boolean evaluateVariable = false;
		if (analyzedHeap.getNumber(this.getName() + "_" + ACCOUNT) >= classList.get(ACCOUNT).getNumber()) {
			evaluateVariable = true;
		} else if (analyzedHeap.getNumber(this.getName() + "_" + CLASSLOADER) >= classList.get(CLASSLOADER).getNumber()) {
			if (analyzedHeap.getNumber(this.getName() + "_" + MAPACCOUNT) >= classList.get(MAPACCOUNT).getNumber() || analyzedHeap.getNumber(this.getName() + "_" + MAPPKPERSONNEL) >= classList.get(MAPPKPERSONNEL).getNumber()) {
				evaluateVariable = true;
			}
		} return evaluateVariable;
	}//end evaluate
	
}//end class
