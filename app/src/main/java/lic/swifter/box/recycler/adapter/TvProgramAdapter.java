package lic.swifter.box.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lic.swifter.box.R;
import lic.swifter.box.api.model.TvProgram;
import lic.swifter.box.recycler.holder.TvProgramHolder;

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
 * Created by cheng on 2016/10/25.
 */

public class TvProgramAdapter extends RecyclerView.Adapter<TvProgramHolder> {

    private List<TvProgram> list;

    public TvProgramAdapter(List<TvProgram> list) {
        this.list = list;
    }

    @Override
    public TvProgramHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_program, parent, false);
        return new TvProgramHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TvProgramHolder holder, int position) {
        holder.time.setText(list.get(position).time);
        holder.title.setText(list.get(position).pName);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
