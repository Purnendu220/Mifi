package mifi.pm.com.exampleproject;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mifi.pm.com.exampleproject.handler.AppMessages;
import mifi.pm.com.exampleproject.handler.AppSharedPreferences;
import mifi.pm.com.exampleproject.handler.HttpRequest;
import mifi.pm.com.exampleproject.interfaces.statusListe;

public class DashBoard extends AppCompatActivity implements statusListe.StatusLite {
RefrenceWrapper refrenceWrapper;
    TextView edt_user,edt_ssid,edt_duration,edt_sessin_start,edt_sessionUsage;
    TextView edt_duration_total,edt_duration1;
    TextView current_usage,current_usage_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        LinearLayout deals=(LinearLayout)findViewById(R.id.deals);
        LinearLayout wifi =(LinearLayout)findViewById(R.id.wifi_list);
        refrenceWrapper=RefrenceWrapper.getRefrenceWrapper(getApplicationContext());
        refrenceWrapper.gestatusliste().setWifiListe(this);
        TextView tv =(TextView)findViewById(R.id.title_logout);
         edt_user =(TextView)findViewById(R.id.edt_user);
        edt_ssid =(TextView)findViewById(R.id.edt_ssid);
        edt_duration =(TextView)findViewById(R.id.edt_duration);
        edt_sessin_start =(TextView)findViewById(R.id.edt_sessin_start);
         edt_sessionUsage =(TextView)findViewById(R.id.edt_sessionUsage);

         edt_duration_total =(TextView)findViewById(R.id.edt_duration_total);
         edt_duration1 =(TextView)findViewById(R.id.edt_duration1);

        current_usage =(TextView)findViewById(R.id.current_usage);
        current_usage_time =(TextView)findViewById(R.id.current_usage_time);

        edt_user.setText(AppSharedPreferences.getInstance(getApplicationContext()).getPrefrence(AppMessages.Constants.USERNAME));
        edt_ssid.setText(AppSharedPreferences.getInstance(getApplicationContext()).getPrefrence(AppMessages.Constants.SSID));
        edt_duration.setText(AppSharedPreferences.getInstance(getApplicationContext()).getPrefrence(AppMessages.Constants.sessionDuration));
        edt_sessin_start.setText(AppSharedPreferences.getInstance(getApplicationContext()).getPrefrence(AppMessages.Constants.sessionStart));
        edt_sessionUsage.setText(AppSharedPreferences.getInstance(getApplicationContext()).getPrefrence(AppMessages.Constants.sessionUsage));
        deals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
            }
        });
        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),WifiList.class);
                startActivity(i);
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppSharedPreferences.getInstance(getApplicationContext()).setPrefrence(false);
                new OTPTast("").execute();
            }
        });
        new HistoryCapture("").execute();
    }

    @Override
    public void StatusConnected(final String sessionStart, final String sessionDuration, final String sessionUsage) {
runOnUiThread(new Runnable() {
    @Override
    public void run() {
        edt_duration.setText(sessionDuration);
        edt_sessin_start.setText(sessionStart);
        edt_sessionUsage.setText(sessionUsage);
        edt_duration1.setText(sessionDuration);
        edt_duration_total.setText(sessionDuration);
        current_usage.setText(sessionUsage);
        current_usage_time.setText(sessionUsage);
    }
});
    }

    @Override
    public void WifiDisConnect(String msg) {

    }

    public class OTPTast extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        String result;
        String firstname;

        public OTPTast(String firstname){
            this.firstname=firstname;

        }
        @Override
        protected void onPreExecute() {

            pd = ProgressDialog.show(DashBoard.this, "Please Wait",
                    "Please Wait");
        }

        @Override
        protected String doInBackground(String... arg0) {
            result= HttpRequest.LogOutRequest();
            return result;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            // TODO Auto-generated method stub
            if(strFromDoInBg!=null&&strFromDoInBg.contains("logout")){
                Intent i=new Intent(getApplicationContext(),FirstActivity.class);
                startActivity(i);
                finish();
            }
            pd.cancel();


        }
    }

    public class HistoryCapture extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        String result;
        String firstname;

        public HistoryCapture(String firstname){
            this.firstname=firstname;

        }
        @Override
        protected void onPreExecute() {

            pd = ProgressDialog.show(DashBoard.this, "Please Wait",
                    "Please Wait");
        }

        @Override
        protected String doInBackground(String... arg0) {
            try {
                result= HttpRequest.SendRequestHistoryCapture(getBH());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            // TODO Auto-generated method stub

            pd.cancel();


        }
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
                browsing_details.put(data);
                // System.out.println(data.toString());

                i = i+1;
            }
            JSONObject bdata1 = new JSONObject();
            bdata1.put("browsing_array",browsing_details);
            bdata.put("browsing_Data", bdata1);
            bdata.put("uid", "4");
            System.out.println(bdata.toString());
//close the cursor
            rows.close();
        }
        return bdata.toString();
    }
}
