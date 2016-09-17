package mifi.pm.com.exampleproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import mifi.pm.com.exampleproject.handler.AlertUtils;
import mifi.pm.com.exampleproject.handler.AppMessages;
import mifi.pm.com.exampleproject.handler.AppSharedPreferences;
import mifi.pm.com.exampleproject.handler.HttpRequest;

public class Verify extends AppCompatActivity {
    EditText editTextotp;
    Context mContext;
    String otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        editTextotp=(EditText)findViewById(R.id.editTextfirst);
        otp=random(6);
        new OTPTast(otp).execute();

        Button button_reg_now=(Button)findViewById(R.id.button_reg_now);
        button_reg_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTextotp.getText().toString().length()>0) {

                if(editTextotp.getText().toString().equalsIgnoreCase(otp)){
                    AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.OTP,otp);
                    Intent i=new Intent(mContext,SetPassword.class);
                    startActivity(i);
                    finish();
                }
                else{
                    AlertUtils.showToast(mContext, AppMessages.CommonSignInSignUpMessages.INVALID_OTP);
                }
                }
                else {
                    AlertUtils.showToast(mContext,"Please fill all feilds");
                }

                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
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

            pd = ProgressDialog.show(Verify.this, "Please Wait",
                    "Please Wait");
        }

        @Override
        protected String doInBackground(String... arg0) {
            result= HttpRequest.RequestOtp(firstname);
            return result;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            // TODO Auto-generated method stub
            if(strFromDoInBg!=null&&strFromDoInBg.contains("success")){

            }
            pd.cancel();


        }
    }
    public static String random(int size) {

        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            // Generate 20 integers 0..20
            for (int i = 0; i < size; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedToken.toString();
    }
}
