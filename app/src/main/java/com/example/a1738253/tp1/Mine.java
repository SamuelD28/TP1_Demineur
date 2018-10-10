package com.example.a1738253.tp1;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class Mine extends android.support.v7.widget.AppCompatButton{

    private int positionX;
    private int positionY;
    private boolean estDesarmer;
    private boolean aUnFlag;

    public Mine(Context context, int x, int y)
    {
        super(context);
        estDesarmer = false;
        aUnFlag = false;
        positionX = x;
        positionY = y;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button)view;
                if (!aUnFlag || !DemineurActivity.isGameOver) {
                    btn.setText("M");
                    DemineurActivity.isGameOver = true;
                    btn.setBackgroundTintList(getResources().getColorStateList(R.color.Crimson));
                }
            }
        });

        this.setOnLongClickListener(new OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {
                Button btn = (Button)view;
                boutonLongClick(btn);
                return true;
            }

            private void boutonLongClick(Button btn){

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
        });
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
