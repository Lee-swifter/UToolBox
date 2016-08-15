package lic.swifter.box.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null)
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });
    }

    private void queryIp(final String ip) {
        fadeInView(progress);

        JuheApi juheApi = ApiHelper.getJuhe();
        Call<Result<IpLocation>> call = juheApi.queryIp(ip);
        call.enqueue(new Callback<Result<IpLocation>>() {
            @Override
            public void onResponse(Call<Result<IpLocation>> call, Response<Result<IpLocation>> response) {
                if (response.isSuccessful()) {
                    IpLocation ipLocation = response.body().result;

                    resultAreaText.setText(ipLocation.area);
                    resultLocationText.setText(ipLocation.location);
                    resultLocationText.setVisibility(View.VISIBLE);
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


}
