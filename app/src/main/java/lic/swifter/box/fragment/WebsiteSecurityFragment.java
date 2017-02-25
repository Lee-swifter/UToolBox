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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.SiteSecurity;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.presenter.WebsiteSecurityPresenter;
import lic.swifter.box.mvp.view.IView;
import lic.swifter.box.widget.CanaroTextView;

public class WebsiteSecurityFragment extends BaseFragment implements IView<String, SiteSecurity> {

    @Bind(R.id.website_input_text)
    EditText inputText;
    @Bind(R.id.website_security_loading_status)
    CanaroTextView loadingStatus;
    @Bind(R.id.website_security_loading_progress)
    ProgressBar loadingProgress;
    @Bind(R.id.result_summary_message)
    CanaroTextView message;
    @Bind(R.id.result_summary_message_time)
    CanaroTextView messageTime;
    @Bind(R.id.website_security_score_result)
    CanaroTextView scoreResult;
    @Bind(R.id.website_security_bug_result)
    CanaroTextView bugResult;
    @Bind(R.id.website_security_trojan_result)
    CanaroTextView trojanResult;
    @Bind(R.id.website_security_distrot_result)
    CanaroTextView distrotResult;
    @Bind(R.id.website_security_fake_result)
    CanaroTextView fakeResult;
    @Bind(R.id.website_security_note_result)
    CanaroTextView noteResult;
    @Bind(R.id.website_security_violation_result)
    CanaroTextView violationResult;
    @Bind(R.id.website_security_google_result)
    CanaroTextView googleResult;
    @Bind(R.id.website_security_result_content)
    ScrollView resultContent;

    WebsiteSecurityPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_website_security, container, false);
        ButterKnife.bind(this, rootView);
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

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null)
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void beforeQuery(String requestParameter) {
        fadeInView(loadingProgress);
        fadeOutView(loadingStatus);
        fadeOutView(resultContent);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<SiteSecurity> response) {
        switch (type) {
            case NET_RESPONSE_SUCCESS:

                message.setText(response.result.msg);
                messageTime.setText(response.result.scantime);

                scoreResult.setText(response.result.data.score.msg);
                trojanResult.setText(response.result.data.guama.msg);
                fakeResult.setText(response.result.data.xujia.msg);
                distrotResult.setText(response.result.data.cuangai.msg);
                noteResult.setText(response.result.data.pangzhu.msg);
                violationResult.setText(response.result.data.violation.msg);
                googleResult.setText(response.result.data.google.msg);
                bugResult.setText(generateBugString(response.result.data.loudong));

                fadeInView(resultContent);
                fadeOutView(loadingProgress);
                fadeOutView(loadingStatus);
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
        ButterKnife.unbind(this);
    }

    private void onFailure() {
        loadingStatus.setVisibility(View.VISIBLE);
        loadingProgress.setVisibility(View.GONE);
        resultContent.setVisibility(View.GONE);
    }
}
