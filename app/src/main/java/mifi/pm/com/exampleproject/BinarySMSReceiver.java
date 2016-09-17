package mifi.pm.com.exampleproject;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class BinarySMSReceiver extends BroadcastReceiver {
	
	//ToDo - Uncomment in case if you want to play video on each incoming SMS
	@Override
    public void onReceive(Context context, Intent intent) {
		try {
			
			SharedPreferences pref = context.getSharedPreferences("MySettings", Context.MODE_PRIVATE); 
	    	String appswitch = pref.getString("appswitch", "ON");
	    	String timefrom =  pref.getString("timefrom", null);
	    	String timeto = pref.getString("timeto", null);
	    	boolean inOptOutTime = false;
	    	if(timefrom!=null && timeto!=null )
	    	inOptOutTime = isCurrentTimeBetween(timefrom, timeto);
	    	String notification = pref.getString("notification", "Call");
	    	
	    	//if(appswitch.equalsIgnoreCase("ON") && (notification.contains("SMS") || notification.contains("Both")) && !inOptOutTime){
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(startMain);
			
			Intent i = new Intent().setClass(context, AdvtActivity.class);
			i.addCategory(Intent.CATEGORY_LAUNCHER);
			i.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);                     
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 

			// Launch the new activity and add the additional flags to the intent
			context.startActivity(i);
	    	//}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	
	public boolean isCurrentTimeBetween(String starthhmmss, String endhhmmss) throws ParseException{
        DateFormat hhmmssFormat = new SimpleDateFormat("yyyyMMddhh a");
        Date now = new Date();
        String yyyMMdd = hhmmssFormat.format(now).substring(0, 8);
        return(hhmmssFormat.parse(yyyMMdd+starthhmmss).before(now) &&
          hhmmssFormat.parse(yyyMMdd+endhhmmss).after(now));
       }
	
	//ToDo - Uncomment in case if you want to download the video from server side 
  /* @Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();        
        		SmsMessage[] msgs = null;
        
        		String info = "";//"Binary SMS from ";
        		String address = "";
        		if(null != bundle) {
        			
            		Object[] pdus = (Object[]) bundle.get("pdus");
            		msgs = new SmsMessage[pdus.length];
            		byte[] data = null;
            
            		for (int i=0; i<msgs.length; i++){
               			msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);  
               			
               			info = msgs[i].getMessageBody();
               			address = msgs[i].getOriginatingAddress();  
                			//info += msgs[i].getOriginatingAddress();                     
                			//info += "\n*****BINARY MESSAGE*****\n";             
                			data = msgs[i].getUserData();              
                			for(int index=0; index<data.length; ++index)
                				info += Character.toString((char)data[index]);
            		}
            		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
            		
            		if(info!=null && info.startsWith("http://")){
            			final Context context_final = context;
                        final String  msg_body =info;
                        final String  number = address;
            			try {
            				
            				Thread thread= new Thread(){
                              	public void run(){
                              		try{
                              			Looper.prepare();
                              			
            			    URL url = new URL(msg_body);
            			    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            			    conn.setDoInput(true);
            			    conn.connect();
            			    InputStream is = conn.getInputStream();
            			    Bitmap bm = BitmapFactory.decodeStream(is);
            			    
            			    try {
								boolean isFileDeleted = context_final.deleteFile(context_final.getFilesDir()+"/adv1.3gp");
								Log.i("isFileDeleted", isFileDeleted+"");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
            			    FileOutputStream fos = context_final.openFileOutput("adv1.3gp", Context.MODE_PRIVATE);
            			             
            			    ByteArrayOutputStream outstream = new ByteArrayOutputStream();
            			             
            			    //bm.compress(Bitmap.CompressFormat.PNG, 100, outstream);
            			    byte[] byteArray = outstream.toByteArray();
            			             
            			    Toast.makeText(context_final, "file saved. Location: "+context_final.getFilesDir(), Toast.LENGTH_LONG).show();
            			    Log.i("File Saved", "file saved. Location: "+context_final.getFilesDir());
            			    fos.write(byteArray);
            			    fos.close();
            			  
                                        deleteSMS(context_final, msg_body, number);
                                           }catch(Exception e)
                                        	 {
                                        	 Log.e("exception ",e.getMessage()+"");
                                        	 }
                            	}
                            };
                            thread.start();
            			} catch(Exception e) {
            			             
            			}
            		}
        		}
	}*/
	

	/*public void deleteSMS(Context context, String message, String number) {
	    try {
	    	Looper myLooper = Looper.myLooper();
	            Looper.loop();
	                         myLooper.quit();
	    	Thread.sleep(1000*1);
	        Uri uriSms = Uri.parse("content://sms/inbox");
	        Cursor c = context.getContentResolver().query(uriSms,
	            new String[] { "_id", "thread_id", "address",
	                "person", "date", "body" }, "read=0", null, null);
	        
	        if (c != null && c.moveToFirst()) {
	            do {
	                long id = c.getLong(0);
	                long threadId = c.getLong(1);
	                String address = c.getString(2);
	                String body = c.getString(5);
	                if (message.equals(body) && address.equals(number)) {
	                    context.getContentResolver().delete(Uri.parse("content://sms/" + id), null, null);
	                }
	            } while (c.moveToNext());
	        }
	    } catch (Exception e) {
	    	Log.e("exception ",e.getMessage());
	    }
	}*/
}