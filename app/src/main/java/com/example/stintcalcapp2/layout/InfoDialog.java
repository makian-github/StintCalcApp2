package com.example.stintcalcapp2.layout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.stintcalcapp2.controller.AsyncFunctionCallback;

/**
 * Dialog表示用のクラス
 * 設定項目
 * ・Title：Dialogに表示するタイトル：setTitleStr(String titleStr)
 * ・Message：Dialogに表示するメッセージ：setMessageStr(String messageStr)
 * ・DialogType：選択ボタンが1つor2つ(Positive or Negative)：setDialogType(int dialogType)
 * ・Positiveボタンの文言：setPositiveStr
 * ・Negativeボタンの文言：setNegativeStr
 * ・DialogTypeがTWO_BUTTON_DIALOGの場合は、
 * 　setAsyncFunctionCallback(AsyncFunctionCallback asyncFunctionCallback)を必ず使用すること
 */
public class InfoDialog extends DialogFragment {

    private final String TAG = "InfoDialog";

    private String messageStr = "";
    private String titleStr = "";
    private String positiveStr = "OK";
    private String negativeStr = "キャンセル";

    private AsyncFunctionCallback asyncFunctionCallback;

    private int dialogType = 0;

    public final static int ONE_BUTTON_DIALOG = 0;
    public final static int TWO_BUTTON_DIALOG = 1;

    public void setMessageStr(String messageStr) {
        this.messageStr = messageStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getPositiveStr() {
        return positiveStr;
    }

    public void setPositiveStr(String positiveStr) {
        this.positiveStr = positiveStr;
    }

    public String getNegativeStr() {
        return negativeStr;
    }

    public void setNegativeStr(String negativeStr) {
        this.negativeStr = negativeStr;
    }

    public AsyncFunctionCallback getAsyncFunctionCallback() {
        return asyncFunctionCallback;
    }

    public void setAsyncFunctionCallback(AsyncFunctionCallback asyncFunctionCallback) {
        this.asyncFunctionCallback = asyncFunctionCallback;
    }

    public int getDialogType() {
        return dialogType;
    }

    public void setDialogType(int dialogType) {
        this.dialogType = dialogType;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        CharSequence title = titleStr;
        CharSequence message = messageStr;
        CharSequence positive = positiveStr;
        CharSequence negative = negativeStr;

        switch (getDialogType()) {
            case ONE_BUTTON_DIALOG:
                builder.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(getPositiveStr(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
            case TWO_BUTTON_DIALOG:
                builder.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(getPositiveStr(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    getAsyncFunctionCallback().onAsyncFunctionFinished(true);
                                } catch (Exception e) {
                                    Log.d(TAG, "Exception e = " + e);
                                }
                            }
                        })
                        .setNegativeButton(getNegativeStr(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    getAsyncFunctionCallback().onAsyncFunctionFinished(false);
                                } catch (Exception e) {
                                    Log.d(TAG, "Exception e = " + e);
                                }
                            }
                        });
        }
        return builder.create();
    }
}
