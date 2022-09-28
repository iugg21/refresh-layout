package com.ctrun.view.cateye.refresh.bean;

import java.io.Serializable;

public class MovieShowBean implements Serializable {
    private static final long serialVersionUID = 1478538596560525766L;

    public int performanceId;
    /**
     * 1：即将开抢，3：抢购中
     */
    public int ticketStatus;
    public int categoryId;
    public String shopId;
    public String shopName;
    public String name;
    public double lat;
    public double lng;
    public String address;
    public String posterUrl;
    public String postUrlForShare;
    public String showTimeRange;
    public String priceRange;
    public String seatUrl;
    public boolean limit;
    public int inventoryCount;
    public int cityId;
    public String cityName;
    public int lowestPrice;
    public String minDiscount;
}
