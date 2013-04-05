package com.patientkeeper.outofmemory;

import java.util.LinkedHashMap;

/*
 * DEV-15997
 * https://jira/browse/DEV-15997
 */

public class Defect_05 extends Signature {

	final String COLUMNDATA = "CrossTabColumnData";
	final String COLUMNROW = "CrossTabRowData";	
		
	public Defect_05() {		
		
		BadClass class_1 = new BadClass();		
		class_1.setName(COLUMNDATA);
		class_1.setNumber(200);
		class_1.setContext("MB");
		
		BadClass class_2 = new BadClass();		
		class_2.setName(COLUMNROW);
		class_2.setNumber(200);	
		class_2.setContext("MB");
		
		LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);		
				
		setName("DEV-15997");
		setDescription("Fill this in later.");
		setBlurb("Calculate the retained heap sizes of the classes below:");
		setFixVersion("7.x Triage");
		setClassList(classList);				
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
		if ((analyzedHeap.getNumber(this.getName() + "_" + COLUMNDATA) >= classList.get(COLUMNDATA).getNumber() || 
			analyzedHeap.getNumber(this.getName() + "_" + COLUMNROW) >= classList.get(COLUMNROW).getNumber())) {			
			return true;
		}		
		return false;
	}
}
