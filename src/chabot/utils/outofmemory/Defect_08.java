/*
 * @author tkain 
 */

package chabot.utils.outofmemory;

import java.util.HashMap;
import java.util.Map;

/*
 * DEV-39170
 * https://jira/browse/DEV-39170
 */

public class Defect_08 extends Signature {
	
	final String HHSUBMISSION = "java.lang.Thread @[address] Sync08:[user_nm]:2:[sessionlog_id]:Submission[PATIENTID-[pat_id]]";
	final String THREADWITHATTRIBUTES = "org.apache.tomcat.util.threads.ThreadWithAttributes @[address] http-0.0.0.0-31140-Processor6";
	final String PROBLEMLIST = "ProblemList";
	
	public Defect_08() {
		
		BadClass class_1 = new BadClass();
		class_1.setName(HHSUBMISSION);
		class_1.setQuestion("Is the largest thread in the dominator tree like " + HHSUBMISSION + " ?");
		
		BadClass class_2 = new BadClass();
		class_2.setName(THREADWITHATTRIBUTES);
		class_2.setQuestion("Is the largest thread in the dominator tree like " + THREADWITHATTRIBUTES + " ?");
		
		BadClass class_3 = new BadClass();
		class_3.setName(PROBLEMLIST);
		class_3.setQuestion("Is the first or second largest class within the largest thread " + PROBLEMLIST + " ?");
		
		Map<String, BadClass> classList = new HashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);
		classList.put(class_3.getName(), class_3);
		
		setName("DEV-39170");
		setDescription("Fill this in later.");
		setFixVersion("7.6.6");
		setClassList(classList);
		
	}//end Defect
	
	public boolean evalute(AnalyzedHeap analyzedHeap) {
		if (analyzedHeap.getBoolean(this.getName() + "_" + HHSUBMISSION) || (analyzedHeap.getBoolean(this.getName() + "_" + THREADWITHATTRIBUTES))) {
			return (analyzedHeap.getBoolean(this.getName() + "_" + PROBLEMLIST));
		}//end if
		return false;
	}//end evaluate
	
}//end class
