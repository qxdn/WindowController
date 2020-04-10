package com.sunbest.service.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sunbest.domain.dto.Air;
import com.sunbest.domain.dto.Weather;
import com.sunbest.listener.AirListener;
import com.sunbest.listener.WeatherListener;
import com.sunbest.service.WeatherService;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNowCity;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class WeatherServiceImpl implements WeatherService {

    private static final String TAG="Weather";

    private static final WeatherService instance=new WeatherServiceImpl();

    public static WeatherService getInstance() {
        return instance;
    }

    private WeatherServiceImpl(){
        HeConfig.init("uname","key");
    }


    @Override
    public void getWeather(Context context, final WeatherListener weatherListener) {
        //获取常规天气
        HeWeather.getWeatherNow(context, new HeWeather.OnResultWeatherNowBeanListener() {
            Weather weather=new Weather();

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG,"WeatherNow getError：",throwable);
                weatherListener.onGetWeather(weather);
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
                weatherListener.onGetWeather(weather);
            }
        });
    }

    @Override
    public void getAir(Context context, final AirListener airListener) {
        //获取空气质量主要pm25
        HeWeather.getAirNow(context, null, null, new HeWeather.OnResultAirNowBeansListener() {
            Air air=new Air();
            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG,"WeatherNow getError：",throwable);
                airListener.onGetAir(air);
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
                    air.setAqi(airNowCity.getAqi());
                    air.setPm25(airNowCity.getPm25());
                    air.setPm10(airNowCity.getPm10());
                } else {
                    //在此查看返回数据失败的原因
                    String status = airNow.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i(TAG, "failed code: " + code);
                }
                airListener.onGetAir(air);
            }
        });
    }
}