package com.example.a1738253.tp1;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public abstract class BoutonDemineur extends android.support.v7.widget.AppCompatButton{

    public boolean aUnFlag;

    public BoutonDemineur(Context context)
    {
        super(context);
        aUnFlag = false;

        //Methode pour montrer le contenu du bouton
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button)view;
                AfficherContenuBouton(btn);
            }
        });

        //Methode pour inserer un flag sur le bouton
        this.setOnLongClickListener(new OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                Button btn = (Button)view;
                InsererDrapeau(btn);
                return true;
            }
        });
    }

    //Abstract Method
    public abstract void InsererDrapeau(Button btn);

    public abstract void AfficherContenuBouton(Button btn);
}