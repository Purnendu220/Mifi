package mifi.pm.com.exampleproject;

import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import mifi.pm.com.exampleproject.handler.AppMessages;
import mifi.pm.com.exampleproject.handler.AppSharedPreferences;
import mifi.pm.com.exampleproject.handler.FZTickerText;
import mifi.pm.com.exampleproject.handler.JsonUtils;

public class TextScrollHome extends Activity {
    FZTickerText ticker1;
    long time=10000;
    ArrayList<char[]> phrases = new ArrayList<char[]>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_scroll_home);
        ticker1 = (FZTickerText) findViewById(R.id.fZTickerText1);
        String message= AppSharedPreferences.getInstance(getApplicationContext()).getPrefrence(AppMessages.Constants.ADVT);
        Example ex= JsonUtils.fromJson(message,Example.class);
        if(ex!=null&&ex.getAdvertisement().getDeleteAfterSeconds().length()>0)
        {
            time=Long.parseLong(ex.getAdvertisement().getDeleteAfterSeconds());
        }
        if(ex!=null&&ex.getAdvertisement().getAdUrl().length()>0){

            phrases.add(new String(ex.getAdvertisement().getAdUrl()).toCharArray());

        }
        else{
            phrases.add(new String("Hello!").toCharArray());
            phrases.add(new String("Welcome To MTS").toCharArray());
            phrases.add(new String("Get 1 GB Data in Just 49").toCharArray());
            phrases.add(new String("Free Unlimited Calling MTS to MTS @ night").toCharArray());
        }

        ticker1.ANIMATION_DELAY = 150;
        ticker1.setPhrases(phrases);
        ticker1.animationStart();
        ticker1.setTextColor(getResources().getColor(R.color.error_red));

        new Handler().postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, time);
        new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                //textView3.setText("Advertisment will close in " + millisUntilFinished / 1000+" seconds");
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                //textView3.setText("done!");
            }

        }.start();
    }
}
