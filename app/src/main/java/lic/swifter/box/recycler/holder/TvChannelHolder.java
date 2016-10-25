package lic.swifter.box.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.model.TvChannel;
import lic.swifter.box.util.ViewUtil;
import lic.swifter.box.widget.TvChannelItem;

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

public class TvChannelHolder extends RecyclerView.ViewHolder {
    private TvChannelItem channelItem;

    public TvChannelHolder(View itemView) {
        super(itemView);
        ViewUtil.waveView(itemView);
        channelItem = ButterKnife.findById(itemView, R.id.item_tv_channel);
    }

    public void setChannel(TvChannel channel) {
        channelItem.setChannel(channel);
    }
}
