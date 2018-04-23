package no.usn.a151710.bachelorprosjekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

public class LoginActivity extends AppCompatActivity {

    private EditText skrivBrukernavn;
    private EditText skrivPassord;
    private Switch huskMeg;
    private ImageButton loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        skrivBrukernavn = findViewById(R.id.userField);
        skrivPassord = findViewById(R.id.passField);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), HovedSkjerm.class);
            startActivity(intent);
            /*String melding = "Success";
            Toast meldingsToast = Toast.makeText(getApplicationContext(), melding, Toast.LENGTH_LONG);
            meldingsToast.show();*/
        }
    };

}