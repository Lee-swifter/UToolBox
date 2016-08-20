package lic.swifter.box.mvp.presenter;

import java.util.Calendar;
import java.util.List;

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.TodayHistoryResult;
import lic.swifter.box.mvp.view.IView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cheng on 2016/8/19.
 */

public class TodayHistoryPresenter implements NPresenter {

    private IView<String, List<TodayHistoryResult>> tohView;
    private Call<Result<List<TodayHistoryResult>>> call;

    public TodayHistoryPresenter(IView<String, List<TodayHistoryResult>> todayHistoryView) {
        this.tohView = todayHistoryView;
    }

    @Override
    public void query() {
        tohView.beforeQuery(null);

        Calendar calendar = Calendar.getInstance();
        JuheApi juheApi = ApiHelper.getJuhe(ApiHelper.API_JUHEAPI_COM);
        call = juheApi.queryToh(calendar.get(Calendar.MONTH) + 1,
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

    @Override
    public void cancelQuery() {
        if(call != null && !call.isCanceled()) {
            call.cancel();
        }
    }

}
