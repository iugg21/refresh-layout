package com.ctrun.view.cateye.refresh.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctrun.view.cateye.refresh.R;
import com.ctrun.view.cateye.refresh.databinding.ActivityMainBinding;
import com.ctrun.view.cateye.refresh.ui.home.HomeRefreshActivity;

import java.util.Arrays;

/**
 * @author ctrun on 2022/8/4.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.actionbar.flBack.setVisibility(View.GONE);
        mBinding.actionbar.tvTitle.setText("仿猫眼电影下拉刷新组件");

        mBinding.rvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(mItemClickListener);
        mAdapter.setNewData(Arrays.asList(Item.values()));
    }

    BaseQuickAdapter<Item, BaseViewHolder> mAdapter = new BaseQuickAdapter<Item, BaseViewHolder>(R.layout.recycle_item_main) {
        @Override
        protected void convert(@NonNull BaseViewHolder holder, Item item) {
            holder.setText(R.id.tv_title, item.title);
            holder.setText(R.id.tv_desc, item.desc);
        }
    };

    BaseQuickAdapter.OnItemClickListener mItemClickListener = (adapter, view, position) -> {
        Item item = mAdapter.getItem(position);
        if (item != null) {
            if (Activity.class.isAssignableFrom(item.clazz)) {
                startActivity(new Intent(MainActivity.this, item.clazz));
            }
        }
    };

    @SuppressWarnings("All")
    private enum Item {
        Refresh("下拉刷新", "仿猫眼下拉刷新组件", RefreshActivity.class),
        HomeRefresh("首页刷新", "仿猫眼首页下拉刷新，继续下拉进入二楼组件", HomeRefreshActivity.class);

        public String title;
        public String desc;
        public Class<?> clazz;

        Item(String title, String desc, Class<?> clazz) {
            this.title = title;
            this.desc = desc;
            this.clazz = clazz;
        }
    }
}
