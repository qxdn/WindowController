package com.sunbest.domain.dto;

import androidx.annotation.Nullable;

public class Air {
    /**
     * pm2.5
     * 未查到为空
     */
    @Nullable
    private String pm25;

    /**
     * pm10
     *
     */
    @Nullable
    private String pm10;

    /**
     * 空气质量指数
     */
    @Nullable
    private String aqi;

    @Nullable
    public String getPm25() {
        return pm25;
    }

    public void setPm25(@Nullable String pm25) {
        this.pm25 = pm25;
    }

    @Nullable
    public String getPm10() {
        return pm10;
    }

    public void setPm10(@Nullable String pm10) {
        this.pm10 = pm10;
    }

    @Nullable
    public String getAqi() {
        return aqi;
    }

    public void setAqi(@Nullable String aqi) {
        this.aqi = aqi;
    }

    @Override
    public String toString() {
        return "Air{" +
                "pm25='" + pm25 + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", aqi='" + aqi + '\'' +
                '}';
    }
}
