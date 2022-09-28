package com.ctrun.view.cateye.refresh.bean;

import java.util.List;

/**
 * @author ctrun on 2021/9/29.
 */
public class AdvertConfigBean {

    public List<AdvertDataBean> config;
    public long positionId;
    public String positionName;
    public int type;

    public static class AdvertDataBean {
        /**
         * {
         * adId: 114067,
         * adImgColor: "#664f3d",
         * frame: 1,
         * image: "http://p0.meituan.net/movie/c778af204141bca70726fb491625db6d70147.jpg",
         * link: "meituanmovie://www.meituan.com/exportweb?wkwebview=1&url=https://s.maoyan.com/5eJYBbcQ",
         * materialId: 89521,
         * materialType: 1,
         * showAdLabel: 1,
         * type: 1
         * }
         */
        public long adId;
        public String adImgColor;
        public int frame;
        public String image;
        public String link;
        public long materialId;
        public int materialType;
        public int showAdLabel;
        public int type;
    }
}
