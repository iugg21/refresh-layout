package com.ctrun.view.cateye.refresh.ui.home;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ctrun.view.cateye.refresh.R;
import com.ctrun.view.cateye.refresh.listener.CommonObservable;
import com.ctrun.view.cateye.refresh.listener.Observer;
import com.ctrun.view.cateye.refresh.util.UIUtils;
import com.ctrun.view.cateye.refresh.util.ViewUtils;
import com.ctrun.view.cateye.refresh.widget.IndicatorView;

import java.util.ArrayList;

/**
 * @author ctrun on 2021/6/5
 */
@SuppressWarnings("FieldCanBeLocal")
public class HeadBannerArea {
    private final AppCompatActivity mContext;
    private final LayoutInflater mInflater;

    private final View mHeadBanner;
    private final ConvenientBanner<BannerDataBean> mBanner;
    private final View mBannerBackground;
    private final IndicatorView mIndicatorView;

    private final ArrayList<BannerDataBean> mBannerData = new ArrayList<>();
    private boolean mEnableColorChange = true;
    @SuppressLint("InflateParams")
    public HeadBannerArea(AppCompatActivity context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

        mHeadBanner = mInflater.inflate(R.layout.home_head_banner, null);
        mHeadBanner.setVisibility(View.GONE);
        mBannerBackground = mHeadBanner.findViewById(R.id.v_background);
        mBanner = mHeadBanner.findViewById(R.id.banner);
        mIndicatorView = mHeadBanner.findViewById(R.id.indicator_view);
        ViewUtils.setViewCornerRadius(mBanner, UIUtils.dp2px(mContext, 7));
        mBanner.setOnPageChangeListener(mBannerPageChangeListener);

        registerObservers(true);
    }

    private final ArgbEvaluator mColorEvaluator = new ArgbEvaluator();
    private RecyclerView mRecyclerView;
    public void attachToRecyclerView(RecyclerView recyclerView) {
        if (mRecyclerView != null) {
            mRecyclerView.removeOnScrollListener(mScrollListener);
        }

        mRecyclerView = recyclerView;
        if (recyclerView != null) {
            mRecyclerView.addOnScrollListener(mScrollListener);
        }
    }

    RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                stopBannerLoop();
            } else {
                startBannerLoop();
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            if (mBannerData.isEmpty()) {
                return;
            }

            if (mRecyclerView.canScrollVertically(-1)) {
                CommonObservable.getInstance().notifyColorChangedObservers(Color.WHITE);
                CommonObservable.getInstance().notifyLightModeChangedObservers(true);
            } else {
                CommonObservable.getInstance().notifyLightModeChangedObservers(false);

                int currentPosition = mBanner.getCurrentItem();
                BannerDataBean bean = mBannerData.get(currentPosition);
                int color = Color.parseColor(bean.backgroundColor);

                CommonObservable.getInstance().notifyColorChangedObservers(color);
            }
        }
    };

    public View getView() {
        return mHeadBanner;
    }

    public void displayData() {
        mBannerData.clear();
        BannerDataBean bean1 = new BannerDataBean("http://p0.meituan.net/movie/adad4c61fab41569e0c76413cb548a5a151819.jpg", "#3E4B66", BANNER_TYPE_H5, "https://eldermovie.maoyan.com/elderMovie/index", -1);
        BannerDataBean bean2 = new BannerDataBean("http://p0.meituan.net/movie/2adb8be53f7af900be80d23ff54f6c1e143718.jpg", "#662C29");
        BannerDataBean bean3 = new BannerDataBean("http://p0.meituan.net/movie/f8a2c843b2d455b7e86452a98866ba62110558.jpg", "#665929");
        BannerDataBean bean4 = new BannerDataBean("http://p1.meituan.net/movie/0b46f1589f8027e950650dbb973b1edf84459.jpg", "#662C29", BANNER_TYPE_H5, "https://i.meituan.com/awp/hfe/block/a107ed271e8c/111994/index.html", -1);
        BannerDataBean bean5 = new BannerDataBean("http://p0.meituan.net/movie/b6d0ed0cfa23027b548e0701042f49fe182494.jpg", "#66643E");
        BannerDataBean bean6 = new BannerDataBean("http://p1.meituan.net/movie/49a3fb89a539bdd7fba9219689361278142120.jpg", "#3C6366");
        BannerDataBean bean7 = new BannerDataBean("http://p0.meituan.net/movie/92e41c6063db58fda00e9b70a968530d129043.jpg", "#662C29");
        BannerDataBean bean8 = new BannerDataBean("http://p0.meituan.net/movie/e74c400425aa4649bade4032a0bceb33254339.jpg", "#3E5866");
        BannerDataBean bean9 = new BannerDataBean("http://p0.meituan.net/movie/b34c8ca5171a9f8e4c5137b667558ec4100099.jpg", "#66513E");
        BannerDataBean bean10 = new BannerDataBean("http://p0.meituan.net/movie/3aa227aa436bb88ff2fd727a058c63f2198679.jpg", "#662944", BANNER_TYPE_H5, "https://i.meituan.com/awp/hfe/block/09533100dcd0aceb589f/132782/index.html", -1);
        mBannerData.add(bean1);
        mBannerData.add(bean2);
        mBannerData.add(bean3);
        mBannerData.add(bean4);
        mBannerData.add(bean5);
        mBannerData.add(bean6);
        mBannerData.add(bean7);
        mBannerData.add(bean8);
        mBannerData.add(bean9);
        mBannerData.add(bean10);

        mBanner.setPages(BannerImageHolder::new, mBannerData);
        mHeadBanner.setVisibility(View.VISIBLE);
        mIndicatorView.attachToViewPager(mBanner);

        if (mContext.getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
            startBannerLoop();
        }
        CommonObservable.getInstance().notifyLightModeChangedObservers(false);
    }

    public void startBannerLoop() {
        if(mBanner != null) {
            mBanner.startTurning(3000);
        }
    }

    public void stopBannerLoop() {
        if(mBanner != null) {
            mBanner.stopTurning();
        }
    }

    ViewPager.SimpleOnPageChangeListener mBannerPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);

            if (mRecyclerView != null && mRecyclerView.canScrollVertically(-1)) {
                return;
            }

            if (!mEnableColorChange) {
                return;
            }

            int currentPosition = mBanner.getViewPager().getAdapter().toRealPosition(position);
            int nextPosition = mBanner.getViewPager().getAdapter().toRealPosition(position + 1);
            BannerDataBean bean = mBannerData.get(currentPosition);
            BannerDataBean nextBean = mBannerData.get(nextPosition);
            int startBackgroundColor = Color.parseColor(bean.backgroundColor);
            int endBackgroundColor = Color.parseColor(nextBean.backgroundColor);

            int color = (int) mColorEvaluator.evaluate(positionOffset, startBackgroundColor, endBackgroundColor);
            CommonObservable.getInstance().notifyColorChangedObservers(color);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);

            if (mRecyclerView != null && mRecyclerView.canScrollVertically(-1)) {
                return;
            }

            if (!mEnableColorChange) {
                return;
            }

            BannerDataBean bean = mBannerData.get(position);
            int color = Color.parseColor(bean.backgroundColor);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);

            if (mRecyclerView != null && mRecyclerView.canScrollVertically(-1)) {
                return;
            }

            if (!mEnableColorChange) {
                return;
            }

            if (state == ViewPager.SCROLL_STATE_IDLE) {
                int currentPosition = mBanner.getCurrentItem();
                BannerDataBean bean = mBannerData.get(currentPosition);
                int color = Color.parseColor(bean.backgroundColor);

                CommonObservable.getInstance().notifyColorChangedObservers(color);
            }
        }

    };

    public void destroy() {
        registerObservers(false);
    }

    /**
     * ************************* 观察者 ********************************
     */
    private void registerObservers(boolean register) {
        CommonObservable.getInstance().registerColorChangedObserver(mColorChangedObserver, register);
    }

    /**
     * 颜色变化观察者
     */
    private final Observer<Integer> mColorChangedObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer color) {
            mBannerBackground.setBackgroundColor(color);
        }
    };


    private class BannerImageHolder implements Holder<BannerDataBean> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setOnClickListener(v -> {
            });
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerDataBean data) {
            Glide.with(context)
                    .load(data.imageUrl)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.common_bg_default_image)
                            .dontAnimate())
                    .into(imageView);
        }
    }

    static final int BANNER_TYPE_NONE = -1;
    static final int BANNER_TYPE_H5 = 1;
    static final int BANNER_TYPE_MOVIE = 2;
    static class BannerDataBean {
        String imageUrl;
        String backgroundColor;
        int type = BANNER_TYPE_NONE;
        String url;
        int movieId;
        BannerDataBean(String imageUrl, String backgroundColor) {
            this.imageUrl = imageUrl;
            this.backgroundColor = backgroundColor;
        }

        BannerDataBean(String imageUrl, String backgroundColor, int type, String url, int movieId) {
            this(imageUrl, backgroundColor);
            this.type = type;
            this.url = url;
            this.movieId = movieId;
        }

    }

}
