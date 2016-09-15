package lic.swifter.box.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lic.swifter.box.R;
import lic.swifter.box.api.model.TopNewsWrapper;
import lic.swifter.box.recycler.holder.NewsItemHolder;

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
 * Created by cheng on 2016/9/15.
 */

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemHolder> {

    private Context context;
    private List<TopNewsWrapper.News> newsList;

    public NewsItemAdapter(Context context, List<TopNewsWrapper.News> list) {
        this.context = context;
        newsList = list;
    }

    @Override
    public NewsItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_news, parent, false);
        return new NewsItemHolder(rootView);
    }

    @Override
    public void onBindViewHolder(NewsItemHolder holder, int position) {
        holder.setData(context, newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
