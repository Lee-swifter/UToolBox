package lic.swifter.box.mvp.presenter;

import java.util.List;

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.MovieRank;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.mvp.view.IView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cheng on 2016/8/28.
 */

public class MovieRankingPresenter implements NetPresenter<String> {

    private IView<String, List<MovieRank>> iView;
    private Call<Result<List<MovieRank>>> call;

    public MovieRankingPresenter(IView<String, List<MovieRank>> view) {
        this.iView = view;
    }

    @Override
    public void query(String queryParameter) {
        iView.beforeQuery(queryParameter);

        JuheApi juheApi = ApiHelper.getJuhe(ApiHelper.V_JUHE_CN);
        call = juheApi.queryMovieRanking(queryParameter);
        call.enqueue(new Callback<Result<List<MovieRank>>>() {
            @Override
            public void onResponse(Call<Result<List<MovieRank>>> call, Response<Result<List<MovieRank>>> response) {
                if(response.isSuccessful()) {
                    Result<List<MovieRank>> resp = response.body();
                    if(resp.resultcode == 200) {
                        iView.afterQuery(NetQueryType.NET_RESPONSE_SUCCESS, resp);
                    } else
                        iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR_REASON, resp);
                } else
                    iView.afterQuery(NetQueryType.NET_RESPONSE_ERROR, null);
            }

            @Override
            public void onFailure(Call<Result<List<MovieRank>>> call, Throwable t) {
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
