package com.ctrun.view.cateye.refresh.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author ctrun on 2021/8/20.
 */
public class VideoFeedImageBean {

    /**
     * height : 357
     * id : 2800536
     * url : http://p1.meituan.net/movie/09725a9cc8f68aaf2cddb77048037337124713.jpeg@300w_225h_1e_1c
     * weight : 357
     */
    public int id;
    public String url;
    @SerializedName(alternate = "width", value = "weight")
    public int width;
    @SerializedName(alternate = "height", value = "hight")
    public int height;

}
