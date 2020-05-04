package com.example.defonce_management;

import model.JSONHandler;
import model.SignIn;
import model.SignUp;
import model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JSONTest {

	JSONHandler jsonHandler = new JSONHandler("test.json");
	@Test
	public void JsonTest(){
		for(int i = 0; i <= 50; i++) {
			String nickname = Double.toString((Double) Math.random());
			String password = Double.toString((Double) Math.random());
			User testuser = new User(nickname, "email@email.com", 50.0, 25, password, "sex");

			SignUp su = new SignUp(nickname, "email@email.com", password, 25, "sex", 50.0, jsonHandler);
			assertEquals(su.getSignedup(), "Signed Up"); //check si sign up s'est déroulé
			assertEquals(testuser.getName(), this.jsonHandler.getUser(nickname).getName()); //check si le user est créé et identique

			SignIn si = new SignIn(testuser.getName(), testuser.getPassword(), jsonHandler);
			assertEquals(si.getSignedin(), "Signed In");//check si le sign in fonctionne
		}
	}
}
