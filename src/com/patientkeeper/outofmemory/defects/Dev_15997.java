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
		
		List<String> instructions = new ArrayList<String>(0);
		instructions.add("Open the heap dump using Eclipse Memory Analyzer.");
		instructions.add("Search for Crosstab in the class histogram.");
		instructions.add("Right-click the <b>CrosstabColumnData</b> class and select \"Calculate Precise Retained Size\".");
		instructions.add("Enter the retained heap size into the <i>CrosstabColumnData</i> field.");
		instructions.add("Right-click the <b>CrosstabRowData</b> class and select \"Calculate Precise Retained Size\".");
		instructions.add("Enter the retained heap size into the <i>CrosstabRowData</i> field.");
		String ordered = WebUtil.getOrderedList(instructions);
				
		setName("DEV-15997");
		setInstructions(ordered);
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
