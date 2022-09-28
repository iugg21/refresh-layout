package com.ctrun.view.cateye.refresh.widget;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.ctrun.view.cateye.refresh.R;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.util.SmartUtil;


/**
 * @author ctrun on 2017/11/10.
 */
@SuppressLint("RestrictedApi")
@SuppressWarnings("unused")
public class CommonRefreshHeader extends RelativeLayout implements RefreshHeader {

    private Animation mRotateAnimation;
    protected RefreshProgressView mProgressView;
    protected RefreshKernel mRefreshKernel;
    protected SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;

    protected int mFinishDuration = 100;
    protected int mBackgroundColor;
    protected int mPaddingTop = 10;
    protected int mPaddingBottom = 10;

    private RefreshState mRefreshState = RefreshState.None;

    public CommonRefreshHeader(Context context) {
        this(context, null);
    }

    public CommonRefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mRotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.common_rotate_anim_loading);

        FrameLayout layout = new FrameLayout(context);

        FrameLayout.LayoutParams lpCircle = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        ImageView circleView = new ImageView(context);
        circleView.setImageResource(R.drawable.bg_pull_process);
        layout.addView(circleView, lpCircle);

        FrameLayout.LayoutParams lpProgress = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpProgress.gravity = Gravity.BOTTOM;
        mProgressView = new RefreshProgressView(context);
        mProgressView.animate().setInterpolator(new LinearInterpolator());
        layout.addView(mProgressView, lpProgress);


        LayoutParams lpHeaderLayout = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpHeaderLayout.addRule(CENTER_IN_PARENT);
        addView(layout, lpHeaderLayout);

        if (isInEditMode()) {
            mProgressView.setVisibility(GONE);
        }

        if (getPaddingTop() == 0) {
            if (getPaddingBottom() == 0) {
                setPadding(getPaddingLeft(), mPaddingTop = SmartUtil.dp2px(12), getPaddingRight(), mPaddingBottom = SmartUtil.dp2px(12));
            } else {
                setPadding(getPaddingLeft(), mPaddingTop = SmartUtil.dp2px(12), getPaddingRight(), mPaddingBottom = getPaddingBottom());
            }
        } else {
            if (getPaddingBottom() == 0) {
                setPadding(getPaddingLeft(), mPaddingTop = getPaddingTop(), getPaddingRight(), mPaddingBottom = SmartUtil.dp2px(12));
            } else {
                mPaddingTop = getPaddingTop();
                mPaddingBottom = getPaddingBottom();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
        } else {
            setPadding(getPaddingLeft(), mPaddingTop, getPaddingRight(), mPaddingBottom);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {
        mRefreshKernel = kernel;
        mRefreshKernel.requestDrawBackgroundFor(this, mBackgroundColor);
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        if (isDragging) {
            final float progress;
            if(percent <= 0.5) {
                progress = -1f;
            } else {
                progress = Math.min(1f, (percent-0.5f)*2);
            }

            mProgressView.setProgress(progress);
        }
        Log.d("ctrun", "percent=" + percent + ",offset=" + offset + ",height=" + height + ",maxDragHeight=" + maxDragHeight);
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout layout, int height, int extendHeight) {
        mProgressView.startAnimation(mRotateAnimation);
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        mProgressView.clearAnimation();
        mProgressView.setProgress(0);

        //延迟弹回(单位：毫秒)
        return mFinishDuration;
    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {
        if (colors.length > 0) {
            if (!(getBackground() instanceof BitmapDrawable)) {
                setPrimaryColor(colors[0]);
            }
        }
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        mRefreshState = newState;
    }

    @SuppressWarnings("UnusedReturnValue")
    public CommonRefreshHeader setPrimaryColor(@ColorInt int primaryColor) {
        setBackgroundColor(mBackgroundColor = primaryColor);
        if (mRefreshKernel != null) {
            mRefreshKernel.requestDrawBackgroundFor(this, mBackgroundColor);
        }
        return this;
    }

    public CommonRefreshHeader setSpinnerStyle(SpinnerStyle style) {
        this.mSpinnerStyle = style;
        return this;
    }

    public CommonRefreshHeader setFinishDuration(int delay) {
        mFinishDuration = delay;
        return this;
    }
}
