package com.patientkeeper.outofmemory;

import java.util.HashMap;
import java.util.Map;

/*
 * DEV-22287
 * https://jira/browse/DEV-22287
 */

public class Defect_04 extends Signature {

	final String MULTI = "MultipleThreads";
	final String PATINTERACT = "PatientInteraction";
	final String CHARGE = "Charge";
	final String POSTDATA = "PostData";
	
	public Defect_04() {		
		
		BadClass class_1 = new BadClass();		
		class_1.setName(MULTI);
		class_1.setQuestion("Are multiple threads consuming a total near or over 900MB?");
		
		BadClass class_2= new BadClass();		
		class_2.setName(PATINTERACT);
		class_2.setQuestion("Do these threads contain references to <span class=\"class\">" + PATINTERACT + "</span>?");
		
		BadClass class_3 = new BadClass();		
		class_3.setName(CHARGE);
		class_3.setQuestion("Are there any <span class=\"class\">java.lang.String</span> objects that reference Charges?");
		
		BadClass class_4 = new BadClass();		
		class_4.setName(POSTDATA);
		class_4.setQuestion("Does PostData under <span class=\"class\">org.apache.catalina.connector.Request</span> reference ChargeDesktopResultList?");
		
		Map<String, BadClass> classList = new HashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);	
		classList.put(class_3.getName(), class_3);	
		classList.put(class_4.getName(), class_4);	
		
		setName("DEV-22287");
		setDescription("Fill this in later.");
		setInstructions("Navigate to Java Basics | Thread Overview to view thread details:");
		setFixVersion("7.6.4");
		setClassList(classList);				
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
			if(analyzedHeap.getBoolean(this.getName() + "_" + MULTI) && 
			   analyzedHeap.getBoolean(this.getName() + "_" + PATINTERACT) && 
			   analyzedHeap.getBoolean(this.getName() + "_" + CHARGE) && 
			   analyzedHeap.getBoolean(this.getName() + "_" + POSTDATA)) {
				return true;				
			}
		return false;
	}
}