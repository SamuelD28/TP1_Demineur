package com.example.a1738253.tp1;

import android.content.Context;
import android.widget.Button;

public class Indice extends BoutonDemineur{

    private int nbMineAdjacente;

    public Indice(Context context, int p_nbMineAdjacente) {
        super(context);
        nbMineAdjacente = p_nbMineAdjacente;
    }

    @Override
    public void InsererDrapeau(Button btn) {
        if (!estRevele) {
            if (aUnFlag) {
                btn.setBackgroundResource(R.color.Grey);
                aUnFlag = false;
            } else {
                btn.setBackgroundResource(R.drawable.flag);
                aUnFlag = true;
            }
        }
    }


    @Override
    public void AfficherContenuBouton(Button btn) {
        if (!aUnFlag) {
            btn.setText(String.valueOf(nbMineAdjacente));
            if (nbMineAdjacente == 0)
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.White));
            else if (nbMineAdjacente == 1)
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.LightCoral));
            else if (nbMineAdjacente == 2)
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.Salmon));
            else if (nbMineAdjacente >= 3)
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.IndianRed));

            estRevele = true;
            DemineurActivity.Victoire();
        }
    }
}
