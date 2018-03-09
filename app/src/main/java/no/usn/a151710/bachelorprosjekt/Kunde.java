package no.usn.a151710.bachelorprosjekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Kunde {

    private int id;
    private String customerName, address, mail;

    private static String TABELL_NAVN = "customer";
    private static String KOL_ID = "cID";
    private static String KOL_CUSTOMER_NAME = "cNAME";
    private static String KOL_ADDRESS = "address";
    private static String KOL_MAIL = "mail";

    public Kunde(int id, String customerName, String address, String mail) {
        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.mail = mail;
    }

    public Kunde(JSONObject jsonKunde) {
        this.id = jsonKunde.optInt(KOL_ID);
        this.customerName = jsonKunde.optString(KOL_CUSTOMER_NAME);
        this.address = jsonKunde.optString(KOL_ADDRESS);
        this.mail = jsonKunde.optString(KOL_MAIL);
    }

    public static ArrayList<Kunde> lagKundeListe(String jsonKundeString)
            throws JSONException, NullPointerException {

        ArrayList<Kunde> kundeListe = new ArrayList<>();
        JSONObject jsonData = new JSONObject(jsonKundeString);
        JSONArray jsonKundeTabell = jsonData.optJSONArray(TABELL_NAVN);
        for (int i = 0; i < jsonKundeTabell.length(); i++) {
            JSONObject jsonKunde= (JSONObject) jsonKundeTabell.get(i);
            Kunde denneKunden  = new Kunde((jsonKunde));
            kundeListe.add((denneKunden));
        }
        return kundeListe;
    }

    public JSONObject lagJSONObject () {
        JSONObject jsonKunde = new JSONObject();

        try {
            jsonKunde.put(KOL_ID, this.id);
            jsonKunde.put(KOL_CUSTOMER_NAME, this.customerName);
            jsonKunde.put(KOL_ADDRESS, this.address);
            jsonKunde.put(KOL_ADDRESS, this.address);
            jsonKunde.put(KOL_MAIL, this.mail);
        } catch (JSONException e) {
            return null;
        }
        return jsonKunde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
