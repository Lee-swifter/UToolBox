package lic.swifter.box.util;

import android.content.Context;

/**
 * Created by lic on 16-7-7.
 */
public class DisplayUtil {

    public static int dip2px(Context context, float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5);
    }
}
