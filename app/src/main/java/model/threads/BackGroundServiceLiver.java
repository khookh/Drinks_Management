package model.threads;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import controller.WelcomePage;
import model.JSONHandler;

import java.util.Timer;

public class BackGroundServiceLiver extends Service
{
	private JSONHandler js;

	private Timer timer;

	public IBinder onBind(Intent arg0)
	{
		return null;
	}

	public BackGroundServiceLiver() {}

	public void onCreate()
	{
		this.js = WelcomePage.getJsonHandler();
		super.onCreate();
		startFoieService(js);
	}
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_NOT_STICKY;
	}

	public void startFoieService(JSONHandler js){
		timer = new Timer();
		timer.scheduleAtFixedRate(new ProcessAlcoolThread(js),60000,60000);
	}


	public void onDestroy()
	{
		timer.cancel();
		super.onDestroy();
		//Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
	}


}