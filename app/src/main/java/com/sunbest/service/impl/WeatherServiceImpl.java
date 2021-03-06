package com.sunbest.service.impl;

import android.content.Context;
import android.util.Log;

import com.sunbest.listener.AirListener;
import com.sunbest.listener.WeatherListener;
import com.sunbest.model.Air;
import com.sunbest.model.Weather;
import com.sunbest.service.WeatherService;
import com.google.gson.Gson;

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
        HeConfig.init("HE2004091554271830","9d25f4e6ce4d4c148fb38a532b32d495");
    }


    @Override
    public void getWeather(Context context, final WeatherListener weatherListener) {
        //获取常规天气
        HeWeather.getWeatherNow(context, new HeWeather.OnResultWeatherNowBeanListener() {
            Weather weather=new Weather();
            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG,"WeatherNow getError：",throwable);
                setFailureWeather(weather);
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
                    weather.setState(nowBase.getCond_txt());
                    weather.setPnpc(nowBase.getPcpn());
                    weather.setTemp(nowBase.getTmp());
                    weather.setWind_dir(nowBase.getWind_dir());
                    weather.setWind_sc(nowBase.getWind_sc());
                } else {
                    //在此查看返回数据失败的原因
                    String status = now.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i(TAG, "failed code: " + code);
                    setFailureWeather(weather);
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
                Log.e(TAG,"AirNow getError：",throwable);
                setFailureAir(air);
                airListener.onGetAir(air);
            }

            @Override
            public void onSuccess(AirNow airNow) {
                Log.i(TAG,"AirNow getSuccess with status:"+airNow.getStatus());
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
                    Log.i(TAG, "AirNow failed code: " + code);
                    setFailureAir(air);
                }
                airListener.onGetAir(air);
            }
        });
    }

    private void setFailureWeather(Weather weather){
        weather.setPnpc("未知");
        weather.setTemp("未知");
        weather.setState("未知");
        weather.setWind_dir("未知");
        weather.setWind_sc("未知");
    }

    private void setFailureAir(Air air){
        air.setAqi("未知");
        air.setPm10("未知");
        air.setPm25("未知");
    }
}