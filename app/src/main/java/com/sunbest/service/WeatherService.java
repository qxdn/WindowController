package com.sunbest.service;

import android.content.Context;

import com.sunbest.listener.AirListener;
import com.sunbest.listener.WeatherListener;

public interface WeatherService {

    /**
     * 获取天气 按照默认地址 需要开启GPS和网络功能
     * @param context
     * @param weatherListener
     */
    public void getWeather(Context context, WeatherListener weatherListener);

    /**
     * 获取空气 按照默认地址 需要开启GPS和网络功能
     * @param context
     * @param airListener
     */
    public void getAir(Context context, AirListener airListener);
}
