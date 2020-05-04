package com.example.defonce_management;

import model.JSONHandler;
import model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JSONTest {

	JSONHandler jsonHandler = new JSONHandler("test.json");
	@Test
	public void adduser(){
		User testuser = new User("Test","Email",50.0,25,"password","sex");
		this.jsonHandler.setActiveUser(testuser);
		this.jsonHandler.addUser(testuser);
		assertEquals(testuser,this.jsonHandler.getUser("Test"));
	}
}
