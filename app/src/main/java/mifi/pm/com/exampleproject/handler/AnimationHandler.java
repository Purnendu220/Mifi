package mifi.pm.com.exampleproject.handler;

import android.app.Activity;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import mifi.pm.com.exampleproject.R;


/**
 * Created by Mobikasa on 6/14/2016.
 */
public class AnimationHandler
{
    public void startAnimation(Context c)
    {
        ((Activity) c).overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

    }
    public void  finishAnimation(Context c)
    {
        ((Activity) c).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
    public void fadeInAnimation(Context c, final TextView textview)
    {
        Animation animation1 = AnimationUtils.loadAnimation(c, R.anim.fade);
        textview.startAnimation(animation1);
    }
}
