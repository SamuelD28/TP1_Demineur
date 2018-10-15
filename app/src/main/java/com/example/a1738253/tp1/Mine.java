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

        if (!estRevele) {
            if (aUnFlag) {
                btn.setBackgroundResource(R.color.Grey);
                RemettreMine(btn.getContext());
                setEstDesarmer(false);
                aUnFlag = false;
            } else {
                btn.setBackgroundResource(R.drawable.flag);
                setEstDesarmer(true);
                 EnleverMine();
                 DemineurActivity.JeuFinit();
                aUnFlag = true;
            }
        }
    }

    @Override
    public void AfficherContenuBouton(Button btn) {
        if (!aUnFlag) {
            btn.setBackgroundResource(R.drawable.mine);
            estRevele = true;
            DemineurActivity.GameOver();
        }
    }

    private void RemettreMine(Context context)
    {
        setEstDesarmer(false);
    }

    private void EnleverMine()
    {
      setEstDesarmer(true);
    }
}
