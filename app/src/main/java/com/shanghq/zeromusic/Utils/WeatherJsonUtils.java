package com.shanghq.zeromusic.Utils;

import com.google.gson.Gson;
import com.shanghq.zeromusic.bean.IdLocationData;
import com.shanghq.zeromusic.bean.LocationData;
import com.shanghq.zeromusic.bean.WeatherData;

public class WeatherJsonUtils {

    //解析 高德地图 ipLocation 数据

    public static LocationData  parseLocationJson(String str){
        Gson gson=new Gson();
        return gson.fromJson(str,LocationData.class);
    }

    //解析  heWeather的 location id
    public static IdLocationData.HeWeather6Bean.BasicBean parseIdLocationJson(String str){
        Gson gson=new Gson();
        IdLocationData idLocationData=gson.fromJson(str,IdLocationData.class);
        return idLocationData.getHeWeather6().get(0).getBasic().get(0);
    }

    public static WeatherData parseWeatherJson(String str){
        Gson gson=new Gson();
        return gson.fromJson(str,WeatherData.class);
    }
}
