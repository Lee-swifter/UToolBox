package lic.swifter.box.mvp.presenter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.baidu.mobstat.StatService;

import java.util.ArrayList;
import java.util.List;

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.IdResult;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.db.BoxContract;
import lic.swifter.box.db.BoxDbHelper;
import lic.swifter.box.mvp.view.IdQueryView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cheng on 2016/8/19.
 */

public class IdQueryPresenter implements NetPresenter<String>, DbPresenter<IdResult> {

    private IdQueryView idQueryView;
    private Result<IdResult> lastResult;
    private Call<Result<IdResult>> call;

    public IdQueryPresenter(IdQueryView idQueryView) {
        this.idQueryView = idQueryView;
    }

    @Override
    public void readFromDb() {
        new AsyncTask<Void, Void, List<IdResult>>() {

            @Override
            protected List<IdResult> doInBackground(Void... params) {
                final List<IdResult> list = new ArrayList<>();

                SQLiteDatabase sqLiteDatabase = BoxDbHelper.getInstance(idQueryView.getContext()).getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(BoxContract.IdEntry.TABLE_NAME, null, null, null, null, null,
                        BoxContract.IdEntry.COLUMN_NAME_SEARCH_TIME_STAMP + " DESC");
                if(cursor.moveToFirst()) {
                    do{
                        IdResult currentRecord = new IdResult();
                        currentRecord.timeStamp = cursor.getLong(cursor.getColumnIndexOrThrow(BoxContract.IdEntry.COLUMN_NAME_SEARCH_TIME_STAMP));
                        currentRecord.idNumber = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.IdEntry.COLUMN_NAME_SEARCH_ID_NUMBER));
                        currentRecord.area = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.IdEntry.COLUMN_NAME_RESULT_AREA));
                        currentRecord.sex = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.IdEntry.COLUMN_NAME_RESULT_SEX));
                        currentRecord.birthday = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.IdEntry.COLUMN_NAME_RESULT_BIRTHDAY));
                        list.add(currentRecord);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                sqLiteDatabase.close();
                return list;
            }

            @Override
            protected void onPostExecute(List<IdResult> ipLocations) {
                idQueryView.afterReadResults(ipLocations);
            }
        }.execute();
    }

    @Override
    public void saveToDb(final IdResult idResult) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase sqLiteDatabase = BoxDbHelper.getInstance(idQueryView.getContext()).getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(BoxContract.IdEntry.COLUMN_NAME_SEARCH_TIME_STAMP, System.currentTimeMillis());
                values.put(BoxContract.IdEntry.COLUMN_NAME_SEARCH_ID_NUMBER, idResult.idNumber);
                values.put(BoxContract.IdEntry.COLUMN_NAME_RESULT_AREA, idResult.area);
                values.put(BoxContract.IdEntry.COLUMN_NAME_RESULT_SEX, idResult.sex);
                values.put(BoxContract.IdEntry.COLUMN_NAME_RESULT_BIRTHDAY, idResult.birthday);
                sqLiteDatabase.insert(BoxContract.IdEntry.TABLE_NAME, "null", values);
            }
        }).start();
    }

    @Override
    public void query(final String queryParameter) {
        idQueryView.beforeQuery(queryParameter);
        StatService.onEvent(idQueryView.getContext(), "id_card_query", "pass", 1);

        if(lastResult != null && lastResult.result != null)
            idQueryView.insertLastResult(lastResult.result);

        JuheApi juheApi = ApiHelper.getJuhe();
        call = juheApi.queryId(queryParameter);
        call.enqueue(new Callback<Result<IdResult>>() {
            @Override
            public void onResponse(Call<Result<IdResult>> call, Response<Result<IdResult>> response) {
                if(response.isSuccessful()) {
                    lastResult = response.body();
                    if(lastResult.resultcode == 200) {
                        lastResult.result.idNumber = queryParameter;
                        idQueryView.afterQuery(NetQueryType.NET_RESPONSE_SUCCESS, lastResult);
                        saveToDb(lastResult.result);
                    } else {
                        idQueryView.afterQuery(NetQueryType.NET_RESPONSE_ERROR_REASON, lastResult);
                        lastResult = null;
                    }
                } else {
                    idQueryView.afterQuery(NetQueryType.NET_RESPONSE_ERROR, null);
                    lastResult = null;
                }

                idQueryView.gotResponse();
            }

            @Override
            public void onFailure(Call<Result<IdResult>> call, Throwable t) {
                t.printStackTrace();
                idQueryView.afterQuery(NetQueryType.NET_REQUEST_FAILURE, null);
                lastResult = null;
            }
        });
    }

    @Override
    public void cancelQuery() {
        if(call != null && !call.isCanceled()) {
            call.cancel();
        }
    }

}
