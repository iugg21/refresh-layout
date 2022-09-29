package com.ctrun.view.cateye.refresh.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.ctrun.view.cateye.refresh.R;

/**
 * @author ctrun
 * loadingView 控件
 */
public class LoadingView extends FrameLayout {

    private View mLoadingView;
    private final Animation mRotateAnimation;
    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //在构造方法的super之后执行
    {
        inflate(getContext(), R.layout.common_loading_view, this);
        mRotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.common_rotate_anim_loading);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mLoadingView = findViewById(R.id.iv_loading);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mLoadingView.startAnimation(mRotateAnimation);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mLoadingView.clearAnimation();
    }

}
