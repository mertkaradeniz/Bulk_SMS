package com.jmteknoloji.jmtech.toplubasliklismsapp;

import android.graphics.Bitmap;

/**
 * Created by JMTech on 29.05.2017.
 */

public class DgknKayitKisi {

    private int ID;
    private Bitmap imagepath;
    private String Ad;
    private String soy;
    private String cinsiyet;
    private String tc;
    private String telefon;
    private String ktarih;
    private String itarih;
    private String dyer;

    public DgknKayitKisi(int ID,Bitmap mimagepath, String mad, String msoy, String mcins, String mtc, String mtel, String mktarih, String mitarih, String mdyer) {
        this.setImagepath(mimagepath);
        this.setAd(mad);
        this.setSoy(msoy);
        this.setCinsiyet(mcins);
        this.setTc(mtc);
        this.setTelefon(mtel);
        this.setKtarih(mktarih);
        this.setItarih(mitarih);
        this.setDyer(mdyer);
        this.setID(ID);
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public String getSoy() {
        return soy;
    }

    public void setSoy(String soy) {
        this.soy = soy;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getKtarih() {
        return ktarih;
    }

    public void setKtarih(String ktarih) {
        this.ktarih = ktarih;
    }

    public String getItarih() {
        return itarih;
    }

    public void setItarih(String itarih) {
        this.itarih = itarih;
    }

    public String getDyer() {
        return dyer;
    }

    public void setDyer(String dyer) {
        this.dyer = dyer;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Bitmap getImagepath() {
        return imagepath;
    }

    public void setImagepath(Bitmap imagepath) {
        this.imagepath = imagepath;
    }
}
