package lic.swifter.box.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lic.swifter.box.R;
import lic.swifter.box.api.model.BaiduWeight;
import lic.swifter.box.recycler.holder.BaiduWeightHolder;

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
 */

public class BaiduWeightAdapter extends RecyclerView.Adapter<BaiduWeightHolder> {

    private Context context;
    private List<BaiduWeight> list;

    public BaiduWeightAdapter(Context context, List<BaiduWeight> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BaiduWeightHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weight_result, parent, false);
        return new BaiduWeightHolder(rootView);
    }

    @Override
    public void onBindViewHolder(BaiduWeightHolder holder, int position) {
        holder.setData(context, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addFirst(BaiduWeight result) {
        list.add(0, result);
        notifyItemInserted(0);
    }
}
