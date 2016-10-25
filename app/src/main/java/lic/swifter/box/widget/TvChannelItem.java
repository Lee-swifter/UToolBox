package lic.swifter.box.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import lic.swifter.box.R;
import lic.swifter.box.activity.TvProgramActivity;
import lic.swifter.box.api.model.TvChannel;

/*
 * Copyright (C) 2015, Lee-swifter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by cheng on 2016/10/25.
 */

public class TvChannelItem extends LinearLayout {

    private TextView name;
    private TextView url;
    private Button button;

    private float downX;
    private float downY;
    private int touchSlop;
    private boolean touchMode;
    private boolean slide;
    private int lastScrollX;
    private int restoreScrollX;

    public TvChannelItem(Context context) {
        super(context, null);
    }

    public TvChannelItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TvChannelItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);

        LayoutInflater.from(context).inflate(R.layout.widget_tv_channel, this);
        name = ButterKnife.findById(this, R.id.widget_channel_name);
        url = ButterKnife.findById(this, R.id.widget_channel_rel);
        button = ButterKnife.findById(this, R.id.widget_channel_live_button);

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                lastScrollX = getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                float nowX = event.getRawX();
                float nowY = event.getRawY();

                if (!touchMode && (Math.abs(nowX - downX) > touchSlop || Math.abs(nowY - downY) > touchSlop)) {
                    touchMode = true;
                    if (Math.abs(nowX - downX) > touchSlop && Math.abs(nowY - downY) <= touchSlop) {
                        slide = true;
                        getParent().requestDisallowInterceptTouchEvent(true);

                        MotionEvent cancelEvent = MotionEvent.obtain(event);
                        cancelEvent.setAction(MotionEvent.ACTION_CANCEL | (event.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                        onTouchEvent(cancelEvent);
                    }
                }

                if (slide) {
                    float diffX = downX - nowX + lastScrollX;
                    if (diffX < 0)
                        diffX /= 3;
                    else if (diffX > button.getWidth())
                        diffX = (diffX - button.getWidth()) / 3 + button.getWidth();

                    scrollTo((int) diffX, 0);
                }

                break;
            case MotionEvent.ACTION_UP:
                if (slide) {
                    ValueAnimator animator;
                    if (getScrollX() > button.getWidth() / 2) {
                        animator = ValueAnimator.ofInt(getScrollX(), button.getWidth());
                    } else {
                        animator = ValueAnimator.ofInt(getScrollX(), 0);
                    }
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            scrollTo((Integer) animation.getAnimatedValue(), 0);
                        }
                    });
                    animator.start();
                    slide = false;
                }
                touchMode = false;
                break;
        }

        return super.onTouchEvent(event);
    }

    public void setChannel(final TvChannel channel) {
        scrollTo(0, 0);

        name.setText(channel.channelName);
        url.setText(channel.url);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(channel.url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        });

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TvProgramActivity.class);
                intent.putExtra(TvProgramActivity.TV_CHANNEL_INTENT, channel);
                getContext().startActivity(intent);
            }
        });
    }
}
