package lic.swifter.box.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
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
                viewPager.setAdapter(new JokesAdapter(response.result.data));

                fadeInView(viewPager);
                fadeOutView(progress);
                break;
            case NET_RESPONSE_ERROR_REASON:
                status.setText(response.reason);

                fadeInView(status);
                fadeOutView(progress);
                break;
            case NET_RESPONSE_ERROR:
                status.setText(R.string.response_error);

                fadeInView(status);
                fadeOutView(progress);
                break;
            case NET_REQUEST_FAILURE:
                status.setText(R.string.net_failure);

                fadeInView(status);
                fadeOutView(progress);
                break;
            default:
                break;
        }
    }


    private class JokesAdapter extends PagerAdapter {

        private JokesWrapper.Joke[] list;

        JokesAdapter(JokesWrapper.Joke[] list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View rootView = LayoutInflater.from(getContext()).inflate(R.layout.page_joke, container, false);
            ((TextView)rootView.findViewById(R.id.page_joke_content)).setText(list[position].content);
            ((TextView)rootView.findViewById(R.id.page_joke_time)).setText(list[position].updatetime);
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
