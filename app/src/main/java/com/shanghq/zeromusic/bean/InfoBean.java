package com.shanghq.zeromusic.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class InfoBean extends RealmObject {

    @PrimaryKey
    private String uerId;

    private String lastUrl;
    private String lastTime;

    public InfoBean(){
        uerId="1";
        lastUrl="";
        lastTime="";
    }

    public String getUerId() {
        return uerId;
    }

    public void setUerId(String uerId) {
        this.uerId = uerId;
    }

    public String getLastUrl() {
        return lastUrl;
    }

    public void setLastUrl(String lastUrl) {
        this.lastUrl = lastUrl;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
