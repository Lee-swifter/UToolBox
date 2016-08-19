package lic.swifter.box.fragment;

import android.content.Context;
import android.os.Bundle;
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

import butterknife.Bind;
import butterknife.ButterKnife;
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

    @Bind(R.id.id_input_text)
    EditText inputEdit;
    @Bind(R.id.id_result_wrapper)
    LinearLayout resultWrapper;
    @Bind(R.id.id_result_location)
    TextView resultLocation;
    @Bind(R.id.id_result_gender)
    TextView resultGender;
    @Bind(R.id.id_result_birthday)
    TextView resultBirthday;
    @Bind(R.id.id_progress)
    ProgressBar progressBar;
    @Bind(R.id.id_record_list)
    RecyclerView recyclerView;

    private IdQueryPresenter presenter;
    private IdResultAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_id_query, container, false);
        ButterKnife.bind(this, rootView);
        presenter = new IdQueryPresenter(this);

        initView();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
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

                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null)
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

                fadeInView(resultWrapper);
                fadeOutView(progressBar);
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
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void afterReadResults(List<IdResult> resultList) {
        adapter = new IdResultAdapter(resultList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new GridDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(adapter);
    }
}
