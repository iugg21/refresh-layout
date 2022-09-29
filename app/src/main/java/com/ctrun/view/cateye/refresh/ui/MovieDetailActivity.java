package com.ctrun.view.cateye.refresh.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ctrun.view.cateye.refresh.R;
import com.ctrun.view.cateye.refresh.bean.MovieBasicDataBean;
import com.ctrun.view.cateye.refresh.databinding.ActivityMovieDetailBinding;
import com.ctrun.view.cateye.refresh.util.ImageSizeUtils;
import com.ctrun.view.cateye.refresh.util.JsonFileUtils;
import com.ctrun.view.cateye.refresh.util.StatusBarUtils;
import com.google.gson.Gson;

import java.util.Locale;

/**
 * @author ctrun on 2022/8/5.
 */
public class MovieDetailActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        context.startActivity(intent);
    }

    private ActivityMovieDetailBinding mBinding;
    private MovieBasicDataBean.DataBean.MovieBean mMovie;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this, false);
        mBinding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.actionbar.getRoot().setBackgroundColor(Color.TRANSPARENT);
        mBinding.actionbar.tvTitle.setText("");
        mBinding.actionbar.flBack.setOnClickListener(v -> onBackPressed());

        ViewCompat.setOnApplyWindowInsetsListener(mBinding.getRoot(), (v, insets) -> {
            int insetTop = insets.getSystemWindowInsetTop();
            mBinding.actionbar.getRoot().setPadding(0, insetTop, 0, 0);
            return new WindowInsetsCompat(insets);
        });

        mBinding.svMovieDetail.setVisibility(View.INVISIBLE);

        mBinding.showFullMovieName.setOnClickListener(v -> showFullMovieNamePopupWindow());

        requestLocalData();
    }

    private void requestLocalData() {
        String jsonDataBasicInfo = JsonFileUtils.readJsonFromAsset(this, "cateye_movie_base_info_new_happy_dad_and_son_5.json");
        Gson gson = new Gson();
        MovieBasicDataBean basicDataBean = gson.fromJson(jsonDataBasicInfo, MovieBasicDataBean.class);
        mMovie = basicDataBean.data.movie;

        initData();
    }

    private void initData() {
        mBinding.llMovieInfoParent.setBackgroundColor(Color.parseColor(mMovie.backgroundColor));

        mBinding.tvMovieName.setText(mMovie.nm);
        if (TextUtils.isEmpty(mMovie.enm)) {
            mBinding.tvEnglishMovieName.setVisibility(View.GONE);
        } else {
            mBinding.tvEnglishMovieName.setVisibility(View.VISIBLE);
            mBinding.tvEnglishMovieName.setText(mMovie.enm);
        }
        if (TextUtils.isEmpty(mMovie.scm)) {
            mBinding.llMoviePraise.setVisibility(View.GONE);
        } else {
            mBinding.llMoviePraise.setVisibility(View.VISIBLE);
            mBinding.tvMoviePraise.setText(mMovie.scm);
        }
        if (TextUtils.isEmpty(mMovie.cat)) {
            mBinding.tvMovieType.setVisibility(View.GONE);
        } else {
            mBinding.tvMovieType.setVisibility(View.VISIBLE);
            mBinding.tvMovieType.setText(mMovie.cat);
        }
        if (mMovie.dur > 0) {
            mBinding.tvPubDescDur.setText(String.format(Locale.getDefault(), "%s %d分钟", mMovie.pubDesc, mMovie.dur));
        } else {
            mBinding.tvPubDescDur.setText(String.format(Locale.getDefault(), "%s", mMovie.pubDesc));
        }
        if(TextUtils.isEmpty(mMovie.videourl)) {
            mBinding.flMovieImg.setOnClickListener(null);
            mBinding.ivMoviePlayIcon.setVisibility(View.GONE);
        } else {
            mBinding.flMovieImg.setOnClickListener(v -> {});
        }

        String imgUrl = ImageSizeUtils.makeSmallUrlSquare(mMovie.img, 447);
        Glide.with(this).load(imgUrl).apply(RequestOptions.centerCropTransform().dontAnimate()).into(mBinding.ivMovieImg);

        mBinding.svMovieDetail.setVisibility(View.VISIBLE);
    }

    private FullMovieNamePopupWindow mFullMovieNamePopupWindow;
    private void showFullMovieNamePopupWindow() {
        if (mFullMovieNamePopupWindow == null) {
            mFullMovieNamePopupWindow = new FullMovieNamePopupWindow(this);
        }

        if (mFullMovieNamePopupWindow.isShowing()) {
            mFullMovieNamePopupWindow.dismiss();
        }

        mFullMovieNamePopupWindow.show(mBinding.showFullMovieName, mMovie.nm, mMovie.enm);
    }
}
