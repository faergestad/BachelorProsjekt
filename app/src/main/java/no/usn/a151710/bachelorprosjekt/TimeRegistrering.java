package no.usn.a151710.bachelorprosjekt;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeRegistrering extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String TAG = "Timeregistrering: ";
    //private static final String URL_DATA = "http://10.0.2.2/BachelorProsjektNettsted/api.php/latlong?&transform=1";
    private static final String URL_DATA = "http://gakk.one/appLatlng.php";
    protected ArrayList<Geofence> mGeofenceList;
    protected GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    private TextView timer;
    private TextView antTimer, arbeidsplass;
    private Button btnStart;
    private Button btnStop;
    private Context mContext;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLIS_TO_HOURS = 3600000;
    long mStartTime = System.currentTimeMillis();
    long storedStart;
    SharedPreferences pref;
    // Brukt for å teste add.circle på map
    LatLng huset = new LatLng(59.414349, 9.058793);
    ArrayList<String> latList = new ArrayList<>();
    ArrayList<String> lngList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_registrering);
        mContext = this;

        timer = findViewById(R.id.timer);
        btnStart = findViewById(R.id.start);
        btnStop = findViewById(R.id.stop);

        SharedPreferences sharedPreferences = getSharedPreferences("Arbeidsplass", 0);
        String name = sharedPreferences.getString("Firma", "");
        final int pID = sharedPreferences.getInt("pID", 0);

        arbeidsplass = findViewById(R.id.timeregPlace);
        arbeidsplass.setText(name);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Setter opp map-fragmentet i aktiviteten
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*
        Timerrelatert
        Setter startTimer til 0, så appen ikke krasjer hvis brukeren trykker stopp før start */
        pref = getApplicationContext().getSharedPreferences("Timer", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("startTimer", 0);
        editor.apply();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                storedStart = System.currentTimeMillis();

                pref = getApplicationContext().getSharedPreferences("Timer", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                // gjør så brukeren ikke kan nullstille telleren ved å trykke start
                // hvis startTimer ikke er 0
                if (pref.getLong("startTimer", 0) == 0) {
                    editor.putLong("startTimer", storedStart);
                    Toast.makeText(mContext, "Timeregistrering startet",Toast.LENGTH_SHORT).show();
                    editor.apply();
                } else {
                    Toast.makeText(mContext, "Du har allerede startet",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pref.getLong("startTimer", 0) == 0) {
                    Toast.makeText(mContext, "Du har ikke startet klokka enda..", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = pref.edit();
                    Long startTimer = pref.getLong("startTimer", 0);
                    // Hvis startTimer innholder en verdi parser vi det og beregner hvor lang tid det har gått
                    if (startTimer != 0) {
                        long since = System.currentTimeMillis() - pref.getLong("startTimer", 0);

                        int seconds = (int) ((since / 1000) % 60);
                        int minutes = (int) ((since / MILLIS_TO_MINUTES) % 60);
                        int hours = (int) ((since / MILLIS_TO_HOURS) % 24);
                        int roundedHours = 8;

                        if (roundedHours < 1) {
                            //Toast.makeText(mContext, "Du har ikke jobbet èn time engang..", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(mContext, "Det ble ikke logget timer", Toast.LENGTH_SHORT).show();
                            //  TODO runde opp hvert påbegynte kvarter til neste halvtime
                            // Runder foreløpig bare opp hvis hver påbegynte time >= 30 min
                            // midlertidig runder vi opp hver startet timer til 1 time for å sjekke mot databasen
                            if (minutes <= 30) {
                                Log.d("Arbeidsdag", "" + hours + ":" + minutes);
                            }
                            // TODO Legge til støtte for varsling om overtid if(hours > 8)
                        } else {
                            if (minutes >= 30) {
                                roundedHours = hours + 1;
                            }

                            // Volley-request for å sende loggede timer til databasen
                            final String URL = "http://gakk.one/appLoggTimer.php";

                            final int finalRoundedHours = roundedHours;
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.contains("success")) {
                                        Toast.makeText(mContext, "Timer registrert!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(),"Kunne ikke logge timer..",Toast.LENGTH_SHORT).show();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("tabell", "timesheet");
                                    params.put("pID", String.valueOf(pID));
                                    params.put("serviceID", "1");
                                    params.put("userID", "10000");
                                    params.put("hours", String.valueOf(finalRoundedHours));
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                            requestQueue.add(stringRequest);

                            //Toast.makeText(mContext, "Timer logget!", Toast.LENGTH_SHORT).show();
                            editor.clear();
                            editor.apply();
                            timer.setText(roundedHours + " Timer, " + minutes + " Minutter");
                        }

                    }

                }
            }
        });

        hentKoordinater();
    }

    public void hentKoordinater() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, URL_DATA, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("latlong");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonobject = jsonArray.getJSONObject(i);
                                latList.add(jsonobject.getString("lat"));
                                lngList.add(jsonobject.getString("lng"));
                            }

                            for (int i = 0; i < latList.size(); i++) {
                                Circle circle = mMap.addCircle(new CircleOptions()
                                        .center(new LatLng(Double.valueOf(latList.get(i)), Double.valueOf(lngList.get(i))))
                                        .radius(60)
                                        .strokeColor(0x440000FF)
                                        .strokeWidth(2)
                                        .fillColor(0x220000FF));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                mMap.getCameraPosition();
            }

        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // PRIORITY_BALANCED_POWER_ACCURACY for å spare strøm
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Showing Current Location Marker on Map
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        //move map camera
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 17);
        mMap.animateCamera(yourLocation);
        //this code stops location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        // Måtte implementeres utenfor onCreate
    }

}