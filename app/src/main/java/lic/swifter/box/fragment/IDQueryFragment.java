package lic.swifter.box.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import lic.swifter.box.R;
import lic.swifter.box.api.model.IdResult;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.mvp.presenter.IdQueryPresenter;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.view.IdQueryView;
import lic.swifter.box.recycler.adapter.IdResultAdapter;
import lic.swifter.box.recycler.divider.GridDivider;

/**
 * Created by cheng on 2016/8/17.
 */

public class IDQueryFragment extends BaseFragment implements IdQueryView {

    EditText inputEdit;
    LinearLayout resultWrapper;
    TextView resultLocation;
    TextView resultGender;
    TextView resultBirthday;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    private IdQueryPresenter presenter;
    private IdResultAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_id_query, container, false);

        inputEdit = rootView.findViewById(R.id.id_input_text);
        resultWrapper = rootView.findViewById(R.id.id_result_wrapper);
        resultLocation = rootView.findViewById(R.id.id_result_location);
        resultGender = rootView.findViewById(R.id.id_result_gender);
        resultBirthday = rootView.findViewById(R.id.id_result_birthday);
        progressBar = rootView.findViewById(R.id.id_progress);
        recyclerView = rootView.findViewById(R.id.id_record_list);

        presenter = new IdQueryPresenter(this);

        initView();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        presenter.cancelQuery();
        super.onDestroyView();
    }

    private void initView() {
        presenter.readFromDb();

        inputEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    String searchString = inputEdit.getText().toString();

                    presenter.query(searchString);
                    if (getContext() == null)
                        return false;

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null)
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void beforeQuery(String requestParameter) {
        fadeInView(progressBar);
        fadeOutView(resultWrapper);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<IdResult> response) {
        switch (type) {
            case NET_RESPONSE_SUCCESS:
                resultLocation.setText(response.result.area);
                resultGender.setText(response.result.sex);
                resultBirthday.setText(response.result.birthday);

                resultGender.setVisibility(View.VISIBLE);
                resultBirthday.setVisibility(View.VISIBLE);
                break;
            case NET_RESPONSE_ERROR_REASON:
                resultLocation.setText(response.reason);

                resultGender.setVisibility(View.GONE);
                resultBirthday.setVisibility(View.GONE);
                break;
            case NET_RESPONSE_ERROR:
                resultLocation.setText(R.string.response_error);

                resultGender.setVisibility(View.GONE);
                resultBirthday.setVisibility(View.GONE);
                break;
            case NET_REQUEST_FAILURE:
                resultLocation.setText(R.string.net_failure);

                resultGender.setVisibility(View.GONE);
                resultBirthday.setVisibility(View.GONE);

                gotResponse();
                break;
        }
    }

    @Override
    public void insertLastResult(IdResult lastResult) {
        adapter.addFirst(lastResult);
    }

    @Override
    public void gotResponse() {
        fadeOutView(progressBar);
        fadeInView(resultWrapper);
    }

    @Override
    public void afterReadResults(List<IdResult> resultList) {
        adapter = new IdResultAdapter(resultList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (getContext() != null)
            recyclerView.addItemDecoration(new GridDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(adapter);
    }
}
