package com.ctrun.view.cateye.refresh.util;

import android.graphics.Outline;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * @author ctrun on 2022/8/11.
 */
public class ViewUtils {

   public static void setViewCornerRadius(View view, final int radius) {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
         //不支持5.0版本以下的系统
         return;
      }

      if (view == null) {
         return;
      }

      view.setOutlineProvider(new ViewOutlineProvider() {
         @Override
         public void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);//这是设置圆角的关键设置
         }
      });
      view.setClipToOutline(true);
   }

}
