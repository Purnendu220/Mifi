package mifi.pm.com.exampleproject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



import com.google.android.gcm.GCMRegistrar;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.os.Build;
import android.os.IBinder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

public class ReService extends Service {

	/** indicates how to behave if the service is killed */
	int mStartMode;

	/** interface for clients that bind */
	IBinder mBinder;     

	/** indicates whether onRebind should be used */
	boolean mAllowRebind;
	private static final String PROJECT_ID = "257696593707";
	String regId,registrationStatus;
	String TAG="Aircel";
	boolean threadstatus=true;
	/** Called when the service is being created. */
	@Override
	public void onCreate() {

	}
	public String registerClient() {

		try {
			// Check that the device supports GCM (should be in a try / catch)
			GCMRegistrar.checkDevice(this);

			// Check the manifest to be sure this app has all the required
			// permissions.
			GCMRegistrar.checkManifest(this);

			// Get the existing registration id, if it exists.
			regId = GCMRegistrar.getRegistrationId(this);

			if (regId.equals("")) {

				registrationStatus = "Registering...";


				// register this device for this project
				GCMRegistrar.register(this, PROJECT_ID);
				regId = GCMRegistrar.getRegistrationId(this);

				registrationStatus = "Registration Acquired";

				// This is actually a dummy function.  At this point, one
				// would send the registration id, and other identifying
				// information to your server, which should save the id
				// for use when broadcasting messages.

				boolean stat=sendRegistrationToServer("AIRCEL",regId);
				if(stat)
				{
					stopSelf();

				}

				//sendRegistrationToServer();

			} else {

				registrationStatus = "Already registered";
				boolean stat=sendRegistrationToServer("AIRCEL",regId);
				if(stat)
				{
					stopSelf();

				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			registrationStatus = e.getMessage();

		}


		Log.d(TAG, registrationStatus);
		//edtgcmregid.setText(regId);
		System.out.println(regId);

		// This is part of our CHEAT.  For this demo, you'll need to
		// capture this registration id so it can be used in our demo web
		// service.
		Log.d(TAG, regId);
		setgcmid(regId);
		return regId;
	}
	private boolean sendRegistrationToServer(String user,String gcmid) {
		try {
			System.out.println("(((((((((((((())))))))))))))))))))))"+gcmid);
			TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			user=telephonyManager.getDeviceId();
			TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); 
			String number = tm.getLine1Number();

			String devicename=getDeviceName();
			String networkname=networkType();
			String email=getUseremail();


			if(gcmid!=null&gcmid.length()>0){

				String responseregcont =  "1";
				System.out.println(responseregcont);
				if(responseregcont.length()>20)
				{
					return false;	
				}
				else
				{
					if(responseregcont.equalsIgnoreCase(String.valueOf(1)))
					{
						
						generateNotification(getApplicationContext(), "Registration Aquired Sucessfully.");
						SharedPreferences sharedpreferences = getSharedPreferences("aircel", Context.MODE_PRIVATE);

						SharedPreferences.Editor editor = sharedpreferences.edit();

						editor.putBoolean("isregisterd", true);


						editor.commit();
						return true;

					}
					else{

						return false;
					}

				}
			}
			else{
				return false;
			}

		}
		catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;
		}
		// This is an empty placeholder for an asynchronous task to post the
		// registration
		// id and any other identifying information to your server.

	}
	/** The service is starting, due to a call to startService() */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Thread t;
		ForThread ft = new ForThread();
		t = new Thread(ft);
		t.start();

