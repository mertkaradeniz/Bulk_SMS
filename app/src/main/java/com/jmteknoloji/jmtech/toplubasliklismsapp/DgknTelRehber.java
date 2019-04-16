package com.jmteknoloji.jmtech.toplubasliklismsapp;

/**
 * Created by JMTech on 4.06.2017.
 */

public class DgknTelRehber {

    private String Rehbername;
    private String size;

    public DgknTelRehber(String mRehber, String mnumber) {
        this.setRehbername(mRehber);
        this.setSize(mnumber);
    }

    public String getRehbername() {
        return Rehbername;
    }

    public void setRehbername(String rehbername) {
        Rehbername = rehbername;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String numara) {
        size = numara;
    }
}
