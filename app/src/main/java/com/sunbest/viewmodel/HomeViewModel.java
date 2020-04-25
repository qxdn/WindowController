package com.sunbest.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sunbest.model.HardwareState;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<HardwareState> hardwareState;

    public MutableLiveData<HardwareState> getHardwareState() {
        if(hardwareState==null){
            HardwareState hardwareState1=new HardwareState();
            hardwareState1.setSmarted(true);
            hardwareState1.setWorked(true);
            hardwareState=new MutableLiveData<HardwareState>(hardwareState1);
        }
        return hardwareState;
    }

    public void setHardwareState(MutableLiveData<HardwareState> hardwareState) {
        this.hardwareState = hardwareState;
    }
}
