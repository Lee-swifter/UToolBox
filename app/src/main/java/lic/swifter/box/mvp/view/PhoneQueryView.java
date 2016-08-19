package lic.swifter.box.mvp.view;

import android.content.Context;

import java.util.List;

import lic.swifter.box.api.model.PhoneResult;

/**
 * Created by cheng on 2016/8/19.
 */

public interface PhoneQueryView extends IView<String, PhoneResult> {

    void insertLastResult(PhoneResult lastResult);
    void gotResponse();

    void afterReadResults(List<PhoneResult> resultList);
    Context getContext();
}
