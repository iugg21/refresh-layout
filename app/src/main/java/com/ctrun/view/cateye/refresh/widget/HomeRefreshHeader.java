package com.ctrun.view.cateye.refresh.widget;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.ctrun.view.cateye.refresh.R;
import com.ctrun.view.cateye.refresh.listener.OnTwoLevelListener;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

/**
 * @author ctrun on 2018/10/13.
 */
@SuppressLint("RestrictedApi")
public class HomeRefreshHeader extends RelativeLayout implements RefreshHeader {
    private static final String TAG = "HomeRefreshHeader";

    private Animation mRotateAnimLoading;

    protected RefreshKernel mRefreshKernel;
    protected SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;
    private HomeRefreshProgressView mProgressView;
    private ImageView mIvArrowDown;
    private TextView mTvState;

    private RefreshState mRefreshState;

    protected int mBackgroundColor;
    protected int mFinishDuration = 100;

    public HomeRefreshHeader(Context context) {
        this(context, null);
    }

    public HomeRefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public HomeRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mRotateAnimLoading = AnimationUtils.loadAnimation(getContext(), R.anim.common_rotate_anim_loading);

        FrameLayout.LayoutParams lpArrowDown = new FrameLayout.LayoutParams(dpToPx(8), dpToPx(8));
        lpArrowDown.gravity = Gravity.CENTER;
        mIvArrowDown = new ImageView(context);
        mIvArrowDown.setLayoutParams(lpArrowDown);
        mIvArrowDown.setImageResource(R.drawable.ic_arrow_down_white);
        mIvArrowDown.setVisibility(INVISIBLE);

        FrameLayout.LayoutParams lpProgress = new FrameLayout.LayoutParams(dpToPx(16), dpToPx(16));
        lpProgress.gravity = Gravity.CENTER;
        mProgressView = new HomeRefreshProgressView(context);
        mProgressView.setLayoutParams(lpProgress);

        LinearLayout.LayoutParams lpContent = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpContent.gravity = Gravity.CENTER_HORIZONTAL;
        FrameLayout contentLayout = new FrameLayout(context);
        contentLayout.setLayoutParams(lpContent);
        contentLayout.addView(mIvArrowDown);
        contentLayout.addView(mProgressView);

        LinearLayout.LayoutParams lpState = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpState.gravity = Gravity.CENTER;
        mTvState = new TextView(context);
        mTvState.setLayoutParams(lpState);
        mTvState.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        mTvState.setTextColor(ContextCompat.getColor(context, R.color.common_font_white));

        LayoutParams lpHeaderLayout = new LayoutParams(MATCH_PARENT, dpToPx(74));
        lpHeaderLayout.addRule(CENTER_IN_PARENT);
        LinearLayout headerLayout = new LinearLayout(context);
        headerLayout.setOrientation(LinearLayout.VERTICAL);
        headerLayout.setLayoutParams(lpHeaderLayout);
        final int headerLayoutPaddingTop = dpToPx(12);
        headerLayout.setPadding(0, headerLayoutPaddingTop, 0, 0);
        headerLayout.setBackgroundResource(R.drawable.home_refresh_bg);

        headerLayout.addView(contentLayout);
        headerLayout.addView(mTvState);

