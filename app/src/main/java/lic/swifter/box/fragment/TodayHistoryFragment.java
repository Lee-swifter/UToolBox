package lic.swifter.box.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import lic.swifter.box.R;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.TodayHistoryResult;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.presenter.TodayHistoryPresenter;
import lic.swifter.box.mvp.view.IView;
import lic.swifter.box.widget.TodayHistoryPage;

/**
 *
 * Created by cheng on 2016/8/19.
 */

public class TodayHistoryFragment extends BaseFragment implements IView<String, List<TodayHistoryResult>> {

    ProgressBar progress;
    TextView statusText;
    ViewPager viewPager;

    TodayHistoryPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_today_history, container, false);
        progress = rootView.findViewById(R.id.today_history_progress);
        statusText = rootView.findViewById(R.id.today_history_text);
        viewPager = rootView.findViewById(R.id.today_history_view_pager);

        presenter = new TodayHistoryPresenter(this);
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
    public void beforeQuery(String requestParameter) {
        statusText.setVisibility(View.GONE);
        fadeInView(progress);
        fadeOutView(viewPager);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<List<TodayHistoryResult>> response) {
        switch (type) {
            case NET_RESPONSE_SUCCESS:
                viewPager.setAdapter(new TohAdapter(response.result));
                fadeInView(viewPager);
                fadeOutView(progress);
                break;
            case NET_RESPONSE_ERROR_REASON:
                statusText.setText(response.reason);
                fadeInView(statusText);
                fadeOutView(progress);
                break;
            case NET_RESPONSE_ERROR:
                statusText.setText(R.string.response_error);
                fadeInView(statusText);
                fadeOutView(progress);
                break;
            case NET_REQUEST_FAILURE:
                statusText.setText(R.string.net_failure);
                fadeInView(statusText);
                fadeOutView(progress);
                break;
            default:
                break;
        }
    }

    private class TohAdapter extends PagerAdapter {

        private List<TodayHistoryResult> list;

        private TohAdapter(List<TodayHistoryResult> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view,@NonNull Object object) {
            return view == object;
        }

        @Override
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TodayHistoryPage page = new TodayHistoryPage(getContext());
            page.setData(getContext(), list.get(position));
            container.addView(page);
            return page;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position,@NonNull Object object) {
            container.removeView((View)object);
        }
    }

}
