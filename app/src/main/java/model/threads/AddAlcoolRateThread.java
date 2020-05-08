package model.threads;

import model.Alcool;
import model.JSONHandler;
import model.Session;

import java.time.LocalDateTime;
import java.util.TimerTask;

/**
 * this class implements a thread corresponding to the absorption of a new drink
 */
public class AddAlcoolRateThread extends TimerTask {
	int min = 0;
	LocalDateTime init;
	JSONHandler js;
	Session s;
	Double alcoholqtPerMin; //la qt max d'alcool qui sera absorbée par le corps
	int eat = 1;

	public AddAlcoolRateThread(Alcool alcohol, JSONHandler js, Session session, Boolean eating){
		if(eating.equals(true)){
			this.eat = 2;
		}
		this.js = js;
		this.s = session;
		Double coef;
		if(js.getActiveUser().getSex().equals("Man")) {
			coef = 0.7 ; //coefficient de diffusion de l'alcool dans le sang chez l'homme
		}
		else{
			coef = 0.6; //coef chez la femme
		}
		this.alcoholqtPerMin = (alcohol.getVolume()*10*alcohol.getPercentage()/100)/(coef*js.getActiveUser().getWeight())/(30*this.eat);
	}

	public void run() {
				if(min == 30*eat){
					cancel();
				}else {
					System.out.println("add " + alcoholqtPerMin);
					min++;
					js.getActiveUser().setAlcoolRate(js.getActiveUser().getAlcoolRate() + alcoholqtPerMin); //update user alcool rate
					js.updateUser(js.getActiveUser()); //update user in json
				}
	}

}

