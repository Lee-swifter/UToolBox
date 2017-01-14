package lic.swifter.box.mvp.presenter;
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

import lic.swifter.box.mvp.view.FontConvertView;

public class FontConvertPresenter {

    public static final int SIMPLIFIED_CHINESE = 0;     //简体中文
    public static final int TRADITIONAL_CHINESE = 1;    //繁体中文
    public static final int LEETSPEAK = 2;              //火星文

    private FontConvertView iView;

    public FontConvertPresenter(FontConvertView view) {
        iView = view;
    }

    public void query(int type, String queryParameter) {

    }

    public void cancelQuery() {

    }
}
