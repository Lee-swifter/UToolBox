package lic.swifter.box.fragment;

import android.content.Context;
import android.os.Bundle;
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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.model.IpLocation;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.mvp.presenter.IpQueryPresenter;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.view.IpQueryView;
import lic.swifter.box.recycler.adapter.IpResultAdapter;
import lic.swifter.box.recycler.divider.GridDivider;
import lic.swifter.box.widget.CanaroTextView;

public class IPQueryFragment extends BaseFragment implements IpQueryView {

    @Bind(R.id.ip_input_text)
    EditText inputEditText;
    @Bind(R.id.ip_progress)
    ProgressBar progress;
    @Bind(R.id.ip_result_area)
    CanaroTextView resultAreaText;
    @Bind(R.id.ip_result_location)
    CanaroTextView resultLocationText;
    @Bind(R.id.ip_result_wrapper)
    LinearLayout resultWrapper;
    @Bind(R.id.ip_record_list)
    RecyclerView recyclerView;

    private IpResultAdapter adapter;
    private IpQueryPresenter presenter;

    public IPQueryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ip_query, container, false);
        ButterKnife.bind(this, rootView);
        presenter = new IpQueryPresenter(this);

        initView();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        presenter.cancelQuery();
        super.onDestroyView();
    }

    private void initView() {
        presenter.readFromDb();

        inputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    String searchString = inputEditText.getText().toString();

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
        fadeInView(progress);
        fadeOutView(resultWrapper);
    }

    @Override
    public void insertLastResult(IpLocation lastResult) {
        adapter.addFirst(lastResult);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<IpLocation> response) {
        switch (type) {
            case NET_RESPONSE_SUCCESS:
                resultAreaText.setText(response.result.area);
                resultLocationText.setText(response.result.location);
                resultLocationText.setVisibility(View.VISIBLE);

                break;
            case NET_RESPONSE_ERROR_REASON:
                resultAreaText.setText(response.reason);
                resultLocationText.setVisibility(View.GONE);
                break;
            case NET_RESPONSE_ERROR:
                resultAreaText.setText(R.string.response_error);
                resultLocationText.setVisibility(View.GONE);
                break;
            case NET_REQUEST_FAILURE:
                resultAreaText.setText(R.string.net_failure);
                resultLocationText.setVisibility(View.GONE);

                gotResponse();
                break;
        }
    }

    @Override
    public void gotResponse() {
        fadeOutView(progress);
        fadeInView(resultWrapper);
    }

    @Override
    public void afterReadResults(List<IpLocation> resultList) {
        adapter = new IpResultAdapter(resultList);
        recyclerView.addItemDecoration(new GridDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

}
