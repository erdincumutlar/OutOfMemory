package chabot.utils.outofmemory;

public class BadClass {

	private String name;
	private String question;
	private long number;
		
	public BadClass() {
		//
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getQuestion() {
		return question;
	}

	public void setNumber(long number) {
		this.number = number;
	}
	
	public long getNumber() {
		return number;
	}	
	
	public boolean isQuestion() {
		return question != null;
	}		
}
