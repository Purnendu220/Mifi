package mifi.pm.com.exampleproject.handler;

import android.util.Log;

/**
 * Created by Mobikasa on 7/25/2016.
 */
public class LogUtils {

    private static boolean mIsDebug = true;

    public static void infoFull(Object object) {
        int maxLogSize = 2000;
        String veryLongString = object.toString();
        for (int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > veryLongString.length() ? veryLongString.length() : end;
            LogUtils.info("Cardamon :  ", veryLongString.substring(start, end));
        }
    }

    public static void info(String tag, Object object) {
        if (mIsDebug) {
            Log.i("Cardamon :  " + tag, "" + object);

        }
    }

    public static void debug(Object object) {
        if (mIsDebug) {
            Log.d("Cardamon :  ", "" + object);
        }
    }

    public static void debug(String tag, Object object) {
        if (mIsDebug) {
            Log.d("Cardamon :  " + tag, "" + object);
        }
    }

    public static void error(Object object) {
        if (mIsDebug) {
            LogUtils.error("Cardamon :  ", "" + object);
            if (object instanceof Exception) {
                ((Exception) object).printStackTrace();

            }
        }
    }



    public static void error(String tag, Object object) {
        if (mIsDebug) {
            Log.e("Cardamon :  " + tag, "" + object);
            if (object instanceof Exception) {
                ((Exception) object).printStackTrace();
            }
        }
    }

    public static void print(Object object) {
        if (mIsDebug) {
            LogUtils.info("Cardamon :  ", "" + object);
        }
    }

    public static void info(Object object) {
        print(object);
    }
    
}
