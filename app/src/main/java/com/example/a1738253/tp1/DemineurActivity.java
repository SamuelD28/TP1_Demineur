package com.example.a1738253.tp1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
            int randomX = new Random().nextInt(9) + 1;
            int randomY = new Random().nextInt(9) + 1;

            Mine mine = new Mine(this, randomX, randomY);
            listeMine.add(mine);
        }
    }

    private void GenererTableau(int height, int width)
    {
        for (int y = 1; y <= 9; y++) {
            TableRow rangee = new TableRow(this);

            for (int x = 1; x <= 9; x++) {

                for(Mine mine : listeMine)
                {
                    if(mine.getPositionX() == x && mine.getPositionY() == y)
                    {
                        rangee.addView(mine,width/9,height/9);
                    }
                }
                if(rangee.getVirtualChildCount() == x - 1)
                {
                    Button button = new Button(this);
                    button.setText("0");
                    rangee.addView(button,width/9,height/9);
                }
            }
            tableauDemineur.addView(rangee);
        }
    }
}
