package com.shanghq.zeromusic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.shanghq.zeromusic.Adapter.FindRecyclerViewAdapter;
import com.shanghq.zeromusic.Adapter.MyRecyclerViewAdapter;
import com.shanghq.zeromusic.AsyncTask.WeatherAsyncTask;
import com.shanghq.zeromusic.BroadcastReceiver.NetworkChangeReceiver;
import com.shanghq.zeromusic.Utils.API;
import com.shanghq.zeromusic.Utils.AppSystemUtil;
import com.shanghq.zeromusic.Utils.MusicUtils;
import com.shanghq.zeromusic.Utils.RealmUtils;
import com.shanghq.zeromusic.Utils.WeatherJsonUtils;
import com.shanghq.zeromusic.bean.IdLocationData;
import com.shanghq.zeromusic.bean.LocationData;
import com.shanghq.zeromusic.bean.SongBean;
import com.shanghq.zeromusic.bean.WeatherBean;
import com.shanghq.zeromusic.bean.WeatherData;
import com.shanghq.zeromusic.service.MusicService;
import com.shanghq.zeromusic.views.MusicInformationDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<View> viewList;
    private View view1;
    private View view2;
    private View view3;

    private LinearLayout playView;

    private Context context;

    //播放窗口的控件
    private ImageView imgPlayNow;
    private TextView tvPlayNowTitle;
    private TextView tvPlayNowSinger;
    private ImageButton imgBtPre;
    private ImageButton imgBtPlay;
    private ImageButton imgBtNext;

    private SearchView searchViewFind;
    private DrawerLayout drawerLayout;
    private SearchView searchView;

    private RecyclerView recyclerViewFind;
    private RecyclerView recyclerViewMy;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private FindRecyclerViewAdapter findRecyclerViewAdapter;

    private NavigationView navigationView;
    private View headerView;

    //headerView里面的 天气控件

    private ImageView imgWeather;
    private TextView tvCity;
    private TextView tvTemp;
    private ImageView imgWeatherCond;
    private TextView tvText;
    private WeatherBean mWeatherBean = new WeatherBean();

    private boolean isOpenReceiver;

    //与音乐控制有关的
    private MediaBrowserCompat mediaBrowser;
    private MediaBrowserCompat.ConnectionCallback connectionCallback = new MediaBrowserCompat.ConnectionCallback() {
        @Override
        public void onConnected() {
            MediaSessionCompat.Token token = mediaBrowser.getSessionToken();
            try {
                MediaControllerCompat mediaController = new MediaControllerCompat(MainActivity.this, token);

                MediaControllerCompat.setMediaController(MainActivity.this, mediaController);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            // Finish building the UI
            buildTransportControls();


        }

        @Override
        public void onConnectionFailed() {
            super.onConnectionFailed();
        }
    };

    private void buildTransportControls() {

        final MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(MainActivity.this);

        // Display the initial state
        MediaMetadataCompat metadata = mediaController.getMetadata();
        PlaybackStateCompat pbState = mediaController.getPlaybackState();


        imgBtPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pbState = MediaControllerCompat.getMediaController(MainActivity.this).getPlaybackState().getState();
                if (pbState == PlaybackStateCompat.STATE_PLAYING) {
                    MediaControllerCompat.getMediaController(MainActivity.this).getTransportControls().pause();
                } else {
                    MediaControllerCompat.getMediaController(MainActivity.this).getTransportControls().play();
                }
            }
        });

        if (pbState.getState() == PlaybackStateCompat.STATE_PLAYING) {
            imgBtPlay.setImageDrawable(getDrawable(R.drawable.ic_pause_blue_700_36dp));
        } else {
            imgBtPlay.setImageDrawable(getDrawable(R.drawable.ic_play_arrow_blue_700_36dp));
        }

        imgBtPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaControllerCompat.getMediaController(MainActivity.this).getTransportControls().skipToPrevious();
            }
        });

        imgBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaControllerCompat.getMediaController(MainActivity.this).getTransportControls().skipToNext();

            }
        });

        if (metadata != null) {
            tvPlayNowTitle.setText(metadata.getDescription().getTitle());
            tvPlayNowSinger.setText(metadata.getDescription().getSubtitle());
        }

        // Register a Callback to stay in sync
        mediaController.registerCallback(callback);
    }


    private MediaControllerCompat.Callback callback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {

            switch (state.getState()) {
                case PlaybackStateCompat.STATE_PAUSED:
                    Log.d("MainActivity", "play to pause");
                    imgBtPlay.setImageDrawable(getDrawable(R.drawable.ic_play_arrow_blue_700_36dp));
                    break;
                case PlaybackStateCompat.STATE_PLAYING:
                    Log.d("MainActivity", "play to play");
                    imgBtPlay.setImageDrawable(getDrawable(R.drawable.ic_pause_blue_700_36dp));
                    break;
            }
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {

            tvPlayNowTitle.setText(metadata.getDescription().getTitle());
            tvPlayNowSinger.setText(metadata.getDescription().getSubtitle());


        }
    };


    private List<SongBean> songBeanList;

    private NetworkChangeReceiver networkChangeReceiver;

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private WeatherAsyncTask.onFinishListener weatherFinishListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setNavigationBarColor(getColor(R.color.colorPrimary));

        //打开数据库
        RealmUtils.openRealm();

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

        context = this;


        initViews();


        //更新drawerLayout的天气数据
        navigationView = findViewById(R.id.navigationView);
        headerView = navigationView.getHeaderView(0);
        imgWeather = headerView.findViewById(R.id.img_weather);
        tvCity = headerView.findViewById(R.id.tv_city);
        tvTemp = headerView.findViewById(R.id.tv_temp);
        tvText = headerView.findViewById(R.id.tv_text);
        imgWeatherCond = headerView.findViewById(R.id.img_weather_cond);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_setting: {
                        Intent intent = new Intent(context, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.menu_about: {
                        Intent intent = new Intent(context, AboutActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //设置播放窗口
        playView = findViewById(R.id.playView);
        playView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);
                startActivity(intent);
            }
        });


        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.pager1, null);
        view2 = inflater.inflate(R.layout.pager2, null);
        view3 = inflater.inflate(R.layout.pager3, null);
        viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);


        final List<String> tabTitles = new ArrayList<>();
        tabTitles.add("我的");
        tabTitles.add("发现");
        tabTitles.add("广场");
        PagerAdapter pagerAdapter = new PagerAdapter() {

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles.get(position);
            }

            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(viewList.get(position));
            }
        };
        viewPager.setAdapter(pagerAdapter);


        songBeanList = new ArrayList<>();
        if (hasExternalStoragePermission()) {
            //获取音乐文件

            songBeanList = RealmUtils.getRealmMusic();

        } else {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            }, 0);
        }

        recyclerViewFind = view2.findViewById(R.id.recyclerView_main_find);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        findRecyclerViewAdapter = new FindRecyclerViewAdapter(songBeanList);
        recyclerViewFind.setLayoutManager(layoutManager);
        recyclerViewFind.setAdapter(findRecyclerViewAdapter);

        searchViewFind = view2.findViewById(R.id.searchView_main_find);
        searchViewFind.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(context, query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        recyclerViewMy = view1.findViewById(R.id.recyclerView_my);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(context, songBeanList);

        myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(int position) {
                SongBean song = songBeanList.get(position);

                //判断当前 音乐 是否在 列表中
                List<MediaSessionCompat.QueueItem> queueItemList = MediaControllerCompat.getMediaController(MainActivity.this)
                        .getQueue();

                for (int i = 0; i < queueItemList.size(); i++) {
                    MediaSessionCompat.QueueItem item = queueItemList.get(i);

                    if (item.getDescription().getMediaId().equals(song.getId() + "")) {
                        Toast.makeText(context, "当前音乐在列表中", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }


                Toast.makeText(context, "添加到播放列表", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putLong("id", song.getId());
                bundle.putString("title", song.getTitle());
                bundle.putString("album", song.getAlbum());
                bundle.putLong("albumId", song.getAlbumId());
                bundle.putInt("duration", song.getDuration());
                bundle.putString("singer", song.getSinger());
                bundle.putString("path", song.getPath());
                MediaControllerCompat.getMediaController(MainActivity.this).
                        getTransportControls().sendCustomAction(MusicService.actionAdd, bundle);
            }

            @Override
            public void onMoreClickListener(int position) {

                MusicInformationDialog dialog = new MusicInformationDialog(context, R.style.Theme_AppCompat_Dialog);
                dialog.show();

            }
        });

        recyclerViewMy.setLayoutManager(layoutManager1);
        recyclerViewMy.setAdapter(myRecyclerViewAdapter);


        loadWeather();


        initMusic();

    }

    private void initViews() {

        //与播放窗口 有关的控件放在这里
        imgPlayNow = findViewById(R.id.img_play_now_img);
        tvPlayNowSinger = findViewById(R.id.tv_play_now_singer);
        tvPlayNowTitle = findViewById(R.id.tv_play_now_title);
        imgBtNext = findViewById(R.id.bt_play_now_next);
        imgBtPlay = findViewById(R.id.bt_play_now_start);
        imgBtPre = findViewById(R.id.bt_play_now_pre);


    }

    private void initMusic() {
        startService(new Intent(this, MusicService.class));


        //需要连接 service session
        mediaBrowser = new MediaBrowserCompat(this,
                new ComponentName(this, MusicService.class),
                connectionCallback, null);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaBrowser.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (MediaControllerCompat.getMediaController(MainActivity.this) != null) {
            MediaControllerCompat.getMediaController(MainActivity.this).unregisterCallback(callback);

        }
        mediaBrowser.disconnect();
    }

    private void loadWeather() {

        mWeatherBean = RealmUtils.getLastWeather();


        weatherFinishListener = new WeatherAsyncTask.onFinishListener() {
            @Override
            public void onFinish(final WeatherData weatherData) {
                if (weatherData == null) {
//                Toast.makeText(context, "获取天气数据失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                tvCity.setText(weatherData.getBasic().getCity());
                tvTemp.setText(weatherData.getNow().getTmp() + "°");
                tvText.setText(weatherData.getNow().getTxt());

                String picUrl = API.backgroundApi + "bg" + weatherData.getNow().getCode();
                String picCondUrl = API.backgroundApi + "cond-" + weatherData.getNow().getCode();
                if (weatherData.getBasic().getUpdateTime().compareTo("18:00") < 0 &&
                        weatherData.getBasic().getUpdateTime().compareTo("06:00") > 0) {
                    picUrl += "d.png";
                    picCondUrl += "d.png";
                } else {
                    picUrl += "n.png";
                    picCondUrl += "n.png";
                }

                final String finalPicUrl = picUrl;
                final String finalPicCondUrl = picCondUrl;

                //在realme事物中 操作
                RealmUtils.workOnRealm(new RealmUtils.OwnTransaction() {
                    @Override
                    public void finish() {
                        mWeatherBean.setText(weatherData.getNow().getTxt());
                        mWeatherBean.setTemp(weatherData.getNow().getTmp());
                        mWeatherBean.setPosition(weatherData.getBasic().getCity());
                        mWeatherBean.setBackgroundUrl(finalPicUrl);
                        mWeatherBean.setCondUrl(finalPicCondUrl);
                    }
                });

                Glide.with(context).load(picUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgWeather);
                Glide.with(context).load(picCondUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgWeatherCond);
            }
        };

        if (AppSystemUtil.isConnected(context)) {
            isOpenReceiver = false;

            new WeatherAsyncTask(okHttpClient, weatherFinishListener).execute();
        } else {

            //如果没有网络  需要打开 广播
            isOpenReceiver = true;
//            Log.d("test",bean.getBackgroundUrl());

            tvText.setText(mWeatherBean.getText());
            tvTemp.setText(mWeatherBean.getTemp() + "°");
            tvCity.setText(mWeatherBean.getPosition());
            Glide.with(context).load(mWeatherBean.getBackgroundUrl()).into(imgWeather);
            Glide.with(context).load(mWeatherBean.getCondUrl()).into(imgWeatherCond);

            ListenNetwork();
        }

    }

    //监听网络变化
    private void ListenNetwork() {
        if (networkChangeReceiver == null) {
            networkChangeReceiver = new NetworkChangeReceiver();
            networkChangeReceiver.setListener(new NetworkChangeReceiver.NetworkChangeListener() {
                @Override
                public void onChangeListener() {
                    new WeatherAsyncTask(okHttpClient, weatherFinishListener).execute();
                }
            });
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isOpenReceiver) {
            unregisterReceiver(networkChangeReceiver);
        }


        //保存当前音乐数据



//        RealmUtils.saveWeather(mWeatherBean);
//
        //关闭数据库
        RealmUtils.closeRealm();

    }

    //判断是否拥有权限
    private boolean hasExternalStoragePermission() {
        int perm1 = context.checkCallingOrSelfPermission(
                "android.permission.WRITE_EXTERNAL_STORAGE");
        int perm2 = context.checkCallingOrSelfPermission(
                "android.permission.READ_EXTERNAL_STORAGE");

        if (perm1 == PackageManager.PERMISSION_GRANTED && perm2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    //获取音乐文件
    private List<SongBean> getLocalSongList() {

        RealmUtils.saveMusic(MusicUtils.scanMusic(this));

        return MusicUtils.scanMusic(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search_main);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
                return false;
            }
        });

        return true;
    }


    //申请权限 返回
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //获取音乐文件
                myRecyclerViewAdapter.setSongBeanList(getLocalSongList());
                songBeanList = getLocalSongList();
                myRecyclerViewAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(context, "储存权限获取失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
