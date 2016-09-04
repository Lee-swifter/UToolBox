package lic.swifter.box.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.TopNewsWrapper;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.presenter.NewsPresenter;
import lic.swifter.box.mvp.view.IView;

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
 * Created by cheng on 2016/9/2.
 */

public class TopNewsFragment extends BaseFragment implements IView<String, TopNewsWrapper> {

    @Bind(R.id.fragment_top_news_view_pager)
    ViewPager viewPager;
    private NewsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_news, container, false);
        ButterKnife.bind(this, rootView);
        presenter = new NewsPresenter(this);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void beforeQuery(String requestParameter) {

    }

    @Override
    public void afterQuery(NetQueryType type, Result<TopNewsWrapper> response) {

    }

}
