package com.ctrun.view.cateye.refresh.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ctrun.view.cateye.refresh.util.MovieVersionHelper;

import java.util.ArrayList;

/**
 * @author ctrun on 2017/1/24.
 */
public class HotMovieListBean {
    public DataBean data;

    public AdvertConfigBean advertData;

    public static class DataBean {

        public String stid;
        public int total;
        public ArrayList<?> coming;
        public ArrayList<HotBean> hot;
        public ArrayList<Integer> movieIds;
        public ArrayList<StidsBean> stids;
        public ArrayList<MoviesBean> movies;

        public static class HotBean implements MultiItemEntity {
            //首页热映的2种item
            //第一个热门
            public static final int TYPE_HOT_HEADLINE = 0;
            //正常
            public static final int TYPE_HOT_NORMAL = 1;
            //预售
            public static final int TYPE_HOT_PRE_SELL = 2;
            /**
             * boxInfo : 上映12天，累计票房26263万
             * cat : 爱情,冒险,科幻
             * civilPubSt : 0
             * desc : 主演:詹妮弗·劳伦斯,克里斯·帕拉特,迈克尔·辛
             * dir : 莫腾·泰杜姆
             * dur : 118
             * effectShowNum : 0
             * fra : 美国
             * frt : 2016-12-21
             * globalReleased : true
             * headLineShow : false
             * headLines : []
             * headLinesVO : [{"movieId":341201,"title":"航天博士提醒太空啪啪啪都是骗人滴！","type":"专题","url":"meituanmovie://www.meituan.com/forum/newsDetail?id= 18314"},{"movieId":341201,"title":"2.37亿美元登顶全球周票房榜冠军","type":"资讯","url":"meituanmovie://www.meituan.com/forum/newsDetail?id= 18294"}]
             * id : 341201
             * img : http://p0.meituan.net/w.h/movie/9b7f7df143af8a13ccd17057d772e9e7654933.png
             * late : false
             * localPubSt : 0
             * mk : 8.5
             * newsHeadlines : [{"movieId":341201,"title":"航天博士提醒太空啪啪啪都是骗人滴！","type":"专题","url":"meituanmovie://www.meituan.com/forum/newsDetail?id= 18314"},{"movieId":341201,"title":"2.37亿美元登顶全球周票房榜冠军","type":"资讯","url":"meituanmovie://www.meituan.com/forum/newsDetail?id= 18294"}]
             * nm : 太空旅客
             * pn : 169
             * preSale : 0
             * preShow : false
             * proScore : 0
             * proScoreNum : 13
             * pubDate : 1484236800000
             * pubShowNum : 0
             * recentShowDate : 1485187200000
             * recentShowNum : 0
             * rt : 2017-01-13
             * sc : 8.5
             * scm : 未达目的地，太空铁达尼
             * showCinemaNum : 139
             * showInfo : 今天139家影院放映1185场
             * showNum : 1185
             * showTimeInfo : 2017-01-13上映
             * showst : 3
             * snum : 79456
             * star : 詹妮弗·劳伦斯,克里斯·帕拉特,迈克尔·辛
             * totalShowNum : 2096
             * ver : 3D/中国巨幕
             * videoId : 82973
             * videoName : “扭转命运”版预告片
             * videourl : http://maoyan.meituan.net/movie/videos/854x48066c53e6a247d45059607a6f0f5e0a42d.mp4
             * vnum : 26
             * weight : 1.0
             * wish : 44420
             * wishst : 0
             */
            public String boxInfo;
            public String cat;
            public int civilPubSt;
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
            public ArrayList<?> headLines;
            public ArrayList<HeadLinesVOBean> headLinesVO;
            public ArrayList<NewsHeadlinesBean> newsHeadlines;

            public String[] cinemaTypeStringArray;
            public Integer[] cinemaTypeIds;
            @Override
            public int getItemType() {
                if(headLinesVO != null && headLinesVO.size() > 0) {
                    return TYPE_HOT_HEADLINE;
                }

                return TYPE_HOT_NORMAL;
            }

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
        }

        public static class StidsBean {
            /**
             * movieId : 341201
             * stid : 576591972453269000_a341201_c0
             */

            public int movieId;
            public String stid;
        }

        public static class MoviesBean {

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
            private boolean globalReleased;
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

    public static DataBean.HotBean mapToHot(DataBean.MoviesBean bean) {
        DataBean.HotBean hotBean = new DataBean.HotBean();
        hotBean.boxInfo = bean.boxInfo;
        hotBean.cat = bean.cat;
        hotBean.civilPubSt = bean.civilPubSt;
        hotBean.id = bean.id;
        //是否重映
        hotBean.isRevival = bean.isRevival;
        //播放场次
        hotBean.showInfo = bean.showInfo;
        hotBean.rt = bean.rt;
        //得分
        hotBean.sc = bean.sc;
        //片名
        hotBean.nm = bean.nm;
        //3D标签
        hotBean.ver = bean.ver;
        //描述
        hotBean.scm = bean.scm;
        //是否预售
        hotBean.preSale = bean.preSale;
        //是否点映
        hotBean.preShow = bean.preShow;
        //期待观影人数
        hotBean.wish = bean.wish;
        hotBean.img = bean.img;
        //主演
        hotBean.star = bean.star;
        hotBean.desc = bean.desc;
        hotBean.videoName = bean.videoName;
        hotBean.videoId = bean.videoId;
        hotBean.videourl = bean.videourl;

        hotBean.cinemaTypeIds = MovieVersionHelper.makeMovieVerDrawableIds(hotBean.ver, hotBean.preShow, hotBean.isRevival);
        return hotBean;
    }

    public static WaitMovieBean.DataBean.ComingBean mapToComing(DataBean.MoviesBean bean) {
        WaitMovieBean.DataBean.ComingBean comingBean = new WaitMovieBean.DataBean.ComingBean();
        comingBean.boxInfo = bean.boxInfo;
        comingBean.cat = bean.cat;
        comingBean.civilPubSt = bean.civilPubSt;
        comingBean.comingTitle = bean.comingTitle;
        //是否重映
        comingBean.isRevival = bean.isRevival;
        comingBean.id = bean.id;
        //播放场次
        comingBean.showInfo = bean.showInfo;
        comingBean.rt = bean.rt;
        //得分
        comingBean.sc = bean.sc;
        //片名
        comingBean.nm = bean.nm;
        //3D标签
        comingBean.ver = bean.ver;
        //描述
        comingBean.scm = bean.scm;
        //是否预售
        comingBean.preSale = bean.preSale;
        //是否点映
        comingBean.preShow = bean.preShow;
        //期待观影人数
        comingBean.wish = bean.wish;
        comingBean.img = bean.img;
        //主演
        comingBean.star = bean.star;
        comingBean.desc = bean.desc;
        comingBean.videoName = bean.videoName;
        comingBean.videoId = bean.videoId;
        comingBean.videourl = bean.videourl;

        comingBean.cinemaTypeIds = MovieVersionHelper.makeMovieVerDrawableIds(comingBean.ver, comingBean.preShow, comingBean.isRevival);

        return comingBean;
    }
}
