package lic.swifter.box.activity;

import android.os.Bundle;
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
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.HashMap;

import lic.swifter.box.R;
import lic.swifter.box.data.FragmentsFlag;
import lic.swifter.box.fragment.BaiduWeightFragment;
import lic.swifter.box.fragment.BaseFragment;
import lic.swifter.box.fragment.BoxOfficeFragment;
import lic.swifter.box.fragment.FontConvertFragment;
import lic.swifter.box.fragment.IDQueryFragment;
import lic.swifter.box.fragment.IPQueryFragment;
import lic.swifter.box.fragment.JokesFragment;
import lic.swifter.box.fragment.MobileInfoFragment;
import lic.swifter.box.fragment.PhoneQueryFragment;
import lic.swifter.box.fragment.QQLuckFragment;
import lic.swifter.box.fragment.TodayHistoryFragment;
import lic.swifter.box.fragment.TopNewsFragment;
import lic.swifter.box.fragment.TvTableFragment;
import lic.swifter.box.fragment.WxChosenFragment;
import lic.swifter.box.recycler.adapter.ToolsAdapter;
import lic.swifter.box.recycler.divider.GridDivider;
import lic.swifter.box.util.FindUtil;
import lic.swifter.box.util.ToastUtil;

public class MainActivity extends AppCompatActivity implements ToolsAdapter.OnItemClickListener, TopNewsFragment.OnNewsTypeChangedListener {

    private static final int RIPPLE_DURATION = 250;
    private static final int TIME_EXIT = 2000;

    private TextView titleText;
    private RecyclerView recycler;
    private GuillotineAnimation guillo;

    private FragmentsFlag currentFlag;

    private HashMap<FragmentsFlag, BaseFragment> fragmentMap;

    private long exitTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
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

        fragmentMap = new HashMap<>();
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
        if(guillo.closed) {
            guillo.open();
            return ;
        }

        if (exitTimeStamp + TIME_EXIT > System.currentTimeMillis())
            super.onBackPressed();
        else {
            exitTimeStamp = System.currentTimeMillis();
            ToastUtil.showShort(this, R.string.ready_exit);
        }
    }

    @Override
    public void onItemClickListener(FragmentsFlag flag) {
        guillo.close();
        if(currentFlag == flag)
            return ;

        currentFlag = flag;
        changeFragment(currentFlag);
    }

    @Override
    public void onNewsTypeChanged(CharSequence newsType) {
        titleText.setText(newsType);
    }

    private void changeFragment(FragmentsFlag flag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (flag) {
            case IpQueryFragment: //IP 查询
                if(fragmentMap.get(flag) == null) {
                    fragmentMap.put(flag, new IPQueryFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(flag));
                titleText.setText(R.string.ip);
                break;
            case IdQueryFragment: //身份证查询
                if(fragmentMap.get(flag) == null) {
                    fragmentMap.put(flag, new IDQueryFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(flag));
                titleText.setText(R.string.id_inquiry);
                break;
            case PhoneQueryFragment:    //手机号码归属地查询
                if(fragmentMap.get(flag) == null) {
                    fragmentMap.put(flag, new PhoneQueryFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(flag));
                titleText.setText(R.string.mobile_number_attr);
                break;
            case TodayHistoryFragment:  //历史上的今天
                if(fragmentMap.get(flag) == null) {
                    fragmentMap.put(flag, new TodayHistoryFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(flag));
                titleText.setText(R.string.today_in_history);
                break;
            case WxChosenFragment:  //微信精选
                if(fragmentMap.get(flag) == null) {
                    fragmentMap.put(flag, new WxChosenFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(flag));
                titleText.setText(R.string.wx_carefully_chosen);
                break;
            case QQLuckFragment: //QQ号吉凶测试
                if(fragmentMap.get(flag) == null) {
                    fragmentMap.put(flag, new QQLuckFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(flag));
                titleText.setText(R.string.qq_number_query);
                break;
            case JokesFragment:  //笑话大全
                if(fragmentMap.get(flag) == null) {
                    fragmentMap.put(flag, new JokesFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(flag));
                titleText.setText(R.string.jokes_collection);
                break;
            case MovieRankingFragment:  //电影票房
                if(fragmentMap.get(flag) == null) {
                    fragmentMap.put(flag, new BoxOfficeFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(flag));
                titleText.setText(R.string.films_box_office);
                break;
            case TopNewsFragment:  //新闻头条
                if(fragmentMap.get(flag) == null) {
                    fragmentMap.put(flag, new TopNewsFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(flag));
                titleText.setText(R.string.top_news);
                break;
            case TvTableFragment:   //电视节目表
                if(fragmentMap.get(flag) == null) {
                    fragmentMap.put(flag, new TvTableFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(flag));
                titleText.setText(R.string.tv_list_table);
                break;
            case MobileInfoFragment:    //本机信息
                if(fragmentMap.get(flag) == null) {
                    fragmentMap.put(flag, new MobileInfoFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(flag));
                titleText.setText(R.string.mobile_info);
                break;
            case FontConvertFragment:   //简繁火星字体转换
                if(fragmentMap.get(flag) == null) {
                    fragmentMap.put(flag, new FontConvertFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(flag));
                titleText.setText(R.string.font_convert_title);
                break;
            case BaiduWeightFragment:   //百度权重
                if(fragmentMap.get(flag) == null) {
                    fragmentMap.put(flag, new BaiduWeightFragment());
                }
                transaction.replace(R.id.fragment_place_holder, fragmentMap.get(flag));
                titleText.setText(R.string.baidu_weight_query);
                break;
            default:
                break;
        }
        transaction.commit();
    }
}