		return mStartMode;
	}
	private class ForThread implements Runnable{
		public void run() {
			while (threadstatus) {
				try {
					Log.v("ThreadSleeping","5 sec");
					SharedPreferences sharedpreferences = getSharedPreferences("aircel", Context.MODE_PRIVATE);
					if(getgcmid().length()<=0){
						String gcmid=getgcmid();
						registerClient();

					}
					else{
						//Stopme();

						stopSelf();
						threadstatus=false;
						if (!sharedpreferences.getBoolean("isregisterd", false)) {
							String gcmid=getgcmid();

							sendRegistrationToServer("nodeviceid", gcmid);

						}

					}
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}finally{
					Log.v("Finally called","Finally called");
				}   
			}
		}
	}
	/** A client is binding to the service with bindService() */
	public void Stopme()
	{
		Thread t;
		ForThread ft = new ForThread();
		t = new Thread(ft);
		t.stop();

	}
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	/** Called when all clients have unbound with unbindService() */
	@Override
	public boolean onUnbind(Intent intent) {
		return mAllowRebind;
	}

	/** Called when a client is binding to the service with bindService()*/
	@Override
	public void onRebind(Intent intent) {

	}

	/** Called when The service is no longer used and is being destroyed */
	@Override
	public void onDestroy() {

	}
	private void setgcmid(String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("GCMID", value);
		editor.commit();
	}
	private String getgcmid() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		return pref.getString("GCMID", "");
	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		}
		return capitalize(manufacturer) + " " + model;
	}

	private static String capitalize(String str) {
		if (TextUtils.isEmpty(str)) {
			return str;
		}
		char[] arr = str.toCharArray();
		boolean capitalizeNext = true;
		String phrase = "";
		for (char c : arr) {
			if (capitalizeNext && Character.isLetter(c)) {
				phrase += Character.toUpperCase(c);
				capitalizeNext = false;
				continue;
			} else if (Character.isWhitespace(c)) {
				capitalizeNext = true;
			}
			phrase += c;
		}
		return phrase;
	}

	private String networkType() {
		TelephonyManager teleMan = (TelephonyManager)
				getSystemService(Context.TELEPHONY_SERVICE);
		int networkType = teleMan.getNetworkType();
		switch (networkType) {
		case TelephonyManager.NETWORK_TYPE_1xRTT: return "1xRTT";
		case TelephonyManager.NETWORK_TYPE_CDMA: return "CDMA";
		case TelephonyManager.NETWORK_TYPE_EDGE: return "EDGE";
		case TelephonyManager.NETWORK_TYPE_EHRPD: return "eHRPD";
		case TelephonyManager.NETWORK_TYPE_EVDO_0: return "EVDO rev. 0";
		case TelephonyManager.NETWORK_TYPE_EVDO_A: return "EVDO rev. A";
		case TelephonyManager.NETWORK_TYPE_EVDO_B: return "EVDO rev. B";
		case TelephonyManager.NETWORK_TYPE_GPRS: return "GPRS";
		case TelephonyManager.NETWORK_TYPE_HSDPA: return "HSDPA";
		case TelephonyManager.NETWORK_TYPE_HSPA: return "HSPA";
		case TelephonyManager.NETWORK_TYPE_HSPAP: return "HSPA+";
		case TelephonyManager.NETWORK_TYPE_HSUPA: return "HSUPA";
		case TelephonyManager.NETWORK_TYPE_IDEN: return "iDen";
		case TelephonyManager.NETWORK_TYPE_LTE: return "LTE";
		case TelephonyManager.NETWORK_TYPE_UMTS: return "UMTS";
		case TelephonyManager.NETWORK_TYPE_UNKNOWN: return "Unknown";
		}
		throw new RuntimeException("New type of network");
	}
	public String getUseremail() {
		AccountManager manager = AccountManager.get(this); 
		Account[] accounts = manager.getAccountsByType("com.google"); 
		List<String> possibleEmails = new LinkedList<String>();

		for (Account account : accounts) {
			// TODO: Check possibleEmail against an email regex or treat
			// account.name as an email address only for certain account.type values.
			possibleEmails.add(account.name);
		}

		if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
			String email = possibleEmails.get(0);
			String[] parts = email.split("@");
			return email;
		}

		return null;
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

}