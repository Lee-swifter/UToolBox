package lic.swifter.box.mvp.view;
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

import android.content.Context;

import lic.swifter.box.api.model.FontConversion;
import lic.swifter.box.mvp.presenter.NetQueryType;

public interface FontConvertView {

    /**
     * 在开始查询之前的界面操作
     */
    void onStartQuery(int type, String text);

    /**
     * 查询结束后的操作
     */
    void onQueryResult(int type, NetQueryType netType, FontConversion response);

    /**
     * 返回上下文
     * @return 上下文
     */
    Context getContext();
}
