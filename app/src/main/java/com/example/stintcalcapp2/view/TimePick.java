package com.example.stintcalcapp2.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePick extends DialogFragment implements
        TimePickerDialog.OnTimeSetListener {

    public static TimePick newInstance(String str) {
        // Fragemnt01 インスタンス生成
        TimePick timePick = new TimePick();
        // Bundle にパラメータを設定
        Bundle barg = new Bundle();
        barg.putString("Message", str);
        timePick.setArguments(barg);

        return timePick;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String time = getTag();

        Log.d("TAG", "onCreateDialog: tag = " + time);

        String[] times = time.toString().split(":");

        final Calendar c = Calendar.getInstance();
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);

        Log.d("TAG", "onCreateDialog: hour = " + hour);
        Log.d("TAG", "onCreateDialog: minute = " + minute);

        return new TimePickerDialog(getActivity(),
                (InputForm) getActivity(), hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
