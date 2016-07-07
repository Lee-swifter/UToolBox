package lic.swifter.box.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lic.swifter.box.R;
import lic.swifter.box.util.DisplayUtil;

/**
 *
 * Created by Swifter on 2016/7/6.
 */
public class ToolView extends LinearLayout {

    private ImageView image;
    private TextView text;

    public ToolView(Context context) {
        this(context, null);
    }

    public ToolView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        final int padding = DisplayUtil.dip2px(context, 10);
        setPadding(padding, padding, padding,padding);

        LayoutInflater.from(context).inflate(R.layout.item_tool, this);
        image = (ImageView) findViewById(R.id.item_tool_image);
        text = (TextView) findViewById(R.id.item_tool_name);
    }

    public void setImageAndText(@DrawableRes int drawableRes, @StringRes int stringRes) {
        image.setImageResource(drawableRes);
        text.setText(stringRes);
    }
}