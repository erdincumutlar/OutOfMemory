package chabot.utils.outofmemory;

import java.util.Map;

public abstract class Signature implements Comparable<Signature>{

	private String name;
	private String description;	
	private String fixVersion;
		
	public Map<String, BadClass> classList;
	
	public Signature() {
		//
	}	

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public void setFixVersion(String fixVersion) {
		this.fixVersion = fixVersion;
	}
		
	public String getFixVersion() {
		return fixVersion;
	}
	
	public long getFixVersionNormalized() {
		try {
			return Long.parseLong(fixVersion.replace(".", ""));
		}
		catch (NumberFormatException nfe) {
			return 1000000;
		}
	}
	
	public void setClassList(Map<String, BadClass>  classList) {
		this.classList = classList;
	}

	public Map<String, BadClass> getClassList() {
		return classList;
	}
	
	public boolean hasQuestion() {	
		for(BadClass clazz : classList.values()) {
			if(clazz.isQuestion()) {
				return true;
			}
		}
		return false;
	}	

	public boolean evaluate(AnalyzedHeap analyzedHeap) {	
		return false;
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
		Signature other = (Signature) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public int compareTo(Signature sig) {		
		if (sig.hasQuestion() && !this.hasQuestion()) {
			System.out.println(sig.getName() + " - " + this.getName());
			return -1;  // Put below
		}
		if (!sig.hasQuestion() && this.hasQuestion()) {
			System.out.println(sig.getName() + " - " + this.getName());
			return 1; // Put above
		}		
		System.out.println(sig.getName() + " - " + this.getName());
		return sig.getName().compareTo(this.getName()) * -1;
	}
	
}
