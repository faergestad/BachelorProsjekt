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

    private int pID, serviceID;
    private String name, address, mail, description, startDate, expDate;

    private static final String TABELL_NAVN = "project";
    private static final String KOL_PID = "pID";
    private static final String KOL_NAME = "name";
    private static final String KOL_ADDRESS = "address";
    private static final String KOL_MAIL = "mail";
    private static final String KOL_SERVICEID = "serviceId";
    private static final String KOL_DESCRIPTION = "description";
    private static final String KOL_STARTDATE = "startDate";
    private static final String KOL_EXPDATE = "expDate";

    public Oppdrag(int pID, String name, String address, String mail, int serviceID, String description, String startDate, String expDate){
        this.pID = pID;
        this.name = name;
        this.address = address;
        this.mail = mail;
        this.serviceID = serviceID;
        this.description = description;
        this.startDate = startDate;
        this.expDate = expDate;

    }

    public Oppdrag(JSONObject jsonOppdrag) {
        this.pID = jsonOppdrag.optInt(KOL_PID);
        this.name = jsonOppdrag.optString(KOL_NAME);
        this.address = jsonOppdrag.optString(KOL_ADDRESS);
        this.mail = jsonOppdrag.optString(KOL_MAIL);
        this.serviceID = jsonOppdrag.optInt(KOL_SERVICEID);
        this.description = jsonOppdrag.optString(KOL_DESCRIPTION);
        this.startDate = jsonOppdrag.optString(KOL_STARTDATE);
        this.expDate = jsonOppdrag.optString(KOL_EXPDATE);
    }

    public static ArrayList<Oppdrag> lagOppdragListe(String jsonOppdragString)
        throws JSONException, NullPointerException {

        ArrayList<Oppdrag> oppdragListe = new ArrayList<>();
        JSONObject jsonData = new JSONObject(jsonOppdragString);
        JSONArray jsonOppdragTabell = jsonData.optJSONArray(TABELL_NAVN);
        for (int i = 0; i < jsonOppdragTabell.length(); i++) {
            JSONObject jsonOppdrag = (JSONObject) jsonOppdragTabell.get(i);
            Oppdrag detteOppdrag = new Oppdrag((jsonOppdrag));
            oppdragListe.add((detteOppdrag));
        }
        return oppdragListe;
    }

    public JSONObject lagJSONObject () {
        JSONObject jsonOppdrag = new JSONObject();

        try {
            jsonOppdrag.put(KOL_PID, this.pID);
            jsonOppdrag.put(KOL_NAME, this.name);
            jsonOppdrag.put(KOL_ADDRESS, this.address);
            jsonOppdrag.put(KOL_MAIL, this.mail);
            jsonOppdrag.put(KOL_SERVICEID, this.serviceID);
            jsonOppdrag.put(KOL_DESCRIPTION, this.description);
            jsonOppdrag.put(KOL_STARTDATE, this.startDate);
            jsonOppdrag.put(KOL_EXPDATE, this.expDate);
        } catch (JSONException e) {
            return null;
        }
        return jsonOppdrag;
    }

    public int getpID() {
        return pID;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }
}
