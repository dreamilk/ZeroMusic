package com.shanghq.zeromusic.AsyncTask;

import android.os.AsyncTask;

import com.shanghq.zeromusic.Utils.API;
import com.shanghq.zeromusic.Utils.WeatherJsonUtils;
import com.shanghq.zeromusic.bean.IdLocationData;
import com.shanghq.zeromusic.bean.LocationData;
import com.shanghq.zeromusic.bean.WeatherData;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherAsyncTask extends AsyncTask<String, Integer, WeatherData> {

    private onFinishListener listener;

    private OkHttpClient okHttpClient;

    @Override
    protected void onPostExecute(WeatherData data) {
        super.onPostExecute(data);
        listener.onFinish(data);
    }

    public WeatherAsyncTask(OkHttpClient okHttpClient, onFinishListener listener) {
        super();
        this.okHttpClient = okHttpClient;
        this.listener = listener;
    }

    @Override
    protected WeatherData doInBackground(String... strings) {
        String ipLocation = API.ipLocation;
        Request request = new Request.Builder().url(ipLocation).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            LocationData data = WeatherJsonUtils.parseLocationJson(response.body().string());


            String idLocation = API.idLocation + data.getAdcode();

            Request request1 = new Request.Builder().url(idLocation).build();
            Response response1 = okHttpClient.newCall(request1).execute();

            if (!response1.isSuccessful()) {
                return null;
            }
            IdLocationData.HeWeather6Bean.BasicBean basicBean = WeatherJsonUtils.parseIdLocationJson(response1.body().string());

            String weatherData = API.weatherApi + basicBean.getCid().substring(2);
            Request request2 = new Request.Builder().url(weatherData).build();
            Response response2 = okHttpClient.newCall(request2).execute();

            if (!response2.isSuccessful()) {
                return null;
            }

            return WeatherJsonUtils.parseWeatherJson(response2.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    public interface onFinishListener {
        void onFinish(WeatherData weatherData);
    }
}
