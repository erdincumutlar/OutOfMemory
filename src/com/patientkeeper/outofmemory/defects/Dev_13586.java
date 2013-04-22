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
 * https://jira/browse/DEV-13586
 */

public class Dev_13586 extends Signature {

	final String MULTI90 = "MultipleThreads90";
	final String MULTI200 = "MultipleThreads200";
	final String CDRL = "ChargeDesktopResultList";
	final String CDRLCHARGE = "ChargeDesktopResultList[Charges] ";
	
	public Dev_13586() {		
		
		BadClass class_1 = new BadClass();		
		class_1.setName(MULTI90);
		class_1.setQuestion("Does <span class=\"class\">" + CDRL + "</span> appear in multiple threads that are larger than 90MB?");
		
		BadClass class_2 = new BadClass();		
		class_2.setName(MULTI200);
		class_2.setQuestion("Does <span class=\"class\">" + CDRL + "</span> appear in multiple threads that have a combined size of 200MB or larger?");
		
		BadClass class_3= new BadClass();		
		class_3.setName(CDRLCHARGE);
		class_3.setQuestion("If there is only one thread, do the Outgoing References reference <span class=\"class\">" + CDRLCHARGE + "</span> ?");
				
		LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);	
		classList.put(class_3.getName(), class_3);	
		
		List<String> instructions = new ArrayList<String>();
		instructions.add("Open the heap dump using Eclipse Memory Analyzer.");
		instructions.add("From the toolbar, select the \"Open Query Browser\" icon, and choose Java Basics | Thread Overview");
		instructions.add("Sort the retained heap size by largest to smallest.");
		instructions.add("Locate the thread(s) at the top of the list. Select them individually.");
		instructions.add("Right-click and select Java Basics | Thread Details.");
		instructions.add("Search the stack trace for <b>ChargeDesktopResultList</b>.");
		instructions.add("Determine if this class appears in multiple threads that are each 90MB or larger.");
		instructions.add("If each thread is smaller than 90MB, add up the total of the large threads to see if they are 200MB or larger.");
		instructions.add("If there is only one thread, examine the outgoing objects by right-clicking the largest thread and select List Objects | With Outgoing References.");
		instructions.add("Expand the thread and sort by classname, search for <b>ChargeDesktopResultList[Charges]</b>.");
		String ordered = WebUtil.getOrderedList(instructions);
				
		setName("DEV-13586");
		setInstructions(ordered);
		setDescription("Fill this in later.");
		setFixVersion("7.6.4");
		setClassList(classList);				
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
			if(analyzedHeap.getBoolean(this.getName() + "_" + MULTI90) || (
			   analyzedHeap.getBoolean(this.getName() + "_" + MULTI200) || 
			   analyzedHeap.getBoolean(this.getName() + "_" + CDRL))) {
				return true;				
			}
		return false;
	}
}