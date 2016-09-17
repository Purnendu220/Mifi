package mifi.pm.com.exampleproject.handler;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AlertUtils {

    public static void showToast(Context context, String message) {
        if (message != null && context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
    public static void showToastLong(Context context, String message) {
        if (message != null && context != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
    public static void showToast(Context context, int resID) {
        Toast.makeText(context, resID, Toast.LENGTH_SHORT).show();
    }

    public static InputStream getInputStreamValue(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        InputStream in = new ByteArrayInputStream(stream.toByteArray());
        return in;
    }
}
