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

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.baidu.mobstat.StatService;

import java.util.ArrayList;
import java.util.List;

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.BaiduWeight;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.db.BoxContract;
import lic.swifter.box.db.BoxDbHelper;
import lic.swifter.box.mvp.view.BaiduWeightView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiduWeightPresenter implements NetPresenter<String>, DbPresenter<BaiduWeight>  {

    private BaiduWeightView iView;
    private Result<BaiduWeight> lastResult;
    private Call<Result<BaiduWeight>> call;

    public BaiduWeightPresenter(BaiduWeightView iView) {
        this.iView = iView;
    }

    @Override
    public void readFromDb() {
        new AsyncTask<Void, Void, List<BaiduWeight>>() {

            @Override
            protected List<BaiduWeight> doInBackground(Void... params) {
                final List<BaiduWeight> list = new ArrayList<>();

                SQLiteDatabase sqLiteDatabase = BoxDbHelper.getInstance(iView.getContext()).getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(BoxContract.BaiduWeightEntry.TABLE_NAME, null, null, null, null, null,
                        BoxContract.BaiduWeightEntry.COLUMN_NAME_SEARCH_TIME_STAMP + " DESC");
                if(cursor.moveToFirst()) {
                    do{
                        BaiduWeight weight = new BaiduWeight();
                        weight.timeStamp = cursor.getLong(cursor.getColumnIndexOrThrow(BoxContract.BaiduWeightEntry.COLUMN_NAME_SEARCH_TIME_STAMP));
                        weight.webSite = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.BaiduWeightEntry.COLUMN_NAME_SEARCH_WEBSITE));
                        weight.Weight = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.BaiduWeightEntry.COLUMN_NAME_RESULT_WEIGHT));
                        weight.From = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.BaiduWeightEntry.COLUMN_NAME_RESULT_WEIGHT_FROM));
                        weight.To = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.BaiduWeightEntry.COLUMN_NAME_RESULT_WEIGHT_TO));
                        list.add(weight);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                sqLiteDatabase.close();
                return list;
            }

            @Override
            protected void onPostExecute(List<BaiduWeight> weight) {
                iView.afterReadResults(weight);
            }
        }.execute();
    }

    @Override
    public void saveToDb(final BaiduWeight data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase sqLiteDatabase = BoxDbHelper.getInstance(iView.getContext()).getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(BoxContract.BaiduWeightEntry.COLUMN_NAME_SEARCH_TIME_STAMP, System.currentTimeMillis());
                values.put(BoxContract.BaiduWeightEntry.COLUMN_NAME_SEARCH_WEBSITE, data.webSite);
                values.put(BoxContract.BaiduWeightEntry.COLUMN_NAME_RESULT_WEIGHT, data.Weight);
                values.put(BoxContract.BaiduWeightEntry.COLUMN_NAME_RESULT_WEIGHT_FROM, data.From);
                values.put(BoxContract.BaiduWeightEntry.COLUMN_NAME_RESULT_WEIGHT_TO, data.To);
                sqLiteDatabase.insert(BoxContract.BaiduWeightEntry.TABLE_NAME, "null", values);
            }
        }).start();
    }

    @Override
    public void query(final String queryParameter) {
        iView.beforeQuery(queryParameter);
        StatService.onEvent(iView.getContext(), "baidu_weight_query", "pass", 1);

        if(lastResult != null && lastResult.reason != null)
            iView.insertLastResult(lastResult.result);

        JuheApi juheApi = ApiHelper.getJuhe(ApiHelper.OP_JUHE_CN);
        call = juheApi.queryBaiduWeight(queryParameter);
        call.enqueue(new Callback<Result<BaiduWeight>>() {
            @Override
            public void onResponse(Call<Result<BaiduWeight>> call, Response<Result<BaiduWeight>> response) {
                if(response.isSuccessful()) {
                    lastResult = response.body();
                    if(lastResult.error_code == 0) {
                        lastResult.result.webSite = queryParameter;
                        iView.afterQuery(NetQueryType.NET_RESPONSE_SUCCESS, lastResult);
                        saveToDb(lastResult.result);
                    } else {
                        iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR_REASON, lastResult);
                        lastResult = null;
                    }
                } else {
                    iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR, null);
                    lastResult = null;
                }

                iView.gotResponse();
            }

            @Override
            public void onFailure(Call<Result<BaiduWeight>> call, Throwable t) {
                t.printStackTrace();
                iView.afterQuery(NetQueryType.NET_REQUEST_FAILURE, null);
                lastResult = null;
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
