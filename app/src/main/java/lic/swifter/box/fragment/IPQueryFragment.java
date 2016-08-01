package lic.swifter.box.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.IpLocation;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.widget.CanaroTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IPQueryFragment extends BaseFragment {

    @Bind(R.id.ip_input_text)
    TextInputEditText inputEditText;
    @Bind(R.id.ip_progress)
    ProgressBar progress;
    @Bind(R.id.ip_result_area)
    CanaroTextView resultAreaText;
    @Bind(R.id.ip_result_location)
    CanaroTextView resultLocationText;
    @Bind(R.id.ip_result_result)
    LinearLayout resultWrapper;
    @Bind(R.id.ip_record_list)
    RecyclerView recyclerView;

    public IPQueryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ip_query, container, false);
        ButterKnife.bind(this, rootView);

        initView();
        return rootView;
    }

    private void initView() {
        inputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    queryIp(inputEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void queryIp(String ip) {
        //TODO: display when progress.

        JuheApi juheApi = ApiHelper.getJuhe();
        Call<Result<IpLocation>> call = juheApi.queryIp(ip);
        call.enqueue(new Callback<Result<IpLocation>>() {
            @Override
            public void onResponse(Call<Result<IpLocation>> call, Response<Result<IpLocation>> response) {
                IpLocation ipLocation = response.body().result;

            }

            @Override
            public void onFailure(Call<Result<IpLocation>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }



}
