package com.shanghq.zeromusic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shanghq.zeromusic.AsyncTask.BingImgAsyncTask;
import com.shanghq.zeromusic.Utils.AppSystemUtil;
import com.shanghq.zeromusic.Utils.RealmUtils;
import com.shanghq.zeromusic.bean.InfoBean;

import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import okhttp3.OkHttpClient;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;
    private Context mContext;

    private LinearLayout layout;

    private Realm realm;

    private String mUrl;


    private TextView tvMore;
    private TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContext = this;
        imageView = findViewById(R.id.img_splash);
        layout = findViewById(R.id.layout_splash_bottom);
        tvMore = findViewById(R.id.tv_splash_more);
        tvSkip = findViewById(R.id.tv_splash_skip);


        realm = Realm.getDefaultInstance();

        initUrl();


        initNotificationChannel();


        if (AppSystemUtil.isConnected(this)) {

//            Log.d("connected", "1");


            new BingImgAsyncTask(new OkHttpClient(), new BingImgAsyncTask.onFinishListener() {

                @Override
                public void onFinish(String url) {
                    mUrl = url;
                    Glide.with(mContext).load(mUrl).into(imageView);
                    layout.setBackgroundColor(Color.WHITE);

                }
            }).execute();

        } else {
//            Log.d("connected", "2");

            Glide.with(mContext).load(mUrl).into(imageView);
            layout.setBackgroundColor(Color.WHITE);


        }


        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                openNewActivity();
            }
        };

        timer.schedule(timerTask, 3000);


        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
                timer.cancel();
            }
        });

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void initNotificationChannel() {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void initUrl() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                InfoBean infoBean = realm.where(InfoBean.class).findFirst();
                if (infoBean == null) {
                    InfoBean infoBean1 = realm.createObject(InfoBean.class, "1");
                    mUrl = infoBean1.getLastUrl();
                } else {
                    mUrl = infoBean.getLastUrl();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                InfoBean infoBean = realm.where(InfoBean.class).findFirst();
                infoBean.setLastTime(AppSystemUtil.getNowTime());
                infoBean.setLastUrl(mUrl);
                realm.copyToRealmOrUpdate(infoBean);
            }
        });

        realm.close();

    }

    private void openNewActivity() {

        RealmUtils.openRealm();

        if (RealmUtils.hasLogin()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        RealmUtils.closeRealm();
    }

}
