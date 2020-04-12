package com.sunbest.service;

import android.content.Context;

import com.sunbest.listener.MqttMessageListener;
import com.sunbest.model.MqttSetting;


public interface MqttClientService {
    /**
     * 初始化整个app调用一次
     * @param context
     * @param mqttSetting  mqtt相关设置
     * @param mqttMessageListener mqtt传回消息的回调
     */
    public void init(Context context, MqttSetting mqttSetting, MqttMessageListener mqttMessageListener);

    /**
     * 是否连接上服务器
     * @return
     */
    public boolean isConnected();

    /**
     * 控制角度
     * @param angle
     */
    public void controlAngle(double angle);

    /**
     * 控制天窗开合
     * @param winId 控制的天窗
     * @param state 天窗状态 true为开
     */
    public void controlWindows(Integer winId, Boolean state);

    /**
     * 向硬件发起获取屋顶状态请求 结果在回调中
     */
    public void toGetRoofState();

    /**
     * 向硬件请求电状态
     */
    public void toGetElectricState();


}
