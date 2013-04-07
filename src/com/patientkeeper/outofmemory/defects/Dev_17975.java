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
 * https://jira/browse/DEV-17975
 */

public class Dev_17975 extends Signature {

	final String JASPER = "JasperPrint";
	final String EXPORT = "JRXIsExporter";	
	
	public Dev_17975() {		

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
		instructions.add("Search for <b>JasperPrint</b> in the class histogram.");
		instructions.add("Right-click the value in the Shallow Heap Size column and select \"Calculate Precise Retained Size\".");
		instructions.add("Enter the retained heap size into the <i>JasperPrint</i> field.");
		instructions.add("Search for <b>JRXIsExporter</b> in the class histogram.");
		instructions.add("Right-click the value in the Shallow Heap Size column and select \"Calculate Precise Retained Size\".");
		instructions.add("Enter the retained heap size into the <i>JRXIsExporter</i> field.");
		String ordered = WebUtil.getOrderedList(instructions);
		
		setName("DEV-17975");
		setDescription("Fill this in later.");
		setInstructions(ordered);
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
