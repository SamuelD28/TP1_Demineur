package com.example.a1738253.tp1;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class DemineurActivity extends AppCompatActivity {

    private static TableLayout tableauDemineur;
    public static ArrayList<Mine> listeMine;
    private static TextView textCountown;
    private static CountDownTimer countdown;
    private Settings settings;
    public static int nbCasesReveles;
    private Button boutonReset;
    private EditText dimensionTableau;
    private EditText nombreMines;

    private final int dimensionParDefault = 9;
    private final int nombreMineParDefault = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demineur);

        //Initialise les settings dans le singleton
        settings = Settings.getInstance();
        settings.setDimensionXTableau(dimensionParDefault);
        settings.setDimensionYTableau(dimensionParDefault);
        settings.setNombreMines(nombreMineParDefault);

        //Recherche les textview associer au settings
        dimensionTableau = findViewById(R.id.dimensionTableau);
        nombreMines = findViewById(R.id.nombreMine);

        tableauDemineur = findViewById(R.id.tableauDemineur);
        listeMine = new ArrayList<>();
        textCountown = findViewById(R.id.textCountdown);

        //Initialisation de la methode de countdown, non completer
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

        //Methode pour recommencer l'execution de la partie
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
        //Vas regler les settings en fonction de ce que lutilisateur a mis dans les inputs
        int dimensionInt = Integer.valueOf(dimensionTableau.getText().toString());
        int nombreMineInt = Integer.valueOf(nombreMines.getText().toString());
        settings.setDimensionXTableau(dimensionInt);
        settings.setDimensionYTableau(dimensionInt);
        settings.setNombreMines(nombreMineInt);

        //On le cancel avant de la partir pour eviter que plusieurs countdown demare en meme temps
        countdown.cancel();
        countdown.start();
        tableauDemineur.removeAllViews();
        listeMine.clear();
        //Genenre une nouvelle liste de mines
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
                //Si une mine est deja place a lendroit aleatoir, on incremente sa postion de 1 en x et y.
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

    private static void DisableToutBouton()
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

    public static void JeuFinit()
    {
        for(Mine mine : listeMine)
        {
            if (!mine.isEstDesarmer())
            {
                break;
            }
            else {
                if (nbCasesReveles == (81 - listeMine.size()))
                {
                    countdown.cancel();
                    textCountown.setText(R.string.victory);
                }
            }
        }
    }

    //Cette méthode est appelée lorsque le temps esr écoulé ou que le joueur active une mine
    public static void GameOver()
    {
        countdown.onFinish();
        countdown.cancel();
        DisableToutBouton();
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
