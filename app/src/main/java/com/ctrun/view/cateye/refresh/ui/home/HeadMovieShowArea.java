package com.ctrun.view.cateye.refresh.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ctrun.view.cateye.refresh.R;
import com.ctrun.view.cateye.refresh.bean.MovieShowBean;
import com.ctrun.view.cateye.refresh.ui.home.adapter.MovieShowAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ctrun on 2021/6/5
 */
@SuppressWarnings("FieldCanBeLocal")
public class HeadMovieShowArea implements BaseQuickAdapter.OnItemClickListener {
    private static final int MAX_COUNT = 12;

    private Context mContext;
    private LayoutInflater mInflater;

    private View mHeadMovieShow;
    private RecyclerView mRvMovieShow;
    private MovieShowAdapter mMovieShowAdapter;
    private TextView mTvAll;

    @SuppressLint("InflateParams")
    public HeadMovieShowArea(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

        mHeadMovieShow = mInflater.inflate(R.layout.home_head_movieshow, null);
        mHeadMovieShow.setVisibility(View.GONE);
        mTvAll = mHeadMovieShow.findViewById(R.id.tv_all);
        mRvMovieShow = mHeadMovieShow.findViewById(R.id.rv_movieshow);
        mMovieShowAdapter = new MovieShowAdapter();
        mMovieShowAdapter.setOnItemClickListener(this);
        mRvMovieShow.setAdapter(mMovieShowAdapter);
    }

    public View getView() {
        return mHeadMovieShow;
    }

    public void displayData(List<MovieShowBean> movieShowData) {
        if (movieShowData == null || movieShowData.isEmpty()) {
            mHeadMovieShow.setVisibility(View.GONE);
        } else {
            mHeadMovieShow.setVisibility(View.VISIBLE);

            if (movieShowData.size() > MAX_COUNT) {
                mMovieShowAdapter.setNewData(movieShowData.subList(0, MAX_COUNT));

                List<String> imgUrls = new ArrayList<>();
                final  int count = movieShowData.size();
                for (int i=count-4; i<count; i++) {
                    imgUrls.add(movieShowData.get(i).posterUrl);
                }
                View footMore = HeadArea.createFootSeeAll(mContext, imgUrls, movieShowData.size());
                footMore.findViewById(R.id.tv_count).setVisibility(View.GONE);
                mMovieShowAdapter.setFooterView(footMore, -1, LinearLayout.HORIZONTAL);
            } else {
                mMovieShowAdapter.setNewData(movieShowData);
                mMovieShowAdapter.removeAllFooterView();
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

}
