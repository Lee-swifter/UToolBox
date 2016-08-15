package lic.swifter.box.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import lic.swifter.box.R;
import lic.swifter.box.fragment.BaseFragment;
import lic.swifter.box.fragment.IPQueryFragment;
import lic.swifter.box.recycler.Divider;
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
    private int currentIndex = -1;

    private MainHandler handler;
    private Map<Integer, BaseFragment> fragmentMap;

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

        fragmentMap = new HashMap<>();
        initRecyclerView();

    }

    private void initRecyclerView() {
        toolsAdapter = new ToolsAdapter();
        toolsAdapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        recycler.addItemDecoration(new Divider(this));
        recycler.setAdapter(toolsAdapter);
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
        guillo.close();
        if(currentIndex == position)
            return ;

        currentIndex = position;
        changeFragment(currentIndex);
    }

    private void changeFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (index) {
            case 0: //IP 查询
                if(fragmentMap.get(index) == null) {
                    fragmentMap.put(index, new IPQueryFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(index));
                break;
            default:
                break;
        }
        transaction.commit();
    }
}
