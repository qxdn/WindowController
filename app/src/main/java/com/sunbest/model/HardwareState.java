package com.sunbest.model;

public class HardwareState {

    boolean worked;

    boolean smarted;

    public boolean isWorked() {
        return worked;
    }

    public void setWorked(boolean worked) {
        this.worked = worked;
    }

    public boolean isSmarted() {
        return smarted;
    }

    public void setSmarted(boolean smarted) {
        this.smarted = smarted;
    }

    @Override
    public String toString() {
        return "HardwareState{" +
                "worked=" + worked +
                ", smarted=" + smarted +
                '}';
    }
}
