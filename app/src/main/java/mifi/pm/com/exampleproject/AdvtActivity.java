package mifi.pm.com.exampleproject;

import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mifi.pm.com.exampleproject.handler.AppMessages;
import mifi.pm.com.exampleproject.handler.AppSharedPreferences;
import mifi.pm.com.exampleproject.handler.JsonUtils;

public class AdvtActivity extends Activity {
TextView textView3;
    long time=10000;
    ImageView imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advt);
        textView3=(TextView)findViewById(R.id.textView3);
        imageView2=(ImageView)findViewById(R.id.imageView2);
        String message= AppSharedPreferences.getInstance(getApplicationContext()).getPrefrence(AppMessages.Constants.ADVT1);
        Example ex= JsonUtils.fromJson(message,Example.class);
        if(ex!=null&&ex.getAdvertisement().getDeleteAfterSeconds().length()>0)
        {
            time=Long.parseLong(ex.getAdvertisement().getDeleteAfterSeconds());
        }
        if(ex!=null&&ex.getAdvertisement().getAdUrl().length()>0){
            Picasso.with(getApplicationContext()).load(ex.getAdvertisement().getAdUrl()).into(imageView2);
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, time);
        new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                textView3.setText("Advertisment will close in " + millisUntilFinished / 1000+" seconds");
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                textView3.setText("done!");
            }

        }.start();
    }


}