        addView(headerLayout);
    }

    enum TwoLevelRefreshState {
        None,
        ReleaseToTwoLevel,
        PullDownToTwoLevel;
    }

    private TwoLevelRefreshState mTwoLevelRefreshState = TwoLevelRefreshState.None;
    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        if (mRefreshState == RefreshState.Refreshing) {
            return;
        }

        final RefreshKernel refreshKernel = mRefreshKernel;

        if (isDragging) {
            int currentProgress = (int) Math.min(mFloorRate * 100, percent * 100);
            mProgressView.setProgress(currentProgress);

            if (percent >= mFloorRate && mEnableTwoLevel) {
                refreshKernel.setState(RefreshState.ReleaseToTwoLevel);

                if (mTwoLevelRefreshState != TwoLevelRefreshState.None) {
                    mTwoLevelRefreshState = TwoLevelRefreshState.None;
                }
            } else if (percent >= mRefreshRate && percent < mFloorRate && mEnableRefresh) {
                if (mTwoLevelRefreshState != TwoLevelRefreshState.PullDownToTwoLevel) {
                    mTwoLevelRefreshState = TwoLevelRefreshState.PullDownToTwoLevel;
                    onTwoLevelStateChanged(mTwoLevelRefreshState);
                }
                refreshKernel.setState(RefreshState.ReleaseToRefresh);
            } else if (percent < mRefreshRate) {
                if (mTwoLevelRefreshState != TwoLevelRefreshState.None) {
                    mTwoLevelRefreshState = TwoLevelRefreshState.None;
                }
            }
            mPercent = percent;

            Log.d(TAG, "perPercent=" + mPercent + ",percent=" + percent);
        }
    }

    protected float mPercent = 0;
    protected float mMaxRate = 2.5f;
    protected float mFloorRate = 1.9f;
    protected float mRefreshRate = 1f;

    protected boolean mEnableTwoLevel = true;
    protected boolean mEnableRefresh = true;

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        mRefreshState = newState;

        switch (newState) {
            case ReleaseToTwoLevel:
                mTvState.setText("即将进入二楼");
                mIvArrowDown.setVisibility(VISIBLE);
                break;
            case TwoLevelReleased:
                if (mTwoLevelListener != null) {
                    mTwoLevelListener.onTwoLevel(refreshLayout);
                }

                final RefreshKernel refreshKernel = mRefreshKernel;
                if (refreshKernel != null) {
                    postDelayed(() -> refreshKernel.setState(RefreshState.None), 100);
                }
                break;
            case RefreshReleased:
                mTvState.setText("正在刷新");
                mIvArrowDown.setVisibility(INVISIBLE);
                break;
            case PullDownToRefresh:
                mTvState.setText("下拉刷新");
                mIvArrowDown.setVisibility(INVISIBLE);
                break;
            default:
                break;
        }
    }

    public void onTwoLevelStateChanged(TwoLevelRefreshState state) {
        switch (state) {
            case None:
                mTvState.setText("下拉刷新");
                mIvArrowDown.setVisibility(INVISIBLE);
                break;
            case ReleaseToTwoLevel:
                mTvState.setText("即将进入二楼");
                mIvArrowDown.setVisibility(VISIBLE);
            break;
            case PullDownToTwoLevel:
                mTvState.setText("继续下拉进入二楼");
                mIvArrowDown.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout layout, int height, int extendHeight) {
        mProgressView.setProgress(100);
        mProgressView.startAnimation(mRotateAnimLoading);
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        mProgressView.clearAnimation();
        return mFinishDuration;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {
        mProgressView.setProgress(100);
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void setPrimaryColors(int... colors) {
        if (colors.length > 0) {
            if (!(getBackground() instanceof BitmapDrawable)) {
                setPrimaryColor(colors[0]);
            }
        }
    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {
        mRefreshKernel = kernel;
        mRefreshKernel.requestDrawBackgroundFor(this, mBackgroundColor);
    }

    @SuppressWarnings("UnusedReturnValue")
    public HomeRefreshHeader setPrimaryColor(@ColorInt int primaryColor) {
        setBackgroundColor(mBackgroundColor = primaryColor);
        if (mRefreshKernel != null) {
            mRefreshKernel.requestDrawBackgroundFor(this, mBackgroundColor);
        }
        return this;
    }

    @SuppressWarnings("unused")
    public HomeRefreshHeader setSpinnerStyle(SpinnerStyle style) {
        this.mSpinnerStyle = style;
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    private OnTwoLevelListener mTwoLevelListener;
    public HomeRefreshHeader setOnTwoLevelListener(final OnTwoLevelListener listener) {
        this.mTwoLevelListener = listener;
        return this;
    }

    int dpToPx(@Dimension(unit = 0) int dps) {
        return Math.round(Resources.getSystem().getDisplayMetrics().density * (float)dps);
    }
}
