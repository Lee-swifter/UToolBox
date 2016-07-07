package lic.swifter.box.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import lic.swifter.box.R;
import lic.swifter.box.util.ToolHolder;

/**
 *
 * Created by Swifter on 2016/7/6.
 */
public class ToolsAdapter extends RecyclerView.Adapter<ToolHolder> {

    @Override
    public ToolHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ToolHolder.getInstance(parent.getContext());
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public void onBindViewHolder(ToolHolder holder, int position) {
        holder.view.setImageAndText(R.drawable.ip_icon, R.string.ip);
    }

}
