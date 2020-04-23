package com.sunbest.model;

/**
 * 天窗状态
 */
public class WindowsState {

    /**
     * 工作状态 true为正常
     */
    private boolean workState;

    /**
     * 是否开合 true为打开
     */
    private boolean switchState;

    public WindowsState() {
    }

    public WindowsState(boolean workState, boolean switchState) {
        this.workState = workState;
        this.switchState = switchState;
    }

    public boolean getWorkState() {
        return workState;
    }

    public void setWorkState(boolean workState) {
        this.workState = workState;
    }

    public boolean getSwitchState() {
        return switchState;
    }

    public void setSwitchState(boolean switchState) {
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
