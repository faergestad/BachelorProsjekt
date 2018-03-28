package no.usn.a151710.bachelorprosjekt;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SeAnsatte extends AppCompatActivity {

    List<Ansatt> ansatt1;
    ArrayAdapter<Ansatt> dataAdapter = null;
    private ArrayList<Ansatt> dataArray = new ArrayList<>();
    RecyclerView recyclerView;
    String password, position, fName, lName;
    int username, accessLevel;

    RecyclerView.LayoutManager recyclerViewLayoutManager;
    RecyclerView.Adapter recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ansatte);

        ansatt1 = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        SeAnsatte.LastAnsatte ansattLaster = new LastAnsatte();
        ansattLaster.execute();
    }

    public void getAnsatt(ArrayList<Ansatt> ansattListe) {
        dataAdapter = new ArrayAdapter<>(this, R.layout.recyclerview_items, ansattListe);
        for (int i = 0; i < ansattListe.size(); i++) {
            Ansatt ansatt2 = new Ansatt(username, password, accessLevel, position, fName, lName);
            try {
                Ansatt data1 = ansattListe.get(i);
                ansatt2.setUsername(data1.getUsername());
                ansatt2.setPassword(data1.getPassword());
                ansatt2.setAccessLevel(data1.getAccessLevel());
                ansatt2.setPosition(data1.getPosition());
                ansatt2.setfName(data1.getfName());
                ansatt2.setlName(data1.getlName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            ansatt1.add(ansatt2);
        }
        recyclerViewAdapter = new RecyclerViewAdapter(ansatt1, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public class LastAnsatte extends AsyncTask<String , String , Long > {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Long doInBackground(String... params) {
            HttpURLConnection connection = null;
            try{
                URL ansattListeURL = new URL("http://10.0.2.2/BachelorProsjektNettsted/api.php/users?&transform=1");
                connection = (HttpURLConnection) ansattListeURL.openConnection();
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
                    String ansattData = sb.toString();
                    dataArray = Ansatt.lagAnsattListe(ansattData);
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
                getAnsatt(dataArray);
            } else {
                Log.d("Feil : ", "feil på databasen");
            }
        }
    }

}
