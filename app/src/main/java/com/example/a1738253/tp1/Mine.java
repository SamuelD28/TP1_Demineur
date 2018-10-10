package com.example.a1738253.tp1;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.Button;

public class Mine extends BoutonDemineur{

    private int positionX;
    private int positionY;
    private boolean estDesarmer;
    private boolean estRevele = false;

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
        if (!estRevele){
            btn.setBackgroundResource(R.color.Grey);
            aUnFlag = false;
            setEstDesarmer(false);
        }
        else {
            btn.setBackgroundResource(R.drawable.mine);
            btn.setBackgroundColor(getResources().getColor(R.color.Crimson));
            aUnFlag = true;
            setEstDesarmer(true);
        }
    }

    @Override
    public void AfficherContenuBouton(Button btn) {
        if (!aUnFlag) {
            btn.setBackgroundResource(R.drawable.mine);
            estRevele = true;
        }
    }
}
