package com.jmteknoloji.jmtech.toplubasliklismsapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static android.R.id.list;

public class ActRehberKisilerActivity extends AppCompatActivity {

    List<DgknSmsGonder> listgonder = new ArrayList<DgknSmsGonder>();
    OzelAdaptorKisiler adaprehber;
    private ListView lstNames;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);


        int id = item.getItemId();
        if (id == R.id.action_import) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisi_kayit_goster);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("Telefon Rehberi");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ListView rehber = (ListView) findViewById(R.id.listrehber);
      //  fetchContacts();



        adaprehber = new OzelAdaptorKisiler(ActRehberKisilerActivity.this, listgonder);
        rehber.setAdapter(adaprehber);
    }

}

