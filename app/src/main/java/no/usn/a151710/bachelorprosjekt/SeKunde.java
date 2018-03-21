package no.usn.a151710.bachelorprosjekt;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;

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

/**
 * Created by GeorgPersen on 09.03.2018.
 */

public class SeKunde extends AppCompatActivity {

    List<Kunde> kunde1;
    ArrayAdapter<Kunde> dataAdapter = null;
    private ArrayList<Kunde> dataArray = new ArrayList<>();
    RecyclerView recyclerView;
    String customerName, email;

    RecyclerView.LayoutManager recylerViewLayoutManager;
    RecyclerView.Adapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kunde);

        kunde1 = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recylerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        SeKunde.LastKunde kundeLaster = new LastKunde();
        kundeLaster.execute();

    }

    public void getKunde(ArrayList<Kunde> kundeListe) {
        dataAdapter = new ArrayAdapter<>(this, R.layout.recylerview2_items, kundeListe);
        for (int i = 0; i < kundeListe.size(); i++) {
            Kunde kunde2 = new Kunde(customerName, email);
            try {
                Kunde data1 = kundeListe.get(i);
                kunde2.setCustomerName(data1.getCustomerName());
                kunde2.setMail(data1.getMail());
            } catch (Exception e) {
                e.printStackTrace();
            }
            kunde1.add(kunde2);
        }
        recyclerViewAdapter = new RecyclerViewKundeAdapter(kunde1, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public class LastKunde extends AsyncTask<String , String , Long > {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Long doInBackground(String... params) {
            HttpURLConnection connection = null;
            try{
                URL kundeListeURL = new URL("http://10.0.2.2/BachelorProsjektNettsted/api.php/users?&transform=1");
                connection = (HttpURLConnection) kundeListeURL.openConnection();
                connection.connect();
                int status =  connection.getResponseCode();
                if (status == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String responseString;
                    StringBuilder sb = new StringBuilder();
                    while ((responseString = reader.readLine()) != null) {
                        sb = sb.append(responseString);
                    }
                    String kundeData = sb.toString();
                    dataArray = Kunde.lagKundeListe(kundeData);
                    return (0L);
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
                getKunde(dataArray);
            } else {
                Log.d("Feil: ", "feil på databasen");
            }
        }
    }

}
