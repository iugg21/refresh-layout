package com.ctrun.view.cateye.refresh.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.ctrun.view.cateye.refresh.R;
import com.ctrun.view.cateye.refresh.util.ImageSizeUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ctrun on 2022/8/4.
 */
public class QuarterImageView extends AppCompatImageView {

    public QuarterImageView(@NonNull Context context) {
        super(context);
    }

    public QuarterImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public QuarterImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * *************************** 对外接口 **********************************
     */

    public void loadAsUrls(List<String> urls) {
        loadAsUrls(urls, R.drawable.round_bg, R.drawable.image_mask);
    }

    public void loadAsUrls(List<String> urls, final int maskId, final int coverMaskId) {
        if (urls == null || urls.isEmpty()) {
            return;
        }

        setBlendDrawable(maskId);
        setCoverDrawable(coverMaskId);

        ArrayList<Observable<Drawable>> observables = new ArrayList<>();
        for (int i=0; i < urls.size(); i++) {
            final int index = i;
            Observable<Drawable> observable =
                    Observable.defer(() -> {
                        String imgUrl = ImageSizeUtils.makeSmallUrlSquare(urls.get(index), 480, "@345w_480h_1e_1c_1l", "?imageView2/1/w/345/h/480");

                        FutureTarget<Drawable> future = Glide.with(getContext())
                                .asDrawable()
                                .load(imgUrl)
                                .submit();
                        Drawable drawable = future.get();
                        return Observable.just(drawable);
                    }).subscribeOn(Schedulers.io());

            observables.add(observable);
        }

        Disposable disposable =
        Observable.zip(observables.get(0), observables.get(1), observables.get(2), observables.get(3), (drawable, drawable2, drawable3, drawable4) -> {
            ArrayList<Drawable> drawables = new ArrayList<>();
            drawables.add(drawable);
            drawables.add(drawable2);
            drawables.add(drawable3);
            drawables.add(drawable4);
            return drawables;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(drawables -> {
                    setDrawablesWithIntrinsicBounds(drawables.get(0), drawables.get(1), drawables.get(2), drawables.get(3));
                }, throwable -> {

                });
    }

    private Drawable[] mDrawables;
    public void setDrawablesWithIntrinsicBounds(Drawable topLeft, Drawable topRight, Drawable bottomLeft, Drawable bottomRight) {
        mDrawables = new Drawable[4];
        mDrawables[0] = topLeft;
        mDrawables[1] = topRight;
        mDrawables[2] = bottomLeft;
        mDrawables[3] = bottomRight;

        post(() -> {
            mDrawables[0].setBounds(0, 0, getWidth()/2, getHeight()/2);
            mDrawables[1].setBounds(getWidth()/2, 0, getWidth(), getHeight()/2);
            mDrawables[2].setBounds(0, getHeight()/2, getWidth()/2, getHeight());
            mDrawables[3].setBounds(getWidth()/2, getHeight()/2, getWidth(), getHeight());
            invalidate();
        });
    }

    private Drawable mask; // blend mask drawable
    private Drawable maskCover;

    private static final Paint paintMask = createMaskPaint();

    private static Paint createMaskPaint() {
        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawables == null) {
            return;
        }

        // bounds
        int width = getWidth();
        int height = getHeight();

        if (mask != null) {
            // create blend layer
            canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);

            //
            // mask
            //
            if (mask != null) {
                mask.setBounds(0, 0, width, height);
                mask.draw(canvas);
            }

            //
            // source
            //
            {
                canvas.saveLayer(0, 0, width, height, paintMask, Canvas.ALL_SAVE_FLAG);
                super.onDraw(canvas);
                for (Drawable drawable : mDrawables) {
                    if (drawable != null) {
                        drawable.draw(canvas);
                    }
                }

                if (maskCover != null) {
                    maskCover.setBounds(0, 0, width, height);
                    maskCover.draw(canvas);
                }

                canvas.restore();
            }

            // apply blend layer
            canvas.restore();
        } else {
            super.onDraw(canvas);

            for (Drawable drawable : mDrawables) {
                if (drawable != null) {
                    drawable.draw(canvas);
                }
            }

            if (maskCover != null) {
                maskCover.setBounds(0, 0, width, height);
                maskCover.draw(canvas);
            }
        }
    }

    private void setBlendDrawable(int maskId) {
        mask = maskId > 0 ? ContextCompat.getDrawable(getContext(), maskId): null;
    }

    private void setCoverDrawable(int maskId) {
        maskCover = maskId > 0 ? ContextCompat.getDrawable(getContext(), maskId) : null;
    }
}
