package mifi.pm.com.exampleproject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.media.AudioManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import mifi.pm.com.exampleproject.handler.AppSharedPreferences;
import mifi.pm.com.exampleproject.handler.HttpRequest;
import mifi.pm.com.exampleproject.handler.SimpleDividerItemDecoration;
import mifi.pm.com.exampleproject.interfaces.VerticalChatAdapter;

public class AlarmReceiver extends BroadcastReceiver {
	private final String SOMEACTION = "com.mts.alarm.ACTION";
	Context context = null;
	WifiManager wifi;
	List<ScanResult> results;
	private ArrayList<String> listWifi;
	RefrenceWrapper refrenceWrapper;
	int size = 0;
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		String action = intent.getAction();
		if (SOMEACTION.equals(action)) {
			Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String formateddate = sdf.format(c.getTime());
			refrenceWrapper =RefrenceWrapper.getRefrenceWrapper(context);
                      new AsyncTaskUpdate(context).execute();

		}

	}

	public class AsyncTaskUpdate extends AsyncTask<String, String, String> {
		Context mContext;
		public AsyncTaskUpdate(Context mContext) {
			this.mContext=mContext;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(String... arg0) {

			String res=HttpRequest.StatusRequest();

			return res;

		}

		@Override
		protected void onPostExecute(String strFromDoInBg) {
			Log.e("TAG","Resp Status"+strFromDoInBg);
			try{
			String str1="<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">";
			String str2="<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">09/08/2016 15:50:20</attr><attr id=\"sessionDuration\">";
			String str3="<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">09/08/2016 15:50:20</attr><attr id=\"sessionDuration\">00:02:57</attr><attr id=\"sessionUsage\">";
			String sessionStart=strFromDoInBg.substring(str1.length(),str1.length()+19);
			String sessionDuration=strFromDoInBg.substring(str2.length(),str2.length()+8);
			String sessionUsage=strFromDoInBg.substring(str3.length(),str3.length()+5);

			Log.e("STR","sessionStart:-"+sessionStart);
			Log.e("STR","sessionDuration:-"+sessionDuration);
			Log.e("STR","sessionUsage:-"+sessionUsage);
			Log.e("STR","Time"+sessionDuration.substring(3,5));

				if(Integer.parseInt(sessionDuration.substring(3,5))>10){
					HttpRequest.LogOutRequest();
					AppSharedPreferences.getInstance(mContext).setPrefrence(false);
					generateNotification(mContext,"Your session for today has Expired");
				}
				else{
					Intent i=new Intent(mContext,TextScrollHome.class);
					mContext.startActivity(i);
					generateNotification(mContext,"Your session Duration is"+sessionDuration+" and session usage is "+sessionUsage);

				}
				refrenceWrapper.gestatusliste().OnStatusConnected(sessionStart,sessionDuration,sessionUsage);

			}
			catch (Exception e){
				e.printStackTrace();}
			//re
		}
	}


	@SuppressLint("NewApi")
	public void generateNotification(Context context, String message){
		System.out.println(message+"++++++++++2");

		NotificationManager manager;
		Notification myNotication;
		manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

		Intent intent = new Intent(context, FirstActivity.class);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);

		Notification.Builder builder = new Notification.Builder(context);

		builder.setAutoCancel(true);
		builder.setTicker("");
		builder.setContentTitle(context.getString(R.string.app_name));
		builder.setContentText(message);
		builder.setSmallIcon(R.mipmap.ic_launcher);
		builder.setContentIntent(pendingIntent);
		builder.setOngoing(true);
		builder.setNumber(100);
		builder.build();

		myNotication = builder.getNotification();
		manager.notify(11, myNotication);
	}

}