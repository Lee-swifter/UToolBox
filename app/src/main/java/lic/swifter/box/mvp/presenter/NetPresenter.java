package lic.swifter.box.mvp.presenter;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * MVP presenter 网络请求基本接口
 * Created by lic on 16-8-16.
 */
public interface NetPresenter<T> {

    int NET_REQUEST_FAILURE = 100;
    int NET_RESPONSE_ERROR = 101;
    int NET_RESPONSE_SUCCESS = 102;

    @IntDef({NET_REQUEST_FAILURE, NET_RESPONSE_ERROR, NET_RESPONSE_SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    @interface NET_QUERY_TYPE { }

    /**
     * 用于在查询数据之前的操作
     */
    void beforeQuery();

    /**
     * 调用服务器端接口查询数据
     */
    void query(T queryParameter);

    /**
     * 获取数据之后的操作
     */
    void afterQuery(NET_QUERY_TYPE type);
}
