package com.patientkeeper.outofmemory;

import java.util.HashMap;
import java.util.Map;

public class AnalyzedHeap {
	
	Map<String, String> analyzedHeap = new HashMap<String, String>();
	
	public void setAnalyzedHeap(Map<String, String> analyzedHeap) {
		this.analyzedHeap = analyzedHeap;
	}
	
	public Map<String, String> getAnalyzedHeap() {
		return analyzedHeap;
	}	
	
	public Long getNumber(String field) {
		return  Long.parseLong(analyzedHeap.get(field));
	}
	
	public Boolean getBoolean(String field) {
		return (analyzedHeap.get(field).toUpperCase().equals("TRUE")) ? true : false;
	}
	
}
