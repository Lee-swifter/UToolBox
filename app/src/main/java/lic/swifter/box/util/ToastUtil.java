package lic.swifter.box.util;

import android.content.Context;
import android.widget.Toast;

/**
 *
 * Created by lic on 16-7-4.
 */
public class ToastUtil {

    private static Toast toast;

    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, int message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

}
