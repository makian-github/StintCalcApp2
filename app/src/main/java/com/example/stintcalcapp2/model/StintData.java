package com.example.stintcalcapp2.model;


import android.app.Application;
import android.util.Log;

import com.example.stintcalcapp2.controller.TimeCalc;
import static com.example.stintcalcapp2.model.ConstantsData.*;

import static android.content.ContentValues.TAG;

public class StintData extends Application {

    private TimeCalc timeCalc = new TimeCalc();

    private String driverName = "default";
    private int maxStintCount = 50;
    private int perStintTime = 0;
    private int stintCnt = 50;
    private int driverCnt = 9;
    private int pauseCnt = 0;
    private int kartNo = 0;
    private String driverNames[] = {"", "", "", "", "", "", ""};
    private int stintCounts[] = {0,0,0,0,0,0,0,0,0,0};

    private int raceTime = 0;
    private int allStint = 0;
    private String startTime = "00:00";
    private String raceEndTime = "00:00";

    //120%ルール用の係数
    private double coef = 1.2;

    //1Stint当たりの最低走行時間(予備実装)
    private int minimumRunningTime = MINIMUM_RUNNING_TIME_DEF;
    //1Stint当たりの最長走行時間
    private int upperRunningTime = UPPER_RUNNING_TIME_DEF;


    /**
     * [スティント数][データ数]
     * データ0 = スティントの終了時間
     * データ1 = 走行時間
     * データ2 = ドライバー名
     * データ3 = 号車
     */
    private String stintData[][] = new String[maxStintCount][4];

    @Override
    public void onCreate() {
        super.onCreate();
        for (int i = 0; i < stintData.length; i++) {
            /**スティントの終了時間*/
            stintData[i][END_TIME] = "00:00";
            /**走行時間*/
            stintData[i][RUNNING_TIME] = "0";
            /**ドライバー名*/
            stintData[i][DRIVER_NAME] = "-";
            /**号車*/
            stintData[i][KART_NO] = "0";
        }
    }

    /**
     * [スティント数][データ数]
     * データ0 = スティントの終了時間
     * データ1 = 走行時間
     * データ2 = ドライバー名
     * データ3 = 号車
     *
     * @return
     */
    public String[][] getStintData() {
        return stintData;
    }

    public void setStintData(String[][] stintData) {
        this.stintData = stintData;
    }

    /**
     * 最長走行時間ルール(120%ルール)の設定値を取得
     *
     * @return
     */
    public double getCoef() {
        return coef;
    }

    /**
     * 最長走行時間ルール(120%ルール)の設定値を何％にするかの設定
     *
     * @param coef
     */
    public void setCoef(double coef) {
        this.coef = coef;
    }

    public int getMinimumRunningTime() {
        return minimumRunningTime;
    }

    public void setMinimumRunningTime(int minimumRunningTime) {
        this.minimumRunningTime = minimumRunningTime;
    }

    public int getUpperRunningTime() {
        return upperRunningTime;
    }

    public void setUpperRunningTime(int upperRunningTime) {
        this.upperRunningTime = upperRunningTime;
    }

    /**
     * 引数で渡されたStintの走行終了時間を返す
     *
     * @param stint
     * @return
     */
    public String getEndTime(int stint) {
        return this.stintData[stint][END_TIME];
    }

    /**
     * 受け取ったStintに受け取った終了時間を設定
     *
     * @param stint
     * @param endTime
     */
    public void setEndTime(int stint, String endTime) {
        Log.d(TAG, "setEndTime in");
        this.stintData[stint][END_TIME] = endTime;
        Log.d(TAG, "setEndTime out");
    }

    public String getStintStartTime(int stint) {
        String stintStartTime = "00:00";
        if (stint == 0) {
            stintStartTime = getStartTime();
        } else {
            stintStartTime = stintData[stint - 1][END_TIME];
        }
        Log.d(TAG, "getStintStartTime stintStartTime = " + stintStartTime);
        return stintStartTime;
    }

