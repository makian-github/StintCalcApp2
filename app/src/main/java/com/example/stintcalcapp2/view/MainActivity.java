package com.example.stintcalcapp2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.stintcalcapp2.R;
import com.example.stintcalcapp2.controller.AsyncFunctionCallback;
import com.example.stintcalcapp2.controller.CheckboxController;
import com.example.stintcalcapp2.controller.TimeCalc;
import com.example.stintcalcapp2.layout.InfoDialog;
import com.example.stintcalcapp2.layout.StintLayout;
import com.example.stintcalcapp2.model.StintData;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private StintLayout stintLayouts[];
    private View view[];
    private Button setBtn;
    private Button setRaceDataBtn;
    private Button nowBtn;
    private Button showStintBtn;

    /*Setタブ*/
    private Button openStintSetting;
    private Button perStintSetBtn;
    private TextView perStintText;
    private Button checkPerStintSetBtn;
    private Button setMinBtn;
    private EditText setMinEditText;
    private Switch recalcSwitch;

    //Debug
    private Button debugBtn;
    private Button debugBtn2;
    private Button debugBtn3;

    /*RaceDataタブ*/
    private EditText raceTimeEditText;
    private EditText allStintEditText;
    private Button startTimeSetBtn;
    private TextView startTimeSetText;
    private Button confirmBtn;
    private Switch coefSwitch;

    private final double COEF_110_PERCENT = 1.1;
    private final double COEF_120_PERCENT = 1.2;

    private StintData stintData;
    private TimeCalc timeCalc;
    private CheckboxController checkboxController;

    /*Stintタブ*/
    private TextView[] runSumTimeTextView;
    private TextView maxRunTimeTextView;
    private TextView[] stintCntTextView;

    private Button akimaSetBtn;
    private Button toyoguchiSetBtn;
    private Button yoshikaiDriverSetBtn;
    private Button lukeSetBtn;
    private Button yokotaSetBtn;
    private Button tuboiSetBtn;
    private Button nittaSetBtn;
    private Button xSetBtn;
    private Button breakeSetBtn;
    private Button nonDriverSetBtn;

    private Button allCheckBtn;
    private Button allUncheckBtn;
    private Button reverseBtn;

    //ドライバーID
    private final int ID_AKIMA = 0;
    private final int ID_TOYOGUCHI = 1;
    private final int ID_YOSHIKAI = 2;
    private final int ID_LUKE = 3;
    private final int ID_YOKOTA = 4;
    private final int ID_TUBOI = 5;
    private final int ID_NITTA = 6;
    private final int ID_X = 7;
    private final int ID_BREAKE = 98;
    private final int ID_NULL = 99;

    //Tab Layout
    private LinearLayout raceDataLayout;
    private LinearLayout setStintData;
    private LinearLayout showStintData;
    private String TAG = "MainActivity";

    private int displayTab = 0;

    //レース時間と全Stintから均等割りした時間
    private int perStintTime = 0;

    private static final int SET_TAB_NUM = 0;
    private static final int RACE_DATA_TAB_NUM = 1;
    private static final int NOW_TAB_NUM = 2;
    private static final int STINT_TAB_NUM = 3;
    private static final int CHECKBOX_NOT_SELECTED = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void testData() {
        raceTimeEditText.setText("120");
        allStintEditText.setText("12");
//        stintData.setRaceTime(300);
//        stintData.setAllStint(20);
//        stintData.setStartTime("10:00");

        Log.v(TAG, "testData raceData.getStint=" + stintData.getAllStint());
    }

    @Override
    protected void onResume() {
        super.onResume();

        stintData = (StintData) this.getApplication();
        timeCalc = new TimeCalc();
        checkboxController = new CheckboxController();

        for (int i = 0; i < getResources().getStringArray(R.array.driverList).length; i++) {
            //stintData.setDriverName(i,getResources().getStringArray(R.array.driverList)[i]);
            Log.i(TAG, "DriverName:" + getResources().getStringArray(R.array.driverList)[i]);
        }

        defineLayout();

        tabBtnStateChange(setBtn);

        Log.v(TAG, "displayTab=" + displayTab);

        setStintData.setVisibility(View.VISIBLE);
        raceDataLayout.setVisibility(View.GONE);
        showStintData.setVisibility(View.GONE);

//        if (displayTab == RACE_DATA_TAB_NUM){
//
//        }
//
//        switch (displayTab){
//            case SET_TAB_NUM:
//                setStintData.setVisibility(View.VISIBLE);
//                raceDataLayout.setVisibility(View.GONE);
//            case RACE_DATA_TAB_NUM:
//                setStintData.setVisibility(View.GONE);
//                raceDataLayout.setVisibility(View.VISIBLE);
//            case NOW_TAB_NUM:
//                setStintData.setVisibility(View.GONE);
//                raceDataLayout.setVisibility(View.GONE);
//            case STINT_TAB_NUM:
//                setStintData.setVisibility(View.GONE);
//                raceDataLayout.setVisibility(View.GONE);
//        }


        reCalcRefreshDisplay();
        //refreshDisplay();

        /**setタブ*/
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxController.setAllCheckBox(stintLayouts, stintData, false);
                raceDataLayout.setVisibility(View.GONE);
                setStintData.setVisibility(View.VISIBLE);
                showStintData.setVisibility(View.GONE);
                displayTab = SET_TAB_NUM;

                tabBtnStateChange(setBtn);

                //均等割りした時間を算出して表示を更新
                if (stintData.getAllStint() > 0 && stintData.getAllStint() <= stintData.getStintCnt()) {
                    stintData.setPerStintTime(timeCalc.perStintTimeCalc(stintData.getRaceTime(), stintData.getAllStint()));
                    perStintText.setText(String.valueOf(stintData.getPerStintTime()));
                }

            }
        });

        /**RaceDataタブ*/
        setRaceDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxController.setAllCheckBox(stintLayouts, stintData, false);
                setStintData.setVisibility(View.GONE);
                raceDataLayout.setVisibility(View.VISIBLE);
                showStintData.setVisibility(View.GONE);
                displayTab = RACE_DATA_TAB_NUM;

                tabBtnStateChange(setRaceDataBtn);
            }
        });
        /** Nowタブ*/
        nowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxController.setAllCheckBox(stintLayouts, stintData, false);
                setStintData.setVisibility(View.GONE);
                raceDataLayout.setVisibility(View.GONE);
                showStintData.setVisibility(View.GONE);
                displayTab = NOW_TAB_NUM;

                tabBtnStateChange(nowBtn);
            }
        });

        /** stintタブ*/
        showStintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxController.setAllCheckBox(stintLayouts, stintData, false);
                setStintData.setVisibility(View.GONE);
                raceDataLayout.setVisibility(View.GONE);
                showStintData.setVisibility(View.VISIBLE);
                displayTab = STINT_TAB_NUM;

                setRuntimeSum();

                tabBtnStateChange(showStintBtn);
            }
        });

        /** Setタブ */
        //チェックが入っている項目の設定画面を開く
        openStintSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), InputForm.class);

                int firstCheckBoxNum = checkboxController.firstCheckBox(stintLayouts, stintData);

                if (firstCheckBoxNum != CHECKBOX_NOT_SELECTED) {
                    intent.putExtra("Stint", checkboxController.firstCheckBox(stintLayouts, stintData));//第一引数key、第二引数渡したい値
                    startActivity(intent);
                } else {
                    InfoDialog dialog = new InfoDialog();
                    dialog.setTitleStr("Error");
                    dialog.setMessageStr("設定したいStintのチェックボックスにチェックを入れてください");
                    dialog.setDialogType(InfoDialog.ONE_BUTTON_DIALOG);
                    dialog.show(getSupportFragmentManager(), "");
                }
            }
        });

        //レース時間・Stint数から全体を均等割りした値を設定する
        perStintSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "in 均等割りボタン");
                InfoDialog dialog = new InfoDialog();
                dialog.setTitleStr("確認");
                dialog.setMessageStr("現在の設定値を上書きしますがよろしいですか？");
                dialog.setDialogType(InfoDialog.TWO_BUTTON_DIALOG);
                AsyncFunctionCallback callback = new AsyncFunctionCallback() {
                    // コールバック処理の定義
                    @Override
                    public void onAsyncFunctionFinished(boolean isSucceed) {
                        Log.d(TAG, "isSucceed = " + isSucceed);
                        if (isSucceed) {
                            setEvenlyDividedStint();
                        }
                    }
                };
                dialog.setAsyncFunctionCallback(callback);
                dialog.show(getSupportFragmentManager(), "");
                Log.d(TAG, "out 均等割りボタン");
            }
        });

        //チェックが入っている項目から先を均等割りするボタン
        checkPerStintSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoDialog dialog = new InfoDialog();
                dialog.setTitleStr("確認");
                dialog.setMessageStr("現在の設定値を上書きしますがよろしいですか？");
                dialog.setDialogType(InfoDialog.TWO_BUTTON_DIALOG);
                AsyncFunctionCallback callback = new AsyncFunctionCallback() {
                    // コールバック処理の定義
                    @Override
                    public void onAsyncFunctionFinished(boolean isSucceed) {
                        Log.d(TAG, "isSucceed = " + isSucceed);
                        if (isSucceed) {
                            setUniformityRunningTime();
                            //reCalcRefreshDisplay();
                            refreshDisplay();
                        }
                    }
                };
                dialog.setAsyncFunctionCallback(callback);
                dialog.show(getSupportFragmentManager(), "");
                Log.d(TAG, "out 均等割りボタン");
            }
        });

        //チェックが入っている項目に設定値を設定するボタン
        setMinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (setMinEditText.getText() == null) {
                        Log.d(TAG, "setMinEditText.getText() is Null");
                    } else {
                        flagItemSetMin(Integer.valueOf(setMinEditText.getText().toString()));
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception = " + e);
                }

                //再計算を行うか否かのSwitchに応じて処理を切り替える
                if (!recalcSwitch.isChecked()) {//再計算を行う場合、チェックがついている項目のEndTime～レース終了時間までで均等割りを行う
                    for (int i = 0; i < stintData.getAllStint(); i++) {
                        if (stintLayouts[i].getFlagCheckBox().isChecked()) {
                            String uniformityStartTime = stintData.getStintStartTime(i + 1);
                            String uniformityEndTime = stintData.getRaceEndTime();

                            Log.d(TAG, "uniformityStartTime = " + uniformityStartTime);
                            Log.d(TAG, "uniformityEndTime = " + uniformityEndTime);
                            uniformitySet(uniformityStartTime, uniformityEndTime, i + 1);
                            break;
                        }
                    }
                    refreshDisplay();
                } else {//再計算を行わない場合、均等割りは行わず表示を更新
                    reCalcRefreshDisplay();
                }

            }
        });

        /** RaceDataタブ */
        startTimeSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), InputForm.class);
                intent.putExtra("Stint", 999);
                startActivity(intent);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != raceTimeEditText.getText()) {
                    try {
                        stintData.setRaceTime(Integer.parseInt(raceTimeEditText.getText().toString()));
                    } catch (Exception e) {
                        e.getStackTrace();
                        stintData.setRaceTime(0);
                        //Todo エラーを表示
                    }
                }
                if (null != allStintEditText.getText()) {
                    try {
                        stintData.setAllStint(Integer.parseInt(allStintEditText.getText().toString()));
                    } catch (Exception e) {
                        e.getStackTrace();
                        stintData.setAllStint(0);
                        //Todo エラーを表示
                    }
                }

                if (coefSwitch.isChecked()) {
                    Log.d(TAG, "COEF = 110");
                    stintData.setCoef(COEF_110_PERCENT);
                } else {
                    Log.d(TAG, "COEF = 120");
                    stintData.setCoef(COEF_120_PERCENT);
                }

                //reCalcRefreshDisplay();
                refreshDisplay();
            }
        });


        //Stintタブ=============================================================================================
        akimaSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDriver(ID_AKIMA);
                setRuntimeSum();
            }
        });

        toyoguchiSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDriver(ID_TOYOGUCHI);
                setRuntimeSum();
            }
        });

        yoshikaiDriverSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDriver(ID_YOSHIKAI);
                setRuntimeSum();
            }
        });

        lukeSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDriver(ID_LUKE);
                setRuntimeSum();
            }
        });

        yokotaSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDriver(ID_YOKOTA);
                setRuntimeSum();
            }
        });

        tuboiSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDriver(ID_TUBOI);
                setRuntimeSum();
            }
        });

        nittaSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDriver(ID_NITTA);
                setRuntimeSum();
            }
        });

        xSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDriver(ID_X);
                setRuntimeSum();
            }
        });

        breakeSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDriver(ID_BREAKE);
                setRuntimeSum();
            }
        });

        nonDriverSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDriver(ID_NULL);
                setRuntimeSum();
            }
        });

        //チェックボックスのコントロール=====================================================
        /**
         * すべてのチェックボックスをTrueにする
         */
        allCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxController.setAllCheckBox(stintLayouts, stintData, true);
            }
        });

        /**
         * すべてのチェックボックスをFalseにする
         */
        allUncheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxController.setAllCheckBox(stintLayouts, stintData, false);
            }
        });

        /**
         * すべてのチェックボックスについて、現在の状況から反転する
         */
        reverseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < stintData.getAllStint(); i++) {
                    stintLayouts[i].setFlagCheckBox(!stintLayouts[i].getFlagCheckBox().isChecked());
                }
            }
        });


        //Debug=================================================================================================
        debugBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "======DebugLOG Start======");
                for (int i = 0; i < stintData.getAllStint(); i++) {
                    Log.d(TAG,
                            "stint:" + i
                                    + ",開始時間：" + stintData.getStintStartTime(i)
                                    + ",終了時間；" + stintData.getEndTime(i)
                                    + ",走行時間：" + stintData.getRunningTime(i)
                                    + ",ドライバー：" + stintData.getDriverName(i)
                                    + ",KartNo：" + stintData.getKartNo(i));
                }
                Log.d(TAG, "======DebugLOG End======");

            }
        });

        debugBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo
                testData();
            }
        });

        debugBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo
                //均等割り
                setEvenlyDividedStint();
                //ドライバー名をランダムで設定
                for (int i = 0; i < stintData.getAllStint(); i++) {
                    Random rand = new Random();
                    int rundomNo = rand.nextInt(4);
                    switch (rundomNo) {
                        case 0:
                            stintData.setDriverName(i, "秋間");
                            break;
                        case 1:
                            stintData.setDriverName(i, "豊口");
                            break;
                        case 2:
                            stintData.setDriverName(i, "吉戒");
                            break;
                        case 3:
                            stintData.setDriverName(i, "ルーク");
                            break;
                        default:
                            stintData.setDriverName(i, "-");
                    }
                }
                refreshDisplay();
            }
        });
    }


    private void setUniformityRunningTime() {
        for (int i = stintData.STINT_UPPER_LIMIT - 1; i >= 0; i--) {
            if (stintLayouts[i].getFlagCheckBox().isChecked()) {
                String uniformityStartTime = "";
                if (0 == i) {
                    uniformityStartTime = stintData.getStartTime();
                } else {
                    uniformityStartTime = stintData.getEndTime(i - 1);
                }
                String uniformityEndTime = timeCalc.calcPlusTime(stintData.getStartTime(), stintData.getRaceTime());

                Log.d(TAG, "uniformityStartTime = " + uniformityStartTime);
                Log.d(TAG, "uniformityEndTime = " + uniformityEndTime);
                uniformitySet(uniformityStartTime, uniformityEndTime, i);
                break;
            }
        }
    }


    private void btnInactive(Button btn) {
        btn.setBackgroundColor(android.R.color.darker_gray);
        btn.setTextColor(R.color.black);
    }

    private void btnActive(Button btn) {
        btn.setBackgroundColor(R.color.purple_700);
        btn.setTextColor(R.color.white);
    }

    private void tabBtnStateChange(Button btn) {

        btnActive(setBtn);
        btnActive(setRaceDataBtn);
        btnActive(nowBtn);
        btnActive(showStintBtn);

        btnInactive(btn);
    }

    /**
     * stintData,raceDataから再計算を行い表示を更新
     */
    private void reCalcRefreshDisplay() {
        Log.v(TAG, "in reCalcRefreshDisplay()");
        for (int i = 0; i < stintLayouts.length; i++) {
            //1Stint目はRaceData.javaで定義されていているスタート時間と比べる必要があるため別処理とする
            Log.v(TAG, "endTime[" + i + "]:" + stintData.getEndTime(i));
            if (i == 0) { //最初のStintはレーススタート時間と走行時間を設定する
                stintLayouts[i].setStartTimeText(stintData.getStartTime());
                stintLayouts[i].setRunTimeText(stintData.getEndTime(i));
            } else if (i == stintData.getAllStint()) { //最終Stintの場合は自身の走行終了時間を設定する
                stintLayouts[i].setRunTimeText(timeCalc.timeFormatExtraction(timeCalc.calcDiffMin(stintData.getEndTime(i - 1), stintData.getEndTime(i))));
            } else { //上記以外の場合は、スタート時間に前走者の走行終了時間を設定と、自身の走行終了時間を設定する
                stintLayouts[i].setStartTimeText(stintData.getEndTime(i - 1));
                stintLayouts[i].setRunTimeText(timeCalc.timeFormatExtraction(timeCalc.calcDiffMin(stintData.getEndTime(i - 1), stintData.getEndTime(i))));
            }
            stintLayouts[i].setEndTimeText(stintData.getEndTime(i));
            stintLayouts[i].setDriverText(stintData.getDriverName(i));
            stintLayouts[i].setKartText(stintData.getKartNo(i));
        }

        //RaceDataタブ
        startTimeSetText.setText(stintData.getStartTime());

        //Stint数以降を非表示にする
        for (int i = 0; i < stintData.getMaxStintCount(); i++) {
            if (i < stintData.getAllStint()) {
                stintLayouts[i].setFlagValid(true);
            } else {
                stintLayouts[i].setFlagValid(false);
            }
        }

        stintData.clearRaceData(stintData.getAllStint());
        Log.v(TAG, "out reCalcRefreshDisplay()");
    }

    private void refreshDisplay() {
        Log.d(TAG, "in refreshDisplay");
        for (int i = 0; i < stintLayouts.length; i++) {
            //1Stint目はRaceData.javaで定義されていているスタート時間と比べる必要があるため別処理とする
            Log.v(TAG, "endTime[" + i + "]:" + stintData.getEndTime(i));
            stintLayouts[i].setStartTimeText(stintData.getStintStartTime(i));
            stintLayouts[i].setEndTimeText(stintData.getEndTime(i));
            stintLayouts[i].setRunTimeText(timeCalc.timeFormatExtraction(Integer.parseInt(stintData.getRunningTime(i))));
            stintLayouts[i].setDriverText(stintData.getDriverName(i));
            stintLayouts[i].setKartText(stintData.getKartNo(i));
        }
        //RaceDataタブ
        startTimeSetText.setText(stintData.getStartTime());

        //Stint数以降を非表示にする
        for (int i = 0; i < stintData.getMaxStintCount(); i++) {
            if (i < stintData.getAllStint()) {
                stintLayouts[i].setFlagValid(true);
            } else {
                stintLayouts[i].setFlagValid(false);
            }
        }

        stintData.clearRaceData(stintData.getAllStint());
        Log.d(TAG, "out refreshDisplay");
    }

    /**
     * 引数のstartTimeからendTimeまでの走行時間を均等割りして、Stint以降の各Stintに反映
     *
     * @param startTime
     * @param endTime
     * @param stint
     */
    private void uniformitySet(String startTime, String endTime, int stint) {
        try {
            //StartTimeからendTimeまでの走行時間を計算
            String time = timeCalc.runTimeCalc(startTime, endTime);
            Log.d(TAG, "time = " + time);
            int time_min = timeCalc.convertTimeToMin(time);
            Log.d(TAG, "onClick: time_min = " + time_min);

            int remainingStint = stintData.getAllStint() - stint;
            int perStintTime = Math.round(time_min / remainingStint);

            Log.d(TAG, "uniformitySet: perStintTime = " + perStintTime);

            for (int i = stint; i < stintData.getAllStint(); i++) {
                if (i == stintData.getAllStint() - 1) {
                    //最終Stintはレース終了時間
                    stintData.setEndTime(i, stintData.getRaceEndTime());
                    stintData.setRunningTime(i, timeCalc.calcDiffMin(stintData.getStintStartTime(i), stintData.getRaceEndTime()));
                } else if (i == 0) {
                    //1Stint目はレーススタート時間に均等割りした時間を足す
                    stintData.setEndTime(i, timeCalc.calcPlusTime(stintData.getStartTime(), perStintTime));
                    stintData.setRunningTime(i, perStintTime);
                } else {
                    //上記以外は、前走者の走行終了時間に均等割りした時間を足す
                    stintData.setEndTime(i, timeCalc.calcPlusTime(stintData.getEndTime(i - 1), perStintTime));
                    stintData.setRunningTime(i, perStintTime);
                }
            }
        } catch (Exception e) {
            Log.d("Exception", "onClick: " + e);
        }
    }

    /**
     * 規則上最長の走行時間を計算して返す
     *
     * @param driverCnt 参加ドライバー人数
     * @return 均等割り＊COEF(120%ルール)の値を返す
     */
    private int maxRunTime(int driverCnt) {
        Log.d("maxRunTime", "raceTime = " + stintData.getRaceTime() + ",COEF = " + stintData.getCoef() + ",driverCnt = " + driverCnt);
        double maxTimeD = stintData.getRaceTime() / driverCnt * stintData.getCoef();
        Log.d("maxRunTime", "maxTimeD(double) = " + maxTimeD);
        int maxTimeI = (int) maxTimeD;
        Log.d("maxRunTime", "maxTimeI(int) = " + maxTimeI);
        return maxTimeI;
    }

    private void setRuntimeSum() {
        int[] runTime = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        runTime[0] = stintData.getDrivingTimeOfDriver("秋間");
        runTime[1] = stintData.getDrivingTimeOfDriver("豊口");
        runTime[2] = stintData.getDrivingTimeOfDriver("吉戒");
        runTime[3] = stintData.getDrivingTimeOfDriver("ルーク");
        runTime[4] = stintData.getDrivingTimeOfDriver("横田");
        runTime[5] = stintData.getDrivingTimeOfDriver("坪井");
        runTime[6] = stintData.getDrivingTimeOfDriver("新田");
        runTime[7] = stintData.getDrivingTimeOfDriver("X");
        runTime[9] = stintData.getDrivingTimeOfDriver("-");
        runTime[9] = stintData.getDrivingTimeOfDriver("中断");

        //走行時間が１分以上のドライバーの数を計算
        int driverCnt = 0;
        for (int i = 0; i < runTime.length - 2; i++) {
            if (runTime[i] > 0) {
                driverCnt++;
            }
        }
        if (driverCnt == 0) {
            driverCnt = runTime.length;
        }

        //規定走行時間以上の場合に赤文字で表示する
        for (int i = 0; i < runTime.length; i++) {
            //runSumTimeTextView[i].setText(timeCalc.timeFormatExtraction(runTime[i]));
            runSumTimeTextView[i].setText(runTime[i] + "min");
            if (runTime[i] >= maxRunTime(driverCnt)) {
                runSumTimeTextView[i].setTextColor(Color.RED);
            } else {
                runSumTimeTextView[i].setTextColor(Color.BLACK);
            }
        }
        maxRunTimeTextView.setText(maxRunTime(driverCnt) + "min");
    }

    /**
     * FlagがTrueのStintに対してセットした走行時間に変更。
     * FlagがFalseの項目に対してはセットした値を考慮した開始時間・終了時間に再セット
     *
     * @param runMin
     */
    private void flagItemSetMin(int runMin) {
        int flagCount = 0;
        int effectiveCheckBox = 99;
        for (int i = 0; i < stintData.getAllStint(); i++) {
            if (stintLayouts[i].isCheckedBox()) {
                flagCount++;
                effectiveCheckBox = i;
                stintData.setRunningTime(i, runMin);
            }
        }
        InfoDialog dialog = new InfoDialog();
        if (flagCount > 1) {
            dialog.setTitleStr("Error");
            dialog.setMessageStr("チェックが複数に入っていたので、" + effectiveCheckBox + 1 + "Stint目のみ更新しました。");
            dialog.setDialogType(InfoDialog.TWO_BUTTON_DIALOG);
            dialog.show(getSupportFragmentManager(), "");
        } else if (flagCount == 0) {
            dialog.setTitleStr("Error");
            dialog.setMessageStr("チェックが入っていません");
            dialog.setDialogType(InfoDialog.TWO_BUTTON_DIALOG);
            dialog.show(getSupportFragmentManager(), "");
        } else {
            dialog.setTitleStr("Success");
            dialog.setMessageStr(effectiveCheckBox + 1 + "Stint目の走行時間を更新しました。");
            dialog.setDialogType(InfoDialog.TWO_BUTTON_DIALOG);
            dialog.show(getSupportFragmentManager(), "");
        }
    }

    private void setDriver(int driverId) {
        boolean checkBoxs[] = checkboxController.getCheckBoxStates(stintLayouts);
        for (int i = 0; i < stintData.getAllStint(); i++) {
            if (checkBoxs[i]) {
                switch (driverId) {
                    case ID_AKIMA:
                        stintData.setDriverName(i, "秋間");
                        break;
                    case ID_TOYOGUCHI:
                        stintData.setDriverName(i, "豊口");
                        break;
                    case ID_YOSHIKAI:
                        stintData.setDriverName(i, "吉戒");
                        break;
                    case ID_LUKE:
                        stintData.setDriverName(i, "ルーク");
                        break;
                    case ID_YOKOTA:
                        stintData.setDriverName(i, "横田");
                        break;
                    case ID_TUBOI:
                        stintData.setDriverName(i, "坪井");
                        break;
                    case ID_NITTA:
                        stintData.setDriverName(i, "新田");
                        break;
                    case ID_X:
                        stintData.setDriverName(i, "X");
                        break;
                    case ID_NULL:
                        stintData.setDriverName(i, "-");
                        break;
                    case ID_BREAKE:
                        //Todo そのうち対応する。たぶん下のコードはうまくいっていない
//                        if (!stintData.getDriverName(i).equals("中断")){
//                            //ドライバーを繰り下げする
//                            shiftList(i);
//                            stintData.setDriver(i,"中断");
//                            //中断後のStintを均等割りする
//                            String uniformityStartTime = stintData.getRaceData()[i+1][1];
//                            String uniformityEndTime = timeCalc.calcPlusTime(stintData.getRaceData()[0][1],raceTime);
//                            uniformitySet(uniformityStartTime,uniformityEndTime,i+1);
//                        }
                        break;
                }

            }
        }
        refreshDisplay();
    }

    /**
     * レース時間・Stint数から全体を均等割りした値を設定する
     */
    public void setEvenlyDividedStint() {
        if (stintData.getPerStintTime() != 0) {
            for (int i = 0; i < stintData.getAllStint(); i++) {
                if (i == stintData.getAllStint() - 1) {
                    //最終Stintはレーススタート時間にレース時間を足したもの
                    stintData.setEndTime(i, timeCalc.calcPlusTime(stintData.getStartTime(), stintData.getRaceTime()));
                    stintData.setRunningTime(i, timeCalc.calcDiffMin(stintData.getEndTime(i - 1), stintData.getRaceEndTime()));
                } else if (i == 0) {
                    //1Stint目はレーススタート時間に均等割りした時間を足す
                    stintData.setEndTime(i, timeCalc.calcPlusTime(stintData.getStartTime(), stintData.getPerStintTime()));
                    stintData.setRunningTime(i, stintData.getPerStintTime());
                } else {
                    //上記以外は、前走者の走行終了時間に均等割りした時間を足す
                    stintData.setEndTime(i, timeCalc.calcPlusTime(stintData.getEndTime(i - 1), stintData.getPerStintTime()));
                    stintData.setRunningTime(i, stintData.getPerStintTime());
                }
                //Log.d(TAG,"onClick stintData.getStintStartTime(" + i + ") = " + stintData.getStintStartTime(i));
                //Log.d(TAG,"onClick stintData.getEndTime(" + i + ") = " + stintData.getEndTime(i));
                //stintData.setRunningTime(i,timeCalc.calcDiffMin(stintData.getStintStartTime(i), stintData.getEndTime(i)));
                Log.d(TAG, "onClick stintData.setRunningTime runningTime:" + stintData.getRunningTime(i));
            }
        }
        refreshDisplay();
//        reCalcRefreshDisplay();
    }

    /**
     * Stint毎にLayoutを定義していて量が多いため
     * メソッドを分けて実装
     */
    private void defineLayout() {

        //Debug用
        debugBtn = findViewById(R.id.debugBtn);
        debugBtn2 = findViewById(R.id.debugBtn2);
        debugBtn3 = findViewById(R.id.debugBtn3);

        //Layout
        raceDataLayout = findViewById(R.id.raceDataLayout);
        setStintData = findViewById(R.id.setStintData);
        showStintData = findViewById(R.id.showStintData);

        //Tabボタン
        setBtn = findViewById(R.id.setBtn);
        setRaceDataBtn = findViewById(R.id.setRaceDataBtn);
        nowBtn = findViewById(R.id.nowBtn);
        showStintBtn = findViewById(R.id.showStintBtn);

        //setタブ内のボタン
        openStintSetting = findViewById(R.id.openStintSetting);
        perStintSetBtn = findViewById(R.id.perStintSetBtn);
        perStintText = findViewById(R.id.perStintText);
        checkPerStintSetBtn = findViewById(R.id.checkPerStintSetBtn);
        setMinBtn = findViewById(R.id.setMinBtn);
        setMinEditText = findViewById(R.id.setMinEditText);
        recalcSwitch = findViewById(R.id.recalcSwitch);

        //RaceDataタブ内の項目
        raceTimeEditText = findViewById(R.id.raceTimeEditText);
        allStintEditText = findViewById(R.id.allStintEditText);
        startTimeSetBtn = findViewById(R.id.startTimeSetBtn);
        startTimeSetText = findViewById(R.id.startTimeSetText);
        confirmBtn = findViewById(R.id.confirmBtn);
        coefSwitch = findViewById(R.id.coefSwitch);

        //Stintタブ内の項目
        akimaSetBtn = findViewById(R.id.akimaSetBtn);
        toyoguchiSetBtn = findViewById(R.id.toyoguchiSetBtn);
        yoshikaiDriverSetBtn = findViewById(R.id.yoshikaiSetBtn);
        lukeSetBtn = findViewById(R.id.lukeSetBtn);
        yokotaSetBtn = findViewById(R.id.yokotaSetBtn);
        tuboiSetBtn = findViewById(R.id.tuboiSetBtn);
        nittaSetBtn = findViewById(R.id.nittaSetBtn);
        xSetBtn = findViewById(R.id.xSetBtn);
        breakeSetBtn = findViewById(R.id.breakSetBtn);
        nonDriverSetBtn = findViewById(R.id.nonDriverSetBtn);
        allCheckBtn = findViewById(R.id.allCheckBtn);
        allUncheckBtn = findViewById(R.id.allUncheckBtn);
        reverseBtn = findViewById(R.id.reverseBtn);


        view = new View[50];
        stintLayouts = new StintLayout[50];

        view[0] = findViewById(R.id.stint_layout0);
        view[1] = findViewById(R.id.stint_layout1);
        view[2] = findViewById(R.id.stint_layout2);
        view[3] = findViewById(R.id.stint_layout3);
        view[4] = findViewById(R.id.stint_layout4);
        view[5] = findViewById(R.id.stint_layout5);
        view[6] = findViewById(R.id.stint_layout6);
        view[7] = findViewById(R.id.stint_layout7);
        view[8] = findViewById(R.id.stint_layout8);
        view[9] = findViewById(R.id.stint_layout9);
        view[10] = findViewById(R.id.stint_layout10);
        view[11] = findViewById(R.id.stint_layout11);
        view[12] = findViewById(R.id.stint_layout12);
        view[13] = findViewById(R.id.stint_layout13);
        view[14] = findViewById(R.id.stint_layout14);
        view[15] = findViewById(R.id.stint_layout15);
        view[16] = findViewById(R.id.stint_layout16);
        view[17] = findViewById(R.id.stint_layout17);
        view[18] = findViewById(R.id.stint_layout18);
        view[19] = findViewById(R.id.stint_layout19);
        view[20] = findViewById(R.id.stint_layout20);
        view[21] = findViewById(R.id.stint_layout21);
        view[22] = findViewById(R.id.stint_layout22);
        view[23] = findViewById(R.id.stint_layout23);
        view[24] = findViewById(R.id.stint_layout24);
        view[25] = findViewById(R.id.stint_layout25);
        view[26] = findViewById(R.id.stint_layout26);
        view[27] = findViewById(R.id.stint_layout27);
        view[28] = findViewById(R.id.stint_layout28);
        view[29] = findViewById(R.id.stint_layout29);
        view[30] = findViewById(R.id.stint_layout30);
        view[31] = findViewById(R.id.stint_layout31);
        view[32] = findViewById(R.id.stint_layout32);
        view[33] = findViewById(R.id.stint_layout33);
        view[34] = findViewById(R.id.stint_layout34);
        view[35] = findViewById(R.id.stint_layout35);
        view[36] = findViewById(R.id.stint_layout36);
        view[37] = findViewById(R.id.stint_layout37);
        view[38] = findViewById(R.id.stint_layout38);
        view[39] = findViewById(R.id.stint_layout39);
        view[40] = findViewById(R.id.stint_layout40);
        view[41] = findViewById(R.id.stint_layout41);
        view[42] = findViewById(R.id.stint_layout42);
        view[43] = findViewById(R.id.stint_layout43);
        view[44] = findViewById(R.id.stint_layout44);
        view[45] = findViewById(R.id.stint_layout45);
        view[46] = findViewById(R.id.stint_layout46);
        view[47] = findViewById(R.id.stint_layout47);
        view[48] = findViewById(R.id.stint_layout48);
        view[49] = findViewById(R.id.stint_layout49);


        for (int i = 0; i < stintData.STINT_UPPER_LIMIT; i++) {
            stintLayouts[i] = new StintLayout(view[i]);
            Log.i(TAG, "i = " + i + ", i+1:" + Integer.toString(i + 1));
            stintLayouts[i].setStintText(Integer.toString(i + 1));
        }

        runSumTimeTextView = new TextView[stintData.getDriverCnt() + 1];
        stintCntTextView = new TextView[stintData.getDriverCnt() + 1];

        runSumTimeTextView[0] = findViewById(R.id.driver0SumTime);
        runSumTimeTextView[1] = findViewById(R.id.driver1SumTime);
        runSumTimeTextView[2] = findViewById(R.id.driver2SumTime);
        runSumTimeTextView[3] = findViewById(R.id.driver3SumTime);
        runSumTimeTextView[4] = findViewById(R.id.driver4SumTime);
        runSumTimeTextView[5] = findViewById(R.id.driver5SumTime);
        runSumTimeTextView[6] = findViewById(R.id.driver6SumTime);
        runSumTimeTextView[7] = findViewById(R.id.driver7SumTime);
        runSumTimeTextView[8] = findViewById(R.id.driver8SumTime);
        runSumTimeTextView[9] = findViewById(R.id.driver9SumTime);

        stintCntTextView[0] = findViewById(R.id.driver0StintCnt);
        stintCntTextView[1] = findViewById(R.id.driver1StintCnt);
        stintCntTextView[2] = findViewById(R.id.driver2StintCnt);
        stintCntTextView[3] = findViewById(R.id.driver3StintCnt);
        stintCntTextView[4] = findViewById(R.id.driver4StintCnt);
        stintCntTextView[5] = findViewById(R.id.driver5StintCnt);
        stintCntTextView[6] = findViewById(R.id.driver6StintCnt);
        stintCntTextView[7] = findViewById(R.id.driver7StintCnt);
        stintCntTextView[8] = findViewById(R.id.driver8StintCnt);
        stintCntTextView[9] = findViewById(R.id.driver9StintCnt);

        maxRunTimeTextView = findViewById(R.id.maxRunTimeTextView);
    }
}