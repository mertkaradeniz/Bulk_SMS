package com.jmteknoloji.jmtech.toplubasliklismsapp;

/**
 * Created by JMTech on 13.06.2017.
 */

public class DgknSecilenNumaralar {
    private String alicino;
    private String aliciname;

    public DgknSecilenNumaralar(String maliciname, String alicino) {
        setAlicino(alicino);
        setAliciname(maliciname);
    }

    public String getAlicino() {
        return alicino;
    }

    public void setAlicino(String alicino) {
        this.alicino = alicino;
    }

    public String getAliciname() {
        return aliciname;
    }

    public void setAliciname(String aliciname) {
        this.aliciname = aliciname;
    }
}
