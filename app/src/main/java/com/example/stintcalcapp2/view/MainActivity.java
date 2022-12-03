package com.example.stintcalcapp2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.stintcalcapp2.R;
import com.example.stintcalcapp2.layout.StintLayout;

public class MainActivity extends AppCompatActivity {

    StintLayout stintLayouts[];
    View view[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defineLayout();
    }

    private void defineLayout(){

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
        view[10] = findViewById(R.id.stint_layout20);
        view[21] = findViewById(R.id.stint_layout21);
        view[22] = findViewById(R.id.stint_layout22);
        view[23] = findViewById(R.id.stint_layout23);
        view[24] = findViewById(R.id.stint_layout24);
        view[25] = findViewById(R.id.stint_layout25);
        view[26] = findViewById(R.id.stint_layout26);
        view[27] = findViewById(R.id.stint_layout27);
        view[28] = findViewById(R.id.stint_layout28);
        view[29] = findViewById(R.id.stint_layout29);
        view[20] = findViewById(R.id.stint_layout30);
        view[31] = findViewById(R.id.stint_layout31);
        view[32] = findViewById(R.id.stint_layout32);
        view[33] = findViewById(R.id.stint_layout33);
        view[34] = findViewById(R.id.stint_layout34);
        view[35] = findViewById(R.id.stint_layout35);
        view[36] = findViewById(R.id.stint_layout36);
        view[37] = findViewById(R.id.stint_layout37);
        view[38] = findViewById(R.id.stint_layout38);
        view[39] = findViewById(R.id.stint_layout39);
        view[30] = findViewById(R.id.stint_layout40);
        view[41] = findViewById(R.id.stint_layout41);
        view[42] = findViewById(R.id.stint_layout42);
        view[43] = findViewById(R.id.stint_layout43);
        view[44] = findViewById(R.id.stint_layout44);
        view[45] = findViewById(R.id.stint_layout45);
        view[46] = findViewById(R.id.stint_layout46);
        view[47] = findViewById(R.id.stint_layout47);
        view[48] = findViewById(R.id.stint_layout48);
        view[49] = findViewById(R.id.stint_layout49);


        stintLayouts[0] = new StintLayout(view[0]);
        stintLayouts[1] = new StintLayout(view[1]);
        stintLayouts[2] = new StintLayout(view[2]);
        stintLayouts[3] = new StintLayout(view[3]);
        stintLayouts[4] = new StintLayout(view[4]);
        stintLayouts[5] = new StintLayout(view[5]);
        stintLayouts[6] = new StintLayout(view[6]);
        stintLayouts[7] = new StintLayout(view[7]);
        stintLayouts[8] = new StintLayout(view[8]);
        stintLayouts[9] = new StintLayout(view[9]);
        stintLayouts[10] = new StintLayout(view[10]);
        stintLayouts[11] = new StintLayout(view[11]);
        stintLayouts[12] = new StintLayout(view[12]);
        stintLayouts[13] = new StintLayout(view[13]);
        stintLayouts[14] = new StintLayout(view[14]);
        stintLayouts[15] = new StintLayout(view[15]);
        stintLayouts[16] = new StintLayout(view[16]);
        stintLayouts[17] = new StintLayout(view[17]);
        stintLayouts[18] = new StintLayout(view[18]);
        stintLayouts[19] = new StintLayout(view[19]);
        stintLayouts[20] = new StintLayout(view[10]);
        stintLayouts[21] = new StintLayout(view[21]);
        stintLayouts[22] = new StintLayout(view[22]);
        stintLayouts[23] = new StintLayout(view[23]);
        stintLayouts[24] = new StintLayout(view[24]);
        stintLayouts[25] = new StintLayout(view[25]);
        stintLayouts[26] = new StintLayout(view[26]);
        stintLayouts[27] = new StintLayout(view[27]);
        stintLayouts[28] = new StintLayout(view[28]);
        stintLayouts[29] = new StintLayout(view[29]);
        stintLayouts[30] = new StintLayout(view[20]);
        stintLayouts[31] = new StintLayout(view[31]);
        stintLayouts[32] = new StintLayout(view[32]);
        stintLayouts[33] = new StintLayout(view[33]);
        stintLayouts[34] = new StintLayout(view[34]);
        stintLayouts[35] = new StintLayout(view[35]);
        stintLayouts[36] = new StintLayout(view[36]);
        stintLayouts[37] = new StintLayout(view[37]);
        stintLayouts[38] = new StintLayout(view[38]);
        stintLayouts[39] = new StintLayout(view[39]);
        stintLayouts[40] = new StintLayout(view[30]);
        stintLayouts[41] = new StintLayout(view[41]);
        stintLayouts[42] = new StintLayout(view[42]);
        stintLayouts[43] = new StintLayout(view[43]);
        stintLayouts[44] = new StintLayout(view[44]);
        stintLayouts[45] = new StintLayout(view[45]);
        stintLayouts[46] = new StintLayout(view[46]);
        stintLayouts[47] = new StintLayout(view[47]);
        stintLayouts[48] = new StintLayout(view[48]);
        stintLayouts[49] = new StintLayout(view[49]);


    }
}