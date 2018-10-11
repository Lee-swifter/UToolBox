package lic.swifter.box.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lic.swifter.box.R;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.widget.NewsPage;

public class TopNewsFragment extends BaseFragment {

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_single_view_pager, container, false);
        viewPager = rootView.findViewById(R.id.single_view_pager);
        setupViewPager();
        return rootView;
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
        public boolean isViewFromObject(@NonNull View view,@NonNull Object object) {
            return view == object;
        }

        @Override
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            NewsPage page = new NewsPage(getContext());
            switch (position) {
                case 0:
                    page.setType(JuheApi.NEWS_TOP);
                    break;
                case 1:
                    page.setType(JuheApi.NEWS_SHEHUI);
                    break;
                case 2:
                    page.setType(JuheApi.NEWS_GUONEI);
                    break;
                case 3:
                    page.setType(JuheApi.NEWS_GUOJI);
                    break;
                case 4:
                    page.setType(JuheApi.NEWS_YULE);
                    break;
                case 5:
                    page.setType(JuheApi.NEWS_TIYU);
                    break;
                case 6:
                    page.setType(JuheApi.NEWS_JUNSHI);
                    break;
                case 7:
                    page.setType(JuheApi.NEWS_KEJI);
                    break;
                case 8:
                    page.setType(JuheApi.NEWS_CAIJING);
                    break;
                case 9:
                    page.setType(JuheApi.NEWS_SHISHANG);
                    break;
                default:
                    break;
            }
            container.addView(page);
            return page;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position,@NonNull Object object) {
            container.removeView((View)object);
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
