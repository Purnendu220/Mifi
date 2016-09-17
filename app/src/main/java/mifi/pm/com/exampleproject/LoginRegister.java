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

import mifi.pm.com.exampleproject.handler.AlertUtils;
import mifi.pm.com.exampleproject.handler.AppMessages;
import mifi.pm.com.exampleproject.handler.AppSharedPreferences;
import mifi.pm.com.exampleproject.handler.HttpRequest;

public class LoginRegister extends AppCompatActivity {
EditText editTextfirst,editTextLast,editTextUser,editTextnum,editTextemail;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        editTextfirst=(EditText)findViewById(R.id.editTextfirst);
        editTextLast=(EditText)findViewById(R.id.editTextlast);
        editTextUser=(EditText)findViewById(R.id.editTextUser);
        editTextnum=(EditText)findViewById(R.id.editTextnum);
        editTextemail=(EditText)findViewById(R.id.editTextemail);
mContext=getApplicationContext();

        Button button_reg_now=(Button)findViewById(R.id.button_reg_now);
        button_reg_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextfirst.getText().toString().length()>0&&
                        editTextLast.getText().toString().length()>0&&
                editTextUser.getText().toString().length()>0&&
                editTextnum.getText().toString().length()>0&&
                editTextemail.getText().toString().length()>0
                        ){
                    new RegisterTast(editTextfirst.getText().toString(),editTextLast.getText().toString(),editTextUser.getText().toString()
                    ,editTextnum.getText().toString(),editTextemail.getText().toString()).execute();
                }
                else {
                    AlertUtils.showToast(mContext,"Please fill all feilds");
                }


            }
        });
    }

    public class RegisterTast extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        String result;
        String firstname;
        String lastname;
        String username;
        String email;
        String num;
        public RegisterTast(String firstname,String lastname,String username,String email,String num){
            this.firstname=firstname;
            this.lastname=lastname;
            this.username=username;
            this.email=email;
            this.num=num;
        }
        @Override
        protected void onPreExecute() {

            pd = ProgressDialog.show(LoginRegister.this, "Please Wait",
                    "Please Wait");
        }

        @Override
        protected String doInBackground(String... arg0) {
            result=HttpRequest.RequestRegister(firstname,lastname,username,email,num);
            return result;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            // TODO Auto-generated method stub
          if(strFromDoInBg!=null&&strFromDoInBg.contains("ok")){
              AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.FIRSTNAME,firstname);
              AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.LASTNAME,lastname);

              AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.USERNAME,username);

              AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.EMAIL,email);

              AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.MOBILE,num);

              Intent i=new Intent(mContext,Verify.class);
              startActivity(i);
              finish();
          }
            pd.cancel();


        }
    }
}
