package com.ctrun.view.cateye.refresh.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ctrun.view.cateye.refresh.R;

/**
 * @author ctrun on 2018/2/27.
 */
@SuppressLint("AppCompatCustomView")
public class RefreshProgressView extends View {

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;
    private Drawable mDrawable;
    private float mCurrentProgress;

    public RefreshProgressView(Context context) {
        this(context, null);
    }

    public RefreshProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_loading_round);
        if (mDrawable != null) {
            mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        }
    }

    public void setProgress(float progress) {
        mCurrentProgress = Math.max(0, progress);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSize(mDrawable.getIntrinsicWidth(), widthMeasureSpec);
        int height = resolveSize(mDrawable.getIntrinsicHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawable == null) {
            return;
        }

        final int height = getHeight();
        final int currentHeight = height - (int) (height * mCurrentProgress);
        canvas.clipRect(0, currentHeight, getWidth(), height);
        mDrawable.draw(canvas);
    }
}
