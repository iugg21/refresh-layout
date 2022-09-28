package com.ctrun.view.cateye.refresh.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Dimension;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ctrun.view.cateye.refresh.R;

/**
 * @author ctrun on 2020/11/16.
 */
public class HomeRefreshProgressView extends View {

    private Paint mSecondProgressPaint;
    private Paint mProgressPaint;
    private final RectF mRectF = new RectF();

    private int mProgress;

    public HomeRefreshProgressView(Context context) {
        this(context, null);
    }

    public HomeRefreshProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RefreshProgressView, 0, 0);
        mProgress = a.getColor(R.styleable.RefreshProgressView_progress, 15);

        int progressColor = a.getColor(R.styleable.RefreshProgressView_progressColor, ContextCompat.getColor(getContext(), R.color.white));
        float progressStrokeWidth = a.getDimension(R.styleable.RefreshProgressView_progressStrokeWidth, dpToPx(1));

        int secondProgressColor = a.getColor(R.styleable.RefreshProgressView_secondProgressColor, Color.parseColor("#CECDD2"));
        float secondProgressStrokeWidth = a.getDimension(R.styleable.RefreshProgressView_secondProgressStrokeWidth, dpToPx(1));
        a.recycle();


        mSecondProgressPaint = new Paint();
        mSecondProgressPaint.setColor(secondProgressColor);
        mSecondProgressPaint.setStyle(Paint.Style.STROKE);
        //防抖动
        mSecondProgressPaint.setDither(true);
        //抗锯齿
        mSecondProgressPaint.setAntiAlias(true);
        mSecondProgressPaint.setStrokeWidth(secondProgressStrokeWidth);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(progressColor);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        //防抖动
        mProgressPaint.setDither(true);
        //抗锯齿
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStrokeWidth(progressStrokeWidth);
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float strokeWidth = Math.max(mSecondProgressPaint.getStrokeWidth(), mProgressPaint.getStrokeWidth()) + 1;

        mRectF.set(strokeWidth/2, strokeWidth/2, w - strokeWidth/2, h - strokeWidth/2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int defaultSize = getResources().getDimensionPixelOffset(R.dimen.home_refresh_loading_size);

        final int width = resolveSize(defaultSize, widthMeasureSpec);
        final int height = resolveSize(defaultSize, heightMeasureSpec);
        final int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float sweepAngle = Math.max(90, 270 * mProgress / 100f);
        canvas.drawArc(mRectF, -90, sweepAngle, false, mProgressPaint);
    }

    int dpToPx(@Dimension(unit = 0) int dps) {
        return Math.round(Resources.getSystem().getDisplayMetrics().density * (float)dps);
    }
}
