package lic.swifter.box.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lic.swifter.box.R;
import lic.swifter.box.api.model.IpLocation;
import lic.swifter.box.recycler.holder.IpResultHolder;

/**
 * Created by lic on 16-8-15.
 */
public class IpResultAdapter extends RecyclerView.Adapter<IpResultHolder> {

    private List<IpLocation> ipList;

    public IpResultAdapter(List<IpLocation> list) {
        ipList = list;
    }

    @Override
    public IpResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ip_result, parent, false);
        return new IpResultHolder(rootView);
    }

    @Override
    public void onBindViewHolder(IpResultHolder holder, int position) {
        holder.setData(ipList.get(position));
    }

    @Override
    public int getItemCount() {
        return ipList.size();
    }
}
