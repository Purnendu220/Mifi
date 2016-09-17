package mifi.pm.com.exampleproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mifi.pm.com.exampleproject.handler.AlertUtils;
import mifi.pm.com.exampleproject.handler.HttpRequest;

public class SetPassword extends AppCompatActivity {
    EditText editTextPass,editTextPassRe;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        editTextPass=(EditText)findViewById(R.id.editTextPass);
        editTextPassRe=(EditText)findViewById(R.id.editTextPassre);

        Button button_reg_now=(Button)findViewById(R.id.button_reg_now);
        button_reg_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTextPassRe.getText().toString().length()>0&&editTextPass.getText().toString().length()>0) {
                    if(editTextPass.getText().toString().equalsIgnoreCase(editTextPassRe.getText().toString())){
                        new PasswordTasK(editTextPass.getText().toString(),editTextPassRe.getText().toString()).execute();
                    }
                    else {
                        AlertUtils.showToast(mContext,"Password must match");
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
    public class PasswordTasK extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        String result;
        String pass;
        String passre;

        public PasswordTasK(String pass,String passre){
            this.pass=pass;
            this.passre=passre;

        }
        @Override
        protected void onPreExecute() {

            pd = ProgressDialog.show(SetPassword.this, "Please Wait",
                    "Please Wait");
        }

        @Override
        protected String doInBackground(String... arg0) {
            //result= HttpRequest.PassWordSet(pass,passre);
            return result;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            // TODO Auto-generated method stub
            if(strFromDoInBg!=null&&strFromDoInBg.contains("success")){
                Intent i=new Intent(mContext,LoginActivity.class);
                startActivity(i);
                finish();
            }
            pd.cancel();


        }
    }
}
