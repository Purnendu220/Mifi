package mifi.pm.com.exampleproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private RefrenceWrapper refrenceWrapper;
    private Button mCall,login_btnActiveBid,login_btnSoldBid,login_btnBoughtBid;
   private View view_ActiveBid,view_SoldBid,view_BoughtBid;
    private Context mContext;
    private TextView text_title;
    private ImageView cart,profile;
    @Override
    public int getContentViewID() {
        return R.layout.activity_home;
    }


    @Override
    public void onCreateBase(Bundle savedInstanceState) {
        refrenceWrapper = RefrenceWrapper.getRefrenceWrapper(mContext);
        mContext=getApplicationContext();

        InitUi();
        setlistener();
    }
    private void InitUi(){
       // mCall=(Button)findViewById(R.id.buttonCall);
        login_btnActiveBid=(Button)findViewById(R.id.login_btnActiveBid);
        login_btnSoldBid=(Button)findViewById(R.id.login_btnSoldBid);
        login_btnBoughtBid=(Button)findViewById(R.id.login_btnBoughtBid);

        view_ActiveBid=(View)findViewById(R.id.view_ActiveBid);
        view_SoldBid=(View)findViewById(R.id.view_SoldBid);
        view_BoughtBid=(View)findViewById(R.id.view_BoughtBid);


    }
    private void setlistener(){

      //  mCall.setOnClickListener(this);
        login_btnActiveBid.setOnClickListener(this);
        login_btnSoldBid.setOnClickListener(this);
        login_btnBoughtBid.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.login_btnActiveBid:
                login_btnActiveBid.setTextColor(getResources().getColor(R.color.black));
                login_btnSoldBid.setTextColor(getResources().getColor(R.color.login_signup_create_account_activity));
                login_btnBoughtBid.setTextColor(getResources().getColor(R.color.login_signup_create_account_activity));

                view_ActiveBid.setVisibility(View.VISIBLE);
                view_SoldBid.setVisibility(View.INVISIBLE);
                view_BoughtBid.setVisibility(View.INVISIBLE);

                break;
            case R.id.login_btnSoldBid:
                login_btnActiveBid.setTextColor(getResources().getColor(R.color.login_signup_create_account_activity));
                login_btnSoldBid.setTextColor(getResources().getColor(R.color.black));
                login_btnBoughtBid.setTextColor(getResources().getColor(R.color.login_signup_create_account_activity));

                view_ActiveBid.setVisibility(View.INVISIBLE);
                view_SoldBid.setVisibility(View.VISIBLE);
                view_BoughtBid.setVisibility(View.INVISIBLE);
                break;
            case R.id.login_btnBoughtBid:
                login_btnActiveBid.setTextColor(getResources().getColor(R.color.login_signup_create_account_activity));
                login_btnSoldBid.setTextColor(getResources().getColor(R.color.login_signup_create_account_activity));
                login_btnBoughtBid.setTextColor(getResources().getColor(R.color.black));

                view_ActiveBid.setVisibility(View.INVISIBLE);
                view_SoldBid.setVisibility(View.INVISIBLE);
                view_BoughtBid.setVisibility(View.VISIBLE);
                break;

        }
    }

}
