package com.sunbest.domain;

public class VtDateValueBean {
    private double fValue;
    private String sYearMonth;

    public VtDateValueBean() {
    }

    public VtDateValueBean(double fValue, String sYearMonth) {
        this.fValue = fValue;
        this.sYearMonth = sYearMonth;
    }

    public double getfValue() {
        return fValue;
    }

    public void setfValue(double fValue) {
        this.fValue = fValue;
    }

    public String getsYearMonth() {
        return sYearMonth;
    }

    public void setsYearMonth(String sYearMonth) {
        this.sYearMonth = sYearMonth;
    }
}

