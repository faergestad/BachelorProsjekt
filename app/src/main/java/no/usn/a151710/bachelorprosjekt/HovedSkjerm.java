package no.usn.a151710.bachelorprosjekt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class HovedSkjerm extends AppCompatActivity {

    ImageView timeRegKnapp;
    ImageView oppdragKnapp;
    ImageView kundeKnapp;
    ImageView ansattKnapp;
    SharedPreferences userPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoved_skjerm);

        timeRegKnapp    = findViewById(R.id.timeregbtn);
        oppdragKnapp    = findViewById(R.id.oppdragbtn);
        kundeKnapp      = findViewById(R.id.kundebtn);
        ansattKnapp     = findViewById(R.id.ansattbtn);

        timeRegKnapp.setOnClickListener(onClickListener);
        oppdragKnapp.setOnClickListener(onClickListener);
        kundeKnapp.setOnClickListener(onClickListener);
        ansattKnapp.setOnClickListener(onClickListener);

        userPref = getSharedPreferences("login", 0);
        editor = userPref.edit();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.timeregbtn:
                    Intent startV1 = new Intent(v.getContext(), TimeRegistrering.class);
                    startActivity(startV1);
                    break;

                case R.id.oppdragbtn:
                    Intent startV2 = new Intent(v.getContext(), SeOppdrag.class);
                    startActivity(startV2);
                    break;

                case R.id.kundebtn:
                    Intent startV3 = new Intent(v.getContext(), SeKunde.class);
                    startActivity(startV3);
                    break;

                case R.id.ansattbtn:
                    Intent startV4 = new Intent(v.getContext(), SeAnsatte.class);
                    startActivity(startV4);

                    break;
            }

        }
    };

    public void logout(View view) {
        editor.putString("uID", "");
        editor.putString("password", "");
        editor.commit();

        Toast.makeText(HovedSkjerm.this, "Du har blitt logget ut", Toast.LENGTH_SHORT).show();

        Intent loggUt = new Intent(HovedSkjerm.this, LoginActivity.class);
        startActivity(loggUt);
    }
}
