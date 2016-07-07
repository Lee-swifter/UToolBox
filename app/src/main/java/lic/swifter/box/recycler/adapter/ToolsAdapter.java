package lic.swifter.box.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import lic.swifter.box.R;
import lic.swifter.box.recycler.DragCallback;
import lic.swifter.box.recycler.ToolHolder;

/**
 *
 * Created by Swifter on 2016/7/6.
 */
public class ToolsAdapter extends RecyclerView.Adapter<ToolHolder> implements DragCallback.ItemTouchHelperAdapter {

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
    public void onBindViewHolder(ToolHolder holder, final int position) {
        holder.view.setImageAndText(R.drawable.ip_icon, R.string.ip);
        holder.view.setOnClickListener(view -> {
            if(onItemClickListener != null)
                onItemClickListener.onItemClickListener(position);
        });
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        notifyItemRemoved(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }
}
