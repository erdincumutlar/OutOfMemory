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
 * https://jira/browse/DEV-31450
 * 
 * 130726 Updated per Jon Piro to DEV-11749 for 4.x only - tkain
 */

public class Dev_11749 extends Signature {

	final String IMPL = "net.sf.hibernate.impl.SessionImpl";
	final String VALCALLBACK = "Val$callback";
	final String ORDERDEF = "GetOrderDefinitionList";
	final String CDSCODE = "com.patientkeeper.comaco.cds.CdsCode";
	
	public Dev_11749() {		
		
		BadClass class_1 = new BadClass();		
		class_1.setName(IMPL);
		class_1.setQuestion("Is there an instance of <span class=\"class\">" + IMPL + "</span> that is over 100MB?");
		
		BadClass class_2 = new BadClass();		
		class_2.setName(VALCALLBACK);
		class_2.setQuestion("Does this instance contain a <span class=\"class\">" + VALCALLBACK + "</span> class?");
		
		BadClass class_3= new BadClass();		
		class_3.setName(ORDERDEF);
		class_3.setQuestion("Does the <span class=\"class\">" + VALCALLBACK + "</span> class reference <span class=\"class\">" + ORDERDEF + "</span>?");
		
		BadClass class_4= new BadClass();		
		class_4.setName(CDSCODE);
		class_4.setQuestion("Does <span class=\"class\">" + CDSCODE + "</span> contain more than 100 thousand objects?");
		
		LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);	
		classList.put(class_3.getName(), class_3);
		classList.put(class_4.getName(), class_4);
		
		List<String> instructions = new ArrayList<String>();
		instructions.add("Open the heap dump using Eclipse Memory Analyzer.");
		instructions.add("Open the Dominator Tree.");
		instructions.add("Look for instance: net.sf.hibernate.impl.SessionImpl that is over 100MB retained size");
		instructions.add("Right-click, select List Objects | With Outgoing References");
		instructions.add("Expand net.sf.hibernate.impl.SessionImpl @ 0xNNNNNNN");
		instructions.add("Expand \"interceptor\" object");
		instructions.add("Expand \"val$callback\" sub object");
		instructions.add("Select \"this$0, val$callback\"");
		instructions.add("See if the the class is: <b>com.patientkeeper.monaco.GetOrderDefinitionList<b>");
		instructions.add("Right-click on entitiesByKey and select Show Objects By Class | By Outgoing References");
		instructions.add("Expand java.util.HashMap, expand java.util.HashMap$Entry[], expand java.util.HashMap$Entry");
		instructions.add("Look for <b>com.patientkeeper.monaco.cds.CdsCode</i> with 100k or more objects");
		instructions.add("Repeat steps 3-11 if more than 1 SessionImpl greater than 100MB");
		String ordered = WebUtil.getOrderedList(instructions);
		
		setName("DEV-11749");
		setInstructions(ordered);
		setDescription("Fill this in later.");
		setFixVersion("4.6.6.0");
		setClassList(classList);				
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
			if(analyzedHeap.getBoolean(this.getName() + "_" + IMPL) && 
			   analyzedHeap.getBoolean(this.getName() + "_" + VALCALLBACK) && 
			   analyzedHeap.getBoolean(this.getName() + "_" + ORDERDEF) &&
			   analyzedHeap.getBoolean(this.getName() + "_" + CDSCODE)){
				return true;				
			}
		return false;
	}
}