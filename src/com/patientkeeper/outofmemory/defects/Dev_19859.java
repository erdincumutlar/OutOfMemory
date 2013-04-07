package com.patientkeeper.outofmemory.defects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.patientkeeper.outofmemory.AnalyzedHeap;
import com.patientkeeper.outofmemory.BadClass;
import com.patientkeeper.outofmemory.Signature;
import com.patientkeeper.tools.WebUtil;

/**
 * @author cthonis
 * https://jira/browse/DEV-19859
 */

public class Dev_19859 extends Signature {

	final String ACCT = "Account";
	final String ACCT_CONF = "GetAccountListForConfidentiality";	
	
	public Dev_19859() {	
		
		BadClass class_1 = new BadClass();		
		class_1.setName(ACCT);
		class_1.setNumber(400);
		class_1.setContext("MB");
		
		BadClass class_2 = new BadClass();		
		class_2.setName(ACCT_CONF);
		class_2.setNumber(200);
		class_2.setContext("MB");
		
		LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);
		
		List<String> instructions = new ArrayList<String>(0);
		instructions.add("Open the heap dump using Eclipse Memory Analyzer.");
		instructions.add("Search for <b>Account</b> in the class histogram.");
		instructions.add("Right-click the Account class and select \"Calculate Precise Retained Size\".");
		instructions.add("Enter the retained heap size into the <i>Account</i> field.");
		instructions.add("Search for <b>GetAccountListForConfidentiality</b> in the class histogram.");
		instructions.add("Right-click the GetAccountListForConfidentiality class and select \"Calculate Precise Retained Size\".");
		instructions.add("Enter the retained heap size into the <i>GetAccountListForConfidentiality</i> field.");
		String ordered = WebUtil.getOrderedList(instructions);		
		
		setName("DEV-19859");
		setDescription("Fill this in later.");
		setInstructions(ordered);
		setFixVersion("5.0.0");
		setClassList(classList);				
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
		if ((analyzedHeap.getNumber(this.getName() + "_" + ACCT) >= classList.get(ACCT).getNumber()) && 
		    (analyzedHeap.getNumber(this.getName() + "_" + ACCT_CONF) >= classList.get(ACCT_CONF).getNumber())) {			
			return true;
		}		
		return false;
	}
}