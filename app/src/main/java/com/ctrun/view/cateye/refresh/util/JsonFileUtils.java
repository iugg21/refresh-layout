package com.ctrun.view.cateye.refresh.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author ctrun on 2022/8/8.
 */
public class JsonFileUtils {

   public static String readJsonFromAsset(Context context, String fileName) {
      //将json数据变成字符串
      StringBuilder stringBuilder = new StringBuilder();
      try {
         //获取assets资源管理器
         AssetManager assetManager = context.getAssets();
         //通过管理器打开文件并读取
         BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
         String line;
         while ((line = bf.readLine()) != null) {
            stringBuilder.append(line);
         }
         bf.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      return stringBuilder.toString();
   }
}
