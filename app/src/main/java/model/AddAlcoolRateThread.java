package model;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

/**
 * this class implements a thread corresponding to the absorption of a new drink
 */
public class AddAlcoolRateThread extends Thread {
	LocalDateTime init;
	JSONHandler js;
	Session s;
	Double alcoholqtPerMin; //la qt max d'alcool qui sera absorbée par le corps
	Integer eat = 1;
	boolean running = true ;

	@RequiresApi(api = Build.VERSION_CODES.O)
	public AddAlcoolRateThread(Alcool alcohol, JSONHandler js, Session session, Boolean eating){
		if(eating.equals(true)){
			this.eat = 2;
		}
		this.init = LocalDateTime.now();
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



	@RequiresApi(api = Build.VERSION_CODES.O)
	public void run() {
		int min = 0;
		while (running) { ;
				int mint = LocalDateTime.now().getMinute()-init.getMinute();
				if(min==30*eat){
					setRunning(false); //stop la boucle  du run(), le thread est automatiquement supprimé
				}
				if(mint<0 || mint >0){
					System.out.println("add " + alcoholqtPerMin );
					min ++;
					js.getActiveUser().setAlcoolRate(js.getActiveUser().getAlcoolRate()+alcoholqtPerMin); //update user alcool rate
					js.updateUser(js.getActiveUser()); //update user in json
					init = LocalDateTime.now(); // reset time reference
				}
		}
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	public boolean getRunning() {
		return running;
	}

}

