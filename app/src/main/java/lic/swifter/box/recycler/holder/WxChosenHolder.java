package lic.swifter.box.recycler.holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.model.WxChosen;
import lic.swifter.box.util.ViewUtil;

/**
 * Created by cheng on 2016/8/20.
 */
public class WxChosenHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.wx_chosen_image)
    ImageView imageView;
    @Bind(R.id.wx_chosen_title)
    TextView titleText;
    @Bind(R.id.wx_chosen_source)
    TextView sourceText;

    public WxChosenHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        ViewUtil.waveView(itemView);
    }

    public void setData(final Context context, final WxChosen data) {
        titleText.setText(data.title);
        sourceText.setText(data.source);

        Glide.with(context).load(data.firstImg).into(imageView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(data.url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
    }
}
