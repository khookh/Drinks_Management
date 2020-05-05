package com.example.defonce_management;

import model.JSONHandler;
import model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersistanceTest {
	@Test
	public void PersistanceTest(){
		String nickname = Double.toString((Double) Math.random());
		JSONHandler jsonHandlerA = new JSONHandler("persistanceTEST");
		User testuser = new User(nickname, "email@email.com", 50.0, 25, "password", "sex");
		jsonHandlerA.addUser(testuser);

		JSONHandler jsonHandlerB = new JSONHandler("persistanceTEST");
		assertEquals(true,jsonHandlerB.doesUserExist(nickname));
	}

}
