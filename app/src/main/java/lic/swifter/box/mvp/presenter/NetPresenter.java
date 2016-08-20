package lic.swifter.box.mvp.presenter;

/**
 * MVP presenter 网络请求基本接口
 * Created by lic on 16-8-16.
 */
interface NetPresenter<T> {

    /**
     * 调用服务器端接口查询数据
     */
    void query(T queryParameter);

    void cancelQuery();

}
