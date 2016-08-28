package lic.swifter.box.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lic.swifter.box.R;
import lic.swifter.box.api.model.MovieRank;
import lic.swifter.box.recycler.holder.MovieRankingHolder;

/**
 * Created by cheng on 2016/8/28.
 */

public class MovieRankingAdapter extends RecyclerView.Adapter<MovieRankingHolder> {

    private List<MovieRank> list;
    private Context context;

    public MovieRankingAdapter(Context context, List<MovieRank> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MovieRankingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_ranking, parent, false);
        return new MovieRankingHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MovieRankingHolder holder, int position) {
        holder.setData(context, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
