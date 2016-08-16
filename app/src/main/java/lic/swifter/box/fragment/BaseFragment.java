package lic.swifter.box.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Fragment 基类，使用基类便于处理公共操作
 * Created by lic on 16-7-7.
 */
public abstract class BaseFragment extends Fragment {

    int mShortAnimationDuration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    public void fadeInView(View view) {
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);
    }

    public void fadeOutView(final View view) {
        if(!view.isShown())
            return ;

        view.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.GONE);
                    }
                });
    }
}
