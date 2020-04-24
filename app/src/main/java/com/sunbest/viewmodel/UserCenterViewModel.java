package com.sunbest.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserCenterViewModel extends ViewModel {
    private MutableLiveData<String> version;
    private MutableLiveData<String> email;
    private MutableLiveData<String> deviceId;

    public MutableLiveData<String> getVersion() {
        return version;
    }

    public void setVersion(MutableLiveData<String> version) {
        this.version = version;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(MutableLiveData<String> email) {
        this.email = email;
    }

    public MutableLiveData<String> getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(MutableLiveData<String> deviceId) {
        this.deviceId = deviceId;
    }
}
