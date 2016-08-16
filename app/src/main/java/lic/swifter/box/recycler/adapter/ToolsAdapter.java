package lic.swifter.box.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import lic.swifter.box.data.FragmentsFlag;
import lic.swifter.box.data.ToolsCollection;
import lic.swifter.box.recycler.holder.ToolHolder;

/**
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
        return ToolsCollection.tools.length;
    }

    @Override
    public void onBindViewHolder(final ToolHolder holder, int position) {
        holder.view.setImageAndText(ToolsCollection.tools[position]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClickListener(ToolsCollection.tools[holder.getAdapterPosition()].flag);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(FragmentsFlag flag);
    }
}
