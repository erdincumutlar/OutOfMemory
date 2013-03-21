package com.patientkeeper.outofmemory;

import java.util.HashMap;
import java.util.Map;

/*
 * DEV-28143
 * https://jira/browse/DEV-28143
 */

public class Defect_06 extends Signature {

	final String TRANSACTION = "ChargeTransaction";
	final String PATINTERACT = "PatientInteraction";	
		
	public Defect_06() {		
		
		BadClass class_1 = new BadClass();		
		class_1.setName(TRANSACTION);
		class_1.setNumber(332);
		class_1.setContext("MB");
		
		BadClass class_2 = new BadClass();		
		class_2.setName(PATINTERACT);
		class_2.setNumber(225);		
		class_2.setContext("MB");
		
		Map<String, BadClass> classList = new HashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);		
				
		setName("DEV-28143");
		setDescription("Fill this in later.");
		setInstructions("Calculate the retained heap sizes of the classes below:");
		setFixVersion("7.x Triage");
		setClassList(classList);				
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
		if ((analyzedHeap.getNumber(this.getName() + "_" + TRANSACTION) >= classList.get(TRANSACTION).getNumber()) && 
			(analyzedHeap.getNumber(this.getName() + "_" + PATINTERACT) >= classList.get(PATINTERACT).getNumber())) {			
			return true;
		}		
		return false;
	}
}
