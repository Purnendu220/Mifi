package mifi.pm.com.exampleproject.handler;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;


public class DeviceUtils
{
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getDeviceID(Context ctx)
    {
        String android_id = Secure.getString(ctx.getContentResolver(), Secure.ANDROID_ID);
        return android_id;
    }
    public static String getDeviceType()
    {
        String device_type = "Android";
        return device_type;
    }

}
