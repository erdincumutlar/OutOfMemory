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
 * https://jira/browse/DEV-37951
 */

public class Dev_37951 extends Signature {
	
	final String TASKTHREAD = "TaskThread";
	final String PKNOTE = "PKNote";
	final String CDSFORMRESULT = "CdsFormResult";
	final String PKNOTEARRAYLIST ="PKNote (within ArrayList)";
	
	public Dev_37951() {
		
		BadClass class_1 = new BadClass();
		class_1.setName(TASKTHREAD);
		class_1.setQuestion("Is <span class=\"class\">" + TASKTHREAD + "</span> the largest thread in the dominator tree and retaining at least 100MB of the heap?");
		
		BadClass class_2 = new BadClass();
		class_2.setName(PKNOTE);
		class_2.setQuestion("Does <span class=\"class\">" + PKNOTE + "</span> or <span class=\"class\">" + CDSFORMRESULT + "</span> exist in the thread details?");
		
		BadClass class_3 = new BadClass();
		class_3.setName(PKNOTEARRAYLIST);
		class_3.setQuestion("Does <span class=\"class\">" + PKNOTEARRAYLIST  + "</span> exist within ArrayList?");
		
		LinkedHashMap<String, BadClass> classList = new LinkedHashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);
		classList.put(class_3.getName(), class_3);
		
		List<String> instructions = new ArrayList<String>(0); 
		instructions.add("Open the heap dump using Eclipse Memory Analyzer.");
		instructions.add("Open the Dominator Tree and sort by percent descending.");
		instructions.add("Determine if <strong> TaskThread </strong> is at the top and retains at least 100MB of the heap; update the <em>TaskThread</em> question accordingly.");
		instructions.add("Expand the TaskThread and determine if either <strong> PKNote </strong> or <strong> CdsFormResult </strong> classes exist and update the <em>PKNote</em> question accordingly."); 
		instructions.add("Expand the Object class directly under ArrayList.");
		instructions.add("Determine if <strong> PKNote </strong> exists within ArrayList and update the <em>PKNote (within ArrayList)</em> question accordingly.");
		String ordered = WebUtil.getOrderedList(instructions);
		
		setName ("Dev-37951");
		setDescription("Fill this in later.");
		setInstructions(ordered);
		setFixVersion("7.6.9");
		setClassList(classList);
		
	}

	public boolean evaluate(AnalyzedHeap analyzedHeap) {
		if (analyzedHeap.getBoolean(this.getName() + "_" + TASKTHREAD)
				&& (analyzedHeap.getBoolean(this.getName() + "_" + PKNOTE) || 
						analyzedHeap.getBoolean(this.getName() + "_" + PKNOTEARRAYLIST))) {
			return true;
		}
		return false;
	} 
	
}
