package com.patientkeeper.outofmemory;

public class BadClass implements Comparable<BadClass>{

	private String name;
	private String question;
	private String context;
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
	
	public void setContext(String context) {
		this.context = context;
	}
	
	public String getContext() {
		return context;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BadClass other = (BadClass) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(BadClass bc) {
		if (bc.isQuestion() && !this.isQuestion()) {
			return -1;  // Put below
		}
		if (!bc.isQuestion() && this.isQuestion()) {
			return 1; // Put above
		}		
		return 0;
	}		
	
}
