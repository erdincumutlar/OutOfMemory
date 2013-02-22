/*
 * @author tkain 
 */

package chabot.utils.outofmemory;

import java.util.HashMap;
import java.util.Map;

/*
 * DEV-36990
 * https://jira/browse/DEV-36990
 */

public class Defect_07 extends Signature {
	
	final String CDSENCOUNTER = "CdsEncounter";
	final String CACHEPOPULATOR = "ProviderCachePopulatorTask.run()";
	
	public Defect_07() {
	
	BadClass class_1 = new BadClass();
	class_1.setName(CDSENCOUNTER);
	class_1.setNumber(200000);
	class_1.setContext("Objects");
	
	BadClass class_2 = new BadClass();
	class_2.setName(CACHEPOPULATOR);
	class_2.setQuestion("Does <span class=\"class\">" + CACHEPOPULATOR + "</span> exist within the thread details of <span class=\"class\">pool-[#]-thread-[#]</span>?");
		
	Map<String, BadClass> classList = new HashMap<String, BadClass>();
	classList.put(class_1.getName(), class_1);
	classList.put(class_2.getName(), class_2);
	
	setName("DEV-36990");
	setDescription("Fill this in later.");
	setFixVersion("Dev To Manage");
	setInstructions("Identify the number of objects in the class below, and review thread details via Java Basics | Thread Details:");
	setClassList(classList);	
		
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {
		if (analyzedHeap.getNumber(this.getName() + "_" + CDSENCOUNTER) >= classList.get(CDSENCOUNTER).getNumber()) {
			return (analyzedHeap.getBoolean(this.getName() + "_" + CACHEPOPULATOR));
		}
		return false;
	}
}
