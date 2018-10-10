package com.example.a1738253.tp1;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class DemineurActivity extends AppCompatActivity {

    private TableLayout tableauDemineur;
    private ArrayList<Mine> listeMine;
    private Button boutonReset;
    private TextView textCountown;
    private CountDownTimer countdown;
    private final int dimensionXTableau = 8;
    private final int dimensionYTableau = 8;
    private final int nombreMines = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demineur);

        tableauDemineur = findViewById(R.id.tableauDemineur);
        listeMine = new ArrayList<>();
        textCountown = findViewById(R.id.textCountdown);
        countdown = new CountDownTimer(200000, 1000){
            @Override
            public void onTick(long l) {
                textCountown.setText("Time left : " + String.valueOf(l / 1000)+ " s");
            }

            @Override
            public void onFinish() {
                textCountown.setText(R.string.game_over);
            }
        };
        boutonReset = findViewById(R.id.btnReset);
        boutonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemarerPartie();
            }
        });
    }


    //On utilise cette methode pour pouvoir obtenir la hauteur et la largeur du tableau.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        DemarerPartie();
    }

    private void DemarerPartie()
    {
        countdown.cancel();
        countdown.start();
        tableauDemineur.removeAllViews();
        listeMine.clear();
        GenererMines();
        GenererTableau(tableauDemineur.getHeight(), tableauDemineur.getWidth());
    }

    private void GenererMines()
    {
        for(int i =0 ; i < nombreMines; i++)
        {
            int randomX = new Random().nextInt(dimensionXTableau - 2) +1 ;
            int randomY = new Random().nextInt(dimensionXTableau - 2) +1 ;


            for(Mine mine : listeMine)
            {
                if(mine.getPositionX() == randomX && mine.getPositionY() == randomY)
                {
                    randomX++;
                    randomY ++;
                }
            }

            Mine mine = new Mine(this, randomX, randomY);
            listeMine.add(mine);
        }
    }

    private void GenererTableau(int height, int width)
    {
        int[][] tempTableau = new int[dimensionXTableau + 2][dimensionYTableau + 2];

        for(Mine mine : listeMine)
        {
            int mineX = mine.getPositionX();
            int mineY = mine.getPositionY();
            tempTableau[mineX][mineY] = -1000;


            tempTableau[mineX+ 1][mineY] =  tempTableau[mineX+ 1][mineY] + 1;
            tempTableau[mineX + 1][mineY + 1] = tempTableau[mineX + 1][mineY + 1] + 1;
            tempTableau[mineX][mineY + 1] = tempTableau[mineX][mineY + 1] + 1;
            tempTableau[mineX + 1][mineY - 1] = tempTableau[mineX + 1][mineY - 1] + 1;
            tempTableau[mineX - 1][mineY + 1] = tempTableau[mineX - 1][mineY + 1] + 1;
            tempTableau[mineX - 1][mineY] = tempTableau[mineX - 1][mineY] + 1;
            tempTableau[mineX - 1][mineY - 1] = tempTableau[mineX - 1][mineY - 1]  + 1;
            tempTableau[mineX][mineY - 1] =  tempTableau[mineX][mineY - 1] + 1;
        }

        for (int y = 1; y <= dimensionYTableau; y++) {
            TableRow rangee = new TableRow(this);


            for (int x = 1; x <= dimensionXTableau; x++) {
                if(tempTableau[x][y] < 0)
                {
                    Mine mine = new Mine(this, x , y);
                    rangee.addView(mine , width / dimensionXTableau, height/dimensionYTableau);
                }
                else{
                    Indice indice = new Indice(this, tempTableau[x][y]);
                    rangee.addView(indice , width / dimensionXTableau, height/dimensionYTableau);
                }
            }
            tableauDemineur.addView(rangee);
        }
    }
}
