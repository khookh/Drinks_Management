package com.example.defonce_management;

import model.JSONHandler;
import model.Session;
import model.SignUp;
import model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomConsTest {
	JSONHandler jsonHandler = new JSONHandler("testCONS.json");
	@Test
	public void customCons(){
		String name= Double.toString((Double) Math.random());
		String nameu = Double.toString((Double) Math.random());
		new SignUp(nameu, "email@email.com", "password", 25, "sex", 50.0, jsonHandler);
		jsonHandler.setActiveUser(jsonHandler.getUser(nameu));
		Session s = new Session(jsonHandler);
		s.addAlcohol(name,10.0,0.0,true);
		assertEquals(checkuserlist(name),true);
	}
	public boolean checkuserlist(String name){
		boolean inside = false;
		User user = jsonHandler.getActiveUser();
		for(int i = 0;i<user.getCustomAlcool().size();i++){
			System.out.println(i+" "+user.getCustomAlcool().get(i).getName());
			if (user.getCustomAlcool().get(i).getName().equals(name)) {
				System.out.println("OK");
				inside = true;
			}
		}
		return inside;
	}
}
