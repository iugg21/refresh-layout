package com.ctrun.view.cateye.refresh.util;

import android.text.TextUtils;

import com.ctrun.view.cateye.refresh.R;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author ctrun on 2021/11/11.
 */
public class MovieVersionHelper {

    public static String[] makeMovieVerStringArray(String verList) {
        if (TextUtils.isEmpty(verList)) {
            return null;
        }

        String[] verArray = verList.split("/");
        HashMap<String, String> map = new HashMap<>(16);
        for (String type : verArray) {
            if ("IMAX 3D".equals(type)) {
                map.put("imax_3d", "IMAX 3D");
            } else if ("杜比影院 3D".equals(type)) {
                map.put("dolby_cinema_3d", "杜比影院");
            } else if ("中国巨幕3D".equals(type)) {
                map.put("cfgs_3d", "中国巨幕");
            } else if ("CINITY 3D".equals(type)) {
                map.put("cinity_3d", "CINITY");
            } else if ("3D".equals(type)) {
                map.put("3d", "3D");
            } else if ("IMAX 2D".equals(type)) {
                if (!map.containsKey("imax_3d")) {
                    map.put("imax_2d", "IMAX 2D");
                }
            } else if ("杜比影院 2D".equals(type)) {
                if (!map.containsKey("dolby_cinema_3d")) {
                    map.put("dolby_cinema_2d", "杜比影院");
                }
            } else if ("中国巨幕2D".equals(type)) {
                if (!map.containsKey("cfgs_3d")) {
                    map.put("cfgs_2d", "中国巨幕");
                }
            } else if ("CINITY 2D".equals(type)) {
                if (!map.containsKey("cinity_3d")) {
                    map.put("cinity_2d", "CINITY");
                }
            }
        }

        if (map.isEmpty()) {
            return null;
        }

        final String[] keys = {"imax_3d", "dolby_cinema_3d", "cfgs_3d", "cinity_3d", "3d", "imax_2d", "dolby_cinema_2d", "cfgs_2d", "cinity_2d"};
        //如果IMAX 3D 或 杜比影院 3D 或 中国巨幕 3D 或 CINITY 3D存在，则删除3D
        if (map.containsKey(keys[0]) || map.containsKey(keys[1]) || map.containsKey(keys[2]) || map.containsKey(keys[3])) {
            map.remove(keys[4]);
        }
        //如果杜比影院 2D存在，则删除 中国巨幕 2D 和 CINITY 2D
        if (map.containsKey(keys[6])) {
            map.remove(keys[7]);
            map.remove(keys[8]);
        }

        for (int i = keys.length - 1; i >= 0; i--) {
            if (map.size() > 2) {
                if (map.containsKey(keys[i])) {
                    map.remove(keys[i]);
                }
            } else {
                break;
            }
        }

        int length = map.size();
        String[] typeStringArray = new String[length];
        if (length <= 1) {
            typeStringArray[0] = map.values().iterator().next();
        } else {
            int count = 0;
            for (int i = 0; i < keys.length; i++) {
                ++count;
                if (map.containsKey(keys[i])) {
                    typeStringArray[0] = map.get(keys[i]);
                    break;
                }
            }

            for (int i = count; i < keys.length; i++) {
                if (map.containsKey(keys[i])) {
                    typeStringArray[1] = map.get(keys[i]);
                    break;
                }
            }
        }

        return typeStringArray;
    }

