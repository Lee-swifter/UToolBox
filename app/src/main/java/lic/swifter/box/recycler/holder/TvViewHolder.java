package lic.swifter.box.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import lic.swifter.box.R;
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
 * Created by cheng on 2016/10/24.
 */

public class TvViewHolder extends RecyclerView.ViewHolder {

    private TextView text;

    public TvViewHolder(View itemView) {
        super(itemView);
        text = ButterKnife.findById(itemView, R.id.item_single_text_view);
        ViewUtil.waveView(itemView);
    }

    public void setText(String content) {
        text.setText(content);
    }
}
