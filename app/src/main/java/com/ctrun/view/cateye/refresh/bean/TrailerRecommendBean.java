package com.ctrun.view.cateye.refresh.bean;

import java.util.ArrayList;

/**
 * 待映-预告片推荐
 */
public class TrailerRecommendBean {
    public ArrayList<DataBean> data;

    public static class DataBean {
        /**
         * img : http://p0.meituan.net/movie/790859165b19bdb28748c01d5e54bc9d24553.jpg
         * movieId : 1170255
         * movieName : 乘风破浪
         * name : 《乘风破浪》终极预告
         * originName : 终极预告片
         * url : http://maoyan.meituan.net/movie/videos/854x480364e9171dfbe44dfbf31e0e653349305.mp4
         * videoId : 82993
         * wish : 44618
         */

        public String img;
        public int movieId;
        public String movieName;
        public String name;
        public String originName;
        public String url;
        public int videoId;
        public int wish;
    }
}
