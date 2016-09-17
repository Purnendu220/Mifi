package mifi.pm.com.exampleproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;
    private RefrenceWrapper refrenceWrapper;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        refrenceWrapper = RefrenceWrapper.getRefrenceWrapper(mContext);
        setContentView(getContentViewID() == 0 ? R.layout.activity_home : getContentViewID());
        onCreateBase(savedInstanceState);
    }
    public abstract int getContentViewID();
    public abstract void onCreateBase(Bundle savedInstanceState);
    public void activitySwitcher(Class<?> classToSwtich)
    {
        Intent i = new Intent(this, classToSwtich);
        startActivity(i);
    }
    //change activity and clean all background activities
    public void activityCleanSwitcher(Class<?> classToSwtich) {
        Intent i = new Intent(this, classToSwtich);
        startActivity(i);
        finish();
    }
    public void activitySwitcher_with_Bundle(Class<?> classToSwtich, Bundle bundle) {
        Intent i = new Intent(this, classToSwtich);
        if (bundle != null) i.putExtras(bundle);
        startActivity(i);
    }
    public void showTextView(final TextView view, String message) {
        view.setVisibility(View.VISIBLE);
        view.setText(message);
        Timer t = new Timer(false);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        refrenceWrapper.getmAnimationHandler().fadeInAnimation(mContext, view);
                        view.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }, 3000);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        refrenceWrapper.getmAnimationHandler().finishAnimation(mContext);
    }

    public void progressDialogShow() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.custom_progress_dialog);
        mProgressDialog.setCancelable(false);
    }

    public void progressDialogDismiss() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
            }
        }
    }
}
