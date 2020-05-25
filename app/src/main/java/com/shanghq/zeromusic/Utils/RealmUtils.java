package com.shanghq.zeromusic.Utils;

import android.text.BoringLayout;
import android.util.Log;

import com.shanghq.zeromusic.bean.SongBean;
import com.shanghq.zeromusic.bean.UserBean;
import com.shanghq.zeromusic.bean.WeatherBean;
import com.shanghq.zeromusic.bean.WeatherData;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmUtils {

    private static Realm realm;

    public static void openRealm() {
        realm = Realm.getDefaultInstance();
    }

    public static void closeRealm() {
        realm.close();
    }

    public static void Login(String id, String name, String password) {

        realm.beginTransaction();
        UserBean userBean = realm.createObject(UserBean.class);
        userBean.setUserId(id);
        userBean.setUserName(name);
        userBean.setPassWord(password);
        realm.commitTransaction();

    }


    public static boolean hasLogin() {

        RealmResults<UserBean> userBeans = realm.where(UserBean.class).findAll();
        if (!userBeans.isEmpty()) {
            return true;
        }

        return false;
    }

    public static void saveMusic(List<SongBean> songBeans) {
        realm.beginTransaction();
        realm.insert(songBeans);
        realm.commitTransaction();

    }

    public static List<SongBean> getRealmMusic() {

        RealmResults<SongBean> results = realm.where(SongBean.class).findAll();
        return results;
    }


    public static void saveWeather(final WeatherBean weatherBean) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                WeatherBean bean = realm.where(WeatherBean.class).findFirst();

                if (bean == null) {
                    Log.d("realm test", "空");
                }

                bean.setCondUrl(weatherBean.getCondUrl());
                bean.setBackgroundUrl(weatherBean.getBackgroundUrl());
                bean.setPosition(weatherBean.getPosition());
                bean.setTemp(weatherBean.getTemp());
                bean.setText(weatherBean.getText());

                realm.copyToRealmOrUpdate(bean);
            }
        });
    }


    public static WeatherBean getLastWeather() {
        WeatherBean bean = realm.where(WeatherBean.class).findFirst();
        if (bean != null) {
            return bean;
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                WeatherBean bean1 = realm.createObject(WeatherBean.class, "1");
                bean1.setText("暂无数据");
            }
        });

        return realm.where(WeatherBean.class).findFirst();
    }


    public static void workOnRealm(final OwnTransaction transaction) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                transaction.finish();
            }
        });
    }

    public interface OwnTransaction {
        void finish();
    }

}
