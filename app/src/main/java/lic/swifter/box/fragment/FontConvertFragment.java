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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;

public class FontConvertFragment extends BaseFragment {

    @Bind(R.id.input_edit_text)
    EditText editText;
    @Bind(R.id.button_convert)
    Button convertButton;
    @Bind(R.id.convert_progress)
    ProgressBar progressBar;

    @Bind(R.id.traditional_result_wrapper)
    LinearLayout traditionalWrapper;
    @Bind(R.id.traditional_copy)
    Button traditionalCopy;
    @Bind(R.id.traditional_chinese_result)
    TextView traditionalResult;

    @Bind(R.id.leetspeak_result_wrapper)
    LinearLayout leetspeakWrapper;
    @Bind(R.id.leetspeak_copy)
    Button leetspeakCopy;
    @Bind(R.id.traditional_chinese_result)
    TextView leetspeakResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_font_convert, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }
}
