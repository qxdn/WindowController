package com.sunbest.domain.dto;

import androidx.annotation.Nullable;

/**
 * 天气信息
 */
public class Weather {



    /**
     * 降雨量
     * 未查到为空
     */
    @Nullable
    private String pnpc;

    /**
     * 风向
     * 未查到为空
     */
    @Nullable
    private String wind_dir;

    /**
     * 风力
     * 未查到为空
     */
    @Nullable
    private String wind_sc;

    /**
     * 温度
     * 未查到为空
     */
    @Nullable
    private String temp;



    @Nullable
    public String getPnpc() {
        return pnpc;
    }

    public void setPnpc(@Nullable String pnpc) {
        this.pnpc = pnpc;
    }

    @Nullable
    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(@Nullable String wind_dir) {
        this.wind_dir = wind_dir;
    }

    @Nullable
    public String getWind_sc() {
        return wind_sc;
    }

    public void setWind_sc(@Nullable String wind_sc) {
        this.wind_sc = wind_sc;
    }

    @Nullable
    public String getTemp() {
        return temp;
    }

    public void setTemp(@Nullable String temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "pnpc='" + pnpc + '\'' +
                ", wind_dir='" + wind_dir + '\'' +
                ", wind_sc='" + wind_sc + '\'' +
                ", temp='" + temp + '\'' +
                '}';
    }
}
