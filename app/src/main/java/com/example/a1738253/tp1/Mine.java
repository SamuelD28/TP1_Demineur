package com.example.a1738253.tp1;

import android.content.Context;

import java.util.Random;

public class Mine extends android.support.v7.widget.AppCompatButton{

    private int positionX;
    private int positionY;
    private boolean estDesarmer;

    public Mine(Context context, int x, int y)
    {
        super(context);
        estDesarmer = false;
        positionX = x;
        positionY = y;
    }



    public boolean isEstDesarmer() {
        return estDesarmer;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setEstDesarmer(boolean estDesarmer) {
        this.estDesarmer = estDesarmer;
    }
}
