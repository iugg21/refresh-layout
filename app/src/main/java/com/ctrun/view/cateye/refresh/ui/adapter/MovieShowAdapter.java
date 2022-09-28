package com.ctrun.view.cateye.refresh.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctrun.view.cateye.refresh.R;
import com.ctrun.view.cateye.refresh.bean.MovieShowBean;
import com.ctrun.view.cateye.refresh.util.ImageSizeUtils;

/**
 * @author ctrun on 2021/6/3.
 */
public class MovieShowAdapter extends BaseQuickAdapter<MovieShowBean, BaseViewHolder> {

    public MovieShowAdapter() {
        super(R.layout.recycle_item_home_head_movieshow);
    }

    @Override
    protected void convert(BaseViewHolder holder, MovieShowBean item) {
        holder.setText(R.id.tv_name, item.name);

        ImageView ivImg = holder.getView(R.id.iv_img);
        //图片地址不能直接使用，需要进行转换
        final String imgUrl = ImageSizeUtils.makePicUrl(item.posterUrl, "@345w_480h_1e_1c_1l");
        Glide.with(mContext).load(imgUrl).apply(RequestOptions.centerCropTransform().dontAnimate()).into(ivImg);
    }
}
