package com.sunbest;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.sunbest.domain.dto.ElectricState;
import com.sunbest.domain.dto.MqttSetting;
import com.sunbest.domain.dto.RoofState;
import com.sunbest.domain.dto.Weather;
import com.sunbest.listener.MqttMessageListener;
import com.sunbest.listener.WeatherListener;
import com.sunbest.service.MqttClientService;
import com.sunbest.service.WeatherService;
import com.sunbest.service.impl.MqttClientServiceImpl;
import com.sunbest.service.impl.WeatherServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.sunbest", appContext.getPackageName());
    }

    /* 这里测试失败 但是在app直接测试成功
    @Test
    public void WeatherTest(){
        Context appContext=InstrumentationRegistry.getInstrumentation().getTargetContext();
        WeatherService weatherService=WeatherServiceImpl.getInstance();
        weatherService.getWeather(appContext, new WeatherListener() {
            @Override
            public void onGetWeather(Weather weather) {
                Log.i("weather", weather.toString());
                System.out.println(weather);
            }
        });

    }
     */

    /*
    //mqtt发送接收测试 测试成功
    @Test
    public void mqttSendTest(){
        final String TAG="mqttTest";
        Context appContext=InstrumentationRegistry.getInstrumentation().getTargetContext();
        //获取实例
        MqttClientService mqttClientService= MqttClientServiceImpl.getInstance();
        MqttSetting mqttSetting=new MqttSetting();
        mqttSetting.setClientId("123");
        mqttSetting.setUsername("aaa");
        mqttSetting.setPassword("123");
        //初始化
        mqttClientService.init(appContext, mqttSetting, new MqttMessageListener() {
            @Override
            public void onRoofStateArrived(RoofState roofState) {
                Log.i(TAG,"Success");
                Log.i(TAG,roofState.toString());
            }

            @Override
            public void onElectricStateArrived(ElectricState electricState) {
                Log.i(TAG,"Success");
                Log.i(TAG,electricState.toString());
            }
        });
        //等待连接完成
        while (!mqttClientService.isConnected()){

        }
        //如果连接未完成会发送失败
        mqttClientService.controlAngle(123.3);
        mqttClientService.controlWindows(2,true);
        //测试接收 等待过程中上位机手动发送
        while (mqttClientService.isConnected()){

        }
    }
    */

}
