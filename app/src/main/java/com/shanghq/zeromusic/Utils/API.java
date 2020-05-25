package com.shanghq.zeromusic.Utils;

import com.shanghq.zeromusic.Adapter.FindRecyclerViewAdapter;

public class API {

    public static final String songSearch = "http://songsearch.kugou.com/song_search_v2";

    //高德地图 web 获取  ip定位
    public static final String ipLocation = "https://restapi.amap.com/v3/ip?key=9185137784294647d5058b67c3912ecb";

    //中国天气网 H5
    public static final String H5 = "https://apip.weatherdt.com/h5.html?id=EGJkBE7gfb";

    //中国天气网 获取 位置id   +   group   +   location
    public static final String idLocation = "https://search.heweather.com/find?key=0e6b2177d7f3421d8495e805eef57c73&group=cn&location=";

    //中国天气网 获取天气数据 api  +  location
    public static final String weatherApi = "https://apip.weatherdt.com/plugin/data?key=EGJkBE7gfb&location=";

    //中国天气网 背景图 https://apip.weatherdt.com/h5/static/images/bg301n.png
    public static final String backgroundApi = "https://apip.weatherdt.com/h5/static/images/";

    //bing每日一图
    public static final String bingImgApi = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";

    public static final String testMusicAPi = "https://webfs.yun.kugou.com/202001171829/f1168066c83b06cc50b79d2617e4bf89/G121/M04/04/17/WZQEAFwOKz-AL9J_AEWp0xiST_8788.mp3";

    //bing每日一图 手机版
    public static final String bingImgPhoneApi = "https://cn.bing.com/th?id=OHR.Zugspitze_ZH-CN1831794930_480x640.jpg&rf=LaDigue_1920x1080.jpg&pid=hp";

}
