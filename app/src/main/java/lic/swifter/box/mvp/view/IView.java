package lic.swifter.box.mvp.view;

import lic.swifter.box.api.model.Result;
import lic.swifter.box.mvp.presenter.NetQueryType;

/**
 * MVP中的基本View
 * Created by lic on 16-8-16.
 */
public interface IView<T, S> {

    /**
     * 在开始查询之前的界面操作
     */
    void beforeQuery(T requestParameter);

    /**
     * 查询结束后的操作
     */
    void afterQuery(NetQueryType type, Result<S> response);
}
