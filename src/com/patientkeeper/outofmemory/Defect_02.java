package com.patientkeeper.outofmemory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.patientkeeper.tools.ToHTML;

/*
 * DEV-17975
 * https://jira/browse/DEV-17975
 */

public class Defect_02 extends Signature {

	final String JASPER = "JasperPrint";
	final String EXPORT = "JRXIsExporter";	
	
	public Defect_02() {		

		BadClass class_1 = new BadClass();		
		class_1.setName(JASPER);
		class_1.setNumber(400);
		class_1.setContext("MB");
		
		BadClass class_2 = new BadClass();		
		class_2.setName(EXPORT);
		class_2.setNumber(400);
		class_2.setContext("MB");
		
		LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);
		
		List<String> instructions = new ArrayList<String>(0);
		instructions.add("Open the heap dump using Eclipse Memory Analyzer.");
		instructions.add("Search for JasperPrint in the histogram.");
		instructions.add("Right-click the value in the Shallow Heap Size column and select \"Calculate Precise Retained Size\".");
		instructions.add("Search for JRXlsExporter in the histogram.");
		instructions.add("Right-click the value in the Shallow Heap Size column and select \"Calculate Precise Retained Size\".");
		instructions.add("Enter the values into appropriate inputs fields.");
		String ordered = ToHTML.getOrderedList(instructions);
		
		setName("DEV-17975");
		setDescription("Fill this in later.");
		setInstructions(ordered);
		setBlurb("Calculate the retained heap sizes of the classes below:");
		setFixVersion("5.1.0");
		setClassList(classList);			
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
		if ((analyzedHeap.getNumber(this.getName() + "_" + JASPER) >= classList.get(JASPER).getNumber() || 
		     analyzedHeap.getNumber(this.getName() + "_" + EXPORT) >= classList.get(EXPORT).getNumber())) {			
			return true;
		}		
		return false;
	}
}
