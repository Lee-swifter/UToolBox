package lic.swifter.box.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.model.PhoneResult;
import lic.swifter.box.util.ViewUtil;

/**
 * Created by cheng on 2016/8/19.
 */

public class PhoneResultHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item_phone_number)
    TextView phoneText;
    @Bind(R.id.item_phone_result_location)
    TextView locationText;
    @Bind(R.id.item_phone_result_card)
    TextView cardText;

    public PhoneResultHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        ViewUtil.waveView(itemView);
    }

    public void setData(PhoneResult data) {
        phoneText.setText(data.phoneNumber);
        locationText.setText(data.province + " " + data.city);
        cardText.setText(data.company + " " + data.card);
    }
}
