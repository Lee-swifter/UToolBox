package lic.swifter.box.recycler.holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
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

    @Bind(R.id.item_news_title)
    TextView titleText;
    @Bind(R.id.item_news_image_1)
    ImageView firstImageView;
    @Bind(R.id.item_news_image_2)
    ImageView secondImageView;
    @Bind(R.id.item_news_image_3)
    ImageView thirdImageView;
    @Bind(R.id.item_news_author_time)
    TextView authorTimeView;

    public NewsItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        ViewUtil.waveView(itemView);
    }

    public void setData(final Context context, final TopNewsWrapper.News news) {
        titleText.setText(news.title);
        authorTimeView.setText(news.author_name + " "+ news.date);

        String[] images = new String[]{news.thumbnail_pic_s,news.thumbnail_pic_s02,news.thumbnail_pic_s03};

        if(images[0] != null)
            Glide.with(context).load(images[0]).into(firstImageView);
        if(images[1] != null && !images[1].equals(images[0]))
            Glide.with(context).load(images[1]).into(secondImageView);
        if(images[2] != null && !images[2].equals(images[0]) && !images[2].equals(images[1]))
            Glide.with(context).load(images[2]).into(thirdImageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(news.url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
    }
}
