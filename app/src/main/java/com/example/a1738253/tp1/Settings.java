package com.example.a1738253.tp1;

public class Settings
{
    /**Properties**/
    private int dimensionXTableau;
    private int dimensionYTableau;
    private int nombreMines;

    private Settings(int dimensionXTableau, int dimensionYTableau, int nombreMines)
    {
        this.dimensionXTableau = dimensionXTableau;
        this.dimensionYTableau = dimensionYTableau;
        this.nombreMines = nombreMines;
    }

    /**Property plus Method permettant dobtenir linstance**/
    private static Settings INSTANCE = null;
    public static synchronized Settings getInstance(int dimensionXTableau , int dimensionYTableau, int nombreMines)
    {
        if (INSTANCE == null)
            INSTANCE = new Settings(dimensionXTableau, dimensionYTableau, nombreMines);

        return INSTANCE;
    }

    /**Getter and Setter**/
    public int getDimensionXTableau() {
        return dimensionXTableau;
    }

    public void setDimensionXTableau(int dimensionXTableau) {
        this.dimensionXTableau = dimensionXTableau;
    }

    public int getDimensionYTableau() {
        return dimensionYTableau;
    }

    public void setDimensionYTableau(int dimensionYTableau) {
        this.dimensionYTableau = dimensionYTableau;
    }

    public int getNombreMines() {
        return nombreMines;
    }

    public void setNombreMines(int nombreMines) {
        this.nombreMines = nombreMines;
    }

}
