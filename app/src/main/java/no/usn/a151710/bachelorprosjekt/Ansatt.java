package no.usn.a151710.bachelorprosjekt;

import org.json.JSONObject;

public class Ansatt {


    private int username, accessLevel;
    private String password, position, fName, lName, image;

    private static final String TABELL_NAVN = "users";
    private static final String KOL_USERNAME = "username";
    private static final String KOL_PASSWORD = "password";
    private static final String KOL_ACCESS_LEVEL = "accessLevel";
    private static final String KOL_POSITION = "position";
    private static final String KOL_FNAME = "fName";
    private static final String KOL_LNAME = "lName";
    private static final String KOL_IMAGE = "image";

    // Lager et JSONObject med data fra Volley string-request
    public Ansatt(JSONObject jsonAnsatt) {
        this.username = jsonAnsatt.optInt(KOL_USERNAME);
        this.password = jsonAnsatt.optString(KOL_PASSWORD);
        this.accessLevel = jsonAnsatt.optInt(KOL_ACCESS_LEVEL);
        this.position = jsonAnsatt.optString(KOL_POSITION);
        this.fName = jsonAnsatt.optString(KOL_FNAME);
        this.lName = jsonAnsatt.optString(KOL_LNAME);
        this.image = jsonAnsatt.optString(KOL_IMAGE);
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
