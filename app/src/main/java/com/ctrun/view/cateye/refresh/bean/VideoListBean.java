package com.ctrun.view.cateye.refresh.bean;

import java.util.ArrayList;

/**
 * @author ctrun on 2021/6/17.
 */
public class VideoListBean {
    public DataBean data;

    public static class DataBean {
        public long timestamp;
        public ArrayList<VideoFeedBean> feeds;
    }

}
