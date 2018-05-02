package no.usn.a151710.bachelorprosjekt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.lang.Integer.parseInt;

/**
 * Created by GeorgPersen on 09.03.2018.
 */

public class SeKunde extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private LinearLayoutManager linearLayoutManager;
    private List<Kunde> kundeListe;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kunde);

        mRecyclerview = findViewById(R.id.recyclerView2);

        kundeListe = new ArrayList<>();
        adapter = new RecyclerViewKundeAdapter(this, kundeListe);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setAdapter(adapter);

        getData();
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Laster...");
        progressDialog.show();

        final String JSON_URL = "http://gakk.one/appKunde.php";

        StringRequest jsonStringRequest = new StringRequest(JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonKundeArray = jsonObject.optJSONArray("kunde");

                for (int i = 0; i < jsonKundeArray.length(); i++) {
                    try {
                        JSONObject jsonKunde = (JSONObject)jsonKundeArray.get(i);
                        Kunde kunde = new Kunde(jsonKunde);

                        kunde.setCustomerName(jsonKunde.getString("cName"));
                        kunde.setMail(jsonKunde.getString("mail"));

                        kundeListe.add(kunde);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonStringRequest);
    }

    public void goBack(View view) {
            this.finish();
    }
}
