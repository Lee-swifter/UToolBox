package lic.swifter.box.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lic.swifter.box.R;
import lic.swifter.box.api.model.IdResult;
import lic.swifter.box.recycler.holder.IdResultHolder;

/**
 * Created by cheng on 2016/8/19.3
 */

public class IdResultAdapter extends RecyclerView.Adapter<IdResultHolder> {

    private List<IdResult> list;

    public IdResultAdapter(List<IdResult> list) {
        this.list = list;
    }

    @Override
    public IdResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_id_result, parent, false);
        return new IdResultHolder(rootView);
    }

    @Override
    public void onBindViewHolder(IdResultHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addFirst(IdResult result) {
        list.add(0, result);
        notifyItemInserted(0);
    }
}