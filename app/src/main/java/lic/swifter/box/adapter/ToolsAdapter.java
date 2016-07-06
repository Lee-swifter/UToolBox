package lic.swifter.box.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import lic.swifter.box.R;
import lic.swifter.box.widget.ToolView;

/**
 *
 * Created by Swifter on 2016/7/6.
 */
public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ToolHolder> {
    private Context context;

    public ToolsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ToolHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ToolHolder(new ToolView(context));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public void onBindViewHolder(ToolHolder holder, int position) {
        ((ToolView)holder.itemView).setImageAndText(R.drawable.ip_icon, R.string.ip);
    }

    class ToolHolder extends RecyclerView.ViewHolder {

        public ToolHolder(View itemView) {
            super(itemView);
        }
    }
}
