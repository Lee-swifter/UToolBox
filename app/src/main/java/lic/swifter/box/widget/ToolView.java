package lic.swifter.box.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lic.swifter.box.R;

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
        LayoutInflater.from(context).inflate(R.layout.item_tool, this);
        image = (ImageView) findViewById(R.id.item_tool_image);
        text = (TextView) findViewById(R.id.item_tool_name);
    }

    public void setImageAndText(int drawableRes, int stringRes) {
        image.setImageResource(drawableRes);
        text.setText(stringRes);
    }
}