package no.usn.a151710.bachelorprosjekt;

import org.json.JSONObject;

public class Kunde {

    private String customerName, mail;

    private static String TABELL_NAVN = "customer";
    private static String KOL_CUSTOMER_NAME = "cName";
    private static String KOL_MAIL = "mail";

    public Kunde(String customerName, String mail) {
        this.customerName = customerName;
        this.mail = mail;
    }
    // Lager et JSONObject med data fra Volley string-request
    public Kunde(JSONObject jsonKunde) {
        this.customerName = jsonKunde.optString(KOL_CUSTOMER_NAME);
        this.mail = jsonKunde.optString(KOL_MAIL);
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
