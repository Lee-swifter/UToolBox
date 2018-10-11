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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import lic.swifter.box.R;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.SiteSecurity;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.presenter.WebsiteSecurityPresenter;
import lic.swifter.box.mvp.view.IView;
import lic.swifter.box.widget.CanaroTextView;

public class WebsiteSecurityFragment extends BaseFragment implements IView<String, SiteSecurity> {

    EditText inputText;
    CanaroTextView loadingStatus;
    ProgressBar loadingProgress;
    CanaroTextView message;
    CanaroTextView messageTime;
    CanaroTextView scoreResult;
    CanaroTextView bugResult;
    CanaroTextView trojanResult;
    CanaroTextView distrotResult;
    CanaroTextView fakeResult;
    CanaroTextView noteResult;
    CanaroTextView violationResult;
    CanaroTextView googleResult;
    ScrollView resultContent;

    WebsiteSecurityPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_website_security, container, false);

        inputText = rootView.findViewById(R.id.website_input_text);
        loadingStatus = rootView.findViewById(R.id.website_security_loading_status);
        loadingProgress = rootView.findViewById(R.id.website_security_loading_progress);
        message = rootView.findViewById(R.id.result_summary_message);
        messageTime = rootView.findViewById(R.id.result_summary_message_time);
        scoreResult = rootView.findViewById(R.id.website_security_score_result);
        bugResult = rootView.findViewById(R.id.website_security_bug_result);
        trojanResult = rootView.findViewById(R.id.website_security_trojan_result);
        distrotResult = rootView.findViewById(R.id.website_security_distrot_result);
        fakeResult = rootView.findViewById(R.id.website_security_fake_result);
        noteResult = rootView.findViewById(R.id.website_security_note_result);
        violationResult = rootView.findViewById(R.id.website_security_violation_result);
        googleResult = rootView.findViewById(R.id.website_security_google_result);
        resultContent = rootView.findViewById(R.id.website_security_result_content);

        presenter = new WebsiteSecurityPresenter(this);

        initViews();
        return rootView;
    }

    private void initViews() {
        inputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    String searchString = inputText.getText().toString();

                    presenter.query(searchString);

                    if(getContext() == null)
                        return false;

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null)
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    loadingProgress.requestFocus();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void beforeQuery(String requestParameter) {
        loadingProgress.setVisibility(View.VISIBLE);
        loadingStatus.setVisibility(View.INVISIBLE);
        resultContent.setVisibility(View.INVISIBLE);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<SiteSecurity> response) {
        switch (type) {
            case NET_RESPONSE_SUCCESS:

                message.setText(response.result.msg);
                if (!TextUtils.isEmpty(response.result.scantime))
                    messageTime.setText(response.result.scantime);

                if (response.result.data.score != null && !TextUtils.isEmpty(response.result.data.score.msg))
                    scoreResult.setText(response.result.data.score.msg);
                if(response.result.data.guama != null && !TextUtils.isEmpty(response.result.data.guama.msg))
                    trojanResult.setText(response.result.data.guama.msg);
                if (response.result.data.xujia != null && !TextUtils.isEmpty(response.result.data.xujia.msg))
                    fakeResult.setText(response.result.data.xujia.msg);
                if (response.result.data.cuangai != null && !TextUtils.isEmpty(response.result.data.cuangai.msg))
                    distrotResult.setText(response.result.data.cuangai.msg);
                if (response.result.data.pangzhu != null && !TextUtils.isEmpty(response.result.data.pangzhu.msg))
                    noteResult.setText(response.result.data.pangzhu.msg);
                if (response.result.data.violation != null && !TextUtils.isEmpty(response.result.data.violation.msg))
                    violationResult.setText(response.result.data.violation.msg);
                if (response.result.data.google != null && !TextUtils.isEmpty(response.result.data.google.msg))
                    googleResult.setText(response.result.data.google.msg);
                bugResult.setText(generateBugString(response.result.data.loudong));

                resultContent.setVisibility(View.VISIBLE);
                loadingProgress.setVisibility(View.INVISIBLE);
                loadingStatus.setVisibility(View.INVISIBLE);
                break;
            case NET_RESPONSE_ERROR_REASON:
                loadingStatus.setText(response.reason);

                onFailure();
                break;
            case NET_RESPONSE_ERROR:
                loadingStatus.setText(R.string.response_error);

                onFailure();
                break;
            case NET_REQUEST_FAILURE:
                loadingStatus.setText(R.string.net_failure);

                onFailure();
                break;
            default:
                break;
        }
    }

    private String generateBugString(SiteSecurity.InfoData.InfoDataBug bug) {
        return getString(R.string.website_security_bug_parse, bug.high, bug.mid, bug.low, bug.info);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cancelQuery();
    }

    private void onFailure() {
        loadingStatus.setVisibility(View.VISIBLE);
        loadingProgress.setVisibility(View.INVISIBLE);
        resultContent.setVisibility(View.INVISIBLE);
    }
}
