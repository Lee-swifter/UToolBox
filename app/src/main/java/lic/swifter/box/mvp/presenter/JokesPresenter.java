package lic.swifter.box.mvp.presenter;

import com.baidu.mobstat.StatService;

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.JokesWrapper;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.mvp.view.IView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cheng on 2016/8/20.
 */

public class JokesPresenter implements NPresenter {

    private IView<Class<Void>, JokesWrapper> iView;
    private Call<Result<JokesWrapper>> call;

    public JokesPresenter(IView<Class<Void>, JokesWrapper> view) {
        this.iView = view;
    }

    @Override
    public void query() {
        StatService.onEvent(iView.getContext(), "jokes_query", "pass", 1);

        JuheApi juheApi = ApiHelper.getJuhe(ApiHelper.JAPI_JUHE_CN);
        call = juheApi.queryJokes();
        call.enqueue(new Callback<Result<JokesWrapper>>() {
            @Override
            public void onResponse(Call<Result<JokesWrapper>> call, Response<Result<JokesWrapper>> response) {
                if(response.isSuccessful()) {
                    Result<JokesWrapper> resp = response.body();
                    if(resp.error_code == 0) {
                        iView.afterQuery(NetQueryType.NET_RESPONSE_SUCCESS, resp);
                    } else
                        iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR_REASON, resp);
                } else
                    iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR, null);
            }

            @Override
            public void onFailure(Call<Result<JokesWrapper>> call, Throwable t) {
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
