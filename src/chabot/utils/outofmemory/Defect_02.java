package chabot.utils.outofmemory;

import java.util.HashMap;
import java.util.Map;

/*
 * DEV-17975
 * https://jira/browse/DEV-17975
 */

public class Defect_02 extends Signature {

	final String JASPER = "JasperPrint";
	final String EXPORT = "JRXIsExporter";	
	
	public Defect_02() {		

		BadClass class_1 = new BadClass();		
		class_1.setName(JASPER);
		class_1.setNumber(400);
		class_1.setContext("MB");
		
		BadClass class_2 = new BadClass();		
		class_2.setName(EXPORT);
		class_2.setNumber(400);
		class_2.setContext("MB");
		
		Map<String, BadClass> classList = new HashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);
		
		setName("DEV-17975");
		setDescription("Fill this in later.");
		setFixVersion("5.1.0");
		setClassList(classList);			
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
		if ((analyzedHeap.getNumber(this.getName() + "_" + JASPER) >= classList.get(JASPER).getNumber() || 
		     analyzedHeap.getNumber(this.getName() + "_" + EXPORT) >= classList.get(EXPORT).getNumber())) {			
			return true;
		}		
		return false;
	}
}
