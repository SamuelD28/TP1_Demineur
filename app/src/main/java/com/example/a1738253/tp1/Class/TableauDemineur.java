package com.example.a1738253.tp1.Class;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.a1738253.tp1.AbstractClass.BoutonDemineur;
import com.example.a1738253.tp1.Controller.Settings;

import java.util.ArrayList;
import java.util.Random;

public class TableauDemineur extends TableLayout {

    private int[][] TableauXY;
    public ArrayList<Mine> ListeMines;
    private Settings settings;
    private int NbMinesRestantes;
    private int NbIndicesRestants;

    private boolean MineActiver;

    public TableauDemineur(Context context) {
        super(context);
        ListeMines = new ArrayList<>();
        settings = Settings.getInstance();
        InitialiserPropertiesStatus();
    }

    public TableauDemineur(Context context, AttributeSet attrs) {
        super(context, attrs);
        ListeMines = new ArrayList<>();
        settings = Settings.getInstance();
        InitialiserPropertiesStatus();
    }

    public boolean getMineActiver() {
        return MineActiver;
    }

    public int getNbIndicesRestants() {
        return NbIndicesRestants;
    }

    public int getNbMinesRestantes() {
        int compteur = 0;
        for (Mine mine : ListeMines) {
            if (!mine.getEstDesarmer())
                compteur++;
        }
        return compteur;
    }

    private void InitialiserPropertiesStatus() {
        NbMinesRestantes = settings.getNombreMines();
        NbIndicesRestants = (int)Math.pow(settings.getDimensionTableau(), 2) - settings.getNombreMines();
        MineActiver = false;
    }

    private Mine GenererMine() {
        int randomX = GenererPositionAleatoire();
        int randomY = GenererPositionAleatoire();
        /**Rappel la fonction si les coordonnes existent deja*/
        if (CoordonnerMinesExiste(randomX, randomX)) GenererMine();
        /***/

        final Mine mine = new Mine(this.getContext(), randomX, randomY);
        mine.setOnClickListener(GenererClickListener(mine));
        return mine;
    }

    private Indice GenererIndice(int nombreMineAdjacente) {
        final Indice indice = new Indice(this.getContext(), nombreMineAdjacente);
        indice.setOnClickListener(GenererClickListener(indice));
        return indice;
    }

    private OnClickListener GenererClickListener(BoutonDemineur btn) {
        final BoutonDemineur bouton = btn;
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bouton.getaUnFlag())
                {
                    bouton.AfficherContenuBouton(bouton);
                    if (bouton instanceof Mine)
                        MineActiver = true;
                    else if(bouton instanceof  Indice)
                        NbIndicesRestants--;
                }
                /**Event bubbling*/
                View parent = (View) view.getParent().getParent();
                parent.performClick();
            }
        };
    }

    private int GenererPositionAleatoire() {
        // On fait un -2 + 1 parce que on genere un tableau deux rangee plus grands que la dimension demande.
        // Evite davoir a utiliser les conditions pour verifier si on est dans un coins
        return new Random().nextInt(settings.getDimensionTableau() - 2) + 1;
    }

    private void GenererListeMines() {
        for (int i = 0; i < settings.getNombreMines(); i++) {
            ListeMines.add(GenererMine());
        }
    }

    private boolean CoordonnerMinesExiste(int positionX, int positionY) {
        for (Mine mine : ListeMines) {
            if (mine.getPositionX() == positionX && mine.getPositionY() == positionY)
                return true;
        }
        return false;
    }

    private void InitialiserTableauXY() {
        // On fait un -2 + 1 parce que on genere un tableau deux rangee plus grands que la dimension demande.
        // Evite davoir a utiliser les conditions pour verifier si on est dans un coins
        TableauXY = new int[settings.getDimensionTableau() + 2][settings.getDimensionTableau() + 2];
    }

    private void GenererMinesDansTableauXY() {
        for (Mine mine : ListeMines) {
            int mineX = mine.getPositionX();
            int mineY = mine.getPositionY();
            TableauXY[mineX][mineY] = -1000;

            TableauXY[mineX + 1][mineY] = TableauXY[mineX + 1][mineY] + 1;
            TableauXY[mineX + 1][mineY + 1] = TableauXY[mineX + 1][mineY + 1] + 1;
            TableauXY[mineX][mineY + 1] = TableauXY[mineX][mineY + 1] + 1;
            TableauXY[mineX + 1][mineY - 1] = TableauXY[mineX + 1][mineY - 1] + 1;
            TableauXY[mineX - 1][mineY + 1] = TableauXY[mineX - 1][mineY + 1] + 1;
            TableauXY[mineX - 1][mineY] = TableauXY[mineX - 1][mineY] + 1;
            TableauXY[mineX - 1][mineY - 1] = TableauXY[mineX - 1][mineY - 1] + 1;
            TableauXY[mineX][mineY - 1] = TableauXY[mineX][mineY - 1] + 1;
        }
    }

    private void GenererContenuTableau() {
        for (int y = 1; y <= settings.getDimensionTableau(); y++) {
            TableRow rangee = new TableRow(this.getContext());
            for (int x = 1; x <= settings.getDimensionTableau(); x++) {
                if (TableauXY[x][y] < 0) {
                    Mine mine = TrouveMineParPosition(x, y);
                    rangee.addView(mine, this.getWidth() / settings.getDimensionTableau(), this.getHeight() / settings.getDimensionTableau());
                } else {
                    Indice indice = GenererIndice(TableauXY[x][y]);
                    rangee.addView(indice, this.getWidth() / settings.getDimensionTableau(), this.getHeight() / settings.getDimensionTableau());
                }
            }
            this.addView(rangee);
        }
    }

    private Mine TrouveMineParPosition(int x, int y) {
        for (Mine mine : ListeMines) {
            if (mine.getPositionX() == x && mine.getPositionY() == y)
                return mine;
        }
        return GenererMine();
    }

    private void ClearTableau() {
        ListeMines.clear();
        this.removeAllViews();
    }

    public void DisableTableau() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View view = this.getChildAt(i);
            if (view instanceof TableRow) {
                TableRow tr = (TableRow) view;
                for (int j = 0; j < tr.getChildCount(); j++) {
                    Button btn = (Button) tr.getChildAt(j);
                    btn.setEnabled(false);
                }
            }
        }
    }

    public void GenererTableau() {
        ClearTableau();
        InitialiserPropertiesStatus();
        InitialiserTableauXY();
        GenererListeMines();
        GenererMinesDansTableauXY();
        GenererContenuTableau();
    }
}