    /**
     * 受け取ったStintに受け取った走行時間を返す
     *
     * @param stint
     * @return
     */
    public String getRunningTime(int stint) {
        return this.stintData[stint][RUNNING_TIME];
    }

    /**
     * 受け取ったStintに受け取った走行時間を設定
     *
     * @param stint
     * @param runningTime
     */
    public void setRunningTime(int stint, int runningTime) {
        this.stintData[stint][RUNNING_TIME] = String.valueOf(runningTime);
        Log.d(TAG, "setRunningTime@@@:" + getRunningTime(stint));
        setEndTime(stint, timeCalc.calcPlusTime(getStintStartRunningTime(stint), runningTime));
        Log.d(TAG, "setRunningTime@:" + getRunningTime(stint));


        //走行時間を変更すると以降の走行終了時間に影響があるので更新を行う
        //Todo 23/02/05 ここの処理がうまくいっていない
//        for (int i = stint; i < allStint; i++) {
//            if (i == 0){
//                setEndTime(i,timeCalc.calcPlusTime(startTime,Integer.parseInt(stintData[i][1])));
//            }else{
//                if (i == allStint-1){
//                    setEndTime(i,getRaceEndTime());
//                }else{
//                    setEndTime(i,timeCalc.calcPlusTime(getEndTime(i-0),Integer.valueOf(stintData[i][1])));
//                }
//            }
//        }
        //refreshEndTime();
    }

    /**
     * 引数で渡されたStintの走行開始時間を返す
     * 1Stint目の場合はレースの開始時間
     * 2Stint目以降は前のStintの終了時間
     *
     * @param stint
     * @return 引数で渡されたStintの走行開始時間
     */
    public String getStintStartRunningTime(int stint) {
        String runningStartTime = "00:00";
        if (stint == 0) {
            runningStartTime = getStartTime();
        } else {
            runningStartTime = getEndTime(stint - 1);
        }
        return runningStartTime;
    }

    /**
     * 引数で渡されたStintのドライバー名を返す
     *
     * @param stint
     * @return
     */
    public String getDriverName(int stint) {
        return this.stintData[stint][DRIVER_NAME];
    }

    /**
     * 受け取ったStintに受け取ったドライバー名を設定
     *
     * @param stint      　設定したいStint
     * @param driverName 　設定したいドライバー名
     */
    public void setDriverName(int stint, String driverName) {
        this.stintData[stint][DRIVER_NAME] = driverName;
    }

    /**
     * 受け取ったStintに受け取った号車を設定
     *
     * @param stint
     * @param kartNo
     */
    public void setKartNo(int stint, String kartNo) {
        this.stintData[stint][KART_NO] = kartNo;
    }

