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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.stintcalcapp2.R;
import com.example.stintcalcapp2.controller.TimeCalc;
import com.example.stintcalcapp2.model.RaceData;
import com.example.stintcalcapp2.model.StintData;

import java.util.Locale;

public class InputForm extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private StintData stintData;
    private RaceData raceData;
    private TextView startTimeText;
    private TextView endTimeText;
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
    private static int START_TIME_NUM = 999;
    private TimeCalc timeCalc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_form_activity);

        endTimeSetLayout = findViewById(R.id.endTimeSetLayout);
        driverSetLayout = findViewById(R.id.driverSetLayout);
        kartNoSetLayout = findViewById(R.id.kartNoSetLayout);

        driverSpinner = findViewById(R.id.driverSpinner);
        driverSetBtn = findViewById(R.id.driverSetBtn);

        kartNoSpinner = findViewById(R.id.kartNoSpinner);
        kartNoSetBtn = findViewById(R.id.kartNoSetBtn);

        Intent intent = getIntent();
        stintNum = intent.getIntExtra("Stint",0);//設定したkeyで取り出す
        stintData = (StintData) getIntent().getSerializableExtra("stintData");
        raceData = (RaceData) getIntent().getSerializableExtra("raceData");
        startTimeText = findViewById(R.id.startTimeText);
        endTimeText = findViewById(R.id.endTimeText);

        timeCalc = new TimeCalc();


        if (stintNum == START_TIME_NUM){
            endTimeSetLayout.setVisibility(View.GONE);
            driverSetLayout.setVisibility(View.GONE);
            kartNoSetLayout.setVisibility(View.GONE);
            startTimeText.setText(raceData.getStartTime());
        }else{
            startTimeText.setText(stintData.getEndTime(stintNum-1));
            endTimeText.setText(stintData.getEndTime(stintNum));

            //この画面を表示した際に、設定された値を取得して表示する
            //todo Stringで名前がかえって来るが、数字で返すように修正
            //driverSpinner.setSelection(Integer.parseInt(stintData.getDriverName(stintNum)));
            kartNoSpinner.setSelection(Integer.parseInt(stintData.getKartNo(stintNum)));
        }


        driverSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String name = driverNameText.getText().toString();
                String name = (String)driverSpinner.getSelectedItem();
                stintData.setDriverName(stintNum,name);
            }
        });

        kartNoSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kartNo = (String)kartNoSpinner.getSelectedItem();
                stintData.setKartNo(stintNum,kartNo);
            }
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String str = String.format(Locale.US, "%02d:%02d", hourOfDay, minute);
        if (Button == 0){
            startTimeText.setText( str );
            //前のStintの終了時間(=このStintの開始時間)をセットする
            stintData.setEndTime(stintNum-1,str);
        }else if(Button == 2){
//            //TODO
//            //スタート時間をセット
//            //上記値をもとに他の時間をずらす処理を考える
//            startTimeText.setText( str );
//
//            Log.v("InputForm","str:" + str);
//            Log.v("InputForm","StintData.getRaceData()[0][1]:" + stintData.getRaceData()[0][1].toString());
//
//            int diffMin = timeCalc.calcDiffMin(stintData.getRaceData()[0][1].toString(),str);
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
//
//            stintData.setStartTime(0,str);
//            Log.v("InputForm","InputForm onTimeSet raceStartTimeSet");
        }else{
            endTimeText.setText( str );
            stintData.setEndTime(stintNum,str);
        }

    }

    public void showTimePickerDialog(View v) {
        String[] times;
        if(stintNum == START_TIME_NUM){
            times = raceData.getStartTime().toString().split(":");
            Button = 2;
            Log.v("InputForm","showTimePickerDialog Button = 2");
        }else{
            times = stintData.getEndTime(stintNum-1).toString().split(":");
            Button = 0;
            Log.v("InputForm","showTimePickerDialog Button = 0");
        }

        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);

        String startTime = String.format("%02d:%02d",hour,minute);

        DialogFragment newFragment = new TimePick();
        newFragment.show(getSupportFragmentManager(), startTime);

    }

    public void showTimePickerDialog1(View v) {
        String[] times = stintData.getEndTime(stintNum).toString().split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);

        String endTime = String.format("%02d:%02d",hour,minute);

        DialogFragment newFragment1 = new TimePick();
        newFragment1.show(getSupportFragmentManager(), endTime);
        Button = 1;
    }

    /**
     * バックキーが押された際にプルダウンメニューから選択した値を設定する処理を追加
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (stintNum == 999) {
            Log.v("InputForm","StartTimeSet. stintNum=999");
        }else{
            stintData.setDriverName(stintNum, (String) driverSpinner.getSelectedItem());
            stintData.setKartNo(stintNum, (String) kartNoSpinner.getSelectedItem());
        }
        return super.onKeyDown(keyCode,event);
    }

}