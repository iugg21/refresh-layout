package com.ctrun.view.cateye.refresh.application;

import android.app.Application;

import com.ctrun.view.cateye.refresh.widget.CommonRefreshHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * @author ctrun on 2022/9/28.
 */
public class MyApp extends Application {
   private static MyApp sApplication;

   public static MyApp getApplication() {
      return sApplication;
   }

   @Override
   public void onCreate() {
      super.onCreate();
      sApplication = this;

      initRefreshLayout();
   }

   /**
    * 初始化下拉刷新控件
    */
   private void initRefreshLayout() {
      SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
         //禁止越界回弹
         layout.setEnableOverScrollBounce(false);
         //禁止越界拖动
         layout.setEnableOverScrollDrag(false);
         //禁止上拉加载更多
         layout.setEnableLoadMore(false);
         return new CommonRefreshHeader(context);
      });
   }
}
