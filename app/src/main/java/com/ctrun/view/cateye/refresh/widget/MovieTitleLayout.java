package com.ctrun.view.cateye.refresh.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @author ctrun on 2022/9/1.
 */
public class MovieTitleLayout extends LinearLayout {
   public MovieTitleLayout(Context context) {
      this(context, null);
   }

   public MovieTitleLayout(Context context, @Nullable AttributeSet attrs) {
      this(context, attrs, 0);
   }

   public MovieTitleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);

      setOrientation(HORIZONTAL);
   }

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      int width = MeasureSpec.getSize(widthMeasureSpec);

      View childTextView = getChildAt(0);
      int parentWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
      measureChildWithMargins(childTextView, parentWidthMeasureSpec, 0, heightMeasureSpec, 0);

      int childTextViewMeasuredWidth = childTextView.getMeasuredWidth();

      View childMoreView = getChildAt(1);
      if (childTextViewMeasuredWidth > width) {
         childMoreView.setVisibility(VISIBLE);
      } else {
         childMoreView.setVisibility(GONE);
      }

      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
   }

}
