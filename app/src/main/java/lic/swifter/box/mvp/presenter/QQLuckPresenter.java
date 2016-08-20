package lic.swifter.box.mvp.presenter;

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.QQLuck;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.mvp.view.IView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cheng on 2016/8/20.
 */

public class QQLuckPresenter implements NetPresenter<String> {

    private IView<String, QQLuck> iView;
    private Call<Result<QQLuck>> call;

    public QQLuckPresenter(IView<String, QQLuck> view) {
        this.iView = view;
    }

    @Override
    public void query(String queryParameter) {
        iView.beforeQuery(queryParameter);

        JuheApi juheApi = ApiHelper.getJuhe(ApiHelper.JAPI_JUHE_CN);
        call = juheApi.queryQQLuck(queryParameter);
        call.enqueue(new Callback<Result<QQLuck>>() {
            @Override
            public void onResponse(Call<Result<QQLuck>> call, Response<Result<QQLuck>> response) {
                if(response.isSuccessful()) {
                    Result<QQLuck> resp = response.body();
                    if(resp.resultcode == 0) {
                        iView.afterQuery(NetQueryType.NET_RESPONSE_SUCCESS, resp);
                    } else
                        iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR_REASON, resp);
                } else {
                    iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<Result<QQLuck>> call, Throwable t) {
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
