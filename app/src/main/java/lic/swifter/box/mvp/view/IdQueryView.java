package lic.swifter.box.mvp.view;

import android.content.Context;

import java.util.List;

import lic.swifter.box.api.model.IdResult;

/**
 * 身份证查询界面需要处理的界面操作
 * Created by cheng on 2016/8/19.
 */
public interface IdQueryView extends IView<String,IdResult> {

    void insertLastResult(IdResult lastResult);
    void gotResponse();

    void afterReadResults(List<IdResult> resultList);
    Context getContext();
}
