package com.example.stintcalcapp2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.stintcalcapp2.R;
import com.example.stintcalcapp2.controller.CheckboxController;
import com.example.stintcalcapp2.controller.TimeCalc;
import com.example.stintcalcapp2.layout.StintLayout;
import com.example.stintcalcapp2.model.StintData;

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

    /*RaceDataタブ*/
    private EditText raceTimeEditText;
    private EditText allStintEditText;
    private Button startTimeSetBtn;
    private TextView startTimeSetText;
    private Button confirmBtn;

    private StintData stintData;
    private TimeCalc timeCalc;
    private CheckboxController checkboxController;

    //Tab Layout
    private LinearLayout raceDataLayout;
    private LinearLayout setStintData;

    private String TAG = "MainActivity";

    private int displayTab = 0;

    //レース時間と全Stintから均等割りした時間
    private int perStintTime = 0;

    private static final int SET_TAB_NUM = 0;
    private static final int RACE_DATA_TAB_NUM = 1;
    private static final int NOW_TAB_NUM = 2;
    private static final int STINT_TAB_NUM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void testData(){
        stintData.setRaceTime(300);
        stintData.setAllStint(20);
        stintData.setStartTime("10:00");

        Log.v(TAG,"testData raceData.getStint=" + stintData.getAllStint());
    }

    @Override
    protected void onResume() {
        super.onResume();

        stintData = (StintData) this.getApplication();
        timeCalc = new TimeCalc();
        checkboxController = new CheckboxController();

        //Todo
        //testData();

        defineLayout();

        tabBtnStateChange(setBtn);

        Log.v(TAG,"displayTab=" + displayTab);

        if (displayTab == RACE_DATA_TAB_NUM){
            setStintData.setVisibility(View.GONE);
            raceDataLayout.setVisibility(View.VISIBLE);
        }

        switch (displayTab){
            case SET_TAB_NUM:
                setStintData.setVisibility(View.VISIBLE);
                raceDataLayout.setVisibility(View.GONE);
            case RACE_DATA_TAB_NUM:
                setStintData.setVisibility(View.GONE);
                raceDataLayout.setVisibility(View.VISIBLE);
            case NOW_TAB_NUM:
                setStintData.setVisibility(View.GONE);
                raceDataLayout.setVisibility(View.GONE);
            case STINT_TAB_NUM:
                setStintData.setVisibility(View.GONE);
                raceDataLayout.setVisibility(View.GONE);
        }


        refreshDisplay();

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raceDataLayout.setVisibility(View.GONE);
                setStintData.setVisibility(View.VISIBLE);
                displayTab = SET_TAB_NUM;

                tabBtnStateChange(setBtn);

                //均等割りした時間を算出して表示を更新
                if (stintData.getAllStint() > 0 && stintData.getAllStint() <= stintData.getStintCnt()) {
                    stintData.setPerStintTime(timeCalc.perStintTimeCalc(stintData.getRaceTime(),stintData.getAllStint()));
                    perStintText.setText(String.valueOf(stintData.getPerStintTime()));
                }

            }
        });

        setRaceDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStintData.setVisibility(View.GONE);
                raceDataLayout.setVisibility(View.VISIBLE);
                displayTab = RACE_DATA_TAB_NUM;

                tabBtnStateChange(setRaceDataBtn);
            }
        });

        nowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStintData.setVisibility(View.GONE);
                raceDataLayout.setVisibility(View.GONE);
                displayTab = NOW_TAB_NUM;

                tabBtnStateChange(nowBtn);
            }
        });

        showStintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStintData.setVisibility(View.GONE);
                raceDataLayout.setVisibility(View.GONE);
                displayTab = STINT_TAB_NUM;

                tabBtnStateChange(showStintBtn);
            }
        });

        /** Setタブ */
        //チェックが入っている項目の設定画面を開く
        openStintSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), InputForm.class);

                for (int i = 0; i < 40; i++) {
                    Log.i(TAG,"CheckBox[" + i + "]=" + stintLayouts[i].getFlagCheckBox().isChecked());
                }

                intent.putExtra("Stint", checkboxController.firstCheckBox(stintLayouts,stintData));//第一引数key、第二引数渡したい値

                Log.v(TAG,"Stint:" + checkboxController.firstCheckBox(stintLayouts,stintData) + ",stintData:" + stintData + "raceData:" + stintData );

                startActivity(intent);
            }
        });

        //レース時間・Stint数から全体を均等割りした値を設定する
        perStintSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stintData.getPerStintTime() != 0) {
                    for (int i = 0; i < stintData.getAllStint(); i++) {
                        if (i == stintData.getAllStint() - 1) {
                            //1Stint目はレーススタート時間に均等割りした時間を足す
                            stintData.setEndTime(i, timeCalc.calcPlusTime(stintData.getStartTime(), stintData.getRaceTime()));
                        } else if (i == 0) {
                            //最終Stintはレーススタート時間にレース時間を足したもの
                            stintData.setEndTime(i, timeCalc.calcPlusTime(stintData.getStartTime(), stintData.getPerStintTime()));
                        } else {
                            //上記以外は、前走者の走行終了時間に均等割りした時間を足す
                            stintData.setEndTime(i, timeCalc.calcPlusTime(stintData.getEndTime(i - 1), stintData.getPerStintTime()));
                        }
                    }
                }
                refreshDisplay();
            }
        });

        checkPerStintSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = stintData.STINT_UPPER_LIMIT-1; i >= 0; i--) {
                    if (stintLayouts[i].getFlagCheckBox().isChecked()){
                        String uniformityStartTime = "";
                        if (0==i){
                            uniformityStartTime = stintData.getStartTime();
                        }else {
                            uniformityStartTime = stintData.getEndTime(i - 1);
                        }
                        String uniformityEndTime = timeCalc.calcPlusTime(stintData.getStartTime(),stintData.getRaceTime());

                        Log.d(TAG, "uniformityStartTime = " + uniformityStartTime);
                        Log.d(TAG, "uniformityEndTime = " + uniformityEndTime);
                        uniformitySet(uniformityStartTime,uniformityEndTime,i);
                        break;
                    }
                }

                refreshDisplay();
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
                if (null != raceTimeEditText.getText()){
                    try {
                        stintData.setRaceTime(Integer.parseInt(raceTimeEditText.getText().toString()));
                    }catch (Exception e){
                        e.getStackTrace();
                        stintData.setRaceTime(0);
                        //Todo エラーを表示
                    }
                }
                if (null != allStintEditText.getText()){
                    try {
                        stintData.setAllStint(Integer.parseInt(allStintEditText.getText().toString()));
                    }catch ( Exception e){
                        e.getStackTrace();
                        stintData.setAllStint(0);
                        //Todo エラーを表示
                    }
                }
                refreshDisplay();
            }
        });
    }

    private void btnInactive(Button btn){
        btn.setBackgroundColor(android.R.color.darker_gray);
        btn.setTextColor(R.color.black);
    }

    private void btnActive(Button btn){
        btn.setBackgroundColor(R.color.purple_700);
        btn.setTextColor(R.color.white);
    }

    private void tabBtnStateChange(Button btn){

        btnActive(setBtn);
        btnActive(setRaceDataBtn);
        btnActive(nowBtn);
        btnActive(showStintBtn);

        btnInactive(btn);
    }

    /**
     * stintData,raceDataから取得した情報をもとに表示を更新
     */
    private void refreshDisplay(){
        Log.v(TAG,"in refreshDisplay()");
        for (int i = 0; i < stintLayouts.length; i++) {
            //1Stint目はRaceData.javaで定義されていているスタート時間と比べる必要があるため別処理とする
            Log.v(TAG,"endTime[" + i + "]:" + stintData.getEndTime(i));
            if (i == 0){ //最初のStintはレーススタート時間と走行時間を設定する
                stintLayouts[i].setStartTimeText(stintData.getStartTime());
                stintLayouts[i].setRunTimeText(stintData.getEndTime(i));
            }else if(i==stintData.getAllStint()){ //最終Stintの場合は自身の走行終了時間を設定する
                stintLayouts[i].setRunTimeText(timeCalc.timeFormatExtraction(timeCalc.calcDiffMin(stintData.getEndTime(i-1),stintData.getEndTime(i))));
            }else{ //上記以外の場合は、スタート時間に前走者の走行終了時間を設定と、自身の走行終了時間を設定する
                stintLayouts[i].setStartTimeText(stintData.getEndTime(i-1));
                stintLayouts[i].setRunTimeText(timeCalc.timeFormatExtraction(timeCalc.calcDiffMin(stintData.getEndTime(i-1),stintData.getEndTime(i))));
            }
            stintLayouts[i].setEndTimeText(stintData.getEndTime(i));
            stintLayouts[i].setDriverText(stintData.getDriverName(i));
            stintLayouts[i].setKartText(stintData.getKartNo(i));
        }

        //RaceDataタブ
        startTimeSetText.setText(stintData.getStartTime());

        //Stint数以降を非表示にする
        for (int i = 0; i < stintData.getMaxStintCount(); i++) {
            if (i < stintData.getAllStint()){
                stintLayouts[i].setFlagValid(true);
            }else {
                stintLayouts[i].setFlagValid(false);
            }
        }

        stintData.clearRaceData(stintData.getAllStint());
        Log.v(TAG,"out refreshDisplay()");
    }

    /**
     *引数のstartTimeからendTimeまでの走行時間を均等割りして、Stint以降の各Stintに反映
     * @param startTime
     * @param endTime
     * @param stint
     */
    private void uniformitySet(String startTime,String endTime,int stint){
        try {
            //StartTimeからendTimeまでの走行時間を計算
            String time = timeCalc.runTimeCalc(startTime,endTime);
            Log.d(TAG, "time = " + time);
            int time_min = timeCalc.convertTimeToMin(time);
            Log.d(TAG, "onClick: time_min = " + time_min);

            int remainingStint = stintData.getAllStint() - stint;
            int perStintTime = Math.round(time_min/remainingStint);

            Log.d(TAG, "uniformitySet: perStintTime = " + perStintTime);

            for (int i = stint; i < stintData.getAllStint(); i++) {
                if (i == stintData.getAllStint() - 1) {
                    //最終Stintはレーススタート時間にレース時間を足したもの
                    stintData.setEndTime(i, timeCalc.calcPlusTime(stintData.getStartTime(), stintData.getRaceTime()));
                } else if (i == 0) {
                    //1Stint目はレーススタート時間に均等割りした時間を足す
                    stintData.setEndTime(i, timeCalc.calcPlusTime(stintData.getStartTime(), perStintTime));
                } else {
                    //上記以外は、前走者の走行終了時間に均等割りした時間を足す
                    stintData.setEndTime(i, timeCalc.calcPlusTime(stintData.getEndTime(i - 1), perStintTime));
                }
            }
        }catch (Exception e){
            Log.d("Exception", "onClick: " + e);
        }
    }


    /**
     * Stint毎にLayoutを定義していて量が多いため
     * メソッドを分けて実装
     */
    private void defineLayout(){

        //Layout
        raceDataLayout = findViewById(R.id.raceDataLayout);
        setStintData = findViewById(R.id.setStintData);

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

        //RaceDataタブ内の項目
        raceTimeEditText = findViewById(R.id.raceTimeEditText);
        allStintEditText = findViewById(R.id.allStintEditText);
        startTimeSetBtn = findViewById(R.id.startTimeSetBtn);
        startTimeSetText = findViewById(R.id.startTimeSetText);
        confirmBtn = findViewById(R.id.confirmBtn);

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
            Log.i(TAG,"i = " + i + ", i+1:" + Integer.toString(i+1));
            stintLayouts[i].setStintText(Integer.toString(i+1));
        }
    }
}