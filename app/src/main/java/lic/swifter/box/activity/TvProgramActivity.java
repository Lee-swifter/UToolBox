package lic.swifter.box.activity;
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
 * Created by cheng on 2016/10/25.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.TvChannel;
import lic.swifter.box.api.model.TvProgram;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.view.IView;

public class TvProgramActivity extends AppCompatActivity implements IView<String, TvProgram> {

    public static final String TV_CHANNEL_INTENT = "TvProgramActivity.TV_CHANNEL_INTENT";

    @Bind(R.id.category_toolbar)
    Toolbar toolbar;
    @Bind(R.id.common_recycler_view)
    RecyclerView recycler;
    @Bind(R.id.common_center_progress)
    ProgressBar progress;
    @Bind(R.id.common_center_status_text)
    TextView status;

    private TvChannel channel;
    private int mShortAnimationDuration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_program);
        ButterKnife.bind(this);

        if(savedInstanceState != null) {
            channel = savedInstanceState.getParcelable(TV_CHANNEL_INTENT);
        } else {
            channel = getIntent().getParcelableExtra(TV_CHANNEL_INTENT);
        }

        if(channel == null) {
                new AlertDialog.Builder(this).setMessage(R.string.data_analyze_error)
                        .setCancelable(false).setPositiveButton(R.string.ensure,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TvProgramActivity.this.finish();
                            }
                        }).show();
                return;
        }

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: refresh data.
            }
        });

        toolbar.setTitle(channel.channelName);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(TV_CHANNEL_INTENT, channel);
    }

    @Override
    public void beforeQuery(String requestParameter) {

    }

    @Override
    public void afterQuery(NetQueryType type, Result<TvProgram> response) {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
