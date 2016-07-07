package lic.swifter.box.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import lic.swifter.box.widget.ToolView;

/**
 * Created by lic on 16-7-7.
 */
public class ToolHolder extends RecyclerView.ViewHolder {

    public ToolView view;

    public ToolHolder(View itemView) {
        super(itemView);
        if (itemView instanceof ToolView)
            view = (ToolView) itemView;
        else
            throw new IllegalArgumentException("only accept ToolView.");
    }

    public static ToolHolder getInstance(Context context) {
        return new ToolHolder(new ToolView(context));
    }
}
