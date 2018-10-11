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

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baidu.mobstat.StatService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lic.swifter.box.R;
import lic.swifter.box.activity.MainActivity;

public class MobileInfoFragment extends BaseFragment {

    ListView listView;

    DateFormat sdf = SimpleDateFormat.getDateInstance();
    String buildTime = sdf.format(new Date(Build.TIME));

    String[] properties = new String[]{
            "手机品牌：" + Build.BRAND,
            "手机型号：" + Build.MODEL,
            "Android版本：" + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")",
            "屏幕分辨率：" + MainActivity.screenWidth + " * " + MainActivity.screenHeight,
            "主板：" + Build.BOARD,
            "设备参数：" + Build.DEVICE,
            "显示屏参数：" + Build.DISPLAY,
            "硬件制造商：" + Build.MANUFACTURER,
            "硬件名：" + Build.HARDWARE,
            "手机产品名：" + Build.PRODUCT,
            "当前开发代号：" + Build.VERSION.CODENAME,
            "源码控制版本号：" + Build.VERSION.INCREMENTAL,
            "编译时间：" + buildTime,
            "描述标签：" + Build.TAGS,
            "系统名称：" + System.getProperty("os.name"),
            "系统架构：" + System.getProperty("os.arch"),
            "系统版本：" + System.getProperty("os.version"),

//            "Home属性："+System.getProperty("os.home"),
//            "Name属性："+System.getProperty("os.name"),
//            "Dir属性："+System.getProperty("os.dir"),
//            "时区："+System.getProperty("user.timezone"),
//            "Java Vendor Url："+System.getProperty("java.vendor.url"),
//            "Java Class Path："+System.getProperty("java.class.path"),
//            "Java Class Version："+System.getProperty("java.class.version"),
//            "Java Vendor："+ System.getProperty("java.vendor"),
//            "Java Version："+System.getProperty("java.version"),
//            "Java Home："+ System.getProperty("java.home"),
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mobile_info, container, false);
        listView = rootView.findViewById(R.id.fragment_mobile_info_list);

        StatService.onEvent(getContext(), "local_info", "pass", 1);
        if (getContext() != null)
            listView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, properties));
        return rootView;
    }
}
