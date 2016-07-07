package lic.swifter.box.activity;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.lang.ref.WeakReference;

import lic.swifter.box.R;
import lic.swifter.box.adapter.ToolsAdapter;
import lic.swifter.box.databinding.ActivityMainBinding;
import lic.swifter.box.util.DividerItemDecoration;
import lic.swifter.box.util.ToastUtil;

public class MainActivity extends AppCompatActivity {

    private final int RIPPLE_DURATION = 250;
    private final int TIME_EXIT = 2000;

    private static final int WHAT_MESSAGE_EXIT = 1001;

    private GuillotineAnimation guillo;
    private View guillioView;
    private RecyclerView recycler;
    private ToolsAdapter toolsAdapter;
    private boolean readyExit;

    private MainHandler handler;

    private static class MainHandler extends Handler {

        private WeakReference<MainActivity> activityWrapper;

        MainHandler(MainActivity activity) {
            activityWrapper = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = activityWrapper.get();
            if(activity != null) {
                switch (msg.what){
                    case WHAT_MESSAGE_EXIT:
                        activity.readyExit = false;
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding main = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(main.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setTitle(null);

        guillioView = LayoutInflater.from(this).inflate(R.layout.guillio, main.rootFrameLayout, false);
        recycler = (RecyclerView) guillioView.findViewById(R.id.guillo_recycler);
        main.rootFrameLayout.addView(guillioView);

        guillo = new GuillotineAnimation.GuillotineBuilder(guillioView, guillioView.findViewById(R.id.guillo_image_close), main.guilloImageOpen)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(main.toolbar)
                .setClosedOnStart(false)
                .build();

        handler = new MainHandler(this);

        toolsAdapter = new ToolsAdapter(this);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        recycler.addItemDecoration(new DividerItemDecoration(this ));
        recycler.setAdapter(toolsAdapter);
    }

    @Override
    public void onBackPressed() {
        if(!readyExit) {
            ToastUtil.showShort(this, R.string.ready_exit);

            readyExit = true;
            handler.sendEmptyMessageDelayed(WHAT_MESSAGE_EXIT, TIME_EXIT);
        }
        super.onBackPressed();
    }
}
