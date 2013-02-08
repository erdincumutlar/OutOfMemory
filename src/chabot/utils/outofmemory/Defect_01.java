package chabot.utils.outofmemory;

import java.util.HashMap;
import java.util.Map;

/*
 * DEV-13285
 * https://jira/browse/DEV-13285
 */

public class Defect_01 extends Signature {

	final String COLUMNDATA = "CrosstabColumnData";
	final String COLUMNROW = "CrossColumnRow";	
		
	public Defect_01() {		
		
		BadClass class_1 = new BadClass();		
		class_1.setName(COLUMNDATA);
		class_1.setNumber(400);
		
		BadClass class_2 = new BadClass();		
		class_2.setName(COLUMNROW);
		class_2.setNumber(400);		
		
		Map<String, BadClass> classList = new HashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);		
				
		setName("DEV-13285");
		setDescription("Fill this in later.");
		setFixVersion("5.1.0");
		setClassList(classList);				
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
		if ((analyzedHeap.getNumber(this.getName() + "_" + COLUMNDATA) + analyzedHeap.getNumber(this.getName() + "_" + COLUMNROW)) >= classList.get(COLUMNDATA).getNumber()) {			
			return true;
		}		
		return false;
	}
}
