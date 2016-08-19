package lic.swifter.box.mvp.presenter;

import java.util.Calendar;
import java.util.List;

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.TodayHistoryResult;
import lic.swifter.box.mvp.view.TodayHistoryView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cheng on 2016/8/19.
 */

public class TodayHistoryPresenter implements NetPresenter<String> {

    private TodayHistoryView tohView;

    public TodayHistoryPresenter(TodayHistoryView todayHistoryView) {
        this.tohView = todayHistoryView;
    }

    @Override
    public void query(String parameter) {
        tohView.beforeQuery(parameter);

        Calendar calendar = Calendar.getInstance();
        JuheApi juheApi = ApiHelper.getJuhe(ApiHelper.API_JUHEAPI);
        Call<Result<List<TodayHistoryResult>>> call =
                juheApi.queryToh(calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH));
        call.enqueue(new Callback<Result<List<TodayHistoryResult>>>() {
            @Override
            public void onResponse(Call<Result<List<TodayHistoryResult>>> call, Response<Result<List<TodayHistoryResult>>> response) {
                if(response.isSuccessful()) {
                    Result<List<TodayHistoryResult>> result = response.body();
                    if(result.resultcode == 0) {
                        tohView.afterQuery(NetQueryType.NET_RESPONSE_SUCCESS, result);
                    } else {
                        tohView.afterQuery(NetQueryType.NET_RESPONSE_ERROR_REASON, result);
                    }
                } else {
                    tohView.afterQuery(NetQueryType.NET_RESPONSE_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<Result<List<TodayHistoryResult>>> call, Throwable t) {
                t.printStackTrace();
                tohView.afterQuery(NetQueryType.NET_REQUEST_FAILURE, null);
            }
        });
    }

}
