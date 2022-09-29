package com.ctrun.view.cateye.refresh.ui.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctrun.view.cateye.refresh.R;
import com.ctrun.view.cateye.refresh.bean.ExpectMovieBean;
import com.ctrun.view.cateye.refresh.util.DateUtils;
import com.ctrun.view.cateye.refresh.util.ImageSizeUtils;
import com.ctrun.view.cateye.refresh.util.StringUtils;

/**
 * @author ctrun on 2021/6/3.
 */
public class WaitRecommendAdapter extends BaseQuickAdapter<ExpectMovieBean.DataBean.ComingBean, BaseViewHolder> {

    public WaitRecommendAdapter() {
        super(R.layout.recycle_item_home_head_wait_movie);
    }

    @Override
    protected void convert(BaseViewHolder holder, ExpectMovieBean.DataBean.ComingBean item) {
        holder.setText(R.id.tv_movie_name, item.nm);
        holder.setText(R.id.tv_movie_expect_time, DateUtils.getRecentExpectShowTime(item.rt));

        if(item.showst == 4) {
            if(item.sc > 0) {
                holder.setVisible(R.id.tv_wish, false);
                holder.setVisible(R.id.tv_movie_score, true);

                holder.setText(R.id.tv_movie_score, String.format("%s 分", item.sc));
            } else {
                holder.setVisible(R.id.tv_wish, true);
                holder.setVisible(R.id.tv_movie_score, false);
                holder.setText(R.id.tv_wish, String.format("%s想看", StringUtils.changeNumToCN2(item.wish)));
            }

            holder.setText(R.id.tv_presale, "预售");
            holder.setBackgroundRes(R.id.tv_presale, R.drawable.movie_btn_presale_bg);
        } else {
            holder.setVisible(R.id.tv_wish, true);
            holder.setVisible(R.id.tv_movie_score, false);
            holder.setText(R.id.tv_wish, String.format("%s想看", StringUtils.changeNumToCN2(item.wish)));

            holder.setText(R.id.tv_presale, "想看");
            holder.setBackgroundRes(R.id.tv_presale, R.drawable.movie_btn_wish_bg);
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
}
