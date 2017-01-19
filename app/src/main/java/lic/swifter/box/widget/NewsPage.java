package lic.swifter.box.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.TopNewsWrapper;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.presenter.NewsPresenter;
import lic.swifter.box.mvp.view.IView;
import lic.swifter.box.recycler.adapter.NewsItemAdapter;
import lic.swifter.box.recycler.divider.GridDivider;
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

public class NewsPage extends FrameLayout implements IView<String, TopNewsWrapper> {

    @Bind(R.id.page_news_progress)
    ProgressBar progress;
    @Bind(R.id.page_news_status_text)
    TextView statusText;
    @Bind(R.id.page_news_recycler_view)
    RecyclerView recyclerView;

    private String type;
    private NewsPresenter presenter;
    private int duration;

    public NewsPage(Context context) {
        this(context, null);
    }

    public NewsPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewsPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        duration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        LayoutInflater.from(context).inflate(R.layout.page_news, this);
        ButterKnife.bind(this);
        presenter = new NewsPresenter(this);
    }

    public void setType(@JuheApi.NewsType String type) {
        this.type = type;
        presenter.query(this.type);
        statusText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.query(NewsPage.this.type);
            }
        });
    }

    @Override
    public void beforeQuery(String requestParameter) {
        ViewUtil.fadeInView(progress, duration);
        ViewUtil.fadeOutView(statusText, duration);
        ViewUtil.fadeOutView(recyclerView, duration);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<TopNewsWrapper> response) {
        switch (type) {
            case NET_RESPONSE_SUCCESS:
                ViewUtil.fadeOutView(progress, duration);
                ViewUtil.fadeOutView(statusText, duration);
                ViewUtil.fadeInView(recyclerView, duration);

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.addItemDecoration(new GridDivider(getContext(), LinearLayoutManager.HORIZONTAL));
                recyclerView.setAdapter(new NewsItemAdapter(getContext(), response.result.data));
                break;
            case NET_RESPONSE_ERROR_REASON:
                changeViewsWhenNetError();
                statusText.setText(response.reason);
                break;
            case NET_RESPONSE_ERROR:
                changeViewsWhenNetError();
                statusText.setText(R.string.response_error);
                break;
            case NET_REQUEST_FAILURE:
                changeViewsWhenNetError();
                statusText.setText(R.string.net_failure);
                break;
        }
    }

    private void changeViewsWhenNetError() {
        progress.setVisibility(View.GONE);
        ViewUtil.fadeInView(statusText, duration);
        ViewUtil.fadeOutView(recyclerView, duration);
    }

}
