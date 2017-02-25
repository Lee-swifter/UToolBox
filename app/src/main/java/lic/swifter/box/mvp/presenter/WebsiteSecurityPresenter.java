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
 */

import com.baidu.mobstat.StatService;

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.SiteSecurity;
import lic.swifter.box.mvp.view.IView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebsiteSecurityPresenter implements NetPresenter<String> {

    private IView<String, SiteSecurity> iView;
    private Call<Result<SiteSecurity>> call;

    public WebsiteSecurityPresenter(IView<String, SiteSecurity> view) {
        iView = view;
    }

    @Override
    public void query(String queryParameter) {
        iView.beforeQuery(queryParameter);
        StatService.onEvent(iView.getContext(), "website_security_query", "pass", 1);

        JuheApi juheApi = ApiHelper.getJuhe(ApiHelper.APIS_JUHE_CN);
        call = juheApi.querySiteSecurity(queryParameter);
        call.enqueue(new Callback<Result<SiteSecurity>>() {
            @Override
            public void onResponse(Call<Result<SiteSecurity>> call, Response<Result<SiteSecurity>> response) {
                if(response.isSuccessful()) {
                    Result<SiteSecurity> resp = response.body();
                    if(resp.resultcode == 200) {
                        iView.afterQuery(NetQueryType.NET_RESPONSE_SUCCESS, resp);
                    } else {
                        iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR_REASON, resp);
                    }
                } else {
                    iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<Result<SiteSecurity>> call, Throwable t) {
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
