package com.example.dao;

import org.mindrot.jbcrypt.BCrypt;

public class Program {

	public static void main(String[] args) {
		 String password = "ram";
	        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
	        System.out.println("Hashed Password: " + hashedPassword);
	}

}
