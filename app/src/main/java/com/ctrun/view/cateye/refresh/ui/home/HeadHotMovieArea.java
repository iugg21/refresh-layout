package com.ctrun.view.cateye.refresh.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctrun.view.cateye.refresh.R;
import com.ctrun.view.cateye.refresh.bean.HotMovieListBean;
import com.ctrun.view.cateye.refresh.util.ImageSizeUtils;
import com.ctrun.view.cateye.refresh.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author ctrun on 2021/6/5
 */
@SuppressWarnings("FieldCanBeLocal")
public class HeadHotMovieArea implements BaseQuickAdapter.OnItemClickListener {
    private static final int MAX_COUNT = 20;
    private Context mContext;
    private LayoutInflater mInflater;

    private View mHeadHotMovie;
    private RecyclerView mRvHot;
    private TextView mTvHotCount;
    private ImageView mIvHotCount;

    @SuppressLint("InflateParams")
    public HeadHotMovieArea(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

        mHeadHotMovie = mInflater.inflate(R.layout.home_head_hot_movie, null);
        mHeadHotMovie.setVisibility(View.GONE);
        mTvHotCount = mHeadHotMovie.findViewById(R.id.tv_hot_count);
        mIvHotCount = mHeadHotMovie.findViewById(R.id.iv_hot_count);
        mRvHot = mHeadHotMovie.findViewById(R.id.rv_hot);
        mRvHot.setAdapter(mAdapter);
    }

    public View getView() {
        return mHeadHotMovie;
    }

    public void displayData(HotMovieListBean.DataBean hotData) {
        if (hotData == null || hotData.hot == null || hotData.hot.isEmpty()) {
            mHeadHotMovie.setVisibility(View.GONE);
            return;
        }

        mTvHotCount.setText(String.format(Locale.getDefault(), "全部%d部", hotData.total));
        if (hotData.hot.size() > MAX_COUNT) {
            mAdapter.setNewData(hotData.hot.subList(0, MAX_COUNT));

            List<String> imgUrls = new ArrayList<>();
            final  int count = hotData.hot.size();
            for (int i=count-4; i<count; i++) {
                imgUrls.add(hotData.hot.get(i).img);
            }
            View footMore = HeadArea.createFootSeeAll(mContext, imgUrls, hotData.total);
            mAdapter.setFooterView(footMore, -1, LinearLayout.HORIZONTAL);
        } else {
            mAdapter.setNewData(hotData.hot);
            mAdapter.removeAllFooterView();
        }

        mHeadHotMovie.setVisibility(View.VISIBLE);
    }

    BaseQuickAdapter<HotMovieListBean.DataBean.HotBean, BaseViewHolder> mAdapter = new BaseQuickAdapter<HotMovieListBean.DataBean.HotBean, BaseViewHolder>(R.layout.recycle_item_movie) {
        @Override
        protected void convert(@NonNull BaseViewHolder holder, HotMovieListBean.DataBean.HotBean item) {
            holder.setText(R.id.tv_movie_name, item.nm);

            if(item.preSale == 0) {
                holder.setVisible(R.id.tv_wish, false);
                holder.setVisible(R.id.tv_movie_score, true);

                TextView tvMovieScore = holder.getView(R.id.tv_movie_score);
                if(item.sc > 0) {
                    tvMovieScore.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    holder.setText(R.id.tv_movie_score, String.format("%s 分", item.sc));
                } else {
                    tvMovieScore.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    holder.setText(R.id.tv_movie_score, "暂无评分");
                }

                holder.setText(R.id.tv_buy_ticket, "购票");
                holder.setBackgroundRes(R.id.tv_buy_ticket, R.drawable.movie_btn_buy_bg);
            } else if(item.preSale == 1) {
                if(item.sc > 0) {
                    holder.setVisible(R.id.tv_wish, false);
                    holder.setVisible(R.id.tv_movie_score, true);

                    TextView tvMovieScore = holder.getView(R.id.tv_movie_score);
                    tvMovieScore.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    holder.setText(R.id.tv_movie_score, String.format("%s 分", item.sc));
                } else {
                    holder.setVisible(R.id.tv_wish, true);
                    holder.setVisible(R.id.tv_movie_score, false);
                    holder.setText(R.id.tv_wish, String.format("%s想看", StringUtils.changeNumToCN2(item.wish)));
                }

                holder.setText(R.id.tv_buy_ticket, "预售");
                holder.setBackgroundRes(R.id.tv_buy_ticket, R.drawable.movie_btn_presale_bg);
            } else {
                holder.setVisible(R.id.tv_wish, false);
                holder.setVisible(R.id.tv_movie_score, false);
            }

            if (item.cinemaTypeStringArray == null || item.cinemaTypeStringArray.length == 0) {
                holder.setVisible(R.id.ll_cinema_types, false);
            } else {
                holder.setVisible(R.id.ll_cinema_types, true);
                holder.setText(R.id.tv_cinema_type_1, item.cinemaTypeStringArray[0]);

                if (item.cinemaTypeStringArray.length > 1) {
                    holder.setText(R.id.tv_cinema_type_2, item.cinemaTypeStringArray[1]);
                    holder.setGone(R.id.tv_cinema_type_2, true);
                } else {
                    holder.setGone(R.id.tv_cinema_type_2, false);
                }
            }

            ImageView ivMovieImg = holder.getView(R.id.iv_movie_img);
            //图片地址不能直接使用，需要进行转换
            final String imgUrl = ImageSizeUtils.makeSmallUrlSquare(item.img, 480);
            Glide.with(mContext).load(imgUrl).apply(RequestOptions.centerCropTransform().dontAnimate()).into(ivMovieImg);
        }
    };

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
