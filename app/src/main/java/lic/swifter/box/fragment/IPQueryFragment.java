package lic.swifter.box.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lic.swifter.box.R;
import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.IpLocation;
import lic.swifter.box.api.model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IPQueryFragment extends BaseFragment {

    private TextInputEditText inputEditText;

    public IPQueryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ip_query, container, false);

        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        inputEditText = (TextInputEditText) rootView.findViewById(R.id.ip_input_text);

        inputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    queryIp();
                    return true;
                }
                return false;
            }
        });
    }

    private void queryIp() {
        JuheApi juheApi = ApiHelper.getJuhe();
        Call<Result<IpLocation>> call = juheApi.queryIp("www.bilibili.com", JuheApi.APP_KEY_IP);
        call.enqueue(new Callback<Result<IpLocation>>() {
            @Override
            public void onResponse(Call<Result<IpLocation>> call, Response<Result<IpLocation>> response) {
                Log.i("taylor", "onResponse... response = "+response.body().toString());
            }

            @Override
            public void onFailure(Call<Result<IpLocation>> call, Throwable t) {
                t.printStackTrace();
                Log.i("taylor", "onFailure...");
            }
        });

    }

}
