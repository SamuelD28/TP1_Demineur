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

    private TextView textCountown;
    private CountDownTimer countdown;

    private Button boutonReset;
    private Button boutonDimension;
    private Button boutonNbMine;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demineur);

        //A optimiser
        boutonDimension = findViewById(R.id.btnDimension);
        boutonNbMine = findViewById(R.id.btnMine);
        settings = Settings.getInstance(Integer.parseInt(boutonDimension.getHint().toString()),
                                        Integer.parseInt(boutonDimension.getHint().toString()),
                                        Integer.parseInt(boutonNbMine.getHint().toString()));

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
        for(int i =0 ; i < settings.getNombreMines(); i++)
        {
            int randomX = new Random().nextInt(settings.getDimensionXTableau() - 2) +1 ;
            int randomY = new Random().nextInt(settings.getDimensionYTableau() - 2) +1 ;


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
        int[][] tempTableau = new int[settings.getDimensionXTableau() + 2][settings.getDimensionYTableau() + 2];

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

        for (int y = 1; y <= settings.getDimensionYTableau(); y++) {
            TableRow rangee = new TableRow(this);


            for (int x = 1; x <= settings.getDimensionXTableau(); x++) {
                if(tempTableau[x][y] < 0)
                {
                    Mine mine = new Mine(this, x , y);
                    rangee.addView(mine , width / settings.getDimensionXTableau(), height/settings.getDimensionYTableau());
                }
                else{
                    Indice indice = new Indice(this, tempTableau[x][y]);
                    rangee.addView(indice , width / settings.getDimensionXTableau(), height/settings.getDimensionYTableau());
                }
            }
            tableauDemineur.addView(rangee);
        }
    }
}
