package com.example.a1738253.tp1;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class DemineurActivity extends AppCompatActivity {

    private TableLayout tableauDemineur;
    public static ArrayList<Mine> listeMine;

    private static TextView textCountown;
    private static CountDownTimer countdown;

    private Button boutonReset;
    private Spinner spinnerDimension;

    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demineur);

        //A optimiser
        spinnerDimension = findViewById(R.id.spinnerDimension);

        settings = Settings.getInstance();
        settings.setDimensionXTableau(9);
        settings.setDimensionYTableau(9);
        settings.setNombreMines(9);

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

    //Cette méthode est appelée lorsque le temps esr écoulé ou que le joueur active une mine
    public static void GameOver()
    {
        countdown.onFinish();
        countdown.cancel();
    }


    private void DisableToutBouton()
    {
        for(int i = 0; i < tableauDemineur.getChildCount(); i++) {
            View view = tableauDemineur.getChildAt(i);
            if (view instanceof TableRow) {
                TableRow tr = (TableRow)view;
                for(int j = 0 ; j < tr.getChildCount(); j++)
                {
                    Button btn = (Button)tr.getChildAt(j);
                    btn.setEnabled(false);
                }
            }
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
