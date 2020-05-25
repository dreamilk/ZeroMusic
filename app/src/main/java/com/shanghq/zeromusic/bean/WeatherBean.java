package com.shanghq.zeromusic.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WeatherBean extends RealmObject {

    @PrimaryKey
    private String version;

    private String temp;//温度
    private String position;//位置
    private String text;//描述

    private String backgroundUrl;//背景图
    private String condUrl;//图标图

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getCondUrl() {
        return condUrl;
    }

    public void setCondUrl(String condUrl) {
        this.condUrl = condUrl;
    }
}
