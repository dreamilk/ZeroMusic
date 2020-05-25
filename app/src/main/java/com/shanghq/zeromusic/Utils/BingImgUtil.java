package com.shanghq.zeromusic.Utils;

import com.google.gson.Gson;
import com.shanghq.zeromusic.bean.BingImgData;

public class BingImgUtil {

    public static String parseBingImgJson(String str){
        Gson gson=new Gson();
        BingImgData bingImgData= gson.fromJson(str,BingImgData.class);
        BingImgData.ImagesBean image=bingImgData.getImages().get(0);
        return image.getUrlbase();

    }
}
