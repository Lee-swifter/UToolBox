package lic.swifter.box.mvp.presenter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.PhoneResult;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.db.BoxContract;
import lic.swifter.box.db.BoxDbHelper;
import lic.swifter.box.mvp.view.PhoneQueryView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cheng on 2016/8/19.
 */

public class PhoneQueryPresenter implements NetPresenter<String>, DbPresenter<PhoneResult> {

    private Result<PhoneResult> lastRecord;
    private PhoneQueryView phoneQueryView;

    public PhoneQueryPresenter(PhoneQueryView phoneQueryView) {
        this.phoneQueryView = phoneQueryView;
    }

    @Override
    public void readFromDb() {
        new AsyncTask<Void, Void, List<PhoneResult>>() {

            @Override
            protected List<PhoneResult> doInBackground(Void... params) {
                final List<PhoneResult> list = new ArrayList<>();

                SQLiteDatabase sqLiteDatabase = BoxDbHelper.getInstance(phoneQueryView.getContext()).getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(BoxContract.PhoneEntry.TABLE_NAME, null, null, null, null, null,
                        BoxContract.PhoneEntry.COLUMN_NAME_SEARCH_TIME_STAMP + " DESC");
                if(cursor.moveToFirst()) {
                    do{
                        PhoneResult currentRecord = new PhoneResult();
                        currentRecord.timeStamp = cursor.getLong(cursor.getColumnIndexOrThrow(BoxContract.PhoneEntry.COLUMN_NAME_SEARCH_TIME_STAMP));
                        currentRecord.phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.PhoneEntry.COLUMN_NAME_SEARCH_PHONE_NUMBER));
                        currentRecord.province = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.PhoneEntry.COLUMN_NAME_RESULT_PROVINCE));
                        currentRecord.city = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.PhoneEntry.COLUMN_NAME_RESULT_CITY));
                        currentRecord.areacode = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.PhoneEntry.COLUMN_NAME_RESULT_AREA_CODE));
                        currentRecord.zip = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.PhoneEntry.COLUMN_NAME_RESULT_ZIP));
                        currentRecord.company = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.PhoneEntry.COLUMN_NAME_RESULT_COMPANY));
                        currentRecord.card = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.PhoneEntry.COLUMN_NAME_RESULT_CARD));
                        list.add(currentRecord);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                sqLiteDatabase.close();
                return list;
            }

            @Override
            protected void onPostExecute(List<PhoneResult> phoneResultList) {
                phoneQueryView.afterReadResults(phoneResultList);
            }
        }.execute();
    }

    @Override
    public void saveToDb(final PhoneResult phoneResult) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase sqLiteDatabase = BoxDbHelper.getInstance(phoneQueryView.getContext()).getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(BoxContract.PhoneEntry.COLUMN_NAME_SEARCH_TIME_STAMP, System.currentTimeMillis());
                values.put(BoxContract.PhoneEntry.COLUMN_NAME_SEARCH_PHONE_NUMBER, phoneResult.phoneNumber);
                values.put(BoxContract.PhoneEntry.COLUMN_NAME_RESULT_PROVINCE, phoneResult.province);
                values.put(BoxContract.PhoneEntry.COLUMN_NAME_RESULT_CITY, phoneResult.city);
                values.put(BoxContract.PhoneEntry.COLUMN_NAME_RESULT_AREA_CODE, phoneResult.areacode);
                values.put(BoxContract.PhoneEntry.COLUMN_NAME_RESULT_ZIP, phoneResult.zip);
                values.put(BoxContract.PhoneEntry.COLUMN_NAME_RESULT_COMPANY, phoneResult.company);
                values.put(BoxContract.PhoneEntry.COLUMN_NAME_RESULT_CARD, phoneResult.card);
                sqLiteDatabase.insert(BoxContract.PhoneEntry.TABLE_NAME, "null", values);
            }
        }).start();

    }

    @Override
    public void query(final String queryParameter) {
        phoneQueryView.beforeQuery(queryParameter);
        if(lastRecord != null && lastRecord.result != null)
            phoneQueryView.insertLastResult(lastRecord.result);

        JuheApi juheApi = ApiHelper.getJuhe();
        Call<Result<PhoneResult>> call = juheApi.queryPhone(queryParameter);
        call.enqueue(new Callback<Result<PhoneResult>>() {
            @Override
            public void onResponse(Call<Result<PhoneResult>> call, Response<Result<PhoneResult>> response) {
                if(response.isSuccessful()) {
                    lastRecord = response.body();
                    if(lastRecord.resultcode == 200) {
                        lastRecord.result.phoneNumber = queryParameter;
                        phoneQueryView.afterQuery(NetQueryType.NET_RESPONSE_SUCCESS, lastRecord);
                        saveToDb(lastRecord.result);
                    } else {
                        phoneQueryView.afterQuery(NetQueryType.NET_RESPONSE_ERROR_REASON, lastRecord);
                        lastRecord = null;
                    }
                } else {
                    phoneQueryView.afterQuery(NetQueryType.NET_RESPONSE_ERROR, null);
                    lastRecord = null;
                }

                phoneQueryView.gotResponse();
            }

            @Override
            public void onFailure(Call<Result<PhoneResult>> call, Throwable t) {
                t.printStackTrace();
                phoneQueryView.afterQuery(NetQueryType.NET_REQUEST_FAILURE, null);
                lastRecord = null;
            }
        });
    }
}
