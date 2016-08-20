package lic.swifter.box.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.api.model.QQLuck;
import lic.swifter.box.api.model.Result;
import lic.swifter.box.mvp.presenter.NetQueryType;
import lic.swifter.box.mvp.presenter.QQLuckPresenter;
import lic.swifter.box.mvp.view.IView;

/**
 * Created by cheng on 2016/8/20.
 */

public class QQLuckFragment extends BaseFragment implements IView<String, QQLuck> {

    @Bind(R.id.qq_input_text)
    EditText inputText;
    @Bind(R.id.qq_result_conclusion)
    TextView conclusion;
    @Bind(R.id.qq_result_analysis)
    TextView analysis;
    @Bind(R.id.qq_result_progress)
    ProgressBar progressBar;

    private QQLuckPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_qq_luck, container, false);
        ButterKnife.bind(this, rootView);

        presenter = new QQLuckPresenter(this);
        initView();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView() {
        inputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    String searchString = inputText.getText().toString();

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
        fadeOutView(conclusion);
        fadeOutView(analysis);
    }

    @Override
    public void afterQuery(NetQueryType type, Result<QQLuck> response) {
        switch (type) {
            case NET_RESPONSE_SUCCESS:
                conclusion.setText(response.result.data.conclusion);
                analysis.setText(response.result.data.analysis);

                fadeInView(conclusion);
                fadeInView(analysis);
                fadeOutView(progressBar);
                break;
            case NET_RESPONSE_ERROR_REASON:
                conclusion.setText(response.reason);

                analysis.setVisibility(View.GONE);
                fadeInView(conclusion);
                fadeOutView(progressBar);
                break;
            case NET_RESPONSE_ERROR:
                conclusion.setText(R.string.response_error);

                analysis.setVisibility(View.GONE);
                fadeInView(conclusion);
                fadeOutView(progressBar);
                break;
            case NET_REQUEST_FAILURE:
                conclusion.setText(R.string.net_failure);

                analysis.setVisibility(View.GONE);
                fadeInView(conclusion);
                fadeOutView(progressBar);
                break;
            default:
                break;
        }
    }

}
