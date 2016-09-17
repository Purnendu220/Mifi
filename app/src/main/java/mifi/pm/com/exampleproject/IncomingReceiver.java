package mifi.pm.com.exampleproject;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

//Here is your broadcast receiver class
public class IncomingReceiver extends BroadcastReceiver{
	private static final String TAG = "IncomingReceiver";
	static CountDownTimer myTimer;
	static String incoming_number;
	boolean isRinging = false;
	static TelephonyManager tmgr;
	String appswitch = "";
	String timefrom = "";
	String timeto = "";
	boolean inOptOutTime = false;
	String notification = "";


	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			final Context context_final =  context;

			Log.i("total_Call", "total_Call : ");
			Log.i("On Recieve isTrigered", "fdfgfgdfgfgdfg");
			//Toast.makeText(context, " status:"+ isTrigered, Toast.LENGTH_LONG).show();
			//boolean isApllicationGeneratedNumber = Constant.getInstance().isApllicationGeneratedNumber();
			SharedPreferences pref = context.getSharedPreferences("MySettings", Context.MODE_PRIVATE); 
			appswitch = pref.getString("appswitch", "ON");
			timefrom =  pref.getString("timefrom", null);
			timeto = pref.getString("timeto", null);

			if(timefrom!=null && timeto!=null )
				inOptOutTime = isCurrentTimeBetween(timefrom, timeto);

			notification = pref.getString("notification", "calls");

			System.out.println(" On Receive appswitch: "+appswitch+" , notification:"+notification+" , !inOptOutTime "+!inOptOutTime);
		/*	if(appswitch.equalsIgnoreCase("ON")  && (notification.contains("calls") || notification.contains("Both") ) && !inOptOutTime ){*//* && isTrigered && (total_Call==1 || total_Call%4==0)*//*
				*/
								tmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
								//Create Listner
								MyPhoneStateListener PhoneListener = new MyPhoneStateListener(context_final);
								// Register listener for LISTEN_CALL_STATE
								tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
		} catch (Exception e) {
			Log.e("Phone Receive Error", " " + e);
			e.printStackTrace();
		}

	}

	public boolean isCurrentTimeBetween(String starthhmmss, String endhhmmss) throws ParseException{
		System.out.println(" starthhmmss: "+starthhmmss+" , endhhmmss: "+endhhmmss);
		DateFormat hhmmssFormat = new SimpleDateFormat("yyyyMMddhh a");
		Date now = new Date();
		String yyyMMdd = hhmmssFormat.format(now).substring(0, 8);
		System.out.println("hhmmssFormat.parse(yyyMMdd+starthhmmss)"+hhmmssFormat.parse(yyyMMdd+starthhmmss).toString());
		System.out.println("hhmmssFormat.parse(yyyMMdd+endhhmmss)"+hhmmssFormat.parse(yyyMMdd+endhhmmss).toString());
		System.out.println("now"+now);
		System.out.println(hhmmssFormat.parse(yyyMMdd+starthhmmss).before(now) + " && "+hhmmssFormat.parse(yyyMMdd+endhhmmss).after(now));
		return(hhmmssFormat.parse(yyyMMdd+starthhmmss).before(now) &&
				hhmmssFormat.parse(yyyMMdd+endhhmmss).after(now));
	}


	private class MyPhoneStateListener extends PhoneStateListener {

		Context context ;
		String incomingnumber;
		public MyPhoneStateListener(Context context_final) {
			// TODO Auto-generated constructor stub
			context =context_final;

		}

		public void onCallStateChanged(int state, String incomingNumber) {

			incomingnumber=incomingNumber;
			Log.d("MyPhoneListener",state+"   incoming no:"+incomingNumber);

			SharedPreferences pref = context.getSharedPreferences("MySettings", Context.MODE_PRIVATE); 
			appswitch = pref.getString("appswitch", "ON");
			timefrom =  pref.getString("timefrom", null);
			timeto = pref.getString("timeto", null);

			if(timefrom!=null && timeto!=null )
				try {
					inOptOutTime = isCurrentTimeBetween(timefrom, timeto);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			notification = pref.getString("notification", "calls");

			System.out.println(" appswitch: "+appswitch+" , notification:"+notification+" , !inOptOutTime "+!inOptOutTime);
			if (state == TelephonyManager.CALL_STATE_RINGING) {
				new Handler().postDelayed(new Runnable() {
					public void run() {

						Intent startMain = new Intent(Intent.ACTION_MAIN);
						startMain.addCategory(Intent.CATEGORY_HOME);
						startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(startMain);

						Intent i = new Intent().setClass(context,AdvtActivity.class);
						i.addCategory(Intent.CATEGORY_LAUNCHER);
						i.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

						// Launch the new activity and add the additional
						// flags to
						// the intent
						context.startActivity(i);
					}
				}, 2000);

				Log.i("CALL_STATE_RINGING", "done");
			} else if(state == TelephonyManager.CALL_STATE_IDLE) {
				Log.i("CALL_STATE_IDLE", "done");

			}else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
				//Not in call: Play music
				Log.i("CALL_STATE_OFFHOOK", "done");

			}
		}

		public  boolean isCallActive(Context context){
			AudioManager manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
			if(manager.getMode()==AudioManager.MODE_IN_CALL){
				/* Toast tag = Toast.makeText(context,"CALL IS ACTIVE", Toast.LENGTH_SHORT);
					   tag.show();*/
				return true;
			}
			else{
				/* Toast tag = Toast.makeText(context,"CALL IS NOT ACTIVE", Toast.LENGTH_SHORT);
					   tag.show();*/
				return false;
			}

		}

		public  boolean isCallRinging(Context context){
			AudioManager manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
			if(manager.getMode()==AudioManager.MODE_RINGTONE){
				return true;
			}
			else{
				return false;
			}
		}



	}


	private void sendmessageIntent(Context ctx, String message) {
		Bundle mBundle = new Bundle(); 


		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("CALL_STATE_IDLE");

		broadcastIntent.putExtras(mBundle);

		ctx.sendBroadcast(broadcastIntent);

	}

	private void sendmessageIntentInCall(Context ctx, String message) {
		Bundle mBundle = new Bundle(); 


		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("CALL_STATE_IDLE_InCall");

		broadcastIntent.putExtras(mBundle);

		ctx.sendBroadcast(broadcastIntent);

	}

}