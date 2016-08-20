package lic.swifter.box.mvp.presenter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.IpLocation;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.db.BoxContract;
import lic.swifter.box.db.BoxDbHelper;
import lic.swifter.box.mvp.view.IpQueryView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 针对 {@link lic.swifter.box.fragment.IPQueryFragment}的MVP中Presenter角色
 * Created by lic on 16-8-16.
 */
public class IpQueryPresenter implements NetPresenter<String>, DbPresenter<IpLocation> {

    private IpQueryView ipQueryView;
    private Result<IpLocation> lastResult;
    private Call<Result<IpLocation>> call;

    public IpQueryPresenter(IpQueryView ipQueryView) {
        this.ipQueryView = ipQueryView;
    }

    @Override
    public void query(final String ip) {
        ipQueryView.beforeQuery(ip);
        if(lastResult != null && lastResult.result != null)
            ipQueryView.insertLastResult(lastResult.result);

        JuheApi juheApi = ApiHelper.getJuhe();
        call = juheApi.queryIp(ip);
        call.enqueue(new Callback<Result<IpLocation>>() {
            @Override
            public void onResponse(Call<Result<IpLocation>> call, Response<Result<IpLocation>> response) {
                if (response.isSuccessful()) {
                    lastResult = response.body();
                    if (lastResult.resultcode == 200) {
                        lastResult.result.ip = ip;
                        ipQueryView.afterQuery(NetQueryType.NET_RESPONSE_SUCCESS, lastResult);
                        saveToDb(lastResult.result);
                    } else {
                        ipQueryView.afterQuery(NetQueryType.NET_RESPONSE_ERROR_REASON, lastResult);
                        lastResult = null;
                    }
                } else {
                    ipQueryView.afterQuery(NetQueryType.NET_RESPONSE_ERROR, null);
                    lastResult = null;
                }

                ipQueryView.gotResponse();
            }

            @Override
            public void onFailure(Call<Result<IpLocation>> call, Throwable t) {
                t.printStackTrace();
                ipQueryView.afterQuery(NetQueryType.NET_REQUEST_FAILURE, null);
                lastResult = null;
            }
        });
    }

    @Override
    public void saveToDb(final IpLocation ipLocation) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase sqLiteDatabase = BoxDbHelper.getInstance(ipQueryView.getContext()).getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(BoxContract.IpEntry.COLUMN_NAME_SEARCH_DATA, ipLocation.ip);
                values.put(BoxContract.IpEntry.COLUMN_NAME_SEARCH_TIME_STAMP, System.currentTimeMillis());
                values.put(BoxContract.IpEntry.COLUMN_NAME_RESULT_AREA, ipLocation.area);
                values.put(BoxContract.IpEntry.COLUMN_NAME_RESULT_LOCATION, ipLocation.location);
                sqLiteDatabase.insert(BoxContract.IpEntry.TABLE_NAME, "null", values);
            }
        }).start();
    }

    @Override
    public void readFromDb() {
        new AsyncTask<Void, Void, List<IpLocation>>() {

            @Override
            protected List<IpLocation> doInBackground(Void... params) {
                final List<IpLocation> list = new ArrayList<>();

                SQLiteDatabase sqLiteDatabase = BoxDbHelper.getInstance(ipQueryView.getContext()).getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(BoxContract.IpEntry.TABLE_NAME, null, null, null, null, null,
                        BoxContract.IpEntry.COLUMN_NAME_SEARCH_TIME_STAMP + " DESC");
                if(cursor.moveToFirst()) {
                    do{
                        IpLocation currentRecord = new IpLocation();
                        currentRecord.timeStamp = cursor.getLong(cursor.getColumnIndexOrThrow(BoxContract.IpEntry.COLUMN_NAME_SEARCH_TIME_STAMP));
                        currentRecord.ip = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.IpEntry.COLUMN_NAME_SEARCH_DATA));
                        currentRecord.area = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.IpEntry.COLUMN_NAME_RESULT_AREA));
                        currentRecord.location = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.IpEntry.COLUMN_NAME_RESULT_LOCATION));
                        list.add(currentRecord);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                sqLiteDatabase.close();
                return list;
            }

            @Override
            protected void onPostExecute(List<IpLocation> ipLocations) {
                ipQueryView.afterReadResults(ipLocations);
            }
        }.execute();
    }

    @Override
    public void cancelQuery() {
        if(call != null && !call.isCanceled()) {
            call.cancel();
        }
    }

}
