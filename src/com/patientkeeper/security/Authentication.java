package com.patientkeeper.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

public class Authentication implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String fullname;
	private TreeSet<String> roles;
	
	public Authentication() {
		roles = new TreeSet<String>();
	}
	
	protected void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	protected void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getFullname() {
		return (fullname != null ? fullname : username);
	}
	
	public TreeSet<String> getRoles() {
		return roles;
	}
	
	protected void addRole(String role) {
		roles.add(role);
	}
	
	protected void addRoles(ArrayList<String> list) {
		roles.addAll(list);
	}
	
	public boolean hasRole(String role) {
		if (role == null || role.equals("")) {
			return false;
		}
		
		return roles.contains(role);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Authentication=[");
		sb.append("username='").append(username).append("'");
		sb.append("]");
		return sb.toString();
	}
}
