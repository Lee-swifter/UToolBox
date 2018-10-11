package lic.swifter.box.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.mobstat.StatService;

import java.util.List;

import lic.swifter.box.R;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.api.model.TvCategory;
import lic.swifter.box.api.model.TvChannel;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.presenter.TvChannelPresenter;
import lic.swifter.box.mvp.view.IView;
import lic.swifter.box.recycler.adapter.TvChannelAdapter;
import lic.swifter.box.recycler.divider.GridDivider;
import lic.swifter.box.util.ViewUtil;

public class TvChannelActivity extends AppCompatActivity implements IView<Integer, List<TvChannel>> {

    public static final String TV_CATEGORY_INTENT = "TvChannelActivity.TV_CATEGORY_INTENT";

    Toolbar toolbar;
    RecyclerView recycler;
    ProgressBar progress;
    TextView status;

    private TvCategory category;
    private TvChannelPresenter presenter;
    private int mShortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_category);
        toolbar = findViewById(R.id.category_toolbar);
        recycler = findViewById(R.id.common_recycler_view);
        progress = findViewById(R.id.common_center_progress);
        status = findViewById(R.id.common_center_status_text);

        if (savedInstanceState == null) {
            category = getIntent().getParcelableExtra(TV_CATEGORY_INTENT);
        } else {
            category = savedInstanceState.getParcelable(TV_CATEGORY_INTENT);
        }

        if (category == null) {
            new AlertDialog.Builder(this).setMessage(R.string.data_analyze_error)
                    .setCancelable(false).setPositiveButton(R.string.ensure,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TvChannelActivity.this.finish();
                        }
                    }).show();
            return;
        }

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        presenter = new TvChannelPresenter(this);
        presenter.query(category.id);

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.query(category.id);
            }
        });

        toolbar.setTitle(category.name);
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
        outState.putParcelable(TV_CATEGORY_INTENT, category);
    }

    @Override
    protected void onResume() {
        StatService.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        StatService.onPause(this);
        super.onPause();
    }

    @Override
    public void beforeQuery(Integer requestParameter) {
        ViewUtil.fadeInView(progress, mShortAnimationDuration);
        ViewUtil.fadeOutView(recycler, mShortAnimationDuration);
        ViewUtil.fadeOutView(status, mShortAnimationDuration);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<List<TvChannel>> response) {
        switch (type) {
            case NET_REQUEST_FAILURE:
                showStatus();
                status.setText(R.string.net_failure);
                break;
            case NET_RESPONSE_SUCCESS:
                ViewUtil.fadeOutView(progress, mShortAnimationDuration);
                ViewUtil.fadeInView(recycler, mShortAnimationDuration);
                ViewUtil.fadeOutView(status, mShortAnimationDuration);

                recycler.setLayoutManager(new LinearLayoutManager(this));
                recycler.addItemDecoration(new GridDivider(this));
                recycler.setAdapter(new TvChannelAdapter(response.result));
                break;
            case NET_RESPONSE_ERROR:
                showStatus();
                status.setText(R.string.response_error);
                break;
            case NET_RESPONSE_ERROR_REASON:
                showStatus();
                status.setText(response.reason);
                break;
        }
    }

    private void showStatus() {
        ViewUtil.fadeOutView(progress, mShortAnimationDuration);
        ViewUtil.fadeOutView(recycler, mShortAnimationDuration);
        ViewUtil.fadeInView(status, mShortAnimationDuration);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
