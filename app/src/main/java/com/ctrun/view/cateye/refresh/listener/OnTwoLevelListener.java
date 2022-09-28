package com.ctrun.view.cateye.refresh.listener;

import androidx.annotation.NonNull;

import com.scwang.smart.refresh.layout.api.RefreshLayout;

/**
 * @author ctrun on 2022/8/11.
 */
public interface OnTwoLevelListener {
    /**
     * 二级刷新触发
     * @param refreshLayout 刷新布局
     * @return true 将会展开二楼状态 false 关闭刷新
     */
    boolean onTwoLevel(@NonNull RefreshLayout refreshLayout);

}
