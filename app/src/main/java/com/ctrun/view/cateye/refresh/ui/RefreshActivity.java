package com.ctrun.view.cateye.refresh.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ctrun.view.cateye.refresh.databinding.ActivityRefreshBinding;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.Random;

/**
 * @author ctrun on 2022/8/4.
 * 下拉刷新组件
 */
public class RefreshActivity extends AppCompatActivity implements OnRefreshListener {

   public static void start(Context context) {
      Intent intent = new Intent(context, RefreshActivity.class);
      context.startActivity(intent);
   }

   private ActivityRefreshBinding mBinding;
   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mBinding = ActivityRefreshBinding.inflate(getLayoutInflater());
      setContentView(mBinding.getRoot());

      mBinding.actionbar.tvTitle.setText("仿猫眼下拉刷新组件");
      mBinding.actionbar.flBack.setOnClickListener(v -> onBackPressed());
   }

   @Override
   public void onRefresh(@NonNull RefreshLayout refreshLayout) {
      mBinding.refreshLayout.postDelayed(() -> mBinding.refreshLayout.finishRefresh(), 1500 + new Random().nextInt(500));
   }
}
