package mifi.pm.com.exampleproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import mifi.pm.com.exampleproject.handler.AppMessages;
import mifi.pm.com.exampleproject.handler.AppSharedPreferences;
import mifi.pm.com.exampleproject.handler.HttpRequest;

public class WifiReceiver extends BroadcastReceiver {
Context mContext;
   @Override
   public void onReceive(final Context context, final Intent intent) {
      /* new Handler().postDelayed(new Runnable() {
           public void run() {
*/
       mContext=context;
       NetworkInfo ni = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

       if (ni != null && ni.getType() == ConnectivityManager.TYPE_WIFI) {

           WifiInfo wi = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
           if (ni.getState() == NetworkInfo.State.CONNECTED) {
               WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
               WifiInfo wifiInfo = wifiManager.getConnectionInfo();
               String ssid = wifiInfo.getSSID();
               Log.e("TAG","Wifi Stattus"+ssid+":"+wifiInfo);
              // if(AppSharedPreferences.getInstance(context).getPrefboolean()){
               if (ssid.contains("unknown")){

               }
               else {
                   /*Intent i = new Intent(context, TextScrollHome.class);
                   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   context.startActivity(i);
                   AppSharedPreferences.getInstance(context).setPrefrence(AppMessages.Constants.SSID,ssid);*/
                   AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.SSID,ssid);

                   new AsyncTaskUpdate().execute();

               }
               }



           else if (ni.getState() == NetworkInfo.State.DISCONNECTED) {
               AppSharedPreferences.getInstance(context).setPrefrence(AppMessages.Constants.SSID,"");

               Log.e("TAG","Wifi Stattus Discoected");

           }

}
        /*   }
       }, 2000);*/
   }

    public class AsyncTaskUpdate extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            Log.e("TAG","onPreExecute");
        }

        @Override
        protected String doInBackground(String... arg0) {

            String res=HttpRequest.ReuestOnConnect();

            return res;

        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            Log.e("TAG","onPostExecute"+strFromDoInBg);
            if(AppSharedPreferences.getInstance(mContext).getPrefrence1()) {
                /*Intent i = new Intent(mContext, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(i);*/
                AppSharedPreferences.getInstance(mContext).setPrefrence1(false);
            }

        }
    }

}