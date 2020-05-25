package com.shanghq.zeromusic;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.shanghq.zeromusic.Adapter.SearchRecyclerViewAdapter;
import com.shanghq.zeromusic.Utils.API;
import com.shanghq.zeromusic.Utils.SearchMusicJsonUtils;
import com.shanghq.zeromusic.bean.SongBean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private List<SongBean> songBeanList;

    private Context context;

    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(getColor(R.color.colorPrimary));

        context=this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        searchView = findViewById(R.id.app_bar_search_search);
        searchView.setIconifiedByDefault(false);
        songBeanList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView_search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(this,songBeanList);
        recyclerView.setAdapter(searchRecyclerViewAdapter);
        recyclerView.setLayoutManager(layoutManager);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                new MyAsyncTask().execute(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    //网络请求

    private class MyAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String test=API.songSearch + "?keyword=" + strings[0] + "&page=1&pagesize=30";
//            Log.d("test",test);
            Request request = new Request.Builder().url(test).build();
            try {

                Response response = okHttpClient.newCall(request).execute();

                if (response.isSuccessful()) {
                    return response.body().string();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s==null){
                Toast.makeText(context,"查找音乐失败",Toast.LENGTH_SHORT).show();
                return;
            }
            songBeanList =SearchMusicJsonUtils.parseMusicJson(s);
            searchRecyclerViewAdapter.setSongBeanList(songBeanList);
            searchRecyclerViewAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
