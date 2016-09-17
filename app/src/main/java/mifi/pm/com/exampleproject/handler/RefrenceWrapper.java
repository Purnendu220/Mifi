package mifi.pm.com.exampleproject.handler;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import java.util.LinkedList;
import java.util.List;

import mifi.pm.com.exampleproject.interfaces.WifiHandler;
import mifi.pm.com.exampleproject.interfaces.statusListe;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Project : Mobikasa Cardamom
 * Author : Balwinder Singh Madaan
 * Creation Date : 25-july-2016
 * Description :
 */
public class RefrenceWrapper {
    public static RefrenceWrapper refrenceWrapper;
    private Context context;
  //  private RestAPI service;
   // private ServiceCallsUtils mServiceCallHandler;
    private DeviceUtils mDeviceUtilHandler;
    private AnimationHandler mAnimationHandler;
    private WifiHandler wifiHandler;
    private FontTypeFace fontTypeFace;
    private statusListe  statusListe;

    private RefrenceWrapper(Context activity) {
        this.context = activity;
    }

    public static RefrenceWrapper getRefrenceWrapper(Context context) {
        if (refrenceWrapper == null) {
            refrenceWrapper = new RefrenceWrapper(context);
        }
        return refrenceWrapper;
    }

   /* public RestAPI getService() {
        return setService();
    }*/
   public String getimsi(){
       TelephonyManager mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
       String imsi = mTelephonyMgr.getSubscriberId();
       return imsi;
   }
    public String getVersion(){
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;
        return version;
    }
    public  String getDeviceID()
    {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public  String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private  String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

//        String phrase = "";
        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
//                phrase += Character.toUpperCase(c);
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
//            phrase += c;
            phrase.append(c);
        }

        return phrase.toString();
    }
    public String getstrength(){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        // for example value of first element
        CellInfoGsm cellinfogsm = (CellInfoGsm)telephonyManager.getAllCellInfo().get(0);
        CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
       String cell= cellSignalStrengthGsm.getDbm()+"";
        return  cell;
    }

    public String getUsername() {
        AccountManager manager = AccountManager.get(context);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");

            if (parts.length > 1)
                return parts[0];
        }
        return null;
    }

    RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {


        }
    };

   /* private RestAPI setService() {
        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.WebConstants.HOST_API).setLogLevel(RestAdapter.LogLevel.FULL).setRequestInterceptor(requestInterceptor).setClient(new OkClient(new OkHttpClient())).build();
        service = restAdapter.create(RestAPI.class);
        return service;
    }*/

    public void destroyInstance() {
        if (refrenceWrapper != null) {
            refrenceWrapper = null;
        }

    }

  /*  public ServiceCallsUtils getmServiceCallHandler() {
        if (mServiceCallHandler == null) {
            mServiceCallHandler = new ServiceCallsUtils();
        }
        return mServiceCallHandler;
    }*/

    public DeviceUtils getmDeviceUtilHandler() {
        if (mDeviceUtilHandler == null) {
            mDeviceUtilHandler = new DeviceUtils();
        }
        return mDeviceUtilHandler;
    }

    public AnimationHandler getmAnimationHandler() {
        if (mAnimationHandler == null) {
            mAnimationHandler = new AnimationHandler();
        }
        return mAnimationHandler;
    }

    public FontTypeFace getFontTypeFace() {
        if (fontTypeFace == null) {
            fontTypeFace = new FontTypeFace();
        }
        return fontTypeFace;
    }
public WifiHandler getSmsHandler(){

    if (wifiHandler == null) {
        wifiHandler = new WifiHandler();
    }
    return wifiHandler;

}
    public statusListe getSratusHandler(){

        if (statusListe == null) {
            statusListe = new statusListe();
        }
        return statusListe;

    }
}