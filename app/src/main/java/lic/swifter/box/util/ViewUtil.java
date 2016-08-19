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

}
