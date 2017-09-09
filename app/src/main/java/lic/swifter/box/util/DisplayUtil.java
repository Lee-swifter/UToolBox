package lic.swifter.box.util;

import android.content.Context;

/**
 * 关于显示的工具类，提供单位转换
 *
 * Created by lic on 16-7-7.
 */
public class DisplayUtil {

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
