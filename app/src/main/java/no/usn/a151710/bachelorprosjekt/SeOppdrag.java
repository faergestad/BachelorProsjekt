package no.usn.a151710.bachelorprosjekt;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GeorgPersen on 09.03.2018.
 */

public class SeOppdrag extends AppCompatActivity {

    List<Oppdrag> oppdrag1;
    ArrayAdapter<Oppdrag> dataAdapter = null;
    private ArrayList<Oppdrag> dataArray = new ArrayList<>();
    RecyclerView recyclerView;
    String name, address, mail, description, startDate, expDate;
    int pID, serviceID;
    public static TextView arbeidsplass;

    RecyclerView.LayoutManager recyclerViewLayoutManager;
    RecyclerView.Adapter recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oppdrag);

        oppdrag1 = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        SharedPreferences sharedPreferences = getSharedPreferences("Arbeidsplass", 0);
        String name = sharedPreferences.getString("Firma", "");

        arbeidsplass = findViewById(R.id.oppdragPlace);
        arbeidsplass.setText(name);

        SeOppdrag.LastOppdrag oppdragLaster = new LastOppdrag();
        oppdragLaster.execute();
    }



    public void getOppdrag(ArrayList<Oppdrag> oppdragListe) {
        dataAdapter = new ArrayAdapter<>(this, R.layout.recyclerview3_items, oppdragListe);
        for (int i = 0; i < oppdragListe.size(); i++) {
            Oppdrag oppdrag2 = new Oppdrag(pID,name,address, mail, serviceID, description, startDate, expDate);
            try {
                Oppdrag data1 = oppdragListe.get(i);
                oppdrag2.setpID(data1.getpID());
                oppdrag2.setName(data1.getName());
                oppdrag2.setAddress(data1.getAddress());
                oppdrag2.setMail(data1.getMail());
                oppdrag2.setServiceID(data1.getServiceID());
                oppdrag2.setDescription(data1.getDescription());
                oppdrag2.setStartDate(data1.getStartDate());
                oppdrag2.setExpDate(data1.getExpDate());
            }  catch (Exception e) {
                e.printStackTrace();
            }
            oppdrag1.add(oppdrag2);
        }
        recyclerViewAdapter = new RecyclerViewOppdragAdapter(oppdrag1, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

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
   }

}
