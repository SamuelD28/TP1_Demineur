package com.example.a1738253.tp1;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a1738253.tp1.Class.TableauDemineur;
import com.example.a1738253.tp1.Controller.Settings;

public class DemineurActivity extends AppCompatActivity {

    /**
     * Controller for the activity
     */
    private Settings settings;
    private TableauDemineur Tableau;
    private CountDownTimer CountDown;

    /**
     * UI Elements
     */
    private Button BtnReset;
    private TextView TextViewCountdown;
    private EditText EditTextMinesNb;
    private EditText EditTextTableDimension;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demineur);

        EditTextTableDimension = findViewById(R.id.dimensionTableau);
        EditTextMinesNb = findViewById(R.id.nombreMine);
        TextViewCountdown = findViewById(R.id.textCountdown);
        BtnReset = findViewById(R.id.btnReset);

        settings = Settings.getInstance();
        Tableau = findViewById(R.id.tableauDemineur);

        Tableau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifierStatusPartie();
            }
        });
        Tableau.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                VerifierStatusPartie();
                return false;
            }
        });
        CountDown = new CountDownTimer(200000, 1000) {
            @Override
            public void onTick(long l) {
                TextViewCountdown.setText("Time left : " + String.valueOf(l / 1000) + " s");
            }

            @Override
            public void onFinish() {
                TextViewCountdown.setText("Game over");
            }
        };
        BtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DemmarerPartie();
            }
        });
        EditTextMinesNb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CountDown.cancel();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty())
                {
                    int nombreMines = Integer.valueOf(editable.toString());
                    if(nombreMines < settings.getDimensionTableau() * settings.getDimensionTableau() && nombreMines > 0)
                    {
                        settings.setNombreMines(Integer.valueOf(editable.toString()));
                        DemmarerPartie();
                    }
                    else
                        TextViewCountdown.setText("Nombre de mines incorect");
                }
            }
        });
        EditTextTableDimension.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CountDown.cancel();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty())
                {
                    int dimension = Integer.valueOf(editable.toString());
                    /**Perdu mon fichier utilitaire, have mercy*/
                    if(dimension >= 5 && dimension <=  12) {
                        settings.setDimensionTableau(Integer.valueOf(editable.toString()));
                        DemmarerPartie();
                    }
                    else
                        TextViewCountdown.setText("Dimension entre {5-12}");
                }
            }
        });
    }

    //On utilise cette methode pour pouvoir obtenir la hauteur et la largeur du tableau.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        InitialiserInput();
        DemmarerPartie();
    }

    public void InitialiserInput() {
        EditTextTableDimension.setText(String.valueOf(settings.getDimensionTableau()));
        EditTextMinesNb.setText(String.valueOf(settings.getNombreMines()));
    }

    public void DemmarerPartie() {
        CountDown.cancel();
        Tableau.GenererTableau();
        CountDown.start();
    }

    public void VerifierStatusPartie() {
        if (Tableau.getMineActiver())
            TerminerPartie("Game Over");
        else if (Tableau.getNbMinesRestantes() <= 0 && Tableau.getNbIndicesRestants() <= 0)
            TerminerPartie("Victoire");
    }

    public void TerminerPartie(String message) {
        TextViewCountdown.setText(message);
        Tableau.DisableTableau();
        CountDown.cancel();
    }
}
