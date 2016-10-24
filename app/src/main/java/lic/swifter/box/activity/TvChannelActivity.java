package lic.swifter.box.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.mobstat.StatService;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.model.TvCategory;

public class TvChannelActivity extends AppCompatActivity {

    public static final String TV_CATEGORY_INTENT = "TvChannelActivity.TV_CATEGORY_INTENT";

    @Bind(R.id.category_toolbar)
    Toolbar toolbar;
    @Bind(R.id.common_recycler_view)
    RecyclerView recycler;
    @Bind(R.id.common_center_progress)
    ProgressBar progress;
    @Bind(R.id.common_center_status_text)
    TextView status;

    private TvCategory category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_category);
        ButterKnife.bind(this);

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
                finish();
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
}
