package com.jmteknoloji.jmtech.toplubasliklismsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JMTech on 25.05.2017.
 */

public class ClsVeriTabani extends SQLiteOpenHelper {

    private static final String Veritabani_adi = "TBSG";
    private static final int surum = 2;

    public ClsVeriTabani(Context c) {
        super(c, Veritabani_adi, null, surum);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE KisilerTable(id INTEGER PRIMARY KEY,res blob not null,adi,soyadi,tc,tel,dtarihi,itarihi,dyer,cns);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABlE IF EXIST KisilerTable");
        onCreate(db);
    }
}
