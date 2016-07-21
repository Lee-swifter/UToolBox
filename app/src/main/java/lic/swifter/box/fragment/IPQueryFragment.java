package lic.swifter.box.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lic.swifter.box.R;

public class IPQueryFragment extends BaseFragment {

    private TextInputEditText inputEditText;

    public IPQueryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ip_query, container, false);

        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        inputEditText = (TextInputEditText) rootView.findViewById(R.id.ip_input_text);

        inputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    queryIp();
                    return true;
                }
                return false;
            }
        });
    }

    private void queryIp() {

    }

}
