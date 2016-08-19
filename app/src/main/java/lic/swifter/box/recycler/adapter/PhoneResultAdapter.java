package lic.swifter.box.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lic.swifter.box.R;
import lic.swifter.box.api.model.PhoneResult;
import lic.swifter.box.recycler.holder.PhoneResultHolder;

/**
 * Created by cheng on 2016/8/19.
 */

public class PhoneResultAdapter extends RecyclerView.Adapter<PhoneResultHolder> {

    private List<PhoneResult> list;

    public PhoneResultAdapter(List<PhoneResult> list) {
        this.list = list;
    }

    @Override
    public PhoneResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_query_result, parent, false);
        return new PhoneResultHolder(rootView);
    }

    @Override
    public void onBindViewHolder(PhoneResultHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addFirst(PhoneResult data) {
        list.add(0, data);
        notifyItemInserted(0);
    }
}
