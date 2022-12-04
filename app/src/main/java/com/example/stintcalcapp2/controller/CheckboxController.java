package com.example.stintcalcapp2.controller;

import com.example.stintcalcapp2.layout.StintLayout;
import com.example.stintcalcapp2.model.RaceData;

public class CheckboxController {

    /**
     * チェックボックスがついている最後のチェックボックスを返す
     * @return チェックのついている最後のチェックボックスの番号
     */
    private int lastCheckBox(StintLayout[] stintLayouts, RaceData raceData){
        int lastChkBox = 0;
        for (int i = raceData.getStint()-1; i >= 0; i--) {
            if (stintLayouts[i].getFlagCheckBox().isChecked()){
                lastChkBox = i;
                break;
            }
        }
        return lastChkBox;
    }

    /**
     * チェックボックスがついている最初のチェックボックスを返す
     * @return チェックのついている最初のチェックボックスの番号
     */
    private int firstCheckBox(StintLayout[] stintLayouts, RaceData raceData){
        int firstChkBox = 0;
        for (int i = 0; i <= raceData.getStint(); i++) {
            if (stintLayouts[i].getFlagCheckBox().isChecked()){
                firstChkBox = i;
                break;
            }
        }
        return firstChkBox;
    }

    /**
     * チェックがついているチェックボックスの数を返す
     * @return チェックがついているチェックボックスの数
     */
    private int checkedBoxesCnt(StintLayout[] stintLayouts, RaceData raceData){
        int checkedBoxesCnt=0;
        for (int i = 0; i <= raceData.getStint(); i++) {
            if (stintLayouts[i].getFlagCheckBox().isChecked()){
                checkedBoxesCnt++;
            }
        }
        return checkedBoxesCnt;
    }
}
