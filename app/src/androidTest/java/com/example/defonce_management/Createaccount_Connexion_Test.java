package com.example.defonce_management;

import androidx.test.runner.AndroidJUnit4;
import controller.SignUp_Control;
import model.SignIn;
import model.SignUp;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class Createaccount_Connexion_Test {

    @Test
    public void create_account(){
        String name = String.valueOf(getRandomDoubleBetweenRange(0.0,100.0));
        String email = String.valueOf(getRandomDoubleBetweenRange(0.0,100.0));
        String password = String.valueOf(getRandomDoubleBetweenRange(111111,999999));
        //sign Up
        SignUp signup = new SignUp();
        signup.signUp(name,email,password,20,"boi",85.0);
        assertEquals("failed to create account",SignUp_Control.getErrormessage4(),signup.getSignedup());
        //connexion
        SignIn.signIn(name, password);
        assertEquals("failed to sign in","Signed In",SignIn.getSignedin());
    }
    public static double getRandomDoubleBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }
}
