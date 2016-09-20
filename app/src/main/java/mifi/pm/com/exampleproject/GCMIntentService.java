package mifi.pm.com.exampleproject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;













import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gcm.GCMBaseIntentService;
import com.squareup.picasso.Picasso;

import mifi.pm.com.exampleproject.handler.AppMessages;
import mifi.pm.com.exampleproject.handler.AppSharedPreferences;
import mifi.pm.com.exampleproject.handler.JsonUtils;


public class GCMIntentService extends GCMBaseIntentService {

	private static final String PROJECT_ID = "257696593707";
	private static final String TAG = "GCMIntentService";
	SharedPreferences sharedpreferences;
	public GCMIntentService()
	{
		super(PROJECT_ID);
		Log.d(TAG, "GCMIntentService init");
	}


	@Override
	protected void onError(Context ctx, String sError) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Error: " + sError);

	}

	@Override
	protected void onMessage(Context ctx, Intent intent) {
		int count=0;

		Log.d(TAG, "Message Received");
		String message = intent.getStringExtra("message");
		Log.d(TAG, "Message Received:-"+message);
		//sendGCMIntent(ctx,message);

		Example ex=JsonUtils.fromJson(message,Example.class);
		Log.d(TAG, "Message Received:-"+ex.getAdvertisement().getDescription());
		if(ex.getAdvertisement().getType().equalsIgnoreCase("text")){
		AppSharedPreferences.getInstance(ctx).setPrefrence(AppMessages.Constants.ADVT,message);
			Intent i = new Intent().setClass(ctx, TextScrollHome.class);
			i.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ctx.startActivity(i);
		}
		else{
			Picasso.with(ctx).load(ex.getAdvertisement().getAdUrl()).fetch();
			AppSharedPreferences.getInstance(ctx).setPrefrence(AppMessages.Constants.ADVT1,message);
			Intent i = new Intent().setClass(ctx, AdvtActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ctx.startActivity(i);
		}

		//Intent ServiceIntent = new Intent(getApplicationContext(), LoginService.class);
		//startService(ServiceIntent);
		generateNotification(ctx,ex.getAdvertisement().getDescription());
	}


	private void sendGCMIntent(Context ctx, String message) {

		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("GCM_RECEIVED_ACTION");

		broadcastIntent.putExtra("gcm", message);

		ctx.sendBroadcast(broadcastIntent);

	}


	@Override
	protected void onRegistered(Context ctx, String regId) {
		// TODO Auto-generated method stub
		// send regId to your server
		Log.d(TAG, regId);

	}

	@Override
	protected void onUnregistered(Context ctx, String regId) {
		// TODO Auto-generated method stub
		// send notification to your server to remove that regId

	}



	@SuppressLint("NewApi")
	public void generateNotification(Context context, String message){
		System.out.println(message+"++++++++++2");

		NotificationManager manager;
		Notification myNotication;
		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Intent intent = new Intent(context, DashBoard.class);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);

		Notification.Builder builder = new Notification.Builder(context);

		builder.setAutoCancel(false);
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

	private void setotp(String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("OTP", value);
		editor.commit();
	}
	private String getotp() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		return pref.getString("OTP", "");
	}

	/*	public String createJsonCampaign(String type, String campaign_name, String campaign_filetype, String campaign_fileurl, String campaign_promotetype
					,String campaign_promotdate,String campaign_createdate  ) {

				try {
					String jsonString = "";
					Long id = System.nanoTime();
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("stanza-name", "advertisement");
					jsonObject.put("stanza-type", type);
					JSONObject jsonObject2 = new JSONObject();
					jsonObject2.put("campaign_name", campaign_name);
					jsonObject2.put("campaign_filetype", campaign_filetype);
					jsonObject2.put("campaign_fileurl", campaign_fileurl);
					jsonObject2.put("campaign_promotetype", campaign_promotetype);
					jsonObject2.put("campaign_promotdate", campaign_promotdate);
					jsonObject2.put("campaign_createdate", campaign_createdate);
					jsonObject.put("stanza-attributes", jsonObject2);
					jsonString = jsonObject.toString();

					System.out.println("res : \n"+jsonString);
					return jsonString+"\n";
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();

					return "";
				}
			}*/


}
