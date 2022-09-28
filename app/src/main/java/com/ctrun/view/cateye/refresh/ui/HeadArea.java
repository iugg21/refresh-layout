package com.ctrun.view.cateye.refresh.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ctrun.view.cateye.refresh.R;
import com.ctrun.view.cateye.refresh.bean.ExpectMovieBean;
import com.ctrun.view.cateye.refresh.bean.HotMovieListBean;
import com.ctrun.view.cateye.refresh.bean.MovieShowBean;
import com.ctrun.view.cateye.refresh.bean.WaitMovieBean;
import com.ctrun.view.cateye.refresh.util.UIUtils;
import com.ctrun.view.cateye.refresh.widget.QuarterImageView;

import java.util.List;
import java.util.Locale;

/**
 * @author ctrun on 2021/6/5
 */
public class HeadArea {
    private final Context mContext;
    private final HeadBannerArea mBannerArea;
    private final HeadMenuArea mMenuArea;
    private final HeadHotMovieArea mHotMovieArea;
    private final HeadWaitMovieArea mWaitMovieArea;
    private final HeadMovieShowArea mPerformArea;

    public HeadArea(AppCompatActivity context) {
        mContext = context;
        mBannerArea = new HeadBannerArea(context);
        mMenuArea = new HeadMenuArea(context);
        mHotMovieArea = new HeadHotMovieArea(context);
        mWaitMovieArea = new HeadWaitMovieArea(context);
        mPerformArea = new HeadMovieShowArea(context);
    }

    public void attachToRecyclerView(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return;
        }

        mBannerArea.attachToRecyclerView(recyclerView);

        if (recyclerView.getAdapter() instanceof BaseQuickAdapter) {
            //noinspection rawtypes
            BaseQuickAdapter adapter = (BaseQuickAdapter) recyclerView.getAdapter();

            if (adapter.getHeaderLayoutCount() == 0) {
                if (mBannerArea.getView() != null) {
                    adapter.addHeaderView(mBannerArea.getView());
                }

                if (mMenuArea.getView() != null) {
                    adapter.addHeaderView(mMenuArea.getView());
                }

                if (mHotMovieArea.getView() != null) {
                    adapter.addHeaderView(mHotMovieArea.getView());
                }

                if (mWaitMovieArea.getView() != null) {
                    adapter.addHeaderView(mWaitMovieArea.getView());
                }

                if (mPerformArea.getView() != null) {
                    adapter.addHeaderView(mPerformArea.getView());
                }

                Space headBottomSpace = new Space(mContext);
                headBottomSpace.setLayoutParams(new ViewGroup.LayoutParams(0, UIUtils.dp2px(mContext, 10)));
                adapter.addHeaderView(headBottomSpace);
            }
        }
    }

    public void displayHeadData(HotMovieListBean.DataBean hotData, WaitMovieBean.DataBean comingData, ExpectMovieBean.DataBean waitData, List<MovieShowBean> movieShowData) {
        mBannerArea.displayData();
        mMenuArea.displayData();
        mHotMovieArea.displayData(hotData);
        mWaitMovieArea.displayData(comingData, waitData);
        mPerformArea.displayData(movieShowData);
    }

    public void startBannerLoop() {
        mBannerArea.startBannerLoop();
    }

    public void stopBannerLoop() {
        mBannerArea.stopBannerLoop();
    }

    @SuppressWarnings("unused")
    @SuppressLint("InflateParams")
    public static View createFootSeeAll(Context context, List<String> imgUrls, int totalCount) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_foot_see_all, null);
        QuarterImageView ivSeeAll = view.findViewById(R.id.iv_see_all);
        TextView tvHotCount = view.findViewById(R.id.tv_count);
        tvHotCount.setText(String.format(Locale.getDefault(), "%d部", totalCount));
        ivSeeAll.loadAsUrls(imgUrls);

        return view;
    }

    public void destroy() {
        mBannerArea.destroy();
    }
}
