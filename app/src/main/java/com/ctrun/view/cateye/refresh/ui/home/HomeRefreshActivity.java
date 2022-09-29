package com.ctrun.view.cateye.refresh.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ctrun.view.cateye.refresh.R;
import com.ctrun.view.cateye.refresh.bean.BaseResponse;
import com.ctrun.view.cateye.refresh.bean.ExpectMovieBean;
import com.ctrun.view.cateye.refresh.bean.HomeHeadData;
import com.ctrun.view.cateye.refresh.bean.HotMovieListBean;
import com.ctrun.view.cateye.refresh.bean.MovieShowBean;
import com.ctrun.view.cateye.refresh.bean.VideoListBean;
import com.ctrun.view.cateye.refresh.databinding.ActivityRefreshHomeBinding;
import com.ctrun.view.cateye.refresh.listener.CommonObservable;
import com.ctrun.view.cateye.refresh.listener.Observer;
import com.ctrun.view.cateye.refresh.ui.MovieDetailActivity;
import com.ctrun.view.cateye.refresh.ui.home.adapter.RecommendContentAdapter;
import com.ctrun.view.cateye.refresh.util.JsonFileUtils;
import com.ctrun.view.cateye.refresh.util.MovieVersionHelper;
import com.ctrun.view.cateye.refresh.util.StatusBarUtils;
import com.ctrun.view.cateye.refresh.widget.HomeRefreshHeader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ctrun on 2022/8/4.
 * 首页下拉刷新，继续下拉进入二楼组件
 */
public class HomeRefreshActivity extends AppCompatActivity implements OnRefreshListener {

    public static void start(Context context) {
        Intent intent = new Intent(context, HomeRefreshActivity.class);
        context.startActivity(intent);
    }

    private ActivityRefreshHomeBinding mBinding;

