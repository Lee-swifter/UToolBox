package lic.swifter.box.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import lic.swifter.box.R;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.WxChosenWrapper;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.presenter.WxChosenPresenter;
import lic.swifter.box.mvp.view.IView;
import lic.swifter.box.recycler.adapter.WxChosenAdapter;
import lic.swifter.box.recycler.divider.GridDivider;

/**
 * Created by cheng on 2016/8/20.
 */

public class WxChosenFragment extends BaseFragment implements IView<Class<Void>, WxChosenWrapper>{

    ProgressBar progressBar;
    TextView statusText;
    RecyclerView recycler;

    private WxChosenPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wechat_chosen, container, false);
        progressBar = rootView.findViewById(R.id.wx_progress_bar);
        statusText = rootView.findViewById(R.id.wx_status_text);
        recycler = rootView.findViewById(R.id.wx_content_recycler);


        presenter = new WxChosenPresenter(this);
        presenter.query();
        statusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.query();
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        presenter.cancelQuery();
        super.onDestroyView();
    }

    @Override
    public void beforeQuery(Class<Void> requestParameter) {
        fadeInView(progressBar);
        fadeOutView(statusText);
        fadeOutView(recycler);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<WxChosenWrapper> response) {
        switch (type) {
            case NET_RESPONSE_SUCCESS:
                recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                if(getContext() != null)
                recycler.addItemDecoration(new GridDivider(getContext(), LinearLayoutManager.HORIZONTAL));
                recycler.setAdapter(new WxChosenAdapter(getContext(), response.result));

                fadeInView(recycler);
                fadeOutView(progressBar);
                break;
            case NET_RESPONSE_ERROR_REASON:
                statusText.setText(response.reason);

                onFailure();
                break;
            case NET_RESPONSE_ERROR:
                statusText.setText(R.string.response_error);

                onFailure();
                break;
            case NET_REQUEST_FAILURE:
                statusText.setText(R.string.net_failure);

                onFailure();
                break;
            default:
                break;
        }
    }

    private void onFailure() {
        fadeInView(statusText);
        fadeOutView(progressBar);
    }
}
