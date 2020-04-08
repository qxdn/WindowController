package com.sunbest.service;

import android.content.Context;

import com.sunbest.domain.dto.Weather;

public interface WeatherService {

    /**
     * 获取天气 按照默认地址 需要开启GPS和网络功能
     * @param context
     * @return Weather数据
     */
    public Weather getWeather(Context context);
}
