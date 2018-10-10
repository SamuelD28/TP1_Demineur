package com.example.a1738253.tp1;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class Mine extends BoutonDemineur{

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

    //Getter and Setter
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

    //Methods
    @Override
    public void InsererDrapeau(Button btn) {
        if (btn.getText() == "D"){
            btn.setText(" ");
            aUnFlag = false;
            setEstDesarmer(false);
        }
        else {
            btn.setText("D");
            aUnFlag = true;
            setEstDesarmer(true);
        }
    }

    @Override
    public void AfficherContenuBouton(Button btn) {
        if (!aUnFlag) {
            btn.setText("M");
            btn.setBackgroundTintList(getResources().getColorStateList(R.color.Crimson));
        }
    }
}
