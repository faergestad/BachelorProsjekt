package no.usn.a151710.bachelorprosjekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Kunde {

    private String customerName, mail;

    private static String TABELL_NAVN = "customer";
    private static String KOL_CUSTOMER_NAME = "cName";
    private static String KOL_MAIL = "mail";

    public Kunde(String customerName, String mail) {
        this.customerName = customerName;
        this.mail = mail;
    }

    public Kunde(JSONObject jsonKunde) {
        this.customerName = jsonKunde.optString(KOL_CUSTOMER_NAME);
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
            jsonKunde.put(KOL_CUSTOMER_NAME, this.customerName);
            jsonKunde.put(KOL_MAIL, this.mail);
        } catch (JSONException e) {
            return null;
        }
        return jsonKunde;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
