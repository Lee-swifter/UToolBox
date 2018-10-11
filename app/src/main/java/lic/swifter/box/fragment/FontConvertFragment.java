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

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;

import lic.swifter.box.R;
import lic.swifter.box.api.model.FontConversion;
import lic.swifter.box.mvp.presenter.FontConvertPresenter;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.view.FontConvertView;

public class FontConvertFragment extends BaseFragment implements FontConvertView{

    EditText editText;
    Button convertButton;
    ProgressBar progressBar;

    LinearLayout traditionalWrapper;
    Button traditionalCopy;
    TextView traditionalResult;

    LinearLayout leetspeakWrapper;
    Button leetspeakCopy;
    TextView leetspeakResult;

    private FontConvertPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_font_convert, container, false);

        editText = rootView.findViewById(R.id.input_edit_text);
        convertButton = rootView.findViewById(R.id.button_convert);
        progressBar = rootView.findViewById(R.id.convert_progress);
        traditionalWrapper = rootView.findViewById(R.id.traditional_result_wrapper);
        traditionalCopy = rootView.findViewById(R.id.traditional_copy);
        traditionalResult = rootView.findViewById(R.id.traditional_chinese_result);
        leetspeakWrapper = rootView.findViewById(R.id.leetspeak_result_wrapper);
        leetspeakCopy = rootView.findViewById(R.id.leetspeak_copy);
        leetspeakResult = rootView.findViewById(R.id.leetspeak_result);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatService.onEvent(getContext(), "font_convert", "pass", 1);

                presenter.cancelQuery();

                String queryText = editText.getText().toString();

                presenter.query(FontConvertPresenter.TRADITIONAL_CHINESE, queryText);
                presenter.query(FontConvertPresenter.LEETSPEAK, queryText);

                convertButton.setVisibility(View.GONE);
                fadeInView(progressBar);
                fadeOutView(traditionalWrapper);
                fadeOutView(leetspeakWrapper);
            }
        });

        traditionalCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clipData = ClipData.newPlainText("text", traditionalResult.getText().toString());
                if(getContext() == null)
                    return;
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                if(cm == null)
                    return ;
                cm.setPrimaryClip(clipData);

                Toast.makeText(getContext(), R.string.text_copy_to_clipboard, Toast.LENGTH_SHORT).show();
            }
        });

        leetspeakCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clipData = ClipData.newPlainText("text", leetspeakResult.getText().toString());
                if(getContext() == null)
                    return;
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                if(cm == null)
                    return ;
                cm.setPrimaryClip(clipData);

                Toast.makeText(getContext(), R.string.text_copy_to_clipboard, Toast.LENGTH_SHORT).show();
            }
        });

        presenter = new FontConvertPresenter(this);
        return rootView;
    }

    @Override
    public void onStartQuery(int type, String text) {
        //do nothing.
    }

    @Override
    public void onQueryResult(int type, NetQueryType netType, FontConversion response) {

        if(!convertButton.isShown()) {
            convertButton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        switch (type) {
            case FontConvertPresenter.TRADITIONAL_CHINESE:
                fadeInView(traditionalWrapper);
                traditionalResult.setText(response.outstr);
                break;
            case FontConvertPresenter.LEETSPEAK:
                fadeInView(leetspeakWrapper);
                leetspeakResult.setText(response.outstr);
                break;
            default:
                break;
        }
    }
}
