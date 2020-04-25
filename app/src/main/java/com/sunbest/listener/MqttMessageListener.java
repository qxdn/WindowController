package com.sunbest.listener;


import androidx.annotation.Nullable;

import com.sunbest.model.ElectricState;
import com.sunbest.model.HardwareState;
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

    /**
     * 硬件是否智能控制等
     * @param hardwareState
     */
    public void onHardwareStateArrived(HardwareState hardwareState);
}
