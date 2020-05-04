package model;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class AddAlcoolRateThread extends Thread {
	LocalDateTime init;
	JSONHandler js;
	Session s;
	Double alcoholqt; //la qt max d'alcool qui sera absorbée par le corps
	boolean running = true ;

	@RequiresApi(api = Build.VERSION_CODES.O)
	public AddAlcoolRateThread(Alcool alcohol, JSONHandler js, Session session){
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
		this.alcoholqt = (alcohol.getVolume()*10*alcohol.getPercentage()/100)/(coef*js.getActiveUser().getWeight());
		System.out.println("alcoholqt "+alcoholqt);
	}



	@RequiresApi(api = Build.VERSION_CODES.O)
	public void run() {
		int min = 0;
		while (running) { ;
				int mint = LocalDateTime.now().getMinute()-init.getMinute();
				if(min==30){
					this.stop();
					break;
				}
				if(mint<0 || mint >0){
					Double add = 1.0/(30.0-min)*alcoholqt; //qt d'alcool absorbée en 1min
					System.out.println("add " + add );
					min ++;
					js.getActiveUser().setAlcoolRate(js.getActiveUser().getAlcoolRate()+add); //update user alcool rate
					alcoholqt = alcoholqt - add; // qt d'alcool non absorbée restante
					js.updateUser(js.getActiveUser()); //update user in json
					init = LocalDateTime.now(); // reset time reference
				}
		}
	}
	public void setRunning(boolean running) {
		this.running = running;
	}

}

