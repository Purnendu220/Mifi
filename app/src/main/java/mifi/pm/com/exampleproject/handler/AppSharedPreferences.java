package mifi.pm.com.exampleproject.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


public class AppSharedPreferences {
    private static final String SIGNUP_RESPONSE = "signup_response";
    private static final String IMAGE_MODEL = "image_model";
    private static final String USER_DATA = "user_data";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String SAVESATE_SIGNUP_USER_DATA = "save_state_signup";
    private static final String SAVESATE_SIGNIN_USER_DATA = "save_state_signin";

    private static AppSharedPreferences instance;
    private String TAG = this.getClass().getSimpleName();
    private SharedPreferences mPrefs;
    private Editor mPrefsEditor;
    private final String TOKENVALUE = "tokenvalue";
    public static final String mImgTermsAndConditions = "mTerms";

    public AppSharedPreferences(Context context) {
        this.mPrefs = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        this.mPrefsEditor = mPrefs.edit();
    }

    public static AppSharedPreferences getInstance(Context mContext) {

        if (instance == null) {
            instance = new AppSharedPreferences(mContext);
        }
        return instance;
    }

    public String getTokenValue() {
        return mPrefs.getString(TOKENVALUE, "");
    }

    public void setTokenValue(String token_value) {
        mPrefsEditor.putString(TOKENVALUE, token_value);
        mPrefsEditor.commit();
        Log.e("GCM",token_value);
    }

    public String getSignupResponse() {
        return mPrefs.getString(SIGNUP_RESPONSE, "");
    }



    public String getImageModel() {
        return mPrefs.getString(IMAGE_MODEL, "");
    }



    public void setPrefrence(String key, String value) {
        mPrefsEditor.putString(key, value);
        mPrefsEditor.commit();
    }
    public void setPrefrence( boolean value) {
        mPrefsEditor.putBoolean("isregister", value);
        mPrefsEditor.commit();
    }
    public void setPrefrence1( boolean value) {
        mPrefsEditor.putBoolean("isregister1", value);
        mPrefsEditor.commit();
    }
    public boolean getPrefrence1() {
        return mPrefs.getBoolean("isregister1", true);
    }
    public boolean getPrefboolean(){
        return mPrefs.getBoolean("isregister", false);
    }
    public String getPrefrence(String key) {
        return mPrefs.getString(key, "");
    }


    public void setAccessToken(String access_token) {
        mPrefsEditor.putString(ACCESS_TOKEN, access_token);
        mPrefsEditor.commit();
    }

    public String getAccessToken() {
        String accessToken = mPrefs.getString(ACCESS_TOKEN, "");
        return accessToken;
    }


}