package com.ctrun.view.cateye.refresh.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.adapter.CBPageAdapter;

/**
 * @author ctrun on 2022/7/25.
 * 轮播图指示器
 */
public class IndicatorView extends View {
    private static final String TAG = "IndicatorView";

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int mItemCount;
    private int mWidth;
    private int mHeight;
    private final Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private void init() {
        mBackgroundPaint.setColor(Color.parseColor("#60ffffff"));
        mPaint.setColor(Color.WHITE);

        mWidth = dipsToPixels(100);
        mHeight = dipsToPixels(3);
    }

    private void setItemCount(int itemCount) {
        mItemCount = itemCount;
        mWidth = (mItemCount <= 4 ? dipsToPixels(40) : dipsToPixels(100));

        if (mItemCount <= 1) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }

        requestLayout();
    }

    private CBPageAdapter<?> mPageAdapter;
    public void attachToViewPager(ConvenientBanner<?> banner) {
        banner.getViewPager().removeOnPageChangeListener(mPageChangeListener);

        mPageAdapter = banner.getViewPager().getAdapter();
        setItemCount(mPageAdapter == null ? 0 : mPageAdapter.getRealCount());
        banner.getViewPager().addOnPageChangeListener(mPageChangeListener);
    }

    private final ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);

            int realPosition = mPageAdapter.toRealPosition(position);
            setProgress(realPosition + 1, positionOffset);

            Log.d(TAG, "onPageScrolled ----> position: " + realPosition + ",positionOffset: " + positionOffset + ",positionOffsetPixels: " + positionOffsetPixels);
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSize(mWidth, widthMeasureSpec);
        int height = resolveSize(mHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int mPosition;
    private float mProgress;
    public void setProgress(int position, float progress) {
        mPosition = position;
        mProgress = Math.abs(progress);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "position: " + mPosition + ",progress: " + mProgress);

        if (mItemCount == 0) {
            return;
        }

        final int width = getWidth();
        final int height = getHeight();

        final float itemWidth = (float) width / mItemCount;

        //绘制背景
        canvas.drawRect(0, 0, width, height, mBackgroundPaint);

        if (mPosition == 0) {
            canvas.drawRect(0, 0, itemWidth * mProgress, height, mPaint);
        } else {
            canvas.drawRect((mPosition - 1) * itemWidth + mProgress * itemWidth, 0, mPosition * itemWidth + itemWidth * mProgress, height, mPaint);
        }
        if (mPosition == mItemCount) {
            canvas.drawRect(0, 0, itemWidth * mProgress, height, mPaint);
        }
    }



    private int dipsToPixels(int dips) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dips * scale + 0.5f);
    }
}
