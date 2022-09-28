package com.ctrun.view.cateye.refresh.bean;

import java.util.ArrayList;

public class WaitMovieBean {

    public DataBean data;

    /**
     * 预告片推荐
     */
    public ArrayList<TrailerRecommendBean.DataBean> recommendData;
    /**
     * 近期最受期待
     */
    public ArrayList<ExpectMovieBean.DataBean.ComingBean> expectData;

    public static class DataBean {

        public ArrayList<ComingBean> coming;
        public ArrayList<Integer> movieIds;

        public static class ComingBean {
            /**
             * boxInfo : 喵，即将上映
             * cat : 喜剧,奇幻,武侠
             * civilPubSt : 0
             * comingTitle : 1月27日 周五
             * desc : 主演:舒淇,王千源,张孝全
             * dir : 陈玉勋
             * dur : 116
             * effectShowNum : 0
             * globalReleased : false
             * headLineShow : false
             * headLinesVO : []
             * id : 345668
             * img : http://p0.meituan.net/w.h/movie/cd23a5f30b3918cf241074bad9589d0d421744.jpg
             * late : false
             * localPubSt : 0
             * mk : 0
             * nm : 健忘村
             * pn : 241
             * preShow : true
             * proScore : 0
             * proScoreNum : 0
             * pubDate : 1485446400000
             * pubDesc : 2017-01-27大陆上映
             * pubShowNum : 0
             * recentShowDate : 0
             * recentShowNum : 0
             * rt : 2017-01-27
             * sc : 0
             * scm : 偏僻小山村，通车换乾坤
             * showCinemaNum : 0
             * showInfo : 今天80家影院放映230场
             * showNum : 0
             * showst : 4
             * snum : 688
             * star : 舒淇,王千源,张孝全
             * ver : 2D
             * videoId : 83103
             * videoName : 四川版预告片
             * videourl : http://maoyan.meituan.net/movie/videos/854x48062cdd36d482848c2a48a08b811d24413.mp4
             * vnum : 28
             * weight : 1.0
             * wish : 13647
             * wishst : 0
             * ftime : 2017-12
             * fra : 美国
             * frt : 2016-02-26
             */

            public String boxInfo;
            public String cat;
            public int civilPubSt;
            public String comingTitle;
            public String desc;
            public String dir;
            public int dur;
            public int effectShowNum;
            public String fra;
            public String frt;
            public boolean globalReleased;
            public boolean headLineShow;
            public int id;
            public String img;
            public boolean isRevival;
            public boolean late;
            public int localPubSt;
            public double mk;
            public String nm;
            public int pn;
            public int preSale;
            public boolean preShow;
            public int proScore;
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
            public String showTimeInfo;
            public int showst;
            public int snum;
            public String star;
            public int totalShowNum;
            public String ver;
            public int videoId;
            public String videoName;
            public String videourl;
            public int vnum;
            public double weight;
            public int wish;
            public int wishst;
            public ArrayList<HeadLinesVOBean> headLinesVO;
            public ArrayList<NewsHeadlinesBean> newsHeadlines;
            public ArrayList<RecentExpect> recentExpectList;
            public ArrayList<TrailerRecommendBean> trailerRecommenList;

            public String[] cinemaTypeStringArray;
            public Integer[] cinemaTypeIds;
            public boolean isGroup;

            public static class HeadLinesVOBean {
                /**
                 * movieId : 341201
                 * title : 航天博士提醒太空啪啪啪都是骗人滴！
                 * type : 专题
                 * url : meituanmovie://www.meituan.com/forum/newsDetail?id= 18314
                 */

                public int movieId;
                public String title;
                public String type;
                public String url;
            }

            public static class NewsHeadlinesBean {
                /**
                 * movieId : 341201
                 * title : 航天博士提醒太空啪啪啪都是骗人滴！
                 * type : 专题
                 * url : meituanmovie://www.meituan.com/forum/newsDetail?id= 18314
                 */

                public int movieId;
                public String title;
                public String type;
                public String url;
            }

            public static class TrailerRecommendBean {
                public String img;
                public int movieId;
                public String movieName;
                public String name;
                public String originName;
                public String url;
                public int videoId;
                public int wish;
            }

            public static class RecentExpect {
                public String boxInfo;
                public String cat;
                public int civilPubSt;
                public String comingTitle;
                public String desc;
                public String dir;
                public int dur;
                public int effectShowNum;
                public String fra;
                public String frt;
                public boolean globalReleased;
                public boolean headLineShow;
                public int id;
                public String img;
                public boolean late;
                public int localPubSt;
                public double mk;
                public String nm;
                public int pn;
                public int preSale;
                public boolean preShow;
                public int proScore;
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
                public String showTimeInfo;
                public int showst;
                public int snum;
                public String star;
                public int totalShowNum;
                public String ver;
                public int videoId;
                public String videoName;
                public String videourl;
                public int vnum;
                public double weight;
                public int wish;
                public int wishst;
            }

        }



    }


}
