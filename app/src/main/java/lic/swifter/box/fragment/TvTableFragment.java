package lic.swifter.box.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.TvCategory;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.presenter.TvPresenter;
import lic.swifter.box.mvp.view.IView;
import lic.swifter.box.recycler.adapter.TvCategoryAdapter;
import lic.swifter.box.recycler.divider.GridDivider;

/**
 *
 * Tv列表界面
 * A simple {@link BaseFragment} subclass.
 */
public class TvTableFragment extends BaseFragment implements IView<Void, List<TvCategory>> {

    @Bind(R.id.common_recycler_view)
    RecyclerView recycler;
    @Bind(R.id.common_center_progress)
    ProgressBar progress;
    @Bind(R.id.common_center_status_text)
    TextView status;

    private TvPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_recycler_status, container, false);
        ButterKnife.bind(this, rootView);
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.query();
            }
        });

        presenter = new TvPresenter(this);
        presenter.query();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void beforeQuery(Void requestParameter) {
        fadeInView(progress);
        fadeOutView(recycler);
        fadeOutView(status);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<List<TvCategory>> response) {
        switch (type) {
            case NET_RESPONSE_SUCCESS:
                fadeInView(recycler);
                fadeOutView(progress);
                fadeOutView(status);

                recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                recycler.addItemDecoration(new GridDivider(getContext()));
                recycler.setAdapter(new TvCategoryAdapter(getContext(), response.result));
                break;
            case NET_RESPONSE_ERROR_REASON:
                showStatus();
                status.setText(getString(R.string.click_to_retry, response.reason));
                break;
            case NET_RESPONSE_ERROR:
                showStatus();
                status.setText(R.string.response_error);
                break;
            case NET_REQUEST_FAILURE:
                showStatus();
                status.setText(R.string.net_failure);
                break;
        }
    }

    private void showStatus() {
        fadeInView(status);
        fadeOutView(progress);
        fadeOutView(recycler);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelQuery();
    }
}
