package lic.swifter.box.recycler.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import lic.swifter.box.R;
import lic.swifter.box.api.model.MovieRank;
import lic.swifter.box.util.ViewUtil;
import lic.swifter.box.widget.CanaroTextView;

/**
 * Created by cheng on 2016/8/28.
 */

public class MovieRankingHolder extends RecyclerView.ViewHolder {

    CanaroTextView rid;
    CanaroTextView name;
    CanaroTextView wk;
    CanaroTextView rankingW;
    CanaroTextView rankingT;

    public MovieRankingHolder(View itemView) {
        super(itemView);
        ViewUtil.waveView(itemView);

        rid = itemView.findViewById(R.id.item_movie_ranking_rid);
        name = itemView.findViewById(R.id.item_movie_ranking_name);
        wk = itemView.findViewById(R.id.item_movie_ranking_wk);
        rankingW = itemView.findViewById(R.id.item_movie_ranking_w);
        rankingT = itemView.findViewById(R.id.item_movie_ranking_t);

    }

    public void setData(Context context, MovieRank data) {
        rid.setText(String.valueOf(data.rid));
        name.setText(data.name);
        wk.setText(data.wk);
        rankingW.setText(context.getString(R.string.movie_ranking_w, data.wboxoffice));
        rankingT.setText(context.getString(R.string.movie_ranking_t, data.tboxoffice));
    }

}
