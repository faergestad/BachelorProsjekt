package no.usn.a151710.bachelorprosjekt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
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

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Created by GeorgPersen on 09.03.2018.
 */

public class SeOppdrag extends AppCompatActivity {


    private List<Oppdrag> oppdragListe;
    private RecyclerView.Adapter adapter;
    String name, description;
    public static TextView arbeidsplass;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oppdrag);

        RecyclerView mRecyclerview = findViewById(R.id.recyclerView3);

        oppdragListe = new ArrayList<>();
        adapter = new RecyclerViewOppdragAdapter(this, oppdragListe);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setAdapter(adapter);

        sharedPreferences = getSharedPreferences("Arbeidsplass", 0);
        String name = sharedPreferences.getString("Firma", "");

        arbeidsplass = findViewById(R.id.oppdragPlace);
        arbeidsplass.setText(name);

        getData();
    }

    public void clearWorkplace(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Firma", "");
        editor.apply();

        arbeidsplass.setText("");
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Laster...");
        progressDialog.show();

        final String JSON_URL = "http://gakk.one/appOppdrag.php";

        StringRequest jsonStringRequest = new StringRequest(JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonOppdragArray = jsonObject.optJSONArray("project");

                for (int i = 0; i < jsonOppdragArray.length(); i++) {
                    try {
                        JSONObject jsonOppdrag = (JSONObject)jsonOppdragArray.get(i);
                        Oppdrag oppdrag = new Oppdrag(jsonOppdrag);

                        oppdrag.setpID(parseInt(jsonOppdrag.getString("pID")));
                        oppdrag.setName(jsonOppdrag.getString("name"));
                        oppdrag.setAddress(jsonOppdrag.getString("address"));
                        oppdrag.setMail(jsonOppdrag.getString("mail"));
                        oppdrag.setServiceID(parseInt(jsonOppdrag.getString("serviceId")));
                        oppdrag.setStartDate(jsonOppdrag.getString("startDate"));
                        oppdrag.setExpDate(jsonOppdrag.getString("expDate"));


                        oppdragListe.add(oppdrag);

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

}
