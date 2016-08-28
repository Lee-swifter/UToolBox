package lic.swifter.box.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lic.swifter.box.api.model.MovieRank;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.view.IView;

/**
 * Created by cheng on 2016/8/28.
 */

public class BoxOfficeFragment extends BaseFragment implements IView<String, List<MovieRank>> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void beforeQuery(String requestParameter) {

    }

    @Override
    public void afterQuery(NetQueryType type, Result<List<MovieRank>> response) {

    }
}
