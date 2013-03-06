package chabot.utils.outofmemory;

import java.util.HashMap;
import java.util.Map;

/*
 * cthonis
 * https://jira/browse/DEV-31450
 */

public class Defect_11 extends Signature {

	final String IMPL = "SessionImpl";
	final String VALCALLBACK = "Val$callback";
	final String ORDERDEF = "GetOrderDefinitionList";
	final String CDSCODE = "CdsCode";
	
	public Defect_11() {		
		
		BadClass class_1 = new BadClass();		
		class_1.setName(IMPL);
		class_1.setQuestion("Is there an instance of net.sf.hibernate.impl.SessionImpl that is over 100MB?");
		
		BadClass class_2 = new BadClass();		
		class_2.setName(VALCALLBACK);
		class_2.setQuestion("Does this instance contain a [this$0, val$callback] class?");
		
		BadClass class_3= new BadClass();		
		class_3.setName(ORDERDEF);
		class_3.setQuestion("Does the [this$0, val$callback] class reference GetOrderDefinition?");
		
		BadClass class_4= new BadClass();		
		class_3.setName(CDSCODE);
		class_3.setQuestion("Does com.patientkeeper.comaco.cds.CdsCode contain more than 100 thousand objects?");
		
		Map<String, BadClass> classList = new HashMap<String, BadClass>();
		classList.put(class_1.getName(), class_1);
		classList.put(class_2.getName(), class_2);	
		classList.put(class_3.getName(), class_3);
		classList.put(class_3.getName(), class_3);
		
		setName("DEV-31450");
		setDescription("Fill this in later.");
		setInstructions("Open the Dominator Tree and look for instances of net.sf.hibernate.impl.SessionImpl");
		setFixVersion("7.x CPOE Triage");
		setClassList(classList);				
	}
	
	public boolean evaluate(AnalyzedHeap analyzedHeap) {		
			if(analyzedHeap.getBoolean(this.getName() + "_" + IMPL) && 
			   analyzedHeap.getBoolean(this.getName() + "_" + VALCALLBACK) && 
			   analyzedHeap.getBoolean(this.getName() + "_" + ORDERDEF) &&
			   analyzedHeap.getBoolean(this.getName() + "_" + CDSCODE)){
				return true;				
			}
		return false;
	}
}