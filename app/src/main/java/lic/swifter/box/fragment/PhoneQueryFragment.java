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
import lic.swifter.box.api.model.PhoneResult;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.presenter.PhoneQueryPresenter;
import lic.swifter.box.mvp.view.PhoneQueryView;
import lic.swifter.box.recycler.adapter.PhoneResultAdapter;
import lic.swifter.box.recycler.divider.GridDivider;

/**
 * Created by cheng on 2016/8/19.
 */

public class PhoneQueryFragment extends BaseFragment implements PhoneQueryView {

    @Bind(R.id.phone_input_text)
    EditText inputEdit;
    @Bind(R.id.phone_result_wrapper)
    LinearLayout resultWrapper;
    @Bind(R.id.phone_result_location)
    TextView resultLocation;
    @Bind(R.id.phone_result_company)
    TextView resultCompany;
    @Bind(R.id.phone_result_area_code)
    TextView resultAreaCode;
    @Bind(R.id.phone_result_zip)
    TextView resultZip;
    @Bind(R.id.phone_progress)
    ProgressBar progressBar;
    @Bind(R.id.phone_record_list)
    RecyclerView recyclerView;

    private PhoneResultAdapter adapter;
    private PhoneQueryPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_phone_query, container, false);
        ButterKnife.bind(this, rootView);
        presenter = new PhoneQueryPresenter(this);

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
    public void insertLastResult(PhoneResult lastResult) {
        adapter.addFirst(lastResult);
    }

    @Override
    public void gotResponse() {
        fadeOutView(progressBar);
        fadeInView(resultWrapper);
    }

    @Override
    public void afterReadResults(List<PhoneResult> resultList) {
        adapter = new PhoneResultAdapter(resultList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new GridDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void beforeQuery(String requestParameter) {
        fadeInView(progressBar);
        fadeOutView(resultWrapper);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<PhoneResult> response) {
        switch (type) {
            case NET_RESPONSE_SUCCESS:
                resultLocation.setText(response.result.province+ " "+response.result.city);
                resultCompany.setText(response.result.company + " "+response.result.card);
                resultAreaCode.setText(getString(R.string.area_code_string, response.result.areacode));
                resultZip.setText(getString(R.string.zip_string, response.result.zip));

                resultCompany.setVisibility(View.VISIBLE);
                resultAreaCode.setVisibility(View.VISIBLE);
                resultZip.setVisibility(View.VISIBLE);
                break;
            case NET_RESPONSE_ERROR_REASON:
                resultLocation.setText(response.reason);

                resultCompany.setVisibility(View.GONE);
                resultAreaCode.setVisibility(View.GONE);
                resultZip.setVisibility(View.GONE);
                break;
            case NET_RESPONSE_ERROR:
                resultLocation.setText(R.string.response_error);

                resultCompany.setVisibility(View.GONE);
                resultAreaCode.setVisibility(View.GONE);
                resultZip.setVisibility(View.GONE);
                break;
            case NET_REQUEST_FAILURE:
                resultLocation.setText(R.string.net_failure);

                resultCompany.setVisibility(View.GONE);
                resultAreaCode.setVisibility(View.GONE);
                resultZip.setVisibility(View.GONE);

                gotResponse();
                break;
            default:
                break;
        }
    }
}
