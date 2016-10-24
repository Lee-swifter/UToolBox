package lic.swifter.box.recycler.adapter;
/*
 * Copyright (C) 2015, Lee-swifter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by cheng on 2016/10/24.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lic.swifter.box.R;
import lic.swifter.box.activity.TvChannelActivity;
import lic.swifter.box.api.model.TvCategory;
import lic.swifter.box.recycler.holder.TvViewHolder;

public class TvCategoryAdapter extends RecyclerView.Adapter<TvViewHolder> {

    private List<TvCategory> list;
    private Context context;

    public TvCategoryAdapter(Context context, List<TvCategory> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_text, parent, false);

        return new TvViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final TvViewHolder holder, int position) {
        holder.setText(list.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TvChannelActivity.class);
                intent.putExtra(TvChannelActivity.TV_CATEGORY_INTENT, list.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
