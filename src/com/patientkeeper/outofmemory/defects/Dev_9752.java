package com.patientkeeper.outofmemory.defects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.patientkeeper.outofmemory.AnalyzedHeap;
import com.patientkeeper.outofmemory.BadClass;
import com.patientkeeper.outofmemory.Signature;
import com.patientkeeper.tools.WebUtil;

/**
 * @author tkain
 * https://jira/browse/DEV-9752
 */

public class Dev_9752 extends Signature {

	final String ACCOUNT = "Account";
	final String CLASSLOADER = "WebappClassLoader";
	final String MAPACCOUNT = "StandardMap.Account";
	final String MAPPKPERSONNEL = "PKPersonnel";

	public Dev_9752() {

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

		LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);
		classList.put(class_3.getName(), class_3);
		classList.put(class_4.getName(), class_4);
		
		List<String> instructions = new ArrayList<String>(0);
		instructions.add("Open the heap dump using Eclipse Memory Analyzer.");
		instructions.add("Search for <strong>Account</strong> in the class histogram.");
		instructions.add("Rich click the Account class and select \"Calculate Precise Retained Size.\"");
		instructions.add("Enter the retained heap size into the <em>Account</em> field.");
		instructions.add("Search for and calculate the precise retained size of <strong>org.apache.catalina.loader.WebappClassLoader</strong> in the Dominator Tree.");
		instructions.add("Enter the retained heap size into the <em>WebappClassLoader</em> field.");
		instructions.add("Expand org.apache.catalina.loader.WebappClassLoader, then expand <strong>com.patientkeeper.syncengine.SyncEngineCache</strong>.");
		instructions.add("Right-click on <strong>com.patientkeeper.syncengine.StandardMap</strong> and select Java Basics, open in Dominator Tree.");
		instructions.add("In the pop-up window select Group By Class from the group drop-down.");
		instructions.add("Expand all classes; if Account or PKPersonnel exist, enter the object count in the respective <em>Account</em> and <em>PKPersonnel</em> fields. Otherwise, enter \"0\" in these fields.");
		String ordered = WebUtil.getOrderedList(instructions);

		setName("DEV-9752");
		setDescription("Fill this in later.");
		setInstructions(ordered);
		setFixVersion("4.3.3");
		setClassList(classList);
	}

	/*
	 * Unable to locate any prior heaps to reference, so I'm interpreting the
	 * below line as "continue to next steps if WebappClassLoader is > 300 MB"
	 * 
	 * "Check if org.apache.catalina.loader.WebappClassLoader is over 300MB"
	 * https://na2.salesforce.com/50140000000HuCl
	 */

	public boolean evaluate(AnalyzedHeap analyzedHeap) {
		if (analyzedHeap.getNumber(this.getName() + "_" + ACCOUNT) >= classList.get(ACCOUNT).getNumber()) {
			return true;
		} else if (analyzedHeap.getNumber(this.getName() + "_" + CLASSLOADER) >= classList.get(CLASSLOADER).getNumber()) {
			if (analyzedHeap.getNumber(this.getName() + "_" + MAPACCOUNT) >= classList.get(MAPACCOUNT).getNumber() || 
				analyzedHeap.getNumber(this.getName() + "_" + MAPPKPERSONNEL) >= classList.get(MAPPKPERSONNEL).getNumber()) {
				return true;
			}
		}
		return false;
	}
}
