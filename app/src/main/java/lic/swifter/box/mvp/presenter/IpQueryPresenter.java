package lic.swifter.box.mvp.presenter;

import android.view.View;

import lic.swifter.box.R;
import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.IpLocation;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.mvp.view.IpQueryView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lic on 16-8-16.
 */
public class IpQueryPresenter implements NetPresenter<String> {

    private IpQueryView ipQueryView;

    public IpQueryPresenter(IpQueryView ipQueryView) {
        this.ipQueryView = ipQueryView;
    }

    @Override
    public void beforeQuery() {
        ipQueryView.beforeQuery();
    }

    @Override
    public void query(String ip) {
        JuheApi juheApi = ApiHelper.getJuhe();
        Call<Result<IpLocation>> call = juheApi.queryIp(ip);
        call.enqueue(new Callback<Result<IpLocation>>() {
            @Override
            public void onResponse(Call<Result<IpLocation>> call, Response<Result<IpLocation>> response) {
                if (response.isSuccessful()) {
                    if(response.body().resultcode != 200) {
                        resultAreaText.setText(response.body().reason);
                        resultLocationText.setVisibility(View.GONE);
                    } else {
                        currentResult = response.body().result;
                        currentResult.searchText = searchString;

                        resultAreaText.setText(currentResult.area);
                        resultLocationText.setText(currentResult.location);
                        resultLocationText.setVisibility(View.VISIBLE);

                        saveInDb(currentResult);
                    }
                } else {
                    resultAreaText.setText(R.string.response_error);
                    resultLocationText.setVisibility(View.GONE);
                }
                fadeOutView(progress);
                fadeInView(resultWrapper);
            }

            @Override
            public void onFailure(Call<Result<IpLocation>> call, Throwable t) {
                t.printStackTrace();

                resultAreaText.setText(R.string.net_failure);
                resultLocationText.setVisibility(View.GONE);
                fadeOutView(progress);
                fadeInView(resultWrapper);
            }
        });
    }

    @Override
    public void afterQuery() {

    }
}
