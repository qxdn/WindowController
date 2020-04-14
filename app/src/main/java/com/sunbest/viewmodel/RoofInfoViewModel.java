package com.sunbest.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sunbest.model.Air;
import com.sunbest.model.Weather;

public class RoofInfoViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<Weather> weather;
    private MutableLiveData<Air> air;
    private RoofInfoViewModel viewModel = this;


    public MutableLiveData<Weather> getWeather() {
        if (null == weather) {
            Weather weather = new Weather();
            weather.setState("未知");
            weather.setTemp("未知");
            weather.setWind_dir("未知");
            weather.setWind_sc("未知");
            this.weather = new MutableLiveData<>(weather);
        }
        return weather;
    }

    public void setWeather(MutableLiveData<Weather> weather) {
        this.weather = weather;
    }

    public MutableLiveData<Air> getAir() {
        if (null == air) {
            Air air = new Air();
            air.setAqi("未知");
            air.setPm25("未知");
            this.air = new MutableLiveData<>(air);
        }
        return air;
    }

    public void setAir(MutableLiveData<Air> air) {
        this.air = air;
    }


}
