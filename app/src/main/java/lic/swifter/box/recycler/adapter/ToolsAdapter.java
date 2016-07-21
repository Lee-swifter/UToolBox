package lic.swifter.box.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import lic.swifter.box.R;
import lic.swifter.box.recycler.ToolHolder;

/**
 *
 * Created by Swifter on 2016/7/6.
 */
public class ToolsAdapter extends RecyclerView.Adapter<ToolHolder> {

    private OnItemClickListener onItemClickListener;

    @Override
    public ToolHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ToolHolder.getInstance(parent.getContext());
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public void onBindViewHolder(final ToolHolder holder, int position) {
        holder.view.setImageAndText(R.drawable.ip_icon, R.string.ip);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(onItemClickListener != null)
                onItemClickListener.onItemClickListener(holder.getAdapterPosition());
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }
}
