// @author = tkain 

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
	class_1.setNumber(200000);//object count
	
	BadClass class_2 = new BadClass();
	class_2.setName(CACHEPOPULATOR);
	class_2.setQuestion("Does " + CACHEPOPULATOR + " exist within the thread details of pool-NN-thread-Y (i.e. pool-27-thread-2)?");
	
	}//end defect
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {
		if (analyzedHeap.getNumber(this.getName() + "_" + CDSENCOUNTER) >= classList.get(CDSENCOUNTER).getNumber()) {
			return (analyzedHeap.getBoolean(this.getName() + "_" + CACHEPOPULATOR));
		}//end if
		return false;
	}//end evaluate
}//end class
