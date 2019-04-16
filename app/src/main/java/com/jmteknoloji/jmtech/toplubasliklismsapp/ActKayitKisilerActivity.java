package com.jmteknoloji.jmtech.toplubasliklismsapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuCreator;

import java.util.ArrayList;
import java.util.List;

public class ActKayitKisilerActivity extends AppCompatActivity {

    private int positionana;
    ClsVeriTabani v1;
    SwipeMenuCreator creator;
    ListView liste;
    Button ara;
    SearchView arama;
    OzelAdaptorKisiler adapter;
    List<DgknSmsGonder> listele = new ArrayList<DgknSmsGonder>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisi_kayit_goster);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        v1 = new ClsVeriTabani(ActKayitKisilerActivity.this);

        listView = (ListView) findViewById(R.id.listkayitrehber);

        try {


            SQLiteDatabase db = v1.getReadableDatabase();
            Cursor okunan = db.rawQuery("SELECT adi,soyadi,tel FROM KisilerTable order by upper(adi)  ", null);

            listele.clear();

            while (okunan.moveToNext()) {

                listele.add(new DgknSmsGonder("","" ,okunan.getString(okunan.getColumnIndex("adi"))+" "+ okunan.getString(okunan.getColumnIndex("soyadi")), okunan.getString(okunan.getColumnIndex("tel")),"","",""));//arrayliste aktarim yapilmasi

            }

            adapter = new OzelAdaptorKisiler(ActKayitKisilerActivity.this, listele);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(ActKayitKisilerActivity.this, "Kayıt Bulunamadı.", Toast.LENGTH_LONG).show();
        }

    }

}
