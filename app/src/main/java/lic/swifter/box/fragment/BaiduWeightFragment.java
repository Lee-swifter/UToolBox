package lic.swifter.box.fragment;
/*
 * Copyright (C) 2015, Lee-swifter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.model.BaiduWeight;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.mvp.presenter.BaiduWeightPresenter;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.view.BaiduWeightView;
import lic.swifter.box.recycler.adapter.BaiduWeightAdapter;
import lic.swifter.box.recycler.divider.GridDivider;
import lic.swifter.box.widget.CanaroTextView;

public class BaiduWeightFragment extends BaseFragment implements BaiduWeightView{

    @Bind(R.id.web_input_text)
    EditText inputText;
    @Bind(R.id.weight_progress)
    ProgressBar progressBar;
    @Bind(R.id.weight_query_site)
    CanaroTextView resultSite;
    @Bind(R.id.weight_result_weight)
    CanaroTextView resultWeight;
    @Bind(R.id.weight_result_range)
    CanaroTextView resultRange;
    @Bind(R.id.weight_result_wrapper)
    LinearLayout resultWrapper;
    @Bind(R.id.weight_record_list)
    RecyclerView recyclerView;

    private BaiduWeightPresenter presenter;
    private BaiduWeightAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_baidu_weight, container, false);
        ButterKnife.bind(this, rootView);

        presenter = new BaiduWeightPresenter(this);
        initView();
        return rootView;
    }

    private void initView() {
        presenter.readFromDb();

        inputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    String searchString = inputText.getText().toString();

                    presenter.query(searchString);

                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null)
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        presenter.cancelQuery();
        super.onDestroyView();
    }

    @Override
    public void beforeQuery(String requestParameter) {
        fadeInView(progressBar);
        fadeOutView(resultWrapper);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<BaiduWeight> response) {
        switch (type) {
            case NET_RESPONSE_SUCCESS:
                resultSite.setText(response.result.webSite);
                resultWeight.setText(getString(R.string.baidu_weight_result, response.result.Weight));
                resultRange.setText(getString(R.string.baidu_weight_range_result,
                        response.result.From, response.result.To));

                resultWeight.setVisibility(View.VISIBLE);
                resultRange.setVisibility(View.VISIBLE);
                break;
            case NET_RESPONSE_ERROR_REASON:
                resultSite.setText(response.reason);

                resultWeight.setVisibility(View.GONE);
                resultRange.setVisibility(View.GONE);
                break;
            case NET_RESPONSE_ERROR:
                resultSite.setText(R.string.response_error);

                resultWeight.setVisibility(View.GONE);
                resultRange.setVisibility(View.GONE);
                break;
            case NET_REQUEST_FAILURE:
                resultSite.setText(R.string.net_failure);

                resultWeight.setVisibility(View.GONE);
                resultRange.setVisibility(View.GONE);

                gotResponse();
                break;
        }
    }

    @Override
    public void insertLastResult(BaiduWeight result) {
        adapter.addFirst(result);
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void gotResponse() {
        fadeOutView(progressBar);
        fadeInView(resultWrapper);
    }

    @Override
    public void afterReadResults(List<BaiduWeight> resultList) {
        adapter = new BaiduWeightAdapter(getContext(), resultList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new GridDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(0);
    }
}
