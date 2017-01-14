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

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.FontConversion;
import lic.swifter.box.mvp.view.FontConvertView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FontConvertPresenter {

    public static final int SIMPLIFIED_CHINESE = 0;     //简体中文
    public static final int TRADITIONAL_CHINESE = 1;    //繁体中文
    public static final int LEETSPEAK = 2;              //火星文

    private FontConvertView iView;
    private Call<FontConversion> traditionCall;
    private Call<FontConversion> leetspeakCall;

    public FontConvertPresenter(FontConvertView view) {
        iView = view;
    }

    public void query(final int type, String queryParameter) {
        iView.onStartQuery(type, queryParameter);
        JuheApi juheApi = ApiHelper.getJuhe(ApiHelper.JAPI_JUHE_CN);

        Call<FontConversion> call = juheApi.fontConvert(type, queryParameter);
        call.enqueue(new Callback<FontConversion>() {
            @Override
            public void onResponse(Call<FontConversion> call, Response<FontConversion> response) {
                if(response.isSuccessful()) {
                    FontConversion result = response.body();
                    if(result.error_code == 0) {
                        iView.onQueryResult(type, NetQueryType.NET_RESPONSE_SUCCESS, result);
                    } else
                        iView.onQueryResult(type, NetQueryType.NET_RESPONSE_ERROR_REASON, result);
                } else
                    iView.onQueryResult(type, NetQueryType.NET_RESPONSE_ERROR, null);
            }

            @Override
            public void onFailure(Call<FontConversion> call, Throwable t) {
                t.printStackTrace();
                iView.onQueryResult(type, NetQueryType.NET_REQUEST_FAILURE, null);
            }
        });

        if(type == TRADITIONAL_CHINESE) {
            traditionCall = call;
        } else if (type == LEETSPEAK) {
            leetspeakCall = call;
        }
    }

    public void cancelQuery() {
        if(traditionCall != null && !traditionCall.isCanceled())
            traditionCall.cancel();

        if(leetspeakCall != null && !leetspeakCall.isCanceled())
            leetspeakCall.cancel();
    }
}
