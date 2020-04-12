package com.sunbest.model;

import java.util.List;

/**
 * 屋顶状态
 */
public class RoofState {

    /**
     * 运行时常
     */
    private String runtime;

    /**
     * 电气特性
     */
    private String electricState;

    /**
     * 各个天窗状态
     */
    private List<WindowsState> windowsStates;

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getElectricState() {
        return electricState;
    }

    public void setElectricState(String electricState) {
        this.electricState = electricState;
    }

    public List<WindowsState> getWindowsStates() {
        return windowsStates;
    }

    public void setWindowsStates(List<WindowsState> windowsStates) {
        this.windowsStates = windowsStates;
    }

    @Override
    public String toString() {
        return "RoofState{" +
                "runtime='" + runtime + '\'' +
                ", electricState='" + electricState + '\'' +
                ", windowsStates=" + windowsStates +
                '}';
    }
}