    public static Integer[] makeMovieVerDrawableIds(String verList) {
        if (TextUtils.isEmpty(verList)) {
            return null;
        }

        String[] verArray = verList.split("/");
        HashMap<String, Integer> drawableIdMap = new HashMap<>(16);
        for (String type : verArray) {
            if ("IMAX 3D".equals(type)) {
                drawableIdMap.put("imax_3d", R.drawable.movie_ic_imax_3d);
            } else if ("杜比影院 3D".equals(type)) {
                drawableIdMap.put("dolby_cinema_3d", R.drawable.movie_ic_dolby_cinema_3d);
            } else if ("中国巨幕3D".equals(type)) {
                drawableIdMap.put("cfgs_3d", R.drawable.movie_ic_cfgs_3d);
            } else if ("CINITY 3D".equals(type)) {
                drawableIdMap.put("cinity_3d", R.drawable.movie_ic_cinity_3d);
            } else if ("3D".equals(type)) {
                drawableIdMap.put("3d", R.drawable.movie_ic_3d_gray);
            } else if ("IMAX 2D".equals(type)) {
                if (!drawableIdMap.containsKey("imax_3d")) {
                    drawableIdMap.put("imax_2d", R.drawable.movie_ic_imax_2d);
                }
            } else if ("杜比影院 2D".equals(type)) {
                if (!drawableIdMap.containsKey("dolby_cinema_3d")) {
                    drawableIdMap.put("dolby_cinema_2d", R.drawable.movie_ic_dolby_cinema_2d);
                }
            } else if ("中国巨幕2D".equals(type)) {
                if (!drawableIdMap.containsKey("cfgs_3d")) {
                    drawableIdMap.put("cfgs_2d", R.drawable.movie_ic_cfgs_2d);
                }
            } else if ("CINITY 2D".equals(type)) {
                if (!drawableIdMap.containsKey("cinity_3d")) {
                    drawableIdMap.put("cinity_2d", R.drawable.movie_ic_cinity_2d);
                }
            }
        }

        if (drawableIdMap.isEmpty()) {
            return null;
        }

        final String[] keys = {"imax_3d", "dolby_cinema_3d", "cfgs_3d", "cinity_3d", "3d", "imax_2d", "dolby_cinema_2d", "cfgs_2d", "cinity_2d"};
        //如果IMAX 3D 或 杜比影院 3D 或 中国巨幕 3D 或 CINITY 3D存在，则删除3D
        if (drawableIdMap.containsKey(keys[0]) || drawableIdMap.containsKey(keys[1]) || drawableIdMap.containsKey(keys[2]) || drawableIdMap.containsKey(keys[3])) {
            drawableIdMap.remove(keys[4]);
        }
        //如果杜比影院 2D存在，则删除 中国巨幕 2D 和 CINITY 2D
        if (drawableIdMap.containsKey(keys[6])) {
            drawableIdMap.remove(keys[7]);
            drawableIdMap.remove(keys[8]);
        }

        for (int i = keys.length - 1; i >= 0; i--) {
            if (drawableIdMap.size() > 2) {
                if (drawableIdMap.containsKey(keys[i])) {
                    drawableIdMap.remove(keys[i]);
                }
            } else {
                break;
            }
        }

        int length = drawableIdMap.size();
        Integer[] drawableIds = new Integer[length];
        if (length <= 1) {
            drawableIds[0] = drawableIdMap.values().iterator().next();
        } else {
            int count = 0;
            for (int i = 0; i < keys.length; i++) {
                ++count;
                if (drawableIdMap.containsKey(keys[i])) {
                    drawableIds[0] = drawableIdMap.get(keys[i]);
                    break;
                }
            }

            for (int i = count; i < keys.length; i++) {
                if (drawableIdMap.containsKey(keys[i])) {
                    drawableIds[1] = drawableIdMap.get(keys[i]);
                    break;
                }
            }
        }

        return drawableIds;
    }

    public static Integer[] makeMovieVerDrawableIds(String typeList, boolean preShow, boolean isRevival) {
        Integer[] drawableIds = makeMovieVerDrawableIds(typeList);

        if (preShow) {
            Integer[] newDrawableIds;
            if (drawableIds != null) {
                newDrawableIds = Arrays.copyOf(drawableIds, drawableIds.length + 1);
                newDrawableIds[newDrawableIds.length - 1] = R.drawable.movie_ic_preview;

            } else {
                newDrawableIds = new Integer[1];
                newDrawableIds[0] = R.drawable.movie_ic_preview;
            }

            return newDrawableIds;
        }

        if (isRevival) {
            Integer[] newDrawableIds;
            if (drawableIds != null) {
                newDrawableIds = Arrays.copyOf(drawableIds, drawableIds.length + 1);
                newDrawableIds[newDrawableIds.length - 1] = R.drawable.movie_ic_revival;

            } else {
                newDrawableIds = new Integer[1];
                newDrawableIds[0] = R.drawable.movie_ic_revival;
            }

            return newDrawableIds;
        }

        return drawableIds;
    }
}
