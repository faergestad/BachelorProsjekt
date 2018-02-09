package no.usn.a151710.bachelorprosjekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText skrivBrukernavn;
    private EditText skrivPassord;
    private Switch huskMeg;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skrivBrukernavn = findViewById(R.id.userField);
        skrivPassord = findViewById(R.id.passField);
        huskMeg = findViewById(R.id.rememberSwitch);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), HovedSkjerm.class);
            startActivity(intent);
            String melding = "Success";
            Toast meldingsToast = Toast.makeText(getApplicationContext(), melding, Toast.LENGTH_LONG);
            meldingsToast.show();
        }
    };
}
