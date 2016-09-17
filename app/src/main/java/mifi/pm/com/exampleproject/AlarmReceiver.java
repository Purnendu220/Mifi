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
                      new AsyncTaskUpdate().execute();

		}

	}

	public class AsyncTaskUpdate extends AsyncTask<String, String, String> {

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
			String str1=strFromDoInBg.replace("<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">","");
			String str2=strFromDoInBg.replace("<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">09/08/2016 15:50:20</attr><attr id=\"sessionDuration\">","");
			String str3=strFromDoInBg.replace("<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">09/08/2016 15:50:20</attr><attr id=\"sessionDuration\">00:02:57</attr><attr id=\"sessionUsage\">","");
			String sessionStart=str1.replace("</attr><attr id=\"sessionDuration\">00:02:57</attr><attr id=\"sessionUsage\">12 KB</attr></response>","");
			String sessionDuration=str2.replace("</attr><attr id=\"sessionUsage\">12 KB</attr></response>","");
			String sessionUsage=str3.replace("</attr></response>","");

			Log.e("STR","sessionStart:-"+sessionStart);
			Log.e("STR","sessionDuration:-"+sessionDuration);
			Log.e("STR","sessionUsage:-"+sessionUsage);
			refrenceWrapper.gestatusliste().OnStatusConnected(sessionStart,sessionDuration,sessionUsage);
			//re
		}
	}


/*	@SuppressWarnings("deprecation")
	private void generateNotification(Context context, String message) {
		System.out.println(message + "++++++++++2");
		int icon = R.drawable.icon1;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);
		String title = context.getString(R.string.app_name);
		String subTitle = message;
		Intent notificationIntent = new Intent(context, Expired.class);
		notificationIntent.putExtra("content", message);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		notification.setLatestEventInfo(context, title, subTitle, intent);
		// To play the default sound with your notification:
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(0, notification);

	}*/


}