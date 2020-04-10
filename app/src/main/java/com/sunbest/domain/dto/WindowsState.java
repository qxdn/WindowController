package com.sunbest.domain.dto;

/**
 * 天窗状态
 */
public class WindowsState {

    /**
     * 工作状态 true为正常
     */
    private Boolean workState;

    /**
     * 是否开合 true为打开
     */
    private Boolean switchState;

    public Boolean getWorkState() {
        return workState;
    }

    public void setWorkState(Boolean workState) {
        this.workState = workState;
    }

    public Boolean getSwitchState() {
        return switchState;
    }

    public void setSwitchState(Boolean switchState) {
        this.switchState = switchState;
    }

    @Override
    public String toString() {
        return "WindowsState{" +
                "workState=" + workState +
                ", switchState=" + switchState +
                '}';
    }
}
