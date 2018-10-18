package com.example.a1738253.tp1.Controller;

import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a1738253.tp1.R;

public class Settings
{
    /**Valeur par default utiliser pour initialiser les settings*/
    private static final int nombreMineParDefault = 1;
    private static final int dimensionParDefault = 5;

    /**Properties**/
    private int nombreMines;
    private int dimensionTableau;
    private static Settings INSTANCE = null;

    private Settings(){
        nombreMines = nombreMineParDefault;
        dimensionTableau = dimensionParDefault;
    }

    public static synchronized Settings getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new Settings();

        return INSTANCE;
    }

    /**Getter and Setter**/
    public int getDimensionTableau() {
        return dimensionTableau;
    }

    public void setDimensionTableau(int dimensionTableau) {
        this.dimensionTableau = dimensionTableau;
    }

    public int getNombreMines()
    {
        return nombreMines;
    }

    public void setNombreMines(int nombreMines) {
        this.nombreMines = nombreMines;
    }

}
