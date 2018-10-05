package com.example.a1738253.tp1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class DemineurActivity extends AppCompatActivity {

    private TableLayout tableauDemineur;
    private ArrayList<Mine> listeMine;
    private Button boutonReset;
    private final int dimensionXTableau = 9;
    private final int dimensionYTableau = 9;
    private final int nombreMines = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demineur);

        tableauDemineur = findViewById(R.id.tableauDemineur);
        listeMine = new ArrayList<>();

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
                Button bouton = new Button(this);
                bouton.setText(String.valueOf(tempTableau[x][y]));

                if(tempTableau[x][y] < 0)
                {
                    bouton.setText("M");
                    bouton.setBackgroundTintList(getResources().getColorStateList(R.color.Crimson));
                }
                else if(tempTableau[x][y] == 1)
                    bouton.setBackgroundTintList(getResources().getColorStateList(R.color.LightCoral));
                else if(tempTableau[x][y] == 2)
                    bouton.setBackgroundTintList(getResources().getColorStateList(R.color.Salmon));
                else if(tempTableau[x][y] == 3)
                    bouton.setBackgroundTintList(getResources().getColorStateList(R.color.IndianRed));

                rangee.addView(bouton , width / 9, height/9);
            }
            tableauDemineur.addView(rangee);
        }
    }
}
