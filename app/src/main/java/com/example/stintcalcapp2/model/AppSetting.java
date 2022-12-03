package com.example.stintcalcapp2.model;

/**
 * ■設定画面
 * ・	1人当たりの上限下限時間
 * 		→下限については予備実装のためデフォルトは０分
 * ・	120%ルールのパーセント
 * 		→120%から変わることはないと思うが念のため予備実装
 * ・	バー表示の有無
 * ・	何時間ごとにバーを表示するのかの設定
 * ・	ドライバー
 */
public class AppSetting {
    private int upperLimits;
    private int lowerLimits;
    private double limitPercent;
    private boolean isDisplayBar;
    private int displayBarPerMin;
    private String[] driverList;

    public AppSetting() {
        this.upperLimits = 40;
        this.lowerLimits = 0;
        this.limitPercent = 1.2;
        this.isDisplayBar = false;
        this.displayBarPerMin = 0;
        this.driverList[0] = "秋間";
        this.driverList[1] = "豊口";
        this.driverList[2] = "吉戒";
        this.driverList[3] = "ルーク";
        this.driverList[4] = "横田";
        this.driverList[5] = "坪井";
        this.driverList[6] = "新田";
        this.driverList[7] = "X";
        this.driverList[8] = "未設定";
        this.driverList[9] = "中断";
    }

    public int getUpperLimits() {
        return upperLimits;
    }

    public void setUpperLimits(int upperLimits) {
        this.upperLimits = upperLimits;
    }

    public int getLowerLimits() {
        return lowerLimits;
    }

    public void setLowerLimits(int lowerLimits) {
        this.lowerLimits = lowerLimits;
    }

    public double getLimitPercent() {
        return limitPercent;
    }

    public void setLimitPercent(double limitPercent) {
        this.limitPercent = limitPercent;
    }

    public boolean isDisplayBar() {
        return isDisplayBar;
    }

    public void setDisplayBar(boolean displayBar) {
        isDisplayBar = displayBar;
    }

    public int getDisplayBarPerMin() {
        return displayBarPerMin;
    }

    public void setDisplayBarPerMin(int displayBarPerMin) {
        this.displayBarPerMin = displayBarPerMin;
    }

    public String[] getDriverList() {
        return driverList;
    }

    public void setDriverList(String[] driverList) {
        this.driverList = driverList;
    }
}
