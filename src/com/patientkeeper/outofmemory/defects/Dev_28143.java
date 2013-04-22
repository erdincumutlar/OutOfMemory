package com.patientkeeper.outofmemory.defects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.patientkeeper.outofmemory.AnalyzedHeap;
import com.patientkeeper.outofmemory.BadClass;
import com.patientkeeper.outofmemory.Signature;
import com.patientkeeper.tools.WebUtil;

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
		
		List<String> instructions = new ArrayList<String>(0);
		
		instructions.add("Open the heap dump using Eclipse Memory Analyzer.");
		instructions.add("Search for <b>ChargeTransaction</b> in the class histogram.");
		instructions.add("Right click the ChargeTransaction class and select \"Calculate Precise Retained Size\".");
		instructions.add("Enter the retained heap size into the <i>ChargeTransaction</i> field.");
		instructions.add("Search for <b>PatientInteraction</b> in the class histogram.");
		instructions.add("Right click the PatientInteraction class and select \"Calculate Precise Retained Size\".");
		instructions.add("Enter the retained heap size into the <i>PatientInteraction</i> field.");
		String ordered = WebUtil.getOrderedList(instructions);
		
		setName("DEV-28143");
		setInstructions(ordered);
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
