package com.shanghq.zeromusic.AsyncTask;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.text.Html;

import com.shanghq.zeromusic.Utils.API;
import com.shanghq.zeromusic.Utils.BingImgUtil;
import com.shanghq.zeromusic.bean.BingImgData;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BingImgAsyncTask extends AsyncTask<String, Integer, String> {

    private OkHttpClient okHttpClient;

    private onFinishListener listener;

    public BingImgAsyncTask(OkHttpClient okHttpClient,onFinishListener listener) {
        super();
        this.okHttpClient = okHttpClient;
        this.listener=listener;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.onFinish(s);
    }

    @Override
    protected String doInBackground(String... strings) {


        Request request = new Request.Builder().url(API.bingImgApi).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                return "";
            }

            String url = "https://cn.bing.com" + BingImgUtil.parseBingImgJson(response.body().string())
                    + "_720x1280.jpg&rf=LaDigue_1920x1080.jpg&pid=hp";

            return url;



        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }

    public interface onFinishListener{
        void onFinish(String url);
    }
}
