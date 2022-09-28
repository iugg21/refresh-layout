package com.ctrun.view.cateye.refresh.bean;

import java.util.ArrayList;
import java.util.List;

public class ExpectMovieBean {
    public DataBean data;

    public static class DataBean {
        public PagingBean paging;
        public String stid;
        public ArrayList<ComingBean> coming;
        public ArrayList<?> hot;

        public static class PagingBean {
            /**
             * hasMore : true
             * limit : 30
             * offset : 0
             * total : 59
             */
            public boolean hasMore;
            public int limit;
            public int offset;
            public int total;
        }

        public static class ComingBean {
            /**
             * cat : 喜剧,动作,冒险
             * civilPubSt : 0
             * comingTitle : 1月28日 周六
             * desc : 主演:王宝强,白客,岳云鹏
             * dir : 王宝强
             * dur : 100
             * effectShowNum : 0
             * globalReleased : false
             * headLineShow : false
             * headLines : []
             * headLinesVO : []
             * id : 248935
             * img : http://p1.meituan.net/w.h/movie/015de6bbaa2ecc299254b24e4f96939d489607.jpg
             * late : false
             * localPubSt : 0
             * mk : 0
             * newsHeadlines : []
             * nm : 大闹天竺
             * pn : 272
             * preShow : false
             * proScore : 0
             * proScoreNum : 0
             * pubDate : 1485532800000
             * pubShowNum : 0
             * recentShowDate : 0
             * recentShowNum : 0
             * rt : 2017-01-28
             * sc : 0
             * scm : 宝强取真经，争当搞笑King
             * showCinemaNum : 0
             * showInfo : 2017-01-28 本周六上映
             * showNum : 0
             * showst : 4
             * snum : 10272
             * star : 王宝强,白客,岳云鹏
             * ver : 2D/中国巨幕/全景声
             * videoId : 83034
             * videoName : 终极版预告片 全明星阵容陪你过年
             * videourl : http://maoyan.meituan.net/movie/videos/854x480388c9f25d2e74655ac048a1bb86d2f55.mp4
             * vnum : 24
             * weight : 1.0
             * wish : 231050
             * wishst : 0
             * fra : 美国,韩国,日本
             * frt : 2017-01-27,,2016-12-23
             * ftime : ,2017-01,
             * time : 2017-02
             */

            public String cat;
            public int civilPubSt;
            public String comingTitle;
            public String desc;
            public String dir;
            public int dur;
            public int effectShowNum;
            public boolean globalReleased;
            public boolean headLineShow;
            public int id;
            public String img;
            public boolean late;
            public int localPubSt;
            public double mk;
            public String nm;
            public int pn;
            public boolean preShow;
            public double proScore;
            public int proScoreNum;
            public long pubDate;
            public int pubShowNum;
            public long recentShowDate;
            public int recentShowNum;
            public String rt;
            public double sc;
            public String scm;
            public int showCinemaNum;
            public String showInfo;
            public int showNum;
            public int showst;
            public int snum;
            public String star;
            public String ver;
            public int videoId;
            public String videoName;
            public String videourl;
            public int vnum;
            public double weight;
            public int wish;
            public int wishst;
            public String fra;
            public String frt;
            public String ftime;
            public String time;
            public List<?> headLines;
            public List<?> headLinesVO;
            public List<?> newsHeadlines;

            public String[] cinemaTypeStringArray;
        }
    }
}
