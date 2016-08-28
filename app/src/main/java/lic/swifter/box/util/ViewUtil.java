package lic.swifter.box.util;

import android.util.TypedValue;
import android.view.View;

import lic.swifter.box.R;

/**
 * Created by lic on 16-8-1.
 */
public class ViewUtil {

    public static void waveView(View view) {
        TypedValue typedValue = new TypedValue();
        view.getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        view.setBackgroundResource(typedValue.resourceId);
        view.setClickable(true);
    }

    public static void fadeInView(View view, int duration) {
        if(view.isShown())
            return ;

        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null);
    }

    public static void fadeOutView(final View view, int duration) {
        if(!view.isShown())
            return ;

        view.animate()
                .alpha(0f)
                .setDuration(duration)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.GONE);
                    }
                });
    }

}
