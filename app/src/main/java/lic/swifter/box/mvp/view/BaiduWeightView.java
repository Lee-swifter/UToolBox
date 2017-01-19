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

import java.util.List;

import lic.swifter.box.api.model.BaiduWeight;

public interface BaiduWeightView extends IView<String, BaiduWeight> {

    void insertLastResult(BaiduWeight result);
    void gotResponse();

    void afterReadResults(List<BaiduWeight> resultList);
    Context getContext();
}
