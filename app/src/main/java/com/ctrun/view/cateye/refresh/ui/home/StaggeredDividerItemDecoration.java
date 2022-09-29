package com.ctrun.view.cateye.refresh.ui.home;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author ctrun on 2021/6/17.
 */
public class StaggeredDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private int mSpace;
    private int mSpaceMiddle;
    private int mSpanCount;

    /**
     * @param spaceDp item的间距
     * @param spanCount 列数
     * */
    public StaggeredDividerItemDecoration(Context context, float spaceDp, float spaceMiddleDp, int spanCount) {
        this.mContext = context;
        this.mSpanCount = spanCount;

        this.mSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, spaceDp, context.getResources().getDisplayMetrics());
        this.mSpaceMiddle = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, spaceMiddleDp, context.getResources().getDisplayMetrics());
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if (position == 0) {
            return;
        }

        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        // 获取item在span中的下标
        int spanIndex = params.getSpanIndex();
        // 中间间隔

        if (spanIndex % mSpanCount == 0) {
            outRect.left = mSpace;
            outRect.right = mSpaceMiddle / 2;
        } else {
            outRect.left = mSpaceMiddle / 2;
            outRect.right = mSpace;
        }

        outRect.bottom = mSpaceMiddle;
    }
}
