package com.example.stintcalcapp2.layout;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stintcalcapp2.R;

public class StintLayout extends AppCompatActivity {

    private CheckBox FlagCheckBox = null;
    private TextView stintTextView = null;
    private TextView startTimeTextView = null;
    private TextView endTimeTextView = null;
    private TextView runTimeTextView = null;
    private TextView driverTextView = null;
    private TextView kartTextView = null;
    private View view = null;

    private static String TAG = "stintLayout";

    /**
     * StintLayout.
     * Layout Template.<br>
     * [LayoutAbout]<br>
     * [CheckBox] [Stint] [StartTime] [EndTime] [Runtime] [driverName] [KartNo]
     *
     * @param view
     */
    public StintLayout(View view) {
        Log.i(TAG, "const IN");
        this.view = view;
        FlagCheckBox = view.findViewById(R.id.checkbox);
        stintTextView = view.findViewById(R.id.stint);
        startTimeTextView = view.findViewById(R.id.startTime);
        endTimeTextView = view.findViewById(R.id.endTime);
        runTimeTextView = view.findViewById(R.id.runTime);
        driverTextView = view.findViewById(R.id.driver);
        kartTextView = view.findViewById(R.id.kartNo);
        Log.i(TAG, "const OUT");
    }

    public void setFlagCheckBox(Boolean flag) {
        FlagCheckBox.setChecked(flag);
    }

    public void setStintText(String stint) {
        this.stintTextView.setText(stint);
    }

    public void setStartTimeText(String startTime) {
        this.startTimeTextView.setText(startTime);
    }

    public void setEndTimeText(String endTime) {
        this.endTimeTextView.setText(endTime);
    }

    public void setRunTimeText(String runTime) {
        this.runTimeTextView.setText(runTime);
    }

    public void setDriverText(String driverName) {
        this.driverTextView.setText(driverName);
    }

    public void setKartText(String kartNo) {
        this.kartTextView.setText(kartNo);
    }

    public CheckBox getFlagCheckBox() {
        return FlagCheckBox;
    }

    /**
     * 引数で渡されたbooleanをもとに対象のチェックボックスの表示非表示を設定する
     *
     * @param valid
     */
    public void setFlagValid(boolean valid) {
        if (valid) {
            FlagCheckBox.setVisibility(View.VISIBLE);
        } else {
            FlagCheckBox.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * Stintのチェックボックスの状態を返す
     *
     * @return チェックボックスの状態
     */
    public boolean isCheckedBox() {
        boolean isChecked = false;
        isChecked = FlagCheckBox.isChecked();
        return isChecked;
    }
}
