package no.usn.a151710.bachelorprosjekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Oppdrag {

    private int id, customerId;
    private String service, address;
    private Date startDate, expDate;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    String strStartDate = df.format(startDate);
    String strExpDate = df.format(expDate);

    private static final String TABELL_NAVN = "project";
    private static final String KOL_ID = "pID";
    private static final String KOL_CUSTOMER_ID = "pCustomerID";
    private static final String KOL_SERVICE = "service";
    private static final String KOL_ADDRESS = "address";
    private static final String KOL_START_DATE = "startDate";
    private static final String KOL_STOP_DATE = "expDate";

    public Oppdrag(int id, int customerId, String service, String address, Date startDate, Date expDate) {
        this.id = id;
        this.customerId = customerId;
        this.service = service;
        this.address = address;
        this.startDate = startDate;
        this.expDate = expDate;
    }

    public Oppdrag(JSONObject jsonOppdrag) {
        this.id = jsonOppdrag.optInt(KOL_ID);
        this.customerId = jsonOppdrag.optInt(KOL_CUSTOMER_ID);
        this.service = jsonOppdrag.optString(KOL_SERVICE);
        this.address = jsonOppdrag.optString(KOL_ADDRESS);
        this.strStartDate = jsonOppdrag.optString((KOL_START_DATE));
        this.strExpDate = jsonOppdrag.optString(KOL_STOP_DATE);
    }

    public static ArrayList<Oppdrag> lagOppdragListe(String jsonOppdragString)
            throws JSONException, NullPointerException {

        ArrayList<Oppdrag> oppdragListe = new ArrayList<>();
        JSONObject jsonData = new JSONObject(jsonOppdragString);
        JSONArray jsonOppdragTabell = jsonData.optJSONArray(TABELL_NAVN);
        for (int i = 0; i < jsonOppdragTabell.length(); i++) {
            JSONObject jsonOppdrag= (JSONObject) jsonOppdragTabell.get(i);
            Oppdrag detteOppdrag  = new Oppdrag((jsonOppdrag));
            oppdragListe.add((detteOppdrag));
        }
        return oppdragListe;
    }

    public JSONObject lagJSONObject () {
        JSONObject jsonOppdrag = new JSONObject();

        try {
            jsonOppdrag.put(KOL_ID, this.id);
            jsonOppdrag.put(KOL_CUSTOMER_ID, this.customerId);
            jsonOppdrag.put(KOL_SERVICE, this.service);
            jsonOppdrag.put(KOL_ADDRESS, this.address);
            jsonOppdrag.put(KOL_START_DATE, this.startDate);
            jsonOppdrag.put(KOL_STOP_DATE, this.expDate);
        } catch (JSONException e) {
            return null;
        }
        return jsonOppdrag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }
}
