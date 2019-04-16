package com.jmteknoloji.jmtech.toplubasliklismsapp;

/**
 * Created by JMTech on 6.06.2017.
 */

public class DgknSmsGonder {

    private String gonderen;
    private String sifre;
    private String alici;
    private String aliciname;
    private String mesaj;
    private String baslik;
    private String tarih;

    public DgknSmsGonder(String mgonderen, String msifre, String maliciname, String alicinumber, String mmesaj, String mbaslik, String mtarih) {
        setGonderen(mgonderen);
        setSifre(msifre);
        setAlici(alicinumber);
        setMesaj(mmesaj);
        setBaslik(mbaslik);
        setTarih(mtarih);
        setAliciname(maliciname);
    }

    public String getGonderen() {
        return gonderen;
    }

    public void setGonderen(String gonderen) {
        this.gonderen = gonderen;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getAlici() {
        return alici;
    }

    public void setAlici(String alici) {
        this.alici = alici;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getAliciname() {
        return aliciname;
    }

    public void setAliciname(String aliciname) {
        this.aliciname = aliciname;
    }
}
