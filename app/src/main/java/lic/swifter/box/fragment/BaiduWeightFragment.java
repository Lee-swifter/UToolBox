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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.widget.CanaroTextView;

public class BaiduWeightFragment extends BaseFragment {

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
    RecyclerView recordList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_baidu_weight, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
