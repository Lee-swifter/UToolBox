package lic.swifter.box.recycler.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import lic.swifter.box.R;
import lic.swifter.box.activity.WebViewActivity;
import lic.swifter.box.api.model.WxChosen;
import lic.swifter.box.util.ViewUtil;

/**
 * Created by cheng on 2016/8/20.
 */
public class WxChosenHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView titleText;
    TextView sourceText;

    public WxChosenHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.wx_chosen_image);
        titleText = itemView.findViewById(R.id.wx_chosen_title);
        sourceText = itemView.findViewById(R.id.wx_chosen_source);

        ViewUtil.waveView(itemView);
    }

    public void setData(final Context context, final WxChosen data) {
        titleText.setText(data.title);
        sourceText.setText(data.source);

        Glide.with(context).load(data.firstImg).into(imageView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.INTENT_URL, data.url);
                intent.putExtra(WebViewActivity.INTENT_TITLE, data.title);
                context.startActivity(intent);
            }
        });
    }
}
