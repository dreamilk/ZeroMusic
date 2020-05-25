package com.shanghq.zeromusic.Utils;

import com.google.gson.Gson;
import com.shanghq.zeromusic.bean.MusicData;
import com.shanghq.zeromusic.bean.SongBean;

import java.util.ArrayList;
import java.util.List;

public class SearchMusicJsonUtils {

    public static List<SongBean> parseMusicJson(String s){
        Gson gson=new Gson();
        MusicData musicData =gson.fromJson(s, MusicData.class);

        List<SongBean> songBeans =new ArrayList<>();
        List<MusicData.DataBean.ListsBean> listsBeans= musicData.getData().getLists();

        for(int i=0;i<listsBeans.size();i++){
            SongBean songBean =new SongBean(listsBeans.get(i).getSongName(),listsBeans.get(i).getSingerName(),"src");
            songBean.setAlbum(listsBeans.get(i).getAlbumName());
            songBean.setDuration(listsBeans.get(i).getDuration());
            songBeans.add(songBean);
        }
        return songBeans;
    }
}
