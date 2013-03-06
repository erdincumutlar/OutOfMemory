package chabot.utils.outofmemory;

import java.util.HashMap;
import java.util.Map;

/*
 * cthonis
 * https://jira/browse/DEV-13586
 */

public class Defect_10 extends Signature {

	final String MULTI90 = "MultipleThreads90";
	final String MULTI200 = "MultipleThreads200";
	final String CHARGEDESKTOPRESULTLIST = "ChargeDesktopResultList";

	
	public Defect_10() {		
		
		BadClass class_1 = new BadClass();		
		class_1.setName(MULTI90);
		class_1.setQuestion("Are multiple threads consuming 90MB or more each?");
		
		BadClass class_2 = new BadClass();		
		class_2.setName(MULTI200);
		class_2.setQuestion("Are multiple threads consuming a combined total of 200MB or more?");
		
		BadClass class_3= new BadClass();		
		class_3.setName(CHARGEDESKTOPRESULTLIST);
		class_3.setQuestion("Do these threads contain references to <span class=\"class\">" + CHARGEDESKTOPRESULTLIST + "</span>?");
				
		Map<String, BadClass> classList = new HashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);	
		classList.put(class_3.getName(), class_3);		
		
		setName("DEV-13586");
		setDescription("Fill this in later.");
		setInstructions("Navigate to Java Basics | Thread Overview to view thread details:");
		setFixVersion("7.6.4");
		setClassList(classList);				
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
			if(analyzedHeap.getBoolean(this.getName() + "_" + MULTI90) && 
			   analyzedHeap.getBoolean(this.getName() + "_" + MULTI200) && 
			   analyzedHeap.getBoolean(this.getName() + "_" + CHARGEDESKTOPRESULTLIST)) {
				return true;				
			}
		return false;
	}
}