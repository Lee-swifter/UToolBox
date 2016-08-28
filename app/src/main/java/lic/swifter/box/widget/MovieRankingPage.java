package lic.swifter.box.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.MovieRank;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.mvp.presenter.MovieRankingPresenter;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.view.IView;
import lic.swifter.box.recycler.adapter.MovieRankingAdapter;
import lic.swifter.box.recycler.divider.GridDivider;
import lic.swifter.box.util.ViewUtil;

/**
 * Created by cheng on 2016/8/28.
 */

public class MovieRankingPage extends RelativeLayout implements IView<String, List<MovieRank>> {

    @Bind(R.id.page_movie_progress)
    ProgressBar progress;
    @Bind(R.id.page_movie_status)
    TextView status;
    @Bind(R.id.movie_ranking_wrapper)
    LinearLayout rankingWrapper;
    @Bind(R.id.page_movie_title)
    CanaroTextView movieTitle;
    @Bind(R.id.movie_ranking_recycler_view)
    RecyclerView recyclerView;

    private int index;
    private int duration;
    private MovieRankingPresenter presenter;

    public MovieRankingPage(Context context) {
        this(context, null);
    }

    public MovieRankingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovieRankingPage(Context context, int index) {
        this(context);
        this.index = index;
    }

    public MovieRankingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        duration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        LayoutInflater.from(context).inflate(R.layout.page_movie_ranking, this);
        presenter = new MovieRankingPresenter(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        switch (index) {
            case 0:
                movieTitle.setText(R.string.movie_ranking_cn_title);
                presenter.query(JuheApi.MOVIE_CN);
                break;
            case 1:
                movieTitle.setText(R.string.movie_ranking_us_title);
                presenter.query(JuheApi.MOVIE_US);
                break;
            case 2:
                movieTitle.setText(R.string.movie_ranking_hk_title);
                presenter.query(JuheApi.MOVIE_HK);
                break;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ButterKnife.unbind(this);
        presenter.cancelQuery();
    }

    @Override
    public void beforeQuery(String requestParameter) {
        ViewUtil.fadeInView(progress, duration);
        ViewUtil.fadeOutView(status, duration);
        ViewUtil.fadeOutView(rankingWrapper, duration);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<List<MovieRank>> response) {
        switch (type) {
            case NET_RESPONSE_SUCCESS:
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.addItemDecoration(new GridDivider(getContext(), LinearLayoutManager.HORIZONTAL));
                recyclerView.setAdapter(new MovieRankingAdapter(getContext(), response.result));

                ViewUtil.fadeOutView(progress, duration);
                ViewUtil.fadeOutView(status, duration);
                ViewUtil.fadeInView(rankingWrapper, duration);
                break;
        }
    }
}
