package lic.swifter.box.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lic.swifter.box.R;

/**
 * Created by cheng on 2016/8/17.
 */

public class IDQueryFragment extends BaseFragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_id_query, container, false);
        ButterKnife.bind(this, rootView);

        initView();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    private void initView() {

    }
}
