package com.example.a1738253.tp1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demineur);

        tableauDemineur = findViewById(R.id.tableauDemineur);
        listeMine = new ArrayList<>();
    }

    //On utilise cette methode pour pouvoir obtenir la hauteur et la largeur du tableau.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        GenererMines();
        GenererTableau(tableauDemineur.getHeight(), tableauDemineur.getWidth());
    }

    private void GenererMines()
    {
        for(int i =0 ; i < 5; i++)
        {
            int randomX = new Random().nextInt(8);
            int randomY = new Random().nextInt(8);

            Mine mine = new Mine(this, randomX, randomY);
            listeMine.add(mine);
        }
    }

    private void GenererTableau(int height, int width)
    {
        String[][] tempTableau = new String[9][9];

        for(Mine mine : listeMine)
        {
            tempTableau[mine.getPositionX()][mine.getPositionY()] = "Mine";

            if(mine.getPositionX() < 9)
                tempTableau[mine.getPositionX()+ 1][mine.getPositionY()] = (tempTableau[mine.getPositionX()+ 1][mine.getPositionY()]  != "Mine")? "1": "Mine";
            if(mine.getPositionY() < 9 && mine.getPositionX() < 9)
                tempTableau[mine.getPositionX() + 1][mine.getPositionY() + 1] = (tempTableau[mine.getPositionX() + 1][mine.getPositionY() + 1]  != "Mine")? "1": "Mine";
            if(mine.getPositionY() < 9)
                tempTableau[mine.getPositionX()][mine.getPositionY() + 1] = (tempTableau[mine.getPositionX()][mine.getPositionY() + 1] != "Mine")? "1": "Mine";
            if(mine.getPositionX() < 9 && mine.getPositionY() > 0)
                tempTableau[mine.getPositionX() + 1][mine.getPositionY() - 1] = (tempTableau[mine.getPositionX() + 1][mine.getPositionY() - 1]  != "Mine")? "1": "Mine";
            if(mine.getPositionX() > 0 && mine.getPositionY() < 9)
                tempTableau[mine.getPositionX() - 1][mine.getPositionY() + 1] = (tempTableau[mine.getPositionX() - 1][mine.getPositionY() + 1]  != "Mine")? "1": "Mine";
            if(mine.getPositionX() > 0)
                tempTableau[mine.getPositionX() - 1][mine.getPositionY()] = (tempTableau[mine.getPositionX() - 1][mine.getPositionY()] != "Mine")? "1": "Mine";
            if(mine.getPositionX() > 0 && mine.getPositionY() > 0)
                tempTableau[mine.getPositionX() - 1][mine.getPositionY() - 1] = (tempTableau[mine.getPositionX() - 1][mine.getPositionY() - 1]   != "Mine")? "1": "Mine";
            if(mine.getPositionY() > 0)
                tempTableau[mine.getPositionX()][mine.getPositionY() - 1] = ( tempTableau[mine.getPositionX()][mine.getPositionY() - 1]  != "Mine")? "1": "Mine";

        }

        for (int y = 0; y < 9; y++) {
            TableRow rangee = new TableRow(this);

            for (int x = 0; x < 9; x++) {
                if(tempTableau[x][y] == null)
                {
                    Button bouton = new Button(this);
                    bouton.setText("0");
                    rangee.addView(bouton , width / 9, height/9);
                }
                else if(tempTableau[x][y] == "1")
                {
                    Button bouton = new Button(this);
                    bouton.setText("1");
                    bouton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    rangee.addView(bouton, width / 9, height/9);
                }
                else if(tempTableau[x][y] == "Mine"){
                    Button bouton = new Button(this);
                    bouton.setText("M");
                    bouton.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                    rangee.addView(bouton, width / 9, height/9);
                }
            }
            tableauDemineur.addView(rangee);
        }
    }
}
