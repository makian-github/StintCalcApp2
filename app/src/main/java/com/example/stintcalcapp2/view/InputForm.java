package com.example.stintcalcapp2.view;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.stintcalcapp2.R;
import com.example.stintcalcapp2.controller.TimeCalc;
import com.example.stintcalcapp2.model.StintData;

import static com.example.stintcalcapp2.model.ConstantsData.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InputForm extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private StintData stintData;
    private TextView startTimeText;
    private TextView endTimeText;
    private TextView runStr;
    private TextView runningTimeText;
    private EditText driverNameText;
    private Spinner driverSpinner;
    private Button driverSetBtn;
    private Button kartNoSetBtn;
    private int stintNum;
    private int Button;
    private Spinner kartNoSpinner;
    private LinearLayout endTimeSetLayout;
    private LinearLayout driverSetLayout;
    private LinearLayout kartNoSetLayout;
    private TimeCalc timeCalc;

    private Button setStartTimeBtn;
    private Button startNowTimeBtn;
    private Button endNowTimeBtn;

    private int diffTime = 0;

    private static final String TAG = "InputForm";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_form_activity);

        stintData = (StintData) this.getApplication();

        endTimeSetLayout = findViewById(R.id.endTimeSetLayout);
        driverSetLayout = findViewById(R.id.driverSetLayout);
        kartNoSetLayout = findViewById(R.id.kartNoSetLayout);

        driverSpinner = findViewById(R.id.driverSpinner);
        driverSetBtn = findViewById(R.id.driverSetBtn);

        kartNoSpinner = findViewById(R.id.kartNoSpinner);
        kartNoSetBtn = findViewById(R.id.kartNoSetBtn);

        startTimeText = findViewById(R.id.startTimeText);
        endTimeText = findViewById(R.id.endTimeText);

        runStr = findViewById(R.id.runStr);
        runningTimeText = findViewById(R.id.runningTime);

        setStartTimeBtn = findViewById(R.id.setStartTimeBtn);
        startNowTimeBtn = findViewById(R.id.startNowTimeBtn);
        endNowTimeBtn = findViewById(R.id.endNowTimeBtn);

        Intent intent = getIntent();
        stintNum = intent.getIntExtra("Stint", 0);//設定したkeyで取り出す

        timeCalc = new TimeCalc();

        //開始時間設定の場合
        if (stintNum == START_TIME_NUM) {
            endTimeSetLayout.setVisibility(View.GONE);
            runStr.setVisibility(View.GONE);
            runningTimeText.setVisibility(View.GONE);
            driverSetLayout.setVisibility(View.GONE);
            kartNoSetLayout.setVisibility(View.GONE);
            startTimeText.setText(stintData.getStartTime());
        } else {
            if (stintNum != 0) {
                startTimeText.setText(stintData.getEndTime(stintNum - 1));
            } else {
                startTimeText.setText(stintData.getStartTime());
                //stint0の場合は変更できないようにボタンを選択不可状態にする
                setStartTimeBtn.setEnabled(false);
                startNowTimeBtn.setEnabled(false);
            }
            endTimeText.setText(stintData.getEndTime(stintNum));

            runningTimeText.setText(timeCalc.timeFormatExtraction(Integer.parseInt(stintData.getRunningTime(stintNum))));

            //この画面を表示した際に、設定された値を取得して表示する
            int driverNo = 0;
            String driverName = stintData.getDriverName(stintNum);
            if (driverName.equals(DRIVER_NAME_INTERRUPTION)) {
                driverNo = 1;
            } else if (driverName.equals(DRIVER_NAME_AKIMA)) {
                driverNo = 2;
            } else if (driverName.equals(DRIVER_NAME_TOYOGUCHI)) {
                driverNo = 3;
            } else if (driverName.equals(DRIVER_NAME_YOSHIKAI)) {
                driverNo = 4;
            } else if (driverName.equals(DRIVER_NAME_LUKE)) {
                driverNo = 5;
            } else if (driverName.equals(DRIVER_NAME_YOKOTA)) {
                driverNo = 6;
            } else if (driverName.equals(DRIVER_NAME_TUBOI)) {
                driverNo = 7;
            } else if (driverName.equals(DRIVER_NAME_NITTA)) {
                driverNo = 8;
            } else if (driverName.equals(DRIVER_NAME_X)) {
                driverNo = 9;
            } else {
                driverNo = 0;
            }
            driverSpinner.setSelection(driverNo);
            kartNoSpinner.setSelection(Integer.parseInt(stintData.getKartNo(stintNum)));
        }

        Log.v(TAG, "stintNum:" + stintNum + ",startTimeText:" + startTimeText.getText() + ",endTime:" + endTimeText.getText());


        driverSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String name = driverNameText.getText().toString();
                String name = (String) driverSpinner.getSelectedItem();
                stintData.setDriverName(stintNum, name);
            }
        });

        kartNoSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kartNo = (String) kartNoSpinner.getSelectedItem();
                stintData.setKartNo(stintNum, kartNo);
            }
        });

        startNowTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("HH:mm");
                Date date = new Date(System.currentTimeMillis());
                String setTime = df.format(date);

                startTimeText.setText(setTime);
                if (stintNum == 0 || stintNum == START_TIME_NUM) {//1Stint目の場合
                    stintData.setStartTime(setTime);
                } else {
                    //前のStintの終了時間(=このStintの開始時間)をセットする
                    stintData.setEndTime(stintNum - 1, setTime);
                }
                runningTimeSet(stintNum);

            }
        });

        endNowTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("HH:mm");
                Date date = new Date(System.currentTimeMillis());
                String setTime = df.format(date);

                endTimeText.setText(setTime);
                stintData.setEndTime(stintNum, setTime);
                runningTimeSet(stintNum);


            }
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.v(TAG, "in onTimeSet()");
        String setTime = String.format(Locale.US, "%02d:%02d", hourOfDay, minute);
        if (Button == 0) {
            startTimeText.setText(setTime);
            if (stintNum == 0) {//1Stint目の場合
                stintData.setStartTime(setTime);
            } else {
                //前のStintの終了時間(=このStintの開始時間)をセットする
                stintData.setEndTime(stintNum - 1, setTime);
            }

        } else if (Button == 2) {
            diffTime = timeCalc.calcDiffMin(stintData.getStartTime(), setTime);
            startTimeText.setText(setTime);
            stintData.setStartTime(setTime);
//            //TODO
//            //スタート時間をセット
//            //上記値をもとに他の時間をずらす処理を考える
//            startTimeText.setText( setTime );
//
//            Log.v("InputForm","setTime:" + setTime);
//            Log.v("InputForm","StintData.getRaceData()[0][1]:" + stintData.getRaceData()[0][1].toString());
//
//            int diffMin = timeCalc.calcDiffMin(stintData.getRaceData()[0][1].toString(),setTime);
//
//            //int diffMin = 15;
//
//            stintData.setStartTime(0,timeCalc.calcPlusTime(stintData.getRaceData()[0][1].toString(),diffMin));
//            //stintData.setEndTime(0,timeCalc.calcPlusTime(stintData.getRaceData()[0][2].toString(),diffMin));
//
//            for (int i = 1; i < stintData.getStintCnt(); i++) {
//                //それぞれのStintの開始時間・終了時間にdiffMinを足すことで全体的に時刻を調整する
//                //stintData.setStartTime(0,timeCalc.calcPlusTime(stintData.getRaceData()[i][1].toString(),diffMin));
//                stintData.setEndTime(0,timeCalc.calcPlusTime(stintData.getRaceData()[i][2].toString(),diffMin));
//                //Log.v("InputForm","["+i+"] start:"+timeCalc.calcPlusTime(stintData.getRaceData()[i][1].toString(),diffMin) + ",end:" + timeCalc.calcPlusTime(stintData.getRaceData()[i][2].toString(),diffMin));
//                Log.v("InputForm","["+i+"] start:"+timeCalc.calcPlusTime(stintData.getRaceData()[i][1].toString(),diffMin) + ",end:" + timeCalc.calcPlusTime(stintData.getRaceData()[i][2].toString(),diffMin));
//            }
        } else {
            endTimeText.setText(setTime);
            stintData.setEndTime(stintNum, setTime);
        }

        runningTimeSet(stintNum);

        Log.v(TAG, "out onTimeSet()");
    }

    /**
     * 走行時間を設定する。ただし、開始時間設定の場合は設定不要のため何も処理を行わない。
     * @param stintNum 設定するStint
     */
    private void runningTimeSet(int stintNum){
        if (stintNum != START_TIME_NUM) {
            int runningTime = timeCalc.calcDiffMin(startTimeText.getText().toString(), endTimeText.getText().toString());
            stintData.setRunningTime(stintNum, runningTime);
            runningTimeText.setText(timeCalc.timeFormatExtraction(Integer.parseInt(stintData.getRunningTime(stintNum))));
        }
    }

    public void showTimePickerDialog(View v) {
        Log.v(TAG, "in showTimePickerDialog()");
        String[] times;
        if (stintNum == START_TIME_NUM) {
            times = stintData.getStartTime().toString().split(":");
            Button = 2;
            Log.v("InputForm", "showTimePickerDialog Button = 2");
        } else {
            times = stintData.getStintStartTime(stintNum).toString().split(":");
            Button = 0;
            Log.v("InputForm", "showTimePickerDialog Button = 0");
        }

        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);

        String startTime = String.format("%02d:%02d", hour, minute);

        DialogFragment newFragment = new TimePick();
        newFragment.show(getSupportFragmentManager(), startTime);

        if (stintNum != START_TIME_NUM) {
            Log.v(TAG, "stintData.getDriverName(stintNum):" + stintData.getDriverName(stintNum) + ",stintData.getEndTime(stintNum):" + stintData.getEndTime(stintNum));
        }

        Log.v(TAG, "out showTimePickerDialog()");
    }

    public void showTimePickerDialog1(View v) {
        Log.v(TAG, "in showTimePickerDialog1()");
        String[] times = stintData.getEndTime(stintNum).toString().split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);

        String endTime = String.format("%02d:%02d", hour, minute);

        DialogFragment newFragment1 = new TimePick();
        newFragment1.show(getSupportFragmentManager(), endTime);
        Button = 1;
        Log.v(TAG, "out showTimePickerDialog1()");
    }

    /**
     * バックキーが押された際にプルダウンメニューから選択した値を設定する処理を追加
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.v("InputForm", "onKeyDown. stintNum=" + stintNum + ",diffTime=" + diffTime);
        if (stintNum == START_TIME_NUM) {
            if (0 != diffTime) {
                for (int i = 0; i < stintData.getAllStint(); i++) {
                    stintData.setEndTime(i, timeCalc.calcPlusTime(stintData.getEndTime(i), diffTime));
                }
            }
            Log.v("InputForm", "StartTimeSet. stintNum=999");
        } else {
            stintData.setDriverName(stintNum, (String) driverSpinner.getSelectedItem());
            stintData.setKartNo(stintNum, (String) kartNoSpinner.getSelectedItem());
        }
        return super.onKeyDown(keyCode, event);
    }

}