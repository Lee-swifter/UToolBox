package lic.swifter.box.recycler.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.activity.WebViewActivity;
import lic.swifter.box.api.model.TopNewsWrapper;
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
 * Created by cheng on 2016/9/15.
 */

public class NewsItemHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item_news_image)
    ImageView firstImageView;
    @Bind(R.id.item_news_title)
    TextView titleText;
    @Bind(R.id.item_news_source)
    TextView authorTimeView;

    public NewsItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        ViewUtil.waveView(itemView);
    }

    public void setData(final Context context, final TopNewsWrapper.News news) {
        titleText.setText(news.title);
        authorTimeView.setText(news.author_name + " "+ news.date);

        if(!TextUtils.isEmpty(news.thumbnail_pic_s))
            Glide.with(context).load(news.thumbnail_pic_s).into(firstImageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.INTENT_URL, news.url);
                intent.putExtra(WebViewActivity.INTENT_TITLE, news.title);
                context.startActivity(intent);
            }
        });
    }
}
