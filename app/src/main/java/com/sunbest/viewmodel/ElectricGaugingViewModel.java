package com.sunbest.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sunbest.model.ElectricState;

public class ElectricGaugingViewModel extends ViewModel {
    private MutableLiveData<ElectricState> electricState;

    public MutableLiveData<ElectricState> getElectricState() {
        if(electricState==null){
            ElectricState electricState1=new ElectricState();
//            electricState1.setAllDayElectric(0);
//            electricState1.setAverageDayElectric(0);
//            electricState1.setaWeekElectrics(new double[7]);
//            electricState1.setWeeklyElectric(0);
//            electricState1.setLastHourElectric(0);
              electricState1.setAllDayElectric(56.23);
              electricState1.setAverageDayElectric(30.2);
              electricState1.setaWeekElectrics(new double[7]);
              electricState1.setWeeklyElectric(321.36);
              electricState1.setLastHourElectric(45.91);
            electricState=new MutableLiveData<ElectricState>(electricState1);
        }
        return electricState;
    }

    public void setElectricState(MutableLiveData<ElectricState> electricState) {
        this.electricState = electricState;
    }
}
