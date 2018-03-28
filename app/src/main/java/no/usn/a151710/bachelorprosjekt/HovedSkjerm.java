package no.usn.a151710.bachelorprosjekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HovedSkjerm extends AppCompatActivity {

    private LinearLayout timeRegKnapp;
    private LinearLayout oppdragKnapp;
    private LinearLayout kundeKnapp;
    private LinearLayout ansattKnapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoved_skjerm);

        timeRegKnapp = findViewById(R.id.timeRegKnapp);
        oppdragKnapp = findViewById(R.id.oppdragKnapp);
        kundeKnapp = findViewById(R.id.kundeKnapp);
        ansattKnapp = findViewById(R.id.ansattKnapp);

        timeRegKnapp.setOnClickListener(onClickListener);
        oppdragKnapp.setOnClickListener(onClickListener);
        kundeKnapp.setOnClickListener(onClickListener);
        ansattKnapp.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.timeRegKnapp:
                    Intent startV1 = new Intent(v.getContext(), TimeRegistrering.class);
                    startActivity(startV1);
                    break;

                case R.id.oppdragKnapp:
                    Intent startV2 = new Intent(v.getContext(), SeOppdrag.class);
                    startActivity(startV2);
                    break;

                case R.id.kundeKnapp:
                    Intent startV3 = new Intent(v.getContext(), SeKunde.class);
                    startActivity(startV3);
                    break;

                case R.id.ansattKnapp:
                    Intent startV4 = new Intent(v.getContext(), SeAnsatte.class);
                    startActivity(startV4);
                    break;
            }

        }
    };

}
