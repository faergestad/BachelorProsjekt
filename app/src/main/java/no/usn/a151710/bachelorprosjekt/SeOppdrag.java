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

    private RecyclerView mRecyclerview;
    Context context;
    private LinearLayoutManager linearLayoutManager;
    private List<Oppdrag> oppdragListe;
    private RecyclerView.Adapter adapter;
    String name, address, mail, description, startDate, expDate;
    int pID, serviceID;
    public static TextView arbeidsplass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oppdrag);

        mRecyclerview = findViewById(R.id.recyclerView3);

        oppdragListe = new ArrayList<>();
        adapter = new RecyclerViewOppdragAdapter(this, oppdragListe);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("Arbeidsplass", 0);
        String name = sharedPreferences.getString("Firma", "");

        arbeidsplass = findViewById(R.id.oppdragPlace);
        arbeidsplass.setText(name);

        getData();
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

  /*
   public class LastOppdrag extends AsyncTask<String , String , Long> {

        @Override
        protected void onPreExecute() {

        }

       @Override
       protected Long doInBackground(String... params) {
            HttpURLConnection connection = null;
            try{
                //URL oppdragListeURL = new URL( "http://10.0.2.2/BachelorProsjektNettsted/api.php/project?&transform=1");
                URL oppdragListeURL = new URL("http://gakk.one/appOppdrag.php");
                connection = (HttpURLConnection) oppdragListeURL.openConnection();
                connection.connect();
                int status = connection.getResponseCode();
                if (status == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String responseString;
                    StringBuilder sb = new StringBuilder();
                    while ((responseString = reader.readLine()) != null) {
                        sb = sb.append(responseString);
                    }
                    String oppdragData = sb.toString();
                    dataArray = Oppdrag.lagOppdragListe(oppdragData);
                    return (0l);
                } else {
                    return (1L);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return (1L);
            } catch (IOException e) {
                e.printStackTrace();
                return (1L);
            } catch (NullPointerException e) {
                e.printStackTrace();
                return (1L);
            } catch (Exception e) {
                e.printStackTrace();
                return (1L);
            } finally {
                connection.disconnect();
            }
       }

       @Override
       protected void onPostExecute(Long result) {
            if (result == 0) {
                getOppdrag(dataArray);
            } else {
                Log.d("Feil: ", "Feil på databasen");
            }
       }
   } */

}
