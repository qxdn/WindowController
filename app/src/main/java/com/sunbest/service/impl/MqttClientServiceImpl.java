package com.sunbest.service.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sunbest.listener.MqttMessageListener;
import com.sunbest.model.ElectricState;
import com.sunbest.model.HardwareState;
import com.sunbest.model.MqttSetting;
import com.sunbest.model.RoofState;
import com.sunbest.model.WindowsState;
import com.sunbest.service.MqttClientService;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

public class MqttClientServiceImpl implements MqttClientService {
    private static final String TAG="MQTT";

    //mqtt服务器位置
    private String host="tcp://101.133.235.188:1883";
    //订阅信息
    private String[] subTopics=new String[]{"electric","roof","hardwareState"};
    //发布信息主题
    private String angleTopic="angle";
    private String windowsTopic="windows";
    private String RequestRoofTopic="getRoof";
    private String RequestElectricTopic="getElectric";
    private String emergencyTopic="emergency";
    private String smartTopic="smart";
    //mqttClient
    private MqttAndroidClient client;
    //回调
    private MqttMessageListener mqttMessageListener;

    //单例模式
    private static MqttClientServiceImpl instance=new MqttClientServiceImpl();

    private MqttClientServiceImpl(){

    }

    /**
     * 获取实例
     */
    public static MqttClientService getInstance() {
        return instance;
    }

    @Override
    public void init(Context context, MqttSetting mqttSetting, MqttMessageListener mqttMessageListener){
        this.mqttMessageListener=mqttMessageListener;
        //创建mqtt客户端
        client=new MqttAndroidClient(context,host,mqttSetting.getClientId());
        //设置连接参数
        MqttConnectOptions mqttConnectOptions=new MqttConnectOptions();
        //设置自动重连
        mqttConnectOptions.setAutomaticReconnect(true);
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录
        // 这里设置为true表示每次连接到服务器都以新的身份连接
        mqttConnectOptions.setCleanSession(true);
        //设置用户名
        mqttConnectOptions.setUserName(mqttSetting.getUsername());
        //设置密码
        mqttConnectOptions.setPassword(mqttSetting.getPassword().toCharArray());
        //设置超时时间 单位为秒
        mqttConnectOptions.setConnectionTimeout(10);
        //设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        mqttConnectOptions.setKeepAliveInterval(20);
        //设置回调函数
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.i(TAG, "connectionLost: ", cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d(TAG, "messageArrived: topic:"+topic+",message:"+message);
                Log.i(TAG, "messageArrived");
                //TODO: 处理返回的消息
                convertMessage(topic,message.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.i(TAG, "deliveryComplete");
            }
        });
        //连接
        try {
            client.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i(TAG, "MQTT connected success");
                    //订阅
                    subscribe();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i(TAG, "MQTT connected failure:",exception);
                }
            });
        } catch (MqttException e) {
            Log.e(TAG, "MQTT connect error",e);
        }
    }


    @Override
    public void controlAngle(double angle) {
        publish(angleTopic,String.valueOf(angle));
    }

    @Override
    public void controlWindows(Integer winId, Boolean state) {
        publish(windowsTopic,winId.toString()+","+state.toString());
    }

    @Override
    public void toGetRoofState() {
        publish(RequestRoofTopic,"get");
    }

    @Override
    public void toGetElectricState() {
        publish(RequestElectricTopic,"get");
    }

    @Override
    public boolean isConnected() {
        if(client==null) {
            return false;
        }
        return client.isConnected();
    }

    @Override
    public void emergency() {
        publish(emergencyTopic,"true");
    }

    @Override
    public void setSmartControl(boolean isSmart) {
        publish(smartTopic,isSmart+"");
    }

    /**
     *  发送mqtt消息
     * @param topic 主题
     * @param msg   消息
     */
    private void publish(String topic,String msg){
        //设置优先级
        int qos = 0;
        //是否被服务器保留
        boolean retained = false;
        try {
            client.publish(topic,msg.getBytes(),qos,retained);
        } catch (MqttException e) {
            Log.e(TAG,"MQTT publish Failure",e);
        }
    }

    /**
     * 订阅消息
     */
    private void subscribe(){
        //优先级
        int qos=0;
        for (int i=0;i<subTopics.length;i++){
            try {
                //订阅消息
                client.subscribe(subTopics[i],qos);
            } catch (MqttException e) {
                Log.e(TAG, "subscribe failure",e);
            }
        }
    }

    /**
     * 处理返回的消息
     * @param topic
     * @param msg
     */
    private void convertMessage(String topic,String msg){
        switch (topic){
            //电信息
            case "electric":
                ElectricState electricState=convertElectricState(msg);
                mqttMessageListener.onElectricStateArrived(electricState);
                break;
            //屋顶信息
            case "roof":
                RoofState roofState=convertRoofState(msg);
                mqttMessageListener.onRoofStateArrived(roofState);
                break;
            case "hardwareState":
                HardwareState hardwareState=convertHardwareState(msg);
                mqttMessageListener.onHardwareStateArrived(hardwareState);
                break;
            default:break;
        }
    }

    /**
     * 转换电信息
     * @param msg
     * @return
     */
    private ElectricState convertElectricState(String msg){
//        ElectricState electricState=new ElectricState();
//        electricState.setAllDayElectric(123.3);
//        electricState.setAverageDayElectric(50.3);
//        electricState.setaWeekElectrics(new double[]{30.3,40.3,20.6,40.5,50.3,30.3,40.3});
//        electricState.setWeeklyElectric(40.3);
        ElectricState electricState=new Gson().fromJson(msg,ElectricState.class);
        return electricState;
    }

    /**
     * 转换屋顶信息
     * @param msg
     * @return
     */
    private RoofState convertRoofState(String msg) {
        //TODO:
//        RoofState roofState=new RoofState();
//        roofState.setElectricState("正常");
//        roofState.setRuntime("4时3分");
//        List<WindowsState> windowsStateList=new ArrayList<>();
//        for(int i=0;i<5;i++){
//            WindowsState windowsState=new WindowsState();
//            windowsState.setSwitchState(true);
//            windowsState.setWorkState(true);
//            windowsStateList.add(windowsState);
//        }
//        roofState.setWindowsStates(windowsStateList);
//        Log.i(TAG, new Gson().toJson(roofState));
        RoofState roofState=new Gson().fromJson(msg,RoofState.class);
        return roofState;
    }

    /**
     * 逗号分隔 第一个为工作状态   第二个为是否智能控制
     * @param msg
     * @return
     */
    private HardwareState convertHardwareState(String msg){
        HardwareState hardwareState=new Gson().fromJson(msg,HardwareState.class);
        return  hardwareState;
    }
}
