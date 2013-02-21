package chabot.utils.outofmemory;

import java.util.HashMap;
import java.util.Map;

/*
 * DEV-15997
 * https://jira/browse/DEV-15997
 */

public class Defect_05 extends Signature {

	final String COLUMNDATA = "CrossTabColumnData";
	final String COLUMNROW = "CrossTabRowData";	
		
	public Defect_05() {		
		
		BadClass class_1 = new BadClass();		
		class_1.setName(COLUMNDATA);
		class_1.setNumber(200);
		class_1.setContext("MB");
		
		BadClass class_2 = new BadClass();		
		class_2.setName(COLUMNROW);
		class_2.setNumber(200);	
		class_2.setContext("MB");
		
		Map<String, BadClass> classList = new HashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);		
				
		setName("DEV-15997");
		setDescription("Fill this in later.");
		setFixVersion("7.x Triage");
		setClassList(classList);				
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
		if ((analyzedHeap.getNumber(this.getName() + "_" + COLUMNDATA) >= classList.get(COLUMNDATA).getNumber() || 
			analyzedHeap.getNumber(this.getName() + "_" + COLUMNROW) >= classList.get(COLUMNROW).getNumber())) {			
			return true;
		}		
		return false;
	}
}
