package com.example.a1738253.tp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class DemineurActivity extends AppCompatActivity {


    private ArrayList<Button> bouttons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demineur);

        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);

        for (int i = 0; i < 10; i++) {
            TableRow rangee = new TableRow(this);

            for (int o = 0; o < 10; o++) {
                Button boutton = new Button(this);
                rangee.addView(boutton,100,100);

            }
            table.addView(rangee);

        }


    }
    // bouttons.add(boutton);





}
