package com.example.stintcalcapp2.model;


import android.app.Application;
import android.util.Log;

import com.example.stintcalcapp2.R;

import static android.content.ContentValues.TAG;

public class StintData {

    private String driverName = "default";
    private int maxStintCount = 45;
    private int perStintTime = 0;
    private int stintCnt = 45;
    private int driverCnt = 9;
    private int pauseCnt = 0;
    private int kartNo = 0;
//    private String driverNames[] = {"","","","","","",""};
    private String driverNames[] = {"", "", "", "", "", "", ""};

    /**
     * [スティント数][データ数]
     * データ0 = スティントの終了時間
     * データ1 = 走行時間
     * データ2 = ドライバー名
     * データ3 = 号車
     */
    private String stintData[][] = new String[maxStintCount][4];

    public StintData() {
        for (int i = 0; i < stintData.length; i++) {
            /**スティントの終了時間*/
            stintData[i][0] = "00:00";
            /**走行時間*/
            stintData[i][1] = "0";
            /**ドライバー名*/
            stintData[i][2] = "-";
            /**号車*/
            stintData[i][3] = "0";
        }

        //Todo 設定したドライバー名を入れる
        //driverNames = ;
    }

    /**
     * [スティント数][データ数]
     * データ0 = スティントの終了時間
     * データ1 = 走行時間
     * データ2 = ドライバー名
     * データ3 = 号車
     * @return
     */
    public String[][] getStintData() {
        return stintData;
    }

    public void setStintData(String[][] stintData) {
        this.stintData = stintData;
    }

    /**
     * 引数で渡されたStintの走行終了時間を返す
     * @param stint
     * @return
     */
    public String getEndTime(int stint) {
        return this.stintData[stint][0];
    }

    /**
     * 受け取ったStintに受け取った終了時間を設定
     * @param stint
     * @param endTime
     */
    public void setEndTime(int stint, String endTime) {
        this.stintData[stint][0] = endTime;
    }

    /**
     * 受け取ったStintに受け取った走行時間を設定
     * @param stint
     * @return
     */
    public String getRunningTime(int stint){
        return this.stintData[stint][1];
    }

    /**
     * 受け取ったStintに受け取った走行時間を設定
     * @param stint
     * @param runningTime
     */
    public void getRunningTime(int stint,String runningTime){
        this.stintData[stint][1] = runningTime;
        //Todo 開始時間＋上で設定した値をendtimeに設定する
        //this.stintData[stint][2] = ;
    }

    /**
     * 引数で渡されたStintのドライバー名を返す
     * @param stint
     * @return
     */
    public String getDriverName(int stint) {
        return this.stintData[stint][2];
    }

    /**
     * 受け取ったStintに受け取ったドライバー名を設定
     * @param stint　設定したいStint
     * @param driverName　設定したいドライバー名
     */
    public void setDriverName(int stint,String driverName) {
        this.stintData[stint][2] = driverName;
    }

    /**
     * 受け取ったStintに受け取った号車を設定
     * @param stint
     * @param kartNo
     */
    public void setKartNo(int stint, String kartNo) {
        this.stintData[stint][3] = kartNo;
    }

    public String getKartNo(int stint){
        return this.stintData[stint][3];
    }

    public int getMaxStintCount() {
        return maxStintCount;
    }

    public void setMaxStintCount(int maxStintCount) {
        this.maxStintCount = maxStintCount;
    }

    public int getPerStintTime() {
        return perStintTime;
    }

    public void setPerStintTime(int perStintTime) {
        this.perStintTime = perStintTime;
    }

    public int getStintCnt() {
        return stintCnt;
    }

    public void setStintCnt(int stintCnt) {
        this.stintCnt = stintCnt;
    }

    public int getDriverCnt() {
        return driverCnt;
    }

    public int getPauseCnt() {
        return pauseCnt;
    }

    public void setPauseCnt(int pauseCnt) {
        this.pauseCnt = pauseCnt;
    }


    /**
     * Stint数よりも先のデータを初期化
     */
    public void clearRaceData(){
        for (int i = stintCnt; i < maxStintCount; i++) {
            /**スティントの終了時間*/
            stintData[i][0] = "00:00";
            /**走行時間*/
            stintData[i][1] = "0";
            /**ドライバー名*/
            stintData[i][2] = "-";
            /**号車*/
            stintData[i][3] = "0";
        }
    }

    /**
     * 引数で渡されたドライバーの合計Stint数を返す
     * @param driverName ドライバー名
     * @return 指定されたドライバーの合計Stint数
     */
    public int getCntStintPerDriver(String driverName){
        int cntStintPerDriver = 0;
        for (int i = 0; i < stintData.length; i++) {
            if(stintData[i][2].equals(driverName)){
                cntStintPerDriver++;
            }
        }
        return cntStintPerDriver;
    }
}
