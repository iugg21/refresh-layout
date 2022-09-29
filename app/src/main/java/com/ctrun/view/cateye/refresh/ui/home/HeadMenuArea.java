package com.ctrun.view.cateye.refresh.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctrun.view.cateye.refresh.R;

import java.util.ArrayList;

/**
 * @author ctrun on 2021/6/5
 */
@SuppressWarnings("FieldCanBeLocal")
public class HeadMenuArea {
    private Context mContext;
    private LayoutInflater mInflater;

    private View mHeadMenu;

    @SuppressLint("InflateParams")
    public HeadMenuArea(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

        mHeadMenu = mInflater.inflate(R.layout.home_head_menu, null);
        mHeadMenu.setVisibility(View.GONE);
        RecyclerView rvMenu1 = mHeadMenu.findViewById(R.id.rv_menu_1);
        rvMenu1.setAdapter(mMenu1Adapter);
        mMenu1Adapter.setOnItemClickListener(mMenuItemClick);

        RecyclerView rvMenu2 = mHeadMenu.findViewById(R.id.rv_menu_2);
        rvMenu2.setAdapter(mMenu2Adapter);
        mMenu2Adapter.setOnItemClickListener(mMenuItemClick);
    }

    public View getView() {
        return mHeadMenu;
    }

    public void displayData() {
        mMenu1Adapter.setNewData(menu1Data);
        mMenu2Adapter.setNewData(menu2Data);
        mHeadMenu.setVisibility(View.VISIBLE);
    }

    static final int TYPE_NONE = -1;
    static final int TYPE_H5 = 1;
    static final int TYPE_MOVIE_CINEMA = 2;
    static final int TYPE_MOVIE_SHOW = 3;
    static final int TYPE_ONLINE_MOVIE = 4;
    static class MenuData {
        String name;
        String imageUrl;
        String subImageUrl;
        int type = TYPE_NONE;
        String url;

        MenuData(String imageUrl, String name) {
            this.imageUrl = imageUrl;
            this.name = name;
        }

        MenuData(String imageUrl, String name, int type) {
            this(imageUrl, name);
            this.type = type;
        }

        MenuData(String imageUrl, String name, int type, String url) {
            this(imageUrl, name, type);
            this.url = url;
        }
    }

    private final ArrayList<MenuData> menu1Data = new ArrayList<>();
    private final ArrayList<MenuData> menu2Data = new ArrayList<>();
    {
        menu1Data.add(new MenuData("http://p0.meituan.net/movie/9bfe621218b6e46acf6c3aeb0eae617622486.png.webp@150w_150h_1e_1l_1c", "电影影院", TYPE_MOVIE_CINEMA));
        menu1Data.add(new MenuData("http://p0.meituan.net/movie/c4a2b705d7b2ff4573884d722cd068d522094.png.webp@150w_150h_1e_1l_1c", "放映厅", TYPE_H5, "https://i.maoyan.com/asgard/longvideo/index"));
        menu1Data.add(new MenuData("http://p0.meituan.net/movie/b6f955249dc50ab6db040df106bc46d822712.png.webp@150w_150h_1e_1l_1c", "演出赛事", TYPE_MOVIE_SHOW));
        menu1Data.add(new MenuData("http://p0.meituan.net/movie/c2df03fa551695c6bd82c812ce30f64522208.png.webp@150w_150h_1e_1l_1c", "剧集综艺"));
        menu1Data.add(new MenuData("http://p0.meituan.net/movie/caadf23f991d1b777a910ba2e003c1f021940.png.webp@150w_150h_1e_1l_1c", "小说", TYPE_H5, "https://i.maoyan.com/ebook/novel"));

        MenuData menuData = new MenuData("http://p0.meituan.net/movie/7ed53308d62de861cfed3f1f177fa05e2926.png", "0元观景", TYPE_H5, "https://m.maoyan.com/swan/bargain/list");
        menuData.subImageUrl = "http://p1.meituan.net/movie/bd30156d30369b2b1274d4331c3747ad19186.png.webp@48w_35h_1e_1l_1c";
        menu2Data.add(menuData);
        menuData = new MenuData("http://p0.meituan.net/movie/4abec2bb59a0766bee4e88a15cc6679a3132.png", "限时抢卷", TYPE_ONLINE_MOVIE);
        menuData.subImageUrl = "http://p0.meituan.net/movie/3b9499e59a0b5abe699b4ff19999a49823025.png.webp@48w_35h_1e_1l_1c";
        menu2Data.add(menuData);
        menuData = new MenuData("http://p0.meituan.net/movie/34e6db094a7942585ec4cfe2cbc859175461.png", "粽情一夏");
        menuData.subImageUrl = "http://p0.meituan.net/movie/2e4d08b6b3ed7c105a55dcbedec1c7a620804.png.webp@48w_35h_1e_1l_1c";
        menu2Data.add(menuData);
        menuData = new MenuData("http://p0.meituan.net/movie/8673d23f1b79cc1fed2efd94841aae161798.png", "3C周边", TYPE_H5, "https://store.maoyan.com/mmall/store");
        menuData.subImageUrl = "http://p0.meituan.net/movie/79e76e8bc77f6ddd9796349e95a4f7a033404.png.webp@48w_35h_1e_1l_1c";
        menu2Data.add(menuData);
        menuData = new MenuData("http://p0.meituan.net/movie/081b8c46e325b43be6b4f7e2db2959de2767.png", "高分推荐");
        menuData.subImageUrl = "http://p0.meituan.net/movie/a4a8446185ff2dfc79bc0293da6d8cc414494.png.webp@48w_35h_1e_1l_1c";
        menu2Data.add(menuData);
    }

    BaseQuickAdapter<MenuData, BaseViewHolder> mMenu1Adapter = new BaseQuickAdapter<MenuData, BaseViewHolder>(R.layout.recycle_item_home_head_menu_1) {
        @Override
        protected void convert(BaseViewHolder holder, MenuData item) {
            holder.setText(R.id.tv_title, item.name);

            ImageView ivIcon = holder.getView(R.id.iv_icon);
            Glide.with(mContext).load(item.imageUrl).apply(RequestOptions.centerCropTransform().dontAnimate()).into(ivIcon);
        }

    };

    BaseQuickAdapter.OnItemClickListener mMenuItemClick = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            MenuData bean = (MenuData) adapter.getItem(position);
            if (bean != null) {
                switch (bean.type) {
                    case TYPE_H5:
                        break;
                    case TYPE_MOVIE_CINEMA:
                        break;
                    case TYPE_MOVIE_SHOW:
                        break;
                    case TYPE_ONLINE_MOVIE:
                        break;
                }
            }
        }
    };

    BaseQuickAdapter<MenuData, BaseViewHolder> mMenu2Adapter = new BaseQuickAdapter<MenuData, BaseViewHolder>(R.layout.recycle_item_home_head_menu_2) {
        @Override
        protected void convert(BaseViewHolder holder, MenuData item) {
            holder.setText(R.id.tv_title, item.name);

            ImageView ivTitle = holder.getView(R.id.iv_title);
            Glide.with(mContext).load(item.imageUrl).apply(RequestOptions.centerCropTransform().dontAnimate()).into(ivTitle);

            ImageView ivIcon = holder.getView(R.id.iv_icon);
            Glide.with(mContext).load(item.subImageUrl).apply(RequestOptions.centerCropTransform().dontAnimate()).into(ivIcon);
        }

    };
}
