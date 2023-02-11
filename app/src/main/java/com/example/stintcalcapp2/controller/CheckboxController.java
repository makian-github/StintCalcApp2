package com.example.stintcalcapp2.controller;

import android.util.Log;

import com.example.stintcalcapp2.layout.StintLayout;
import com.example.stintcalcapp2.model.StintData;

public class CheckboxController {

    private static final  String TAG = "CheckboxController";



    /**
     * チェックボックスがついている最後のチェックボックスを返す
     * @return チェックのついている最後のチェックボックスの番号
     */
    public int lastCheckBox(StintLayout[] stintLayouts, StintData stintData){
        int lastChkBox = 0;
        for (int i = stintData.getAllStint()-1; i >= 0; i--) {
            Log.v(TAG,"[" + i + "]" + stintLayouts[i].getFlagCheckBox().isChecked());
            if (stintLayouts[i].getFlagCheckBox().isChecked()){
                lastChkBox = i;
                break;
            }
        }

        Log.v(TAG,"lastCheck:" + lastChkBox);
        return lastChkBox;
    }

    /**
     * チェックボックスがついている最初のチェックボックスを返す
     * @return チェックのついている最初のチェックボックスの番号
     */
    public int firstCheckBox(StintLayout[] stintLayouts, StintData stintData){
        Log.v(TAG,"raceData.getStint=" + stintData.getAllStint());
        int firstChkBox = 99;
        for (int i = 0; i <= stintData.getAllStint(); i++) {
            Log.v(TAG,"[" + i + "]" + stintLayouts[i].getFlagCheckBox().isChecked());
            if (stintLayouts[i].getFlagCheckBox().isChecked()){
                firstChkBox = i;
                break;
            }
        }
        Log.v(TAG,"firstChkBox:" + firstChkBox);
        return firstChkBox;
    }

    /**
     * チェックがついているチェックボックスの数を返す
     * @return チェックがついているチェックボックスの数
     */
    public int checkedBoxesCnt(StintLayout[] stintLayouts, StintData stintData){
        int checkedBoxesCnt=0;
        for (int i = 0; i <= stintData.getAllStint(); i++) {
            if (stintLayouts[i].getFlagCheckBox().isChecked()){
                checkedBoxesCnt++;
            }
        }
        return checkedBoxesCnt;
    }

    public boolean[] getCheckBoxStates(StintLayout[] stintLayouts) {
        boolean[] checkedBoxNos = new boolean[stintLayouts.length];
        for (int i = 0; i < stintLayouts.length; i++) {
            if (stintLayouts[i].getFlagCheckBox().isChecked()) {
                checkedBoxNos[i] = true;
            } else {
                checkedBoxNos[i] = false;
            }
        }
        return checkedBoxNos;
    }

    public void setAllCheckBox(StintLayout[] stintLayouts, StintData stintData, boolean setbool){
        for (int i = 0; i < stintData.getAllStint(); i++) {
            stintLayouts[i].setFlagCheckBox(setbool);
        }
    }
}
