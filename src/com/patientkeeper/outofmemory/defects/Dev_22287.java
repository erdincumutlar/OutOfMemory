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
 * https://jira/browse/DEV-22287
 */

public class Dev_22287 extends Signature {

	final String SESSION = "net.sf.hibernate.impl.SessionImpl";
	final String GETPATIENT = "com.patientkeeper.monaco.GetPatientInteractionList";
	final String ENCOUNTER = "CdsEncounter";

	public Dev_22287() {		
		
		BadClass class_1 = new BadClass();		
		class_1.setName(SESSION);
		class_1.setQuestion("Are there instances of <span class=\"class\">" + SESSION + "</span> over 150 MB?");
		
		BadClass class_2= new BadClass();		
		class_2.setName(GETPATIENT);
		class_2.setQuestion("Is <span class=\"class\">" + GETPATIENT + "</span> in the Inspector (or to the right of <span class=\"class\">\"this$0, val$callback\"</span>?");
		
		BadClass class_3 = new BadClass();		
		class_3.setName(ENCOUNTER);
		class_3.setNumber(5000);
		class_3.setContext("Objects");
		
		LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);	
		classList.put(class_3.getName(), class_3);	
				
		List<String> instructions = new ArrayList<String>(0);
		instructions.add("Open the heap dump using Eclipse Memory Analyzer.");
		instructions.add("Open the Dominator Tree and search for <b>.*SessionImpl .*</b> (note the space).");
		instructions.add("Look for an instance of <b>net.sf.hibernate.impl.SessionImpl</b> that is over 150MB retained size.");
		instructions.add("For each instance, right-click, select List Objects | With Outgoing References.");
		instructions.add("Expand <b>net.sf.hibernate.impl.SessionImpl @ 0xNNNNNNN</b>.");
		instructions.add("Expand \"interceptor\", Expand \"val$callback.\"");
		instructions.add("Select \"this$0, val$callback\".");
		instructions.add("Determine if the <b>class com.patientkeeper.monaco.GetPatientInteractionList</b> is in the Inspector (or to the right of \"this$0, val$callback\").");
		instructions.add("Contract \"interceptor\" and right-click on \"entitiesByKey.\" Select Show Objects By Class | By Outgoing References.");
		instructions.add("Expand \"java.util.HashMap\", expand \"java.util.HashMap$Entry[]\", expand \"java.util.HashMap$Entry\".");
		instructions.add("Find <b>com.patientkeeper.monaco.cds.CdsEncounter</b>, and note the number of objects.");
		instructions.add("Enter the number of objects into the <i>CdsEncounter</i> input field.");
		String ordered = WebUtil.getOrderedList(instructions);
		
		setName("DEV-22287");
		setDescription("Fill this in later.");
		setInstructions(ordered);
		setFixVersion("7.6.4");
		setClassList(classList);				
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
			if(analyzedHeap.getBoolean(this.getName() + "_" + SESSION) && 
			   analyzedHeap.getBoolean(this.getName() + "_" + GETPATIENT) && 
			   analyzedHeap.getNumber(this.getName() + "_" + ENCOUNTER) >= classList.get(ENCOUNTER).getNumber()) {
				return true;				
			}
		return false;
	}
}