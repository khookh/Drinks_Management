package model.threads;

import model.JSONHandler;

import java.util.TimerTask;

/**
 * this class implements a thread which imitates the natural process of the alcool in the user's body
 * (hesitated to call it "virtual liver")
 */
public class ProcessAlcoolThread extends TimerTask {
	JSONHandler js;
	Double elimrate;
	public ProcessAlcoolThread(JSONHandler js){
		this.js = js;
		if(js.getActiveUser().getSex().equals("Man")) {
			this.elimrate = 0.125 ; //taux d'élimination horaire moyen chez l'homme (g/L par heure)
		}
		else{
			this.elimrate = 0.0925; //taux chez la femme
		}
	}
	public void run(){
				double newAR = js.getActiveUser().getAlcoolRate()-(this.elimrate/60);
				if(newAR<0){
					js.getActiveUser().setAlcoolRate(0.0);
				}
				else {
					js.getActiveUser().setAlcoolRate(newAR); //update user alcool rate
				}
				js.updateUser(js.getActiveUser()); //update user in json
				System.out.println("Remove"+this.elimrate/60);
	}
}

