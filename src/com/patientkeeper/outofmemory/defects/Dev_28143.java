package com.patientkeeper.outofmemory.defects;

import java.util.LinkedHashMap;

import com.patientkeeper.outofmemory.AnalyzedHeap;
import com.patientkeeper.outofmemory.BadClass;
import com.patientkeeper.outofmemory.Signature;

/**
 * @author mchabot
 * https://jira/browse/DEV-28143
 */

public class Dev_28143 extends Signature {

	final String TRANSACTION = "ChargeTransaction";
	final String PATINTERACT = "PatientInteraction";	
		
	public Dev_28143() {		
		
		BadClass class_1 = new BadClass();		
		class_1.setName(TRANSACTION);
		class_1.setNumber(332);
		class_1.setContext("MB");
		
		BadClass class_2 = new BadClass();		
		class_2.setName(PATINTERACT);
		class_2.setNumber(225);		
		class_2.setContext("MB");
		
		LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);		
				
		setName("DEV-28143");
		setDescription("Fill this in later.");
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
