package com.patientkeeper.security;

import java.util.Date;

public class AuthenticationCookie {
	private static final String PASSPHRASE = "^pat13ntk33p3r$";
	
	private CryptoHelper crypto;
	private String username;
	private String password;
	
	public AuthenticationCookie() throws Exception {
		crypto = new CryptoHelper(PASSPHRASE);
    }
	
	public void decode(String encrypted) throws Exception {
        String decrypted = crypto.decrypt(encrypted);
        
        String[] parts = decrypted.split(":");
        if (parts == null || parts.length != 3) {
        	throw new Exception("Invalid cookie string specified");
        }
        
        username = parts[0];
        password = parts[1];
	}
	
	public String encode() throws Exception {
		String phrase = username + ":" + password + ":" + (new Date()).getTime();
		String encrypted = crypto.encrypt(phrase);
		return encrypted;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
