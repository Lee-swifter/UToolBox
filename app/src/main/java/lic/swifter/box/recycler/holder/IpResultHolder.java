package lic.swifter.box.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import lic.swifter.box.R;
import lic.swifter.box.api.model.IpLocation;
import lic.swifter.box.util.ViewUtil;

/**
 * Created by lic on 16-8-15.
 */
public class IpResultHolder extends RecyclerView.ViewHolder {

    TextView searchDataTextView;
    TextView resultAreaTextView;
    TextView resultLocationTextView;

    public IpResultHolder(View itemView) {
        super(itemView);
        searchDataTextView = itemView.findViewById(R.id.item_ip_search_data);
        resultAreaTextView = itemView.findViewById(R.id.item_ip_result_area);
        resultLocationTextView = itemView.findViewById(R.id.item_ip_result_location);

        ViewUtil.waveView(itemView);
    }

    public void setData(IpLocation ipLocation) {
        searchDataTextView.setText(ipLocation.ip);
        resultAreaTextView.setText(ipLocation.area);
        resultLocationTextView.setText(ipLocation.location);
    }
}
