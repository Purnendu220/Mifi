package mifi.pm.com.exampleproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mifi.pm.com.exampleproject.handler.AlertUtils;
import mifi.pm.com.exampleproject.handler.AppMessages;
import mifi.pm.com.exampleproject.handler.AppSharedPreferences;
import mifi.pm.com.exampleproject.handler.HttpRequest;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUser,editTextPass;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUser=(EditText)findViewById(R.id.editTextUser);
        editTextPass=(EditText)findViewById(R.id.editTextPass);
        mContext=getApplicationContext();

        Button button_reg_now=(Button)findViewById(R.id.button_reg_now);
        button_reg_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextUser.getText().toString().length()>0&&
                                editTextPass.getText().toString().length()>0
                        ){
                    new RegisterTast(editTextUser.getText().toString(),editTextPass.getText().toString()).execute();
                }
                else {
                    AlertUtils.showToast(mContext,"Please fill all feilds");
                }
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
    }


    public class RegisterTast extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        String result;

        String username;
        String password;
        String num;
        public RegisterTast(String username,String password){

            this.username=username;
            this.password=password;
        }
        @Override
        protected void onPreExecute() {

            pd = ProgressDialog.show(LoginActivity.this, "Please Wait",
                    "Please Wait");
        }

        @Override
        protected String doInBackground(String... arg0) {
            result= HttpRequest.RequestLogin(username,password);
            return result;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            // TODO Auto-generated method stub
            Log.e("TAG","Resp"+strFromDoInBg);
            if(strFromDoInBg!=null&&strFromDoInBg.contains("sessionStart")){
                AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.USERNAME,username);
                AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.PASS,username);
                AppSharedPreferences.getInstance(mContext).setPrefrence(true);
                try {
                    String str1="<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">";
                    String str2="<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">09/08/2016 15:50:20</attr><attr id=\"sessionDuration\">";
                    String str3="<?xml version=\"1.0\"?><response type=\"status\"><attr id=\"sessionStart\">09/08/2016 15:50:20</attr><attr id=\"sessionDuration\">00:02:57</attr><attr id=\"sessionUsage\">";
                    String sessionStart=strFromDoInBg.substring(str1.length(),str1.length()+19);
                    String sessionDuration=strFromDoInBg.substring(str2.length(),str2.length()+8);
                    String sessionUsage=strFromDoInBg.substring(str3.length(),str3.length()+5);
                    AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.sessionStart,sessionStart);
                    AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.sessionDuration,sessionDuration);
                    AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.sessionUsage,sessionUsage);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                Intent i=new Intent(mContext,DashBoard.class);
                startActivity(i);
                finish();
            }
            pd.cancel();


        }
    }
}
