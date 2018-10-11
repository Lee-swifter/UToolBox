package lic.swifter.box.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import lic.swifter.box.R;
import lic.swifter.box.api.model.TodayHistoryResult;

/**
 * Created by cheng on 2016/8/19.
 */

public class TodayHistoryPage extends ScrollView {

    TextView title;
    TextView lunarDate;
    TextView content;
    ImageView imageView;

    public TodayHistoryPage(Context context) {
        this(context, null);
    }

    public TodayHistoryPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TodayHistoryPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.page_today_in_history, this);

        title = findViewById(R.id.page_today_title);
        lunarDate = findViewById(R.id.page_today_lunar_date);
        content = findViewById(R.id.page_today_content);
        imageView = findViewById(R.id.page_today_image);
    }

    public void setData(Context context, TodayHistoryResult data) {
        title.setText(data.title);
        lunarDate.setText(context.getString(R.string.date_format_string, data.year, data.month, data.day));
        content.setText(data.des);

        Glide.with(context).load(data.pic).into(imageView);
    }
}
