package com.ctrun.view.cateye.refresh.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctrun.view.cateye.refresh.R;
import com.ctrun.view.cateye.refresh.application.MyApp;
import com.ctrun.view.cateye.refresh.bean.VideoFeedBean;
import com.ctrun.view.cateye.refresh.bean.VideoFeedImageBean;
import com.ctrun.view.cateye.refresh.util.ImageSizeUtils;
import com.ctrun.view.cateye.refresh.util.UIUtils;

public class RecommendContentAdapter extends BaseQuickAdapter<VideoFeedBean, BaseViewHolder> {

    private final int mImageWidth = UIUtils.getScreenWidth(MyApp.getApplication()) / 2 - UIUtils.dp2px(MyApp.getApplication(), 20);

    public RecommendContentAdapter() {
        super(R.layout.recycle_item_home_recommend);
    }

    @Override
    protected void convert(BaseViewHolder holder, VideoFeedBean item) {
        VideoFeedImageBean imagesBean;
        ImageView imageView = holder.getView(R.id.iv_image);
        if (item.images == null || item.images.isEmpty()) {
            imagesBean = null;
        } else {
            imagesBean = item.images.get(0);
        }

        if (imagesBean == null || imagesBean.width <= 0 || imagesBean.height <= 0) {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.height = UIUtils.dp2px(mContext, 120);
            imageView.setLayoutParams(layoutParams);
        } else {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.height = (int) ((imagesBean.height / (float)imagesBean.width) * mImageWidth);
            imageView.setLayoutParams(layoutParams);

            Glide.with(mContext).load(imagesBean.url).apply(RequestOptions.centerCropTransform().dontAnimate()).into(imageView);
        }


        holder.setText(R.id.tv_title, item.title);
        holder.setText(R.id.tv_nick_name, item.user.nickName);
        holder.setText(R.id.tv_approve_count, String.valueOf(item.upCount));

        ImageView ivAvatar = holder.getView(R.id.iv_avatar);
        final String avatarUrl = ImageSizeUtils.makePicUrl(item.user.avatarurl, "@80w_80h_1e_1c_1l");
        Glide.with(mContext).load(avatarUrl).apply(RequestOptions.centerCropTransform().dontAnimate()).into(ivAvatar);
    }
}
