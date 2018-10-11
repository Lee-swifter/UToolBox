package lic.swifter.box.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lic.swifter.box.R;
import lic.swifter.box.widget.MovieRankingPage;

/**
 * Created by cheng on 2016/8/28.
 */
public class BoxOfficeFragment extends BaseFragment {

    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_ranking, container, false);
        viewPager = rootView.findViewById(R.id.movie_ranking_view_pager);

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new MovieRankingAdapter());

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private class MovieRankingAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position,@NonNull Object object) {
            container.removeView((View)object);
        }

        @Override
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            MovieRankingPage page = new MovieRankingPage(getContext(), position);
            container.addView(page);
            return page;
        }
    }

}