    public String getKartNo(int stint) {
        return this.stintData[stint][KART_NO];
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

    public int getRaceTime() {
        return raceTime;
    }

    public void setRaceTime(int raceTime) {
        this.raceTime = raceTime;
    }

    public int getAllStint() {
        return allStint;
    }

    public void setAllStint(int stint) {
        this.allStint = stint;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * レースの終了時間を返却
     * レース開始時間にレース時間(分)を足して終了時間を計算
     *
     * @return レースの終了時間
     */
    public String getRaceEndTime() {
        raceEndTime = timeCalc.calcPlusTime(getStartTime(), raceTime);
        return raceEndTime;
    }


    /**
     * Stint数よりも先のデータを初期化
     */
    public void clearRaceData(int stint) {
        for (int i = stint; i < maxStintCount; i++) {
            /**スティントの終了時間*/
            stintData[i][END_TIME] = "00:00";
            /**走行時間*/
            stintData[i][RUNNING_TIME] = "0";
            /**ドライバー名*/
            stintData[i][DRIVER_NAME] = "-";
            /**号車*/
            stintData[i][KART_NO] = "0";
        }
    }

    /**
     * 引数で渡されたドライバーの合計Stint数を返す
     *
     * @param driverName ドライバー名
     * @return 指定されたドライバーの合計Stint数
     */
    public int getCntStintPerDriver(String driverName) {
        int cntStintPerDriver = 0;
        for (int i = 0; i < stintData.length; i++) {
            if (stintData[i][DRIVER_NAME].equals(driverName)) {
                cntStintPerDriver++;
            }
        }
        return cntStintPerDriver;
    }

    public int getDrivingTimeOfDriver(String driverName) {
        int totalRunTime = 0;
        for (int i = 0; i < stintData.length; i++) {
            if (stintData[i][DRIVER_NAME].equals(driverName)) {
                totalRunTime += Integer.parseInt(stintData[i][RUNNING_TIME]);
            }
        }
        Log.d(TAG, "totalRunTime=" + totalRunTime);
        return totalRunTime;
    }

    public int getDrivingTimeOfDriver(String driverName,int stintCnt) {
        int totalRunTime = 0;
        for (int i = 0; i < stintCnt; i++) {
            if (stintData[i][DRIVER_NAME].equals(driverName)) {
                totalRunTime += Integer.parseInt(stintData[i][RUNNING_TIME]);
            }
        }
        Log.d(TAG, "totalRunTime=" + totalRunTime);
        return totalRunTime;
    }

    /**
     * 走行時間をもとに走行終了時間を更新する
     */
    public void refreshEndTime() {
        for (int i = 0; i < allStint; i++) {
            if (i == 0) {
                setEndTime(i, timeCalc.calcPlusTime(startTime, Integer.parseInt(stintData[i][RUNNING_TIME])));
            } else {
                if (i == allStint - 1) {
                    setEndTime(i, getRaceEndTime());
                } else {
                    setEndTime(i, timeCalc.calcPlusTime(getEndTime(i - 0), Integer.valueOf(stintData[i][RUNNING_TIME])));
                }
            }
        }
    }

    /**
     * 各ドライバーのStint数を返却
     * @return Stint数をまとめた配列
     * [0] = 秋間
     * [1] = 豊口
     * [2] = 吉戒
     * [3] = ルーク
     * [4] = 横田
     * [5] = 坪井
     * [6] = 新田
     * [7] = X
     * [8] = -
     * [9] = 中断
     */
    public int[] getStintCount(int allStintCount){
        for (int i = 0; i <stintCounts.length; i++) {
            stintCounts[i] = 0;
        }

        for (int i = 0; i < allStintCount; i++) {
            switch (getDriverName(i)){
                case DRIVER_NAME_AKIMA:
                    stintCounts[DRIVER_ID_AKIMA]++;
                    break;
                case DRIVER_NAME_TOYOGUCHI:
                    stintCounts[DRIVER_ID_TOYOGUCHI]++;
                    break;
                case DRIVER_NAME_YOSHIKAI:
                    stintCounts[DRIVER_ID_YOSHIKAI]++;
                    break;
                case DRIVER_NAME_LUKE:
                    stintCounts[DRIVER_ID_LUKE]++;
                    break;
                case DRIVER_NAME_YOKOTA:
                    stintCounts[DRIVER_ID_YOKOTA]++;
                    break;
                case DRIVER_NAME_TUBOI:
                    stintCounts[DRIVER_ID_TUBOI]++;
                    break;
                case DRIVER_NAME_NITTA:
                    stintCounts[DRIVER_ID_NITTA]++;
                    break;
                case DRIVER_NAME_X:
                    stintCounts[DRIVER_ID_X]++;
                    break;
                case DRIVER_NAME_NONE:
                    stintCounts[DRIVER_ID_NONE]++;
                    break;
                case DRIVER_NAME_INTERRUPTION:
                    stintCounts[DRIVER_ID_INTERRUPTION]++;
                    break;
            }
        }




        return stintCounts;
    }
}
