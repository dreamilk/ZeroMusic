package com.shanghq.zeromusic;

import android.app.Application;
import android.icu.text.IDNA;

import com.shanghq.zeromusic.Utils.RealmUtils;
import com.shanghq.zeromusic.bean.InfoBean;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MyApplication extends Application {

    private static MyApplication instance;


    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(configuration);

    }


}
