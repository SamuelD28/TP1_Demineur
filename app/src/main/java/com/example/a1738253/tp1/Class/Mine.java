package com.example.a1738253.tp1.Class;

import android.content.Context;
import android.widget.Button;

import com.example.a1738253.tp1.AbstractClass.BoutonDemineur;
import com.example.a1738253.tp1.R;

public class Mine extends BoutonDemineur {

    /**
     * Properties
     */
    private int positionX;
    private int positionY;
    private boolean estDesarmer;

    public Mine(Context context, int x, int y) {
        super(context);
        estDesarmer = false;
        positionX = x;
        positionY = y;
    }

    @Override
    public void AfficherContenuBouton(Button btn) {
        if (!getaUnFlag()) {
            btn.setBackgroundResource(R.drawable.mine);
            setEstRevele(true);
        }
    }

    @Override
    public void HandleFlagCommand(Button btn, int ressourceId) {
        btn.setBackgroundResource(ressourceId);
        setEstDesarmer(!getEstDesarmer());
        setaUnFlag(!getaUnFlag());
    }

    public boolean getEstDesarmer() {
        return estDesarmer;
    }

    public void setEstDesarmer(boolean estDesarmer) {
        this.estDesarmer = estDesarmer;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

}
