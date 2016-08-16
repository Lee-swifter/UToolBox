package lic.swifter.box.mvp.view;

import android.content.Context;

import java.util.List;

import lic.swifter.box.api.model.IpLocation;

/**
 * IP查询需要处理的界面接口
 * Created by lic on 16-8-16.
 */
public interface IpQueryView extends IView<String, IpLocation> {

    void insertLastResult(IpLocation lastResult);
    void gotResponse();

    void afterReadResults(List<IpLocation> resultList);
    Context getContext();
}
