package lic.swifter.box.recycler.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import lic.swifter.box.R;
import lic.swifter.box.api.model.BaiduWeight;
import lic.swifter.box.util.ViewUtil;

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

public class BaiduWeightHolder extends RecyclerView.ViewHolder {

    private TextView searchText;
    private TextView resultWeight;
    private TextView resultRange;

    public BaiduWeightHolder(View itemView) {
        super(itemView);
        searchText = itemView.findViewById(R.id.item_weight_search_data);
        resultWeight = itemView.findViewById(R.id.item_weight_result);
        resultRange = itemView.findViewById(R.id.item_weight_result_range);

        ViewUtil.waveView(itemView);
    }

    public void setData(Context context, BaiduWeight data) {
        searchText.setText(data.webSite);
        resultWeight.setText(context.getString(R.string.baidu_weight_result_item, data.Weight));
        resultRange.setText(context.getString(R.string.baidu_weight_range_result_item, data.From, data.To));
    }
}
