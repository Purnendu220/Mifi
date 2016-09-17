package mifi.pm.com.exampleproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
}
