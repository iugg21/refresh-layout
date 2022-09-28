package com.ctrun.view.cateye.refresh.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ctrun.view.cateye.refresh.R;
import com.ctrun.view.cateye.refresh.bean.ExpectMovieBean;
import com.ctrun.view.cateye.refresh.bean.WaitMovieBean;
import com.ctrun.view.cateye.refresh.ui.adapter.WaitComingAdapter;
import com.ctrun.view.cateye.refresh.ui.adapter.WaitRecommendAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author ctrun on 2021/6/5
 */
@SuppressWarnings("FieldCanBeLocal")
public class HeadWaitMovieArea {
    private static final int MAX_COUNT = 16;

    private Context mContext;
    private LayoutInflater mInflater;

    private View mHeadWaitMovie;
    private TextView mTvWaitRecommend;
    private TextView mTvWaitComing;
    private TextView mTvWaitCount;
    private ImageView mIvWaitCount;
    private RecyclerView mRvWaitRecommend;
    private RecyclerView mRvWaitComing;
    private WaitRecommendAdapter mWaitRecommendAdapter;
    private WaitComingAdapter mWaitComingAdapter;

    @SuppressLint("InflateParams")
    public HeadWaitMovieArea(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

        mHeadWaitMovie = mInflater.inflate(R.layout.home_head_wait_movie, null);
        mHeadWaitMovie.setVisibility(View.GONE);
        mTvWaitRecommend = mHeadWaitMovie.findViewById(R.id.tv_wait_recommend);
        mTvWaitComing = mHeadWaitMovie.findViewById(R.id.tv_wait_coming);
        mTvWaitCount = mHeadWaitMovie.findViewById(R.id.tv_wait_count);
        mIvWaitCount = mHeadWaitMovie.findViewById(R.id.iv_wait_count);
        mIvWaitCount.setOnClickListener(v -> mTvWaitCount.performClick());
        mRvWaitRecommend = mHeadWaitMovie.findViewById(R.id.rv_wait_recommend);
        mRvWaitComing = mHeadWaitMovie.findViewById(R.id.rv_wait_coming);
        mWaitRecommendAdapter = new WaitRecommendAdapter();
        mWaitComingAdapter = new WaitComingAdapter();
        mRvWaitRecommend.setAdapter(mWaitRecommendAdapter);
        mRvWaitComing.setAdapter(mWaitComingAdapter);

        mTvWaitRecommend.setOnClickListener(v -> {
            mTvWaitRecommend.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); //加粗
            mTvWaitRecommend.setTextColor(ContextCompat.getColor(mContext, R.color.common_font_black));
            mTvWaitComing.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            mTvWaitComing.setTextColor(ContextCompat.getColor(mContext, R.color.common_font_gray_deep));

            mRvWaitComing.setVisibility(View.GONE);
            mRvWaitRecommend.setVisibility(View.VISIBLE);
        });
        mTvWaitComing.setOnClickListener(v -> {
            mTvWaitRecommend.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            mTvWaitRecommend.setTextColor(ContextCompat.getColor(mContext, R.color.common_font_gray_deep));
            mTvWaitComing.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            mTvWaitComing.setTextColor(ContextCompat.getColor(mContext, R.color.common_font_black));

            mRvWaitComing.setVisibility(View.VISIBLE);
            mRvWaitRecommend.setVisibility(View.GONE);
        });
    }

    public View getView() {
        return mHeadWaitMovie;
    }

    public void displayData(WaitMovieBean.DataBean comingData, ExpectMovieBean.DataBean waitData) {
        if (waitData == null || waitData.coming == null || waitData.coming.isEmpty()) {
            mHeadWaitMovie.setVisibility(View.GONE);
            return;
        }

        mTvWaitRecommend.performClick();
        mTvWaitCount.setText(String.format(Locale.getDefault(), "全部%d部", waitData.paging.total));

        if (waitData.coming.size() > MAX_COUNT) {
            mWaitRecommendAdapter.setNewData(waitData.coming.subList(0, MAX_COUNT));

            List<String> imgUrls = new ArrayList<>();
            final  int count = waitData.coming.size();
            for (int i=count-4; i<count; i++) {
                imgUrls.add(waitData.coming.get(i).img);
            }
            View footMore = HeadArea.createFootSeeAll(mContext, imgUrls, waitData.paging.total);
            mWaitRecommendAdapter.setFooterView(footMore, -1, LinearLayout.HORIZONTAL);
        } else {
            mWaitRecommendAdapter.setNewData(waitData.coming);
            mWaitRecommendAdapter.removeAllFooterView();
        }

        if (comingData == null || comingData.coming == null || comingData.coming.isEmpty()) {
            mTvWaitComing.setVisibility(View.GONE);
        } else if (comingData.coming.size() > MAX_COUNT) {
            mWaitComingAdapter.setNewData(comingData.coming.subList(0, MAX_COUNT));

            List<String> imgUrls = new ArrayList<>();
            final  int count = comingData.coming.size();
            for (int i=count-4; i<count; i++) {
                imgUrls.add(comingData.coming.get(i).img);
            }
            View footMore = HeadArea.createFootSeeAll(mContext, imgUrls, waitData.paging.total);
            mWaitComingAdapter.setFooterView(footMore, -1, LinearLayout.HORIZONTAL);
        } else {
            mWaitComingAdapter.setNewData(comingData.coming);
            mWaitComingAdapter.removeAllFooterView();
        }

        mHeadWaitMovie.setVisibility(View.VISIBLE);
    }

}
