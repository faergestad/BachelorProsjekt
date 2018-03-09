package no.usn.a151710.bachelorprosjekt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by GeorgPersen on 02.03.2018.
 */

public class Ansatt {

    private int username;
    private String password, position, fName, lName;
    private int accessLevel;

    private static final String TABELL_NAVN = "users";
    private static final String KOL_USERNAME = "username";
    private static final String KOL_PASSWORD = "password";
    private static final String KOL_ACCESS_LEVEL = "accessLevel";
    private static final String KOL_POSITION = "position";
    private static final String KOL_FNAME = "fName";
    private static final String KOL_LNAME = "lName";

    public Ansatt(int username, String password, int accessLevel, String position, String fName, String lName) {
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
        this.position = position;
        this.fName = fName;
        this.lName = lName;
    }

    public Ansatt(JSONObject jsonAnsatt) {
        this.username = jsonAnsatt.optInt(KOL_USERNAME);
        this.password = jsonAnsatt.optString(KOL_PASSWORD);
        this.accessLevel = jsonAnsatt.optInt(KOL_ACCESS_LEVEL);
        this.position = jsonAnsatt.optString(KOL_POSITION);
        this.fName = jsonAnsatt.optString(KOL_FNAME);
        this.lName = jsonAnsatt.optString(KOL_LNAME);
    }

    public static ArrayList<Ansatt> lagAnsattListe(String jsonAnsattString)
        throws JSONException, NullPointerException {

        ArrayList<Ansatt> ansattListe = new ArrayList<>();
        JSONObject jsonData = new JSONObject(jsonAnsattString);
        JSONArray jsonAnsattTabell = jsonData.optJSONArray(TABELL_NAVN);
        for (int i = 0; i < jsonAnsattTabell.length(); i++) {
            JSONObject jsonAnsatt = (JSONObject) jsonAnsattTabell.get(i);
            Ansatt denneAnsatt  = new Ansatt((jsonAnsatt));
            ansattListe.add((denneAnsatt));
        }
        return ansattListe;
    }

    public JSONObject lagJSONObject () {
        JSONObject jsonAnsatt = new JSONObject();

        try {
            jsonAnsatt.put(KOL_USERNAME, this.username);
            jsonAnsatt.put(KOL_PASSWORD, this.password);
            jsonAnsatt.put(KOL_ACCESS_LEVEL, this.accessLevel);
            jsonAnsatt.put(KOL_POSITION, this.position);
            jsonAnsatt.put(KOL_FNAME, this.fName);
            jsonAnsatt.put(KOL_FNAME, this.lName);
        } catch (JSONException e) {
            return null;
        }
        return jsonAnsatt;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) { this.username = username; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getfName() { return fName; }

    public void setfName(String fName) { this.fName = fName; }

    public String getlName() { return lName; }

    public void  setlName(String lName) { this.lName = lName; }

}
