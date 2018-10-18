package com.example.a1738253.tp1.Class;

import android.content.Context;
import android.widget.Button;

import com.example.a1738253.tp1.AbstractClass.BoutonDemineur;
import com.example.a1738253.tp1.R;

public class Indice extends BoutonDemineur {

    private int nbMineAdjacente;

    public Indice(Context context, int p_nbMineAdjacente) {
        super(context);
        nbMineAdjacente = p_nbMineAdjacente;
    }

    @Override
    public void AfficherContenuBouton(Button btn) {
        if (!getaUnFlag()) {
            btn.setText(String.valueOf(nbMineAdjacente));
            if (nbMineAdjacente == 0)
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.White));
            else if (nbMineAdjacente == 1)
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.LightCoral));
            else if (nbMineAdjacente == 2)
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.Salmon));
            else if (nbMineAdjacente >= 3)
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.IndianRed));

            setEstRevele(true);
        }
    }

    @Override
    public void HandleFlagCommand(Button btn, int ressourceId) {
        btn.setBackgroundResource(ressourceId);
        setaUnFlag(!getaUnFlag());
    }
}
