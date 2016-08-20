package lic.swifter.box.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.model.JokesWrapper;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.mvp.presenter.JokesPresenter;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.view.IView;

/**
 * Created by cheng on 2016/8/20.
 */

public class JokesFragment extends BaseFragment implements IView<Class<Void>, JokesWrapper> {

    @Bind(R.id.jokes_progress)
    ProgressBar progress;
    @Bind(R.id.jokes_status)
    TextView status;
    @Bind(R.id.jokes_view_pager)
    ViewPager viewPager;

    private JokesPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jokes, container, false);
        ButterKnife.bind(this, rootView);

        presenter = new JokesPresenter(this);
        presenter.query();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        presenter.cancelQuery();
        super.onDestroyView();
    }

    @Override
    public void beforeQuery(Class<Void> requestParameter) {
        status.setVisibility(View.GONE);
        fadeInView(progress);
        fadeOutView(viewPager);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<JokesWrapper> response) {
        switch (type) {
            case NET_RESPONSE_SUCCESS:


                break;
        }
    }

}
