package com.sunbest.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sunbest.listener.AirListener;
import com.sunbest.listener.WeatherListener;
import com.sunbest.model.Air;
import com.sunbest.model.Weather;
import com.sunbest.service.WeatherService;
import com.sunbest.service.impl.WeatherServiceImpl;

public class RoofInfoViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private Application application;
    private MutableLiveData<Weather> weather;
    private MutableLiveData<Air> air;
    private RoofInfoViewModel viewModel = this;

    public void setApplication(Application application) {
        this.application = application;
    }

    public MutableLiveData<Weather> getWeather() {
        if (null == weather) {
            Weather weather = new Weather();
            weather.setState("未知");
            weather.setTemp("未知");
            weather.setWind_dir("未知");
            weather.setWind_sc("未知");
            this.weather = new MutableLiveData<>(weather);
        }
        return weather;
    }

    public void setWeather(MutableLiveData<Weather> weather) {
        this.weather = weather;
    }

    public MutableLiveData<Air> getAir() {
        if (null == air) {
            Air air = new Air();
            air.setAqi("未知");
            air.setPm25("未知");
            this.air = new MutableLiveData<>(air);
        }
        return air;
    }

    public void setAir(MutableLiveData<Air> air) {
        this.air = air;
    }

    /**
     * 加载天气数据
     */
    private void loadWeather() {
        Context appContext = application.getApplicationContext();
        WeatherService weatherService= WeatherServiceImpl.getInstance();
        weatherService.getWeather(appContext, new WeatherListener() {
            @Override
            public void onGetWeather(Weather weather) {
                Log.i("weather", weather.toString());
                viewModel.weather.postValue(weather);
            }
        });
    }


    /**
     * 加载空气数据
     */
    private void loadAir() {
        Context appContext = application.getApplicationContext();
        WeatherService weatherService= WeatherServiceImpl.getInstance();
        weatherService.getAir(appContext, new AirListener() {
            @Override
            public void onGetAir(Air air) {
                Log.i("air", air.toString());
                System.out.println(air);
                viewModel.getAir().postValue(air);
            }
        });
    }
}
