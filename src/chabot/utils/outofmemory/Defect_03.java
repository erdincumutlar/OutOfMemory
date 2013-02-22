package chabot.utils.outofmemory;

import java.util.HashMap;
import java.util.Map;

/*
 * DEV-19859
 * https://jira/browse/DEV-19859
 */

public class Defect_03 extends Signature {

	final String ACCT = "Account";
	final String ACCT_CONF = "GetAccountListForConfidentiality";	
	
	public Defect_03() {	
		
		BadClass class_1 = new BadClass();		
		class_1.setName(ACCT);
		class_1.setNumber(400);
		class_1.setContext("MB");
		
		BadClass class_2 = new BadClass();		
		class_2.setName(ACCT_CONF);
		class_2.setNumber(200);
		class_2.setContext("MB");
		
		Map<String, BadClass> classList = new HashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);
		
		setName("DEV-19859");
		setDescription("Fill this in later.");
		setInstructions("Calculate the retained heap sizes of the classes below:");
		setFixVersion("5.0.0");
		setClassList(classList);				
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
		if ((analyzedHeap.getNumber(this.getName() + "_" + ACCT) >= classList.get(ACCT).getNumber()) && 
		    (analyzedHeap.getNumber(this.getName() + "_" + ACCT_CONF) >= classList.get(ACCT_CONF).getNumber())) {			
			return true;
		}		
		return false;
	}
}