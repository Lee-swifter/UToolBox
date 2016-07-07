package lic.swifter.box.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.lang.ref.WeakReference;

import lic.swifter.box.R;
import lic.swifter.box.data.ToolData;
import lic.swifter.box.fragment.IPQueryFragment;
import lic.swifter.box.recycler.Divider;
import lic.swifter.box.recycler.DragCallback;
import lic.swifter.box.recycler.adapter.ToolsAdapter;
import lic.swifter.box.util.FindUtil;
import lic.swifter.box.util.ToastUtil;

public class MainActivity extends AppCompatActivity implements ToolsAdapter.OnItemClickListener {

    private final int RIPPLE_DURATION = 250;
    private final int TIME_EXIT = 2000;

    private static final int WHAT_MESSAGE_EXIT = 1001;

    private FrameLayout rootFrame;
    private Toolbar toolBar;
    private ImageView openImage;
    private FrameLayout placeHolder;

    private View guillioView;
    private ImageView closeImage;
    private RecyclerView recycler;

    private GuillotineAnimation guillo;
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
            if (activity != null) {
                switch (msg.what) {
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
        setContentView(R.layout.activity_main);

        initView();
        handler = new MainHandler(this);
    }

    private void initView() {
        rootFrame = FindUtil.$(this, R.id.root_frame_layout);
        toolBar = FindUtil.$(rootFrame, R.id.toolbar);
        openImage = FindUtil.$(rootFrame, R.id.guillo_image_open);
        placeHolder = FindUtil.$(rootFrame, R.id.fragment_place_holder);

        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(null);

        guillioView = LayoutInflater.from(this).inflate(R.layout.guillio, rootFrame, false);
        rootFrame.addView(guillioView);

        recycler = FindUtil.$(guillioView, R.id.guillo_recycler);
        closeImage = FindUtil.$(guillioView, R.id.guillo_image_close);

        guillo = new GuillotineAnimation.GuillotineBuilder(guillioView, closeImage, openImage)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolBar)
                .setClosedOnStart(false)
                .build();

        initRecyclerView();
    }

    private void initRecyclerView() {
        toolsAdapter = new ToolsAdapter();
        toolsAdapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        recycler.addItemDecoration(new Divider(this));
        recycler.setAdapter(toolsAdapter);

//        TODO: unsuitable for this app;
//        DragCallback callback = new DragCallback();
//        callback.setItemTouchHelperAdapter(toolsAdapter);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//        touchHelper.attachToRecyclerView(recycler);
    }

    @Override
    public void onBackPressed() {
        if (!readyExit) {
            ToastUtil.showShort(this, R.string.ready_exit);

            readyExit = true;
            handler.sendEmptyMessageDelayed(WHAT_MESSAGE_EXIT, TIME_EXIT);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onItemClickListener(int position) {
        changeFragment(null);
    }

    private void changeFragment(ToolData toolData) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_place_holder, IPQueryFragment.newInstance("1", "2")).commit();
    }
}
