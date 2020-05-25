package com.shanghq.zeromusic.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserBean extends RealmObject {

    private String userId;

    private String userName;
    private String passWord;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
