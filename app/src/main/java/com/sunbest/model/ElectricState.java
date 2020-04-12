package com.sunbest.model;

import java.util.Arrays;

/**
 * 发电状况
 */
public class ElectricState {

    /**
     * 日均发电
     */
    private double averageDayElectric;

    /**
     * 全天发电
     */
    private double allDayElectric;

    /**
     * 近一周发电
     */
    private double weeklyElectric;

    /**
     * 一周内每天发电
     */
    private double[] aWeekElectrics;

    /**
     * 上小时发电
     */
    private double lastHourElectric;

    public double getAverageDayElectric() {
        return averageDayElectric;
    }

    public void setAverageDayElectric(double averageDayElectric) {
        this.averageDayElectric = averageDayElectric;
    }

    public double getAllDayElectric() {
        return allDayElectric;
    }

    public void setAllDayElectric(double allDayElectric) {
        this.allDayElectric = allDayElectric;
    }

    public double getWeeklyElectric() {
        return weeklyElectric;
    }

    public void setWeeklyElectric(double weeklyElectric) {
        this.weeklyElectric = weeklyElectric;
    }

    public double[] getaWeekElectrics() {
        return aWeekElectrics;
    }

    public void setaWeekElectrics(double[] aWeekElectrics) {
        this.aWeekElectrics = aWeekElectrics;
    }

    public double getLastHourElectric() {
        return lastHourElectric;
    }

    public void setLastHourElectric(double lastHourElectric) {
        this.lastHourElectric = lastHourElectric;
    }

    @Override
    public String toString() {
        return "ElectricState{" +
                "averageDayElectric=" + averageDayElectric +
                ", allDayElectric=" + allDayElectric +
                ", weeklyElectric=" + weeklyElectric +
                ", aWeekElectrics=" + Arrays.toString(aWeekElectrics) +
                ", lastHourElectric=" + lastHourElectric +
                '}';
    }
}
