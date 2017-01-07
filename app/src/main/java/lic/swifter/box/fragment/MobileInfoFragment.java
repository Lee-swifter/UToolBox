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
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;

public class MobileInfoFragment extends BaseFragment {

    @Bind(R.id.fragment_mobile_info_list)
    ListView listView;

    DateFormat sdf= SimpleDateFormat.getDateInstance();
    String buildTime = sdf.format(new Date(Build.TIME));

    String[] properties = new String[]{
            "主板："+ Build.BOARD,
            "系统定制商："+Build.BRAND,
            "设备参数："+Build.DEVICE,
            "显示屏参数："+Build.DISPLAY,
            "硬件制造商："+Build.MANUFACTURER,
            "硬件名："+Build.HARDWARE,
            "手机产品名："+Build.PRODUCT,
            "当前版本号："+Build.VERSION.SDK_INT,
            "版本名称："+Build.VERSION.RELEASE,
            "当前开发代号："+Build.VERSION.CODENAME,
            "源码控制版本号："+Build.VERSION.INCREMENTAL,
            "编译时间："+buildTime,
            "描述标签："+Build.TAGS,
            "系统版本："+System.getProperty("os.version"),
            "系统名称："+System.getProperty("os.name"),
            "系统架构："+System.getProperty("os.arch"),
            "Home属性："+System.getProperty("os.home"),
            "Name属性："+System.getProperty("os.name"),
            "Dir属性："+System.getProperty("os.dir"),
            "时区："+System.getProperty("user.timezone"),
            "Java Vendor Url："+System.getProperty("java.vendor.url"),
            "Java Class Path："+System.getProperty("java.class.path"),
            "Java Class Version："+System.getProperty("java.class.version"),
            "Java Vendor："+ System.getProperty("java.vendor"),
            "Java Version："+System.getProperty("java.version"),
            "Java Home："+ System.getProperty("java.home"),
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mobile_info, container, false);
        ButterKnife.bind(this, rootView);

        listView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, properties));
        return rootView;
    }
}
