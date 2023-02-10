package com.example.stintcalcapp2.layout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class InfoDialog extends DialogFragment{

    private String messageStr = "";
    private String titleStr = "";

    public void setMessageStr(String messageStr) {
        this.messageStr = messageStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        CharSequence title = titleStr;
        CharSequence message = messageStr;


        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // ボタンを押した時の処理
                    }
                });
        return builder.create();
    }
}
