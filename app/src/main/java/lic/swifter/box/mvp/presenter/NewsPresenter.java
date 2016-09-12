package lic.swifter.box.mvp.presenter;

/**
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

import com.baidu.mobstat.StatService;

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.TopNewsWrapper;
import lic.swifter.box.mvp.view.IView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsPresenter implements NetPresenter<String> {

    private IView<String, TopNewsWrapper> iView;
    private Call<Result<TopNewsWrapper>> call;

    public NewsPresenter(IView<String, TopNewsWrapper> iView) {
        this.iView = iView;
    }

    @Override
    public void query(String queryParameter) {
        StatService.onEvent(iView.getContext(), "news_query", "pass", 1);
        iView.beforeQuery(queryParameter);

        JuheApi juheApi = ApiHelper.getJuhe(ApiHelper.V_JUHE_CN);
        call = juheApi.queryNews(queryParameter);
        call.enqueue(new Callback<Result<TopNewsWrapper>>() {
            @Override
            public void onResponse(Call<Result<TopNewsWrapper>> call, Response<Result<TopNewsWrapper>> response) {
                if(response.isSuccessful()) {
                    Result<TopNewsWrapper> resp = response.body();
                    if(resp.resultcode == 200) {
                        iView.afterQuery(NetQueryType.NET_RESPONSE_SUCCESS, resp);
                    } else
                        iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR_REASON, resp);
                } else
                    iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR, null);
            }

            @Override
            public void onFailure(Call<Result<TopNewsWrapper>> call, Throwable t) {
                t.printStackTrace();
                iView.afterQuery(NetQueryType.NET_REQUEST_FAILURE, null);
            }
        });
    }

    @Override
    public void cancelQuery() {
        if(call != null && !call.isCanceled()) {
            call.cancel();
        }
    }
}
