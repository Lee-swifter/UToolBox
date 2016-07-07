package lic.swifter.box.util;

import android.app.Activity;
import android.view.View;

/**
 * Created by lic on 16-7-7.
 */
public class FindUtil {

    @SuppressWarnings({ "unchecked", "UnusedDeclaration" })
    public static <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }

    @SuppressWarnings({ "unchecked", "UnusedDeclaration" })
    public static <T extends View> T $(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }
}
