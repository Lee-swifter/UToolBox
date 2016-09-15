package lic.swifter.box.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;

public class TopNewsFragment extends BaseFragment {

    @Bind(R.id.single_view_pager)
    ViewPager viewPager;
    private NewsPagerAdapter adapter;
    private OnNewsTypeChangedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewsTypeChangedListener) {
            listener = (OnNewsTypeChangedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNewsTypeChangedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.commen_single_view_pager, container, false);
        ButterKnife.bind(this, rootView);
        setupViewPager();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    private void setupViewPager() {
        adapter = new NewsPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                if(listener != null)
                    listener.onNewsTypeChanged(adapter.getPageTitle(position));
            }
        });
    }

    public interface OnNewsTypeChangedListener {
        void onNewsTypeChanged(CharSequence newsType);
    }

    private class NewsPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            switch (position) {
                case 0:
                    
            }
            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "头条新闻";
                case 1:
                    return "社会新闻";
                case 2:
                    return "国内新闻";
                case 3:
                    return "国际新闻";
                case 4:
                    return "娱乐新闻";
                case 5:
                    return "体育新闻";
                case 6:
                    return "军事新闻";
                case 7:
                    return "科技新闻";
                case 8:
                    return "财经新闻";
                case 9:
                    return "时尚新闻";
                default:
                    return null;
            }
        }
    }

}
