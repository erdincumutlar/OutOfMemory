package com.patientkeeper.outofmemory.defects;

import java.util.LinkedHashMap;

import com.patientkeeper.outofmemory.AnalyzedHeap;
import com.patientkeeper.outofmemory.BadClass;
import com.patientkeeper.outofmemory.Signature;

/**
 * @author mchabot
 * https://jira/browse/DEV-15997
 */

public class Dev_15997 extends Signature {

	final String COLUMNDATA = "CrossTabColumnData";
	final String COLUMNROW = "CrossTabRowData";	
		
	public Dev_15997() {		
		
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
