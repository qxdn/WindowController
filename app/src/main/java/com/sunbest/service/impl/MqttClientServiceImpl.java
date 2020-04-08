package com.sunbest.service.impl;

import com.sunbest.service.MqttClientService;

import org.eclipse.paho.android.service.MqttAndroidClient;

public class MqttClientServiceImpl implements MqttClientService {
    private static final String TAG="MQTT";

    //mqtt服务器位置
    private String host="host";
    //订阅信息
    private String[] subTopics;
    //发布信息
    private String[] pubTopics;
    //mqttClient
    private MqttAndroidClient client;

    private static MqttClientServiceImpl instance=new MqttClientServiceImpl();

    private MqttClientServiceImpl(){

    }

    public static MqttClientServiceImpl getInstance() {
        return instance;
    }
}
