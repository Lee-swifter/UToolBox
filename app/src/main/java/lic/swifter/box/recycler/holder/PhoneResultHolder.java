package lic.swifter.box.recycler.holder;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import lic.swifter.box.R;
import lic.swifter.box.api.model.PhoneResult;
import lic.swifter.box.util.ViewUtil;

/**
 * Created by cheng on 2016/8/19.
 */

public class PhoneResultHolder extends RecyclerView.ViewHolder {

    TextView phoneText;
    TextView locationText;
    TextView cardText;

    public PhoneResultHolder(View itemView) {
        super(itemView);
        phoneText = itemView.findViewById(R.id.item_phone_number);
        locationText = itemView.findViewById(R.id.item_phone_result_location);
        cardText = itemView.findViewById(R.id.item_phone_result_card);

        ViewUtil.waveView(itemView);
    }

    @SuppressLint("SetTextI18n")
    public void setData(PhoneResult data) {
        phoneText.setText(data.phoneNumber);
        locationText.setText(data.province + " " + data.city);
        cardText.setText(data.company + " " + data.card);
    }
}
