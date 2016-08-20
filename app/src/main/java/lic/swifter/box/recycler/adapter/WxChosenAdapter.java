package lic.swifter.box.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lic.swifter.box.R;
import lic.swifter.box.api.model.WxChosenWrapper;
import lic.swifter.box.recycler.holder.WxChosenHolder;

/**
 * Created by cheng on 2016/8/20.
 */

public class WxChosenAdapter extends RecyclerView.Adapter<WxChosenHolder> {

    private WxChosenWrapper wrapper;
    private Context context;

    public WxChosenAdapter(Context context, WxChosenWrapper wrapper) {
        this.context = context;
        this.wrapper = wrapper;
    }

    @Override
    public WxChosenHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wx_chosen, parent, false);
        return new WxChosenHolder(rootView);
    }

    @Override
    public void onBindViewHolder(WxChosenHolder holder, int position) {
        holder.setData(context, wrapper.list[position]);
    }

    @Override
    public int getItemCount() {
        return wrapper.list.length;
    }
}
