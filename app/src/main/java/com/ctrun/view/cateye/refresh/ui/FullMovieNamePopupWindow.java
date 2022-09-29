package com.ctrun.view.cateye.refresh.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ctrun.view.cateye.refresh.R;

/**
 * @author ctrun on 2018/11/15.
 */
@SuppressWarnings("WeakerAccess")
public class FullMovieNamePopupWindow extends PopupWindow {

    @SuppressLint("InflateParams")
    public FullMovieNamePopupWindow(final Activity activity) {
        super(activity.getLayoutInflater().inflate(R.layout.popup_full_movie_name, null), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        setAnimationStyle(0);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(false);

        mTvName = getContentView().findViewById(R.id.tv_movie_name);
        mTvEName = getContentView().findViewById(R.id.tv_english_movie_name);
    }

    private final TextView mTvName;
    private final TextView mTvEName;
    public void show(View anchor, String name, String englishName) {
        mTvName.setText(name);
        mTvEName.setText(englishName);

        if (TextUtils.isEmpty(englishName)) {
            mTvEName.setVisibility(View.GONE);
        } else {
            mTvEName.setVisibility(View.VISIBLE);
        }

        showAsDropDown(anchor);
    }
}
