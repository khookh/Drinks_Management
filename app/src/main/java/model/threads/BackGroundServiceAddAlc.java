package model.threads;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import controller.WelcomePage;
import model.Alcool;
import model.JSONHandler;

import java.util.Timer;

public class BackGroundServiceAddAlc extends Service
{
	private Timer timer;
	private JSONHandler js;
	private Alcool alcool;
	private boolean eating;

	public IBinder onBind(Intent arg0)
	{
		return null;
	}

	public BackGroundServiceAddAlc() {}

	public void onCreate()
	{
		this.js = WelcomePage.getJsonHandler();
		super.onCreate();
	}
	public int onStartCommand(Intent intent, int flags, int startId) {
		this.alcool = (Alcool) intent.getSerializableExtra("alcool");
		this.eating = (boolean) intent.getSerializableExtra("eating");
		startAddService(js);
		return Service.START_NOT_STICKY;
	}

	public void startAddService(JSONHandler js){
		timer = new Timer();
		timer.scheduleAtFixedRate(new AddAlcoolRateThread(alcool,js,eating,this),60000,60000);
	}


	public void onDestroy()
	{
		super.onDestroy();
		timer.cancel();
		//Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
	}


}