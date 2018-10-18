package com.example.a1738253.tp1.AbstractClass;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.example.a1738253.tp1.R;

public abstract class BoutonDemineur extends android.support.v7.widget.AppCompatButton {

    /**
     * Properties communes a tout les types de boutons
     */
    private boolean aUnFlag;
    private boolean estRevele;

    public BoutonDemineur(Context context) {
        super(context);
        aUnFlag = false;
        estRevele = false;
        this.setBackgroundResource(R.drawable.button_border);
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Button btn = (Button) view;
                InsererDrapeau(btn);
                /**Event Bubbling*/
                View parent = (View) view.getParent().getParent();
                parent.performClick();
                return true;
            }
        });
    }

    public abstract void AfficherContenuBouton(Button btn);

    public abstract void HandleFlagCommand(Button btn, int ressourceId);

    public boolean getEstRevele() {
        return estRevele;
    }

    public void setEstRevele(boolean estRevele) {
        this.estRevele = estRevele;
    }

    public boolean getaUnFlag() {
        return aUnFlag;
    }

    public void setaUnFlag(boolean aUnFlag) {
        this.aUnFlag = aUnFlag;
    }

    public void InsererDrapeau(Button btn) {

        if (!getEstRevele()) {
            /**Si le bouton a un flag*/
            if (getaUnFlag())
                HandleFlagCommand(btn, R.color.Grey);
            /**Si le bouton na pas de flag*/
            else
                HandleFlagCommand(btn, R.drawable.flag);
        }
    }
}
