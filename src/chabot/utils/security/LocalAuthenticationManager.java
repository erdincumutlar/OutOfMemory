package chabot.utils.security;

import java.util.ArrayList;

public class LocalAuthenticationManager extends AuthenticationManager {

	private String localUsername;
	private String localFullname;
	private String localPassword;
	private ArrayList<String> localRoles;
	
	public LocalAuthenticationManager() {
		localRoles = new ArrayList<String>(0);
	}
	
	public synchronized Authentication authenticate(String username, String password) {
		if (username == null || username.equals("")) {
			return null;
		}
		if (password == null || password.equals("")) {
			return null;
		}
		
		if (!username.equals(localUsername)) {
			return null;
		}
		if (!password.equals(localPassword)) {
			return null;
		}
		
		Authentication auth = new Authentication();
		auth.setUsername(localUsername);
		auth.setFullname(localFullname);
		for (String role : localRoles) {
			auth.addRole(role);
		}
		return auth;
	}
	
	public String getLocalUsername() {
		return localUsername;
	}
	
	public void setLocalUsername(String localUsername) {
		this.localUsername = localUsername;
	}
	
	public String getLocalFullname() {
		return localFullname;
	}
	
	public void setLocalFullname(String localFullname) {
		this.localFullname = localFullname;
	}
	
	public String getLocalPassword() {
		return localPassword;
	}
	
	public void setLocalPassword(String localPassword) {
		this.localPassword = localPassword;
	}
	
	public ArrayList<String> getLocalRoles() {
		return localRoles;
	}
	
	public void setLocalRoles(ArrayList<String> localRoles) {
		this.localRoles = localRoles;
	}
	
	public void addLocalRole(String localRole) {
		localRoles.add(localRole);
	}
}
