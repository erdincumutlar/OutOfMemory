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
 * https://jira/browse/DEV-39170
 */

public class Dev_39170 extends Signature {
	
	final String HHSUBMISSION = "java.lang.Thread @[address] Sync08:[user_nm]:2:[sessionlog_id]:Submission[PATIENTID-[pat_id]]";
	final String THREADWITHATTRIBUTES = "org.apache.tomcat.util.threads.ThreadWithAttributes @[address] http-0.0.0.0-31140-Processor6";
	final String PROBLEMLIST = "ProblemList";
	
	public Dev_39170() {
		
		BadClass class_1 = new BadClass();
		class_1.setName(HHSUBMISSION);
		class_1.setQuestion("Is the largest thread in the dominator tree like <span class=\"class\">" + HHSUBMISSION + "</span> ?");
		
		BadClass class_2 = new BadClass();
		class_2.setName(THREADWITHATTRIBUTES);
		class_2.setQuestion("Is the largest thread in the dominator tree like <span class=\"class\">" + THREADWITHATTRIBUTES + "</span> ?");
		
		BadClass class_3 = new BadClass();
		class_3.setName(PROBLEMLIST);
		class_3.setQuestion("Is <span class=\"class\">" + PROBLEMLIST + "</span> the first or second largest class within the largest thread?");
		
		LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);
		classList.put(class_3.getName(), class_3);
		
		List<String> instructions = new ArrayList<String>(0);
		instructions.add("Open the heap dump using Eclipse Memory Analyzer.");
		instructions.add("Open the Dominator Tree and sort by percent descending.");
		instructions.add("Determine if either of the below class syntaxes are the largest class and update the <em>largest threads</em> questions.");
		instructions.add("A HH thread like <strong>) java.lang.Thread @<address> Sync08:<user_nm>:2:<sessionlog_id>:Submission[PATIENTID-<pat_id>]</strong>.");
		instructions.add("A web thread (or ThreadWithAttributes) like <strong>org.apache.tomcat.util.threads.ThreadWithAttributes @<address> http-0.0.0.0-31140-Processor6</strong>.");
		instructions.add("Expand this thread, determine if <strong>com.patientkeeper.datamodel.ProblemList</strong> is the first or second largest class, and update the <em>ProblemList</em> question accordingly.");
		String ordered = WebUtil.getOrderedList(instructions);
		
		setName("DEV-39170");
		setDescription("Fill this in later.");
		setInstructions(ordered);
		setFixVersion("7.6.6");
		setClassList(classList);		
	}
	
	public boolean evalute(AnalyzedHeap analyzedHeap) {
		if ((analyzedHeap.getBoolean(this.getName() + "_" + HHSUBMISSION) || 
			(analyzedHeap.getBoolean(this.getName() + "_" + THREADWITHATTRIBUTES))) && 
			analyzedHeap.getBoolean(this.getName() + "_" + PROBLEMLIST)) {
			return true;
		}
		return false;
	}	
}
