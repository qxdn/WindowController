package com.sunbest.listener;


import com.sunbest.model.ElectricState;
import com.sunbest.model.RoofState;

/**
 * mqtt消息返回
 */
public interface MqttMessageListener {

    /**
     * 当硬件传来屋顶状态时回调
     * @param roofState
     */
    public void onRoofStateArrived(RoofState roofState);

    /**
     * 当硬件传来发电量时回调
     * @param electricState
     */
    public void onElectricStateArrived(ElectricState electricState);

}
