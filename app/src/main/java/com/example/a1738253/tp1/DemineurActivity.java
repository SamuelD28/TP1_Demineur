package com.example.a1738253.tp1;

import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class DemineurActivity extends AppCompatActivity {

    private TableLayout tableauDemineur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demineur);

        tableauDemineur = findViewById(R.id.tableauDemineur);
    }

    //On utilise cette methode pour pouvoir obtenir la hauteur et la largeur du tableau.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        GenererTableau(tableauDemineur.getHeight(), tableauDemineur.getWidth());
    }

    private void GenererTableau(int height, int width)
    {

        for (int i = 0; i < 9; i++) {
            TableRow rangee = new TableRow(this);

            for (int o = 0; o < 9; o++) {
                Button boutton = new Button(this);
                rangee.addView(boutton,width/9,height/9);
            }

            tableauDemineur.addView(rangee);
        }
    }
}