    private RecommendContentAdapter mAdapter;
    private HeadArea mHeadArea;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this, true);

        mBinding = ActivityRefreshHomeBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        registerObservers(true);

        initSearchHot();
        initRefreshLayout();

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //防止Item切换
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mBinding.rvList.setLayoutManager(layoutManager);
        mBinding.rvList.addItemDecoration(new StaggeredDividerItemDecoration(this, 15, 10, 2));
        mAdapter = new RecommendContentAdapter();
        mBinding.rvList.setAdapter(mAdapter);

        mHeadArea = new HeadArea(this);
        mHeadArea.attachToRecyclerView(mBinding.rvList);

        requestLocalData();

        Glide.with(this).load("http://p1.meituan.net/movie/fdb20c8dc0c4061defd6a5a5cc16ea703124.png.webp@66w_80h_1e_1l_1c").into(mBinding.toolbarActionbar.ivLicense);

        Glide.with(this)
                .load("https://p0.meituan.net/movie/a26b335e46bfccf982bdd672a8df8992169307.jpg")
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return true;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                runOnUiThread(() -> mBinding.ivSecondFloorBg.setImageDrawable(resource));
                                return true;
                            }
                        }).submit();
    }

    @Override
    protected void onDestroy() {
        registerObservers(false);
        mHeadArea.destroy();
        super.onDestroy();
    }

    void initRefreshLayout() {
        HomeRefreshHeader refreshHeader = new HomeRefreshHeader(this);
        refreshHeader.setOnTwoLevelListener(refreshLayout -> {
            MovieDetailActivity.start(HomeRefreshActivity.this);
            overridePendingTransition(0, 0);
            return false;
        });
        mBinding.refreshLayout.setRefreshHeader(refreshHeader);
        mBinding.refreshLayout.setOnRefreshListener(this);

        mBinding.refreshLayout.post(() -> {
            int maxDragRate = (mBinding.refreshLayout.getHeight() / 2) / refreshHeader.getView().getHeight();
            if (maxDragRate > 1) {
                mBinding.refreshLayout.setHeaderMaxDragRate(maxDragRate);
            }
        });

        mBinding.refreshLayout.setOnMultiListener(new SimpleMultiListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                mBinding.toolbarActionbar.getRoot().setAlpha(1 - Math.min(percent * 2, 1));
            }
        });

        mBinding.refreshLayout.setEnableOverScrollBounce(false);
        mBinding.refreshLayout.setEnableOverScrollDrag(false);
        mBinding.refreshLayout.setEnableLoadMore(false);
        mBinding.refreshLayout.setEnableNestedScroll(false);
        mBinding.refreshLayout.setEnableRefresh(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mBinding.refreshLayout.postDelayed(() -> mBinding.refreshLayout.finishRefresh(), 1000 + new Random().nextInt(500));
    }

    void initSearchHot() {
        Animation outTop = AnimationUtils.loadAnimation(this, R.anim.home_search_slide_out_to_top);
        Animation inBottom = AnimationUtils.loadAnimation(this, R.anim.home_search_slide_in_from_bottom);
        mBinding.toolbarActionbar.vfSearchHot.setInAnimation(inBottom);
        mBinding.toolbarActionbar.vfSearchHot.setOutAnimation(outTop);

        if(mBinding.toolbarActionbar.vfSearchHot.isFlipping()) {
            mBinding.toolbarActionbar.vfSearchHot.stopFlipping();
        }
        for(int i=0; i<mBinding.toolbarActionbar.vfSearchHot.getChildCount(); i++) {
            ((TextView)mBinding.toolbarActionbar.vfSearchHot.getChildAt(i)).setText("");
        }
        mBinding.toolbarActionbar.vfSearchHot.removeAllViews();

        final String[] hotSearchData = {"速度与激情9", "阳光姐妹淘", "战狼2"};
        LayoutInflater layoutInflater = getLayoutInflater();
        for(String hot : hotSearchData) {
            TextView tv = (TextView) layoutInflater.inflate(R.layout.home_actionbar_search_hot_item, null);
            tv.setText(hot);
            tv.setTag(hot);
            //tv.setOnClickListener(mClickHotSearch);
            mBinding.toolbarActionbar.vfSearchHot.addView(tv);
        }
        mBinding.toolbarActionbar.vfSearchHot.startFlipping();
    }

    @Override
    public void onResume() {
        super.onResume();

        mBinding.toolbarActionbar.vfSearchHot.startFlipping();
        if (mHeadArea != null) {
            mHeadArea.startBannerLoop();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mHeadArea != null) {
            mHeadArea.stopBannerLoop();
        }

        if(mBinding.toolbarActionbar.vfSearchHot.isFlipping()) {
            mBinding.toolbarActionbar.vfSearchHot.stopFlipping();

            for(int i=0, count=mBinding.toolbarActionbar.vfSearchHot.getChildCount(); i<count; i++) {
                mBinding.toolbarActionbar.vfSearchHot.getChildAt(i).clearAnimation();
            }
        }
    }

    /**
     * ************************* 观察者 ********************************
     */
    private void registerObservers(boolean register) {
        CommonObservable.getInstance().registerColorChangedObserver(mColorChangedObserver, register);
        CommonObservable.getInstance().registerLightModeChangedObserver(mLightModeChangedObserver, register);
    }

    /**
     * 颜色变化观察者
     */
    private final Observer<Integer> mColorChangedObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer color) {
            mBinding.toolbarActionbar.getRoot().setBackgroundColor(color);
        }
    };

    /**
     * 亮色 or 暗色模式变化观察者
     */
    private final Observer<Boolean> mLightModeChangedObserver = new Observer<Boolean>() {

        @Override
        public void onEvent(Boolean lightMode) {
            if (isFinishing()) {
                return;
            }

            if (lightMode) {
                StatusBarUtils.setStatusBarMode(HomeRefreshActivity.this, true);
                mBinding.toolbarActionbar.tvCity.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.common_font_black_333));
                Drawable arrowDownRightDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_arrow_down_small_solid);
                mBinding.toolbarActionbar.tvCity.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDownRightDrawable, null);

                mBinding.toolbarActionbar.getRoot().setElevation(getResources().getDimensionPixelOffset(R.dimen.home_actionbar_elevation_size));
            } else {
                StatusBarUtils.setStatusBarMode(HomeRefreshActivity.this, false);
                mBinding.toolbarActionbar.tvCity.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.common_font_white));
                Drawable arrowDownRightDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_arrow_down_small_solid);
                Drawable wrappedArrowDownRightDrawable = DrawableCompat.wrap(arrowDownRightDrawable.mutate());
                DrawableCompat.setTint(wrappedArrowDownRightDrawable, ContextCompat.getColor(getApplicationContext(), R.color.white));
                mBinding.toolbarActionbar.tvCity.setCompoundDrawablesWithIntrinsicBounds(null, null, wrappedArrowDownRightDrawable, null);

                mBinding.toolbarActionbar.getRoot().setElevation(0);
            }
        }
    };


    private void requestLocalData() {
        mBinding.loadingView.setVisibility(View.VISIBLE);
        Observable<HomeHeadData> observable =
                Observable.defer((Callable<ObservableSource<HomeHeadData>>) () -> {
                    String jsonDataHotMovie = JsonFileUtils.readJsonFromAsset(this, "cateye_movie_hot.json");
                    HotMovieListBean bean = new Gson().fromJson(jsonDataHotMovie, HotMovieListBean.class);
                    for (HotMovieListBean.DataBean.HotBean hotBean : bean.data.hot) {
                        hotBean.cinemaTypeIds = MovieVersionHelper.makeMovieVerDrawableIds(hotBean.ver, hotBean.preShow, hotBean.isRevival);
                    }

                    String jsonDataMovieWait = JsonFileUtils.readJsonFromAsset(this, "cateye_movie_wait.json");
                    ExpectMovieBean expectMovieBean = new Gson().fromJson(jsonDataMovieWait, ExpectMovieBean.class);

                    String jsonDataMovieShow = JsonFileUtils.readJsonFromAsset(this, "cateye_movie_show.json");
                    BaseResponse<List<MovieShowBean>> movieShowResponse = new Gson().fromJson(jsonDataMovieShow, new TypeToken<BaseResponse<List<MovieShowBean>>>(){}.getType());

                    String jsonDataRecommend = JsonFileUtils.readJsonFromAsset(this, "cateye_recommend_content.json");
                    VideoListBean recommendBean = new Gson().fromJson(jsonDataRecommend, VideoListBean.class);

                    HomeHeadData data = new HomeHeadData();
                    data.hotMovieListBean = bean;
                    data.expectMovieBean = expectMovieBean;
                    data.movieShowListBean = movieShowResponse.data;
                    data.videoListBean = recommendBean;
                    return Observable.just(data);
                });

        //noinspection CheckResult
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(headData -> {
                    mBinding.refreshLayout.setEnableRefresh(true);
                    mBinding.loadingView.setVisibility(View.GONE);
                    mHeadArea.displayHeadData(headData.hotMovieListBean.data, null, headData.expectMovieBean.data, headData.movieShowListBean);
                    mAdapter.setNewData(headData.videoListBean.data.feeds);
                });
    }
}
