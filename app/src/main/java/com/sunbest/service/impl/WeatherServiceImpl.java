package com.sunbest.service.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sunbest.domain.dto.Weather;
import com.sunbest.service.WeatherService;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNowCity;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class WeatherServiceImpl implements WeatherService {

    private static final String TAG="Weather";

    @Override
    public Weather getWeather(Context context) {
        final Weather weather=new Weather();
        //获取常规天气
        HeWeather.getWeatherNow(context, new HeWeather.OnResultWeatherNowBeanListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG,"WeatherNow getError：",throwable);
            }

            @Override
            public void onSuccess(Now now) {
                Log.i(TAG,"WeatherNow getSuccess with status:"+now.getStatus());
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if ( Code.OK.getCode().equalsIgnoreCase(now.getStatus()) ){
                    Log.i(TAG,"true status");
                    //debug观察数据
                    Log.d(TAG, " Weather Now onSuccess: " + new Gson().toJson(now));
                    //此时返回数据
                    NowBase nowBase = now.getNow();
                    weather.setPnpc(nowBase.getPcpn());
                    weather.setTemp(nowBase.getTmp());
                    weather.setWind_dir(nowBase.getWind_dir());
                    weather.setWind_sc(nowBase.getWind_sc());
                } else {
                    //在此查看返回数据失败的原因
                    String status = now.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });
        //获取空气质量主要pm25
        HeWeather.getAirNow(context, null, null, new HeWeather.OnResultAirNowBeansListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG,"WeatherNow getError：",throwable);
            }

            @Override
            public void onSuccess(AirNow airNow) {
                Log.i(TAG,"WeatherNow getSuccess with status:"+airNow.getStatus());
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if ( Code.OK.getCode().equalsIgnoreCase(airNow.getStatus()) ){
                    Log.i(TAG,"true status");
                    //debug观察数据
                    Log.d(TAG, " Weather Now onSuccess: " + new Gson().toJson(airNow));
                    //此时返回数据
                    AirNowCity airNowCity= airNow.getAir_now_city();
                    weather.setPm25(airNowCity.getPm25());
                } else {
                    //在此查看返回数据失败的原因
                    String status = airNow.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });
        return weather;
    }



}
