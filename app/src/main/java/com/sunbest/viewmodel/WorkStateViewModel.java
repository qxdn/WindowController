package com.sunbest.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sunbest.model.RoofState;
import com.sunbest.model.WindowsState;

import java.util.ArrayList;
import java.util.List;

public class WorkStateViewModel extends ViewModel {

    private MutableLiveData<RoofState>  roofState;

    public MutableLiveData<RoofState> getRoofState() {
        if(roofState==null){
            RoofState roofState1=new RoofState();
//            roofState1.setElectricState("未知");
//            roofState1.setRuntime("未知");
            roofState1.setElectricState("正常");
            roofState1.setRuntime("2时3分15秒");
            List<WindowsState> windowsStates=new ArrayList<>();
            roofState1.setWindowsStates(windowsStates);
            roofState=new MutableLiveData<RoofState>(roofState1);
        }
        return roofState;
    }

    public void setRoofState(MutableLiveData<RoofState> roofState) {
        this.roofState = roofState;
    }
}
