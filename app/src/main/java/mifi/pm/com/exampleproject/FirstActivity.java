package mifi.pm.com.exampleproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mifi.pm.com.exampleproject.handler.AppMessages;
import mifi.pm.com.exampleproject.handler.AppSharedPreferences;
import mifi.pm.com.exampleproject.handler.HttpRequest;
import mifi.pm.com.exampleproject.handler.UtilClass;

public class FirstActivity extends AppCompatActivity {
    static AlarmManager alarmManager;
    static PendingIntent pendingIntent;
    Context context = null;
    WifiManager wifi;
    List<ScanResult> results;
    private ArrayList<String> listWifi;
    RefrenceWrapper refrenceWrapper;
    double latitude ;
    double  longitude;
    int size = 0;
    String str="<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">09/08/2016 15:50:20</attr><attr id=\"sessionDuration\">00:02:57</attr><attr id=\"sessionUsage\">12 KB</attr></response>";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Button log=(Button)findViewById(R.id.button);
        refrenceWrapper  =  RefrenceWrapper.getRefrenceWrapper(getApplicationContext());
        launchGCMService(FirstActivity.this);
        GPSTracker gps = new GPSTracker(getApplicationContext());
          latitude = gps.getLatitude();
           longitude = gps.getLongitude();
AppSharedPreferences.getInstance(getApplicationContext()).setTokenValue(getgcmid());
        //HttpRequest.ReuestOnConnect();
        String str1=str.replace("<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">","");
        String str2=str.replace("<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">09/08/2016 15:50:20</attr><attr id=\"sessionDuration\">","");
        String str3=str.replace("<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">09/08/2016 15:50:20</attr><attr id=\"sessionDuration\">00:02:57</attr><attr id=\"sessionUsage\">","");
        String sessionStart=str1.replace("</attr><attr id=\"sessionDuration\">00:02:57</attr><attr id=\"sessionUsage\">12 KB</attr></response>","");
        String sessionDuration=str2.replace("</attr><attr id=\"sessionUsage\">12 KB</attr></response>","");
        String sessionUsage=str3.replace("</attr></response>","");

        Log.e("STR","sessionStart:-"+sessionStart);
        Log.e("STR","sessionDuration:-"+sessionDuration);
        Log.e("STR","sessionUsage:-"+sessionUsage);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RegisterTast().execute();
                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
        Button buttonRegistor=(Button)findViewById(R.id.buttonRegistor);
        buttonRegistor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),LoginRegister.class);
                startActivity(i);
            }
        });
        if( AppSharedPreferences.getInstance(getApplicationContext()).getPrefboolean()){
            Intent i=new Intent(getApplicationContext(),DashBoard.class);
            startActivity(i);
        }
        AppSharedPreferences.getInstance(getApplicationContext()).setPrefrence1(true);
        Intent intentsOpen = new Intent(this, AlarmReceiver.class);
        intentsOpen.setAction("com.mts.alarm.ACTION");
        pendingIntent = PendingIntent.getBroadcast(this,111, intentsOpen, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        fireAlarm();
        try {
            wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
            listWifi = new ArrayList<>();
            wifi.startScan();
            registerReceiver(new BroadcastReceiver()
            {
                @Override
                public void onReceive(Context c, Intent intent)
                {
                    if(AppSharedPreferences.getInstance(getApplicationContext()).getPrefrence1()) {
                    results = wifi.getScanResults();
                    size = results.size();
                    scanWifi();
                    Log.e("Tag","Size"+results.size());
                    }
                }
            }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            Log.e("Status Url Called","Status");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void fireAlarm() {
        /**
         * call broadcost reciver
         */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 100000, pendingIntent);


    }
    private void scanWifi(){
        //arraylist.clear();
        listWifi.clear();
        // wifi.startScan();

        try
        {
            size = size - 1;
            while (size >= 0)
            {
				/*HashMap<String, String> item = new HashMap<String, String>();
				item.put(ITEM_KEY, results.get(size).SSID + "  " + results.get(size).capabilities);
				listWifi.add(results.get(size).SSID);
				arraylist.add(item);*/
                String capabilities= results.get(size).capabilities;
                Log.w ("TAG", results.get(size).SSID + " capabilities : " + capabilities);
                if(results.get(size).capabilities.equalsIgnoreCase("[ESS]")){
                    Log.e("OPEN NETWORK",results.get(size).SSID);
                    connectToWifi(results.get(size).SSID);
                }
                size--;

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void connectToWifi(String ssid) {

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + ssid + "\""; // Please note the quotes.

        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        wifi.addNetwork(conf);
        Log.d("MIFI", ssid+" added");

        List<WifiConfiguration> list = wifi.getConfiguredNetworks();
        for (WifiConfiguration i : list) {
            if (i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                wifi.disconnect();
                wifi.enableNetwork(i.networkId, true);
                wifi.reconnect();
                Log.d("MIFI", "conneting to: ssid"+i.SSID);
                break;
            }
        }
    }

    public class RegisterTast extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        String result;
        String firstname;
        String lastname;
        String username;
        String email;
        String num;
        public RegisterTast(){
          /*  this.firstname=firstname;
            this.lastname=lastname;
            this.username=username;
            this.email=email;
            this.num=num;*/
        }
        @Override
        protected void onPreExecute() {

            pd = ProgressDialog.show(FirstActivity.this, "Please Wait",
                    "Please Wait");
        }

        @Override
        protected String doInBackground(String... arg0) {
            try{
                UtilClass utilClass= new UtilClass();
                String json= utilClass.CreateRegisterRequest(
                        refrenceWrapper.getUsername(),"pass",AppSharedPreferences.getInstance(getApplicationContext()).getTokenValue(),"mobile","wifissid",refrenceWrapper.getUsername(),latitude+","+longitude,refrenceWrapper.getimsi(),refrenceWrapper.getDeviceID(),refrenceWrapper.getVersion()
                        ,refrenceWrapper.getDeviceName(),refrenceWrapper.getstrength()
                );
              result=HttpRequest.SendRequestToGST(json);
            }
            catch (Exception e){
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            // TODO Auto-generated method stub
            Log.e("EXAMPLE","strFromDoInBg"+strFromDoInBg);
            if(strFromDoInBg!=null&&strFromDoInBg.contains("ok")){


               /* Intent i=new Intent(mContext,Verify.class);
                startActivity(i);
                finish();*/
            }
            pd.cancel();


        }
    }
    public void launchGCMService(AppCompatActivity fActivity)
    {
        Intent i = new Intent(fActivity, ReService.class);
        fActivity.startService(i);
    }
    private String getgcmid() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return pref.getString("GCMID", "");
    }
}
