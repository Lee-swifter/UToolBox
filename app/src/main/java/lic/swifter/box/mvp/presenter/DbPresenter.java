package lic.swifter.box.mvp.presenter;

/**
 * Created by cheng on 2016/8/16.
 */

interface DbPresenter<T> {
    void readFromDb();
    void saveToDb(T data);
}
