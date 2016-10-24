package lic.swifter.box.mvp.presenter;
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

import java.util.List;

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.TvCategory;
import lic.swifter.box.mvp.view.IView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvPresenter implements NPresenter {

    private IView<Void, List<TvCategory>> iView;
    private Call<Result<List<TvCategory>>> call;

    public TvPresenter(IView<Void, List<TvCategory>> view) {
        this.iView = view;
    }

    @Override
    public void query() {
        iView.beforeQuery(null);

        call = ApiHelper.getJuhe(ApiHelper.JAPI_JUHE_CN).queryTvCategory();
        call.enqueue(new Callback<Result<List<TvCategory>>>() {
            @Override
            public void onResponse(Call<Result<List<TvCategory>>> call, Response<Result<List<TvCategory>>> response) {
                if(response.isSuccessful()) {
                    Result<List<TvCategory>> body = response.body();
                    if(body.error_code == 0) {
                        iView.afterQuery(NetQueryType.NET_RESPONSE_SUCCESS, body);
                    } else {
                        iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR_REASON, body);
                    }
                } else {
                    iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<Result<List<TvCategory>>> call, Throwable t) {
                t.printStackTrace();
                iView.afterQuery(NetQueryType.NET_REQUEST_FAILURE, null);
            }
        });
    }

    @Override
    public void cancelQuery() {
        if(call != null && !call.isCanceled())
            call.cancel();
    }
}
