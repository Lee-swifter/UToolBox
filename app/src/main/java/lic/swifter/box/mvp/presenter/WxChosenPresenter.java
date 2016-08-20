package lic.swifter.box.mvp.presenter;

import com.baidu.mobstat.StatService;

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.WxChosenWrapper;
import lic.swifter.box.mvp.view.IView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cheng on 2016/8/20.
 */

public class WxChosenPresenter implements NPresenter {

    private IView<Class<Void>, WxChosenWrapper> iView;
    private Call<Result<WxChosenWrapper>> call;

    public WxChosenPresenter(IView<Class<Void>, WxChosenWrapper> view) {
        this.iView = view;
    }

    @Override
    public void query() {
        iView.beforeQuery(Void.TYPE);
        StatService.onEvent(iView.getContext(), "wechat_chosen", "pass", 1);

        JuheApi juheApi = ApiHelper.getJuhe(ApiHelper.V_JUHE_CN);
        call = juheApi.queryWxChosen();
        call.enqueue(new Callback<Result<WxChosenWrapper>>() {
            @Override
            public void onResponse(Call<Result<WxChosenWrapper>> call, Response<Result<WxChosenWrapper>> response) {
                if(response.isSuccessful()) {
                    Result<WxChosenWrapper> resp = response.body();
                    if(resp.resultcode == 0) {
                        iView.afterQuery(NetQueryType.NET_RESPONSE_SUCCESS, resp);
                    } else {
                        iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR_REASON, resp);
                    }
                } else {
                    iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR, null);
                }
            }

            @Override
            public void onFailure(Call<Result<WxChosenWrapper>> call, Throwable t) {
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
