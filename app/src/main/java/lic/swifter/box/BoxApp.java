package lic.swifter.box;

import android.app.Application;
import android.graphics.Typeface;

/**
 *
 * Created by lic on 16-7-4.
 */
public class BoxApp extends Application {

    public static Typeface canaroFont;

    @Override
    public void onCreate() {
        super.onCreate();

        initTypeFace();
    }

    private void initTypeFace() {
        canaroFont = Typeface.createFromAsset(getAssets(), "fonts/canaro_extra_bold.otf");
    }
}
