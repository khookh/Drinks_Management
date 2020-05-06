package model;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

/**
 * this class implements a thread which imitates the natural process of the alcool in the user's body
 * (hesitated to call it "virtual liver")
 */
public class ProcessAlcoolThread extends Thread {
	LocalDateTime init;
	JSONHandler js;
	Session s;
	Double elimrate;
	boolean running = true;
	@RequiresApi(api = Build.VERSION_CODES.O)
	public ProcessAlcoolThread(JSONHandler js, Session session){
		this.init = LocalDateTime.now();
		this.js = js;
		this.s = session;

		if(js.getActiveUser().getSex().equals("Man")) {
			this.elimrate = 0.125 ; //taux d'élimination horaire moyen chez l'homme (g/L par heure)
		}
		else{
			this.elimrate = 0.0925; //taux chez la femme
		}
	}
	@RequiresApi(api = Build.VERSION_CODES.O)
	public void run(){
		while (running) {
			int mint = LocalDateTime.now().getMinute()-init.getMinute();
			if(mint<0 || mint >0){
				Double newAR = js.getActiveUser().getAlcoolRate()-(this.elimrate/60);
				if(newAR<0){ //pas de niveau d'alcool négatif
					js.getActiveUser().setAlcoolRate(0.0);
				}
				else {
					js.getActiveUser().setAlcoolRate(js.getActiveUser().getAlcoolRate() - (this.elimrate /60)); //update user alcool rate
				}
				js.updateUser(js.getActiveUser()); //update user in json
				init = LocalDateTime.now(); // reset time reference
				System.out.println("Remove"+this.elimrate/60);
			}
		}
	}
	public void setRunning(boolean running) {
		this.running = running;
	}

}

