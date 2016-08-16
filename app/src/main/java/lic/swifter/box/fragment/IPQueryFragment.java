package lic.swifter.box.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.ApiHelper;
import lic.swifter.box.api.JuheApi;
import lic.swifter.box.api.model.IpLocation;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.db.BoxContract;
import lic.swifter.box.db.BoxDbHelper;
import lic.swifter.box.recycler.divider.GridDivider;
import lic.swifter.box.recycler.adapter.IpResultAdapter;
import lic.swifter.box.widget.CanaroTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IPQueryFragment extends BaseFragment {

    @Bind(R.id.ip_input_text)
    TextInputEditText inputEditText;
    @Bind(R.id.ip_progress)
    ProgressBar progress;
    @Bind(R.id.ip_result_area)
    CanaroTextView resultAreaText;
    @Bind(R.id.ip_result_location)
    CanaroTextView resultLocationText;
    @Bind(R.id.ip_result_result)
    LinearLayout resultWrapper;
    @Bind(R.id.ip_record_list)
    RecyclerView recyclerView;

    private String searchString;
    private IpResultAdapter adapter;
    private IpLocation currentResult;

    public IPQueryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ip_query, container, false);
        ButterKnife.bind(this, rootView);

        initView();
        return rootView;
    }

    private void initView() {
        readRecordsIntoList();

        inputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    searchString = inputEditText.getText().toString();

                    queryIp(searchString);

                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null)
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });
    }

    private void queryIp(final String ip) {
        fadeInView(progress);
        fadeOutView(resultWrapper);

        if(currentResult != null)
            adapter.addFirst(currentResult);

        JuheApi juheApi = ApiHelper.getJuhe();
        Call<Result<IpLocation>> call = juheApi.queryIp(ip);
        call.enqueue(new Callback<Result<IpLocation>>() {
            @Override
            public void onResponse(Call<Result<IpLocation>> call, Response<Result<IpLocation>> response) {
                if (response.isSuccessful()) {
                    if(response.body().resultcode != 200) {
                        resultAreaText.setText(response.body().reason);
                        resultLocationText.setVisibility(View.GONE);
                    } else {
                        currentResult = response.body().result;
                        currentResult.searchText = searchString;

                        resultAreaText.setText(currentResult.area);
                        resultLocationText.setText(currentResult.location);
                        resultLocationText.setVisibility(View.VISIBLE);

                        saveInDb(currentResult);
                    }
                } else {
                    resultAreaText.setText(R.string.response_error);
                    resultLocationText.setVisibility(View.GONE);
                }
                fadeOutView(progress);
                fadeInView(resultWrapper);
            }

            @Override
            public void onFailure(Call<Result<IpLocation>> call, Throwable t) {
                t.printStackTrace();

                resultAreaText.setText(R.string.net_failure);
                resultLocationText.setVisibility(View.GONE);
                fadeOutView(progress);
                fadeInView(resultWrapper);
            }
        });
    }

    private void saveInDb(final IpLocation ipLocation) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase sqLiteDatabase = BoxDbHelper.getInstance(getContext()).getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(BoxContract.IpEntry.COLUMN_NAME_SEARCH_DATA, ipLocation.searchText);
                values.put(BoxContract.IpEntry.COLUMN_NAME_SEARCH_TIME_STAMP, System.currentTimeMillis());
                values.put(BoxContract.IpEntry.COLUMN_NAME_RESULT_AREA, ipLocation.area);
                values.put(BoxContract.IpEntry.COLUMN_NAME_RESULT_LOCATION, ipLocation.location);
                sqLiteDatabase.insert(BoxContract.IpEntry.TABLE_NAME, "null", values);
            }
        }).start();
    }

    private void readRecordsIntoList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<IpLocation> list = new ArrayList<>();

                SQLiteDatabase sqLiteDatabase = BoxDbHelper.getInstance(getContext()).getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(BoxContract.IpEntry.TABLE_NAME, null, null, null, null, null,
                        BoxContract.IpEntry.COLUMN_NAME_SEARCH_TIME_STAMP + " DESC");
                if(cursor.moveToFirst()) {
                    do{
                        IpLocation currentRecord = new IpLocation();
                        currentRecord.timeStamp = cursor.getInt(cursor.getColumnIndexOrThrow(BoxContract.IpEntry.COLUMN_NAME_SEARCH_TIME_STAMP));
                        currentRecord.searchText = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.IpEntry.COLUMN_NAME_SEARCH_DATA));
                        currentRecord.area = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.IpEntry.COLUMN_NAME_RESULT_AREA));
                        currentRecord.location = cursor.getString(cursor.getColumnIndexOrThrow(BoxContract.IpEntry.COLUMN_NAME_RESULT_LOCATION));
                        list.add(currentRecord);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                sqLiteDatabase.close();

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        initRecycler(list);
                    }
                });
            }
        }).start();
    }

    private void initRecycler(List<IpLocation> list) {
        adapter = new IpResultAdapter(list);
        recyclerView.addItemDecoration(new GridDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

}
