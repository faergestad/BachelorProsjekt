package no.usn.a151710.bachelorprosjekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class LoginActivity extends AppCompatActivity {

    private EditText skrivBrukernavn, skrivPassord;
    private Switch huskMeg;
    private ImageButton loginBtn;
    boolean ikkeTom;
    SharedPreferences pref, userPref;
    SharedPreferences.Editor editor, userEditor;
    private static final String JSON_URL = "http://gakk.one/appLogin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("Arbeidsplass", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Firma", "");
        editor.apply();

        huskMeg = findViewById(R.id.huskMeg);
        skrivBrukernavn = findViewById(R.id.userField);
        skrivPassord = findViewById(R.id.passField);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(onClickListener);

        userPref = getSharedPreferences("login", 0);
        userEditor = userPref.edit();
        String prefUname = userPref.getString("uID", "");
        String prefPass = userPref.getString("password", "");

        if (!(prefUname.equals("") && prefPass.equals(""))) {
            loggInn(prefUname, prefPass);
        }

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sjekkTekstFelt();
            if(ikkeTom) {
                loggInn(skrivBrukernavn.getText().toString(), skrivPassord.getText().toString());

                if(huskMeg.isChecked()) {
                    userEditor.putString("uID", skrivBrukernavn.getText().toString());
                    userEditor.putString("password", skrivPassord.getText().toString());
                    userEditor.apply();
                }

            } else {
                skrivBrukernavn.setError("Fyll ut alle feltene");
                skrivBrukernavn.requestFocus();
            }
        }
    };

    public void sjekkTekstFelt() {
        // Sjekker om id og passord er fylt ut
        if (TextUtils.isEmpty(skrivBrukernavn.getText()) || TextUtils.isEmpty(skrivPassord.getText())) {
            ikkeTom = false;
        } else {
            ikkeTom = true;
        }
    }

    private void loggInn(final String uID, final String password) {
        // Sender en forespørsel til serveren
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Sjekker svaret fra serveren
                        if(response.equals("login")){
                            // Login vellykket, sendes til hovedmenyen
                            Toast.makeText(LoginActivity.this, "Logget Inn", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HovedSkjerm.class);
                            startActivity(intent);
                        }else{
                            // Login feilet, gir beskjed til bruker
                            skrivPassord.setError("Feil ID eller passord");
                            skrivPassord.requestFocus();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Det skjedde en feil ved sendt request
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            // Sender med parametere i request
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("password", password);
                params.put("uID", uID);
                return params;
            }
        };
        // Legger til request i RequestQueue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}