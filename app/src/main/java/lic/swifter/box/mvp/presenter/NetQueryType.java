package lic.swifter.box.mvp.presenter;

/**
 * 网络请求后结果类型
 * Created by cheng on 2016/8/16.
 */
public enum NetQueryType {
    /**
     * 因网络原因请求失败
     */
    NET_REQUEST_FAILURE,
    /**
     * 服务器返回失败
     */
    NET_RESPONSE_ERROR,
    /**
     * 服务器返回失败，但是此时可获取原因
     */
    NET_RESPONSE_ERROR_REASON,
    /**
     * 请求成功
     */
    NET_RESPONSE_SUCCESS
}
