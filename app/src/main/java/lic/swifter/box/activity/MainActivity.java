package lic.swifter.box.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yalantis.guillotine.animation.GuillotineAnimation;

import lic.swifter.box.R;
import lic.swifter.box.fragment.BaseFragment;
import lic.swifter.box.fragment.IPQueryFragment;
import lic.swifter.box.recycler.adapter.ToolsAdapter;
import lic.swifter.box.recycler.divider.GridDivider;
import lic.swifter.box.util.FindUtil;
import lic.swifter.box.util.ToastUtil;


public class MainActivity extends AppCompatActivity implements ToolsAdapter.OnItemClickListener {

    private static final int RIPPLE_DURATION = 250;
    private static final int TIME_EXIT = 2000;

    private TextView titleText;
    private RecyclerView recycler;
    private GuillotineAnimation guillo;

    private int currentIndex = -1;

    private SparseArray<BaseFragment> fragmentMap;

    private long exitTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        FrameLayout rootFrame = FindUtil.$(this, R.id.root_frame_layout);
        Toolbar toolBar = FindUtil.$(rootFrame, R.id.toolbar);
        ImageView openImage = FindUtil.$(rootFrame, R.id.guillo_image_open);
        titleText = FindUtil.$(rootFrame, R.id.title_text);

        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(null);

        View guillioView = LayoutInflater.from(this).inflate(R.layout.guillio, rootFrame, false);
        rootFrame.addView(guillioView);

        recycler = FindUtil.$(guillioView, R.id.guillo_recycler);
        ImageView closeImage = FindUtil.$(guillioView, R.id.guillo_image_close);

        guillo = new GuillotineAnimation.GuillotineBuilder(guillioView, closeImage, openImage)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolBar)
                .setClosedOnStart(false)
                .build();

        fragmentMap = new SparseArray<>();
        initRecyclerView();

    }

    private void initRecyclerView() {
        ToolsAdapter toolsAdapter = new ToolsAdapter();
        toolsAdapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        recycler.addItemDecoration(new GridDivider(this, GridDivider.BOTH));
        recycler.setAdapter(toolsAdapter);
    }

    @Override
    public void onBackPressed() {
        if (exitTimeStamp + TIME_EXIT > System.currentTimeMillis())
            super.onBackPressed();
        else {
            exitTimeStamp = System.currentTimeMillis();
            ToastUtil.showShort(this, R.string.ready_exit);
        }
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
                titleText.setText(R.string.ip);
                break;
            default:
                break;
        }
        transaction.commit();
    }
}
