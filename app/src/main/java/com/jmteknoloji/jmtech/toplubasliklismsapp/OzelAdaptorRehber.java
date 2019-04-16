package com.jmteknoloji.jmtech.toplubasliklismsapp;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuCreator;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JMTech on 4.06.2017.
 */

public class OzelAdaptorRehber extends BaseAdapter {

    LayoutInflater lytsatir;
    List<DgknTelRehber> liste;
    Context activity;

    DgknTelRehber ogr;
   public static List<DgknSmsGonder> listgonder = new ArrayList<DgknSmsGonder>();
    OzelAdaptorKisiler adaprehber;
    private ListView lstNames;
    Dialog DialogTelefonRehberi;
    Dialog DialogDataBaseRehberi;
    private int positionana;
    ClsVeriTabani v1;
    SwipeMenuCreator creator;
    ListView liste_;
    Button ara;
    SearchView arama;
    OzelAdaptorKisiler adapter;
    List<DgknSmsGonder> listele = new ArrayList<DgknSmsGonder>();

    ListView listView;
    private DgknTelRehber satiroku;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    public OzelAdaptorRehber(Context mactivity, List<DgknTelRehber> mList) {
        activity = mactivity;
        lytsatir = (LayoutInflater) mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        liste = mList;
    }

    @Override
    public int getCount() {
        return liste.size();
    }

    @Override
    public Object getItem(int position) {
        return liste.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View row = lytsatir.inflate(R.layout.kisi_row_rehberler, parent, false);

        TextView name = (TextView) row.findViewById(R.id.txtrehbername);
        TextView number = (TextView) row.findViewById(R.id.txtrehbersize);
        final CheckBox onoff = (CheckBox) row.findViewById(R.id.switchonoff);

        ogr = liste.get(position);
        name.setText(ogr.getRehbername().toString());
        number.setText(ogr.getSize().toString());

        DialogTelefonRehberi = new Dialog(activity);
        DialogTelefonRehberi.setContentView(R.layout.kisi_kayit_rehber);
        DialogTelefonRehberi.setTitle("Rehber Kayıtları");
        DialogDataBaseRehberi = new Dialog(activity);
        DialogDataBaseRehberi.setTitle("Eklenen Kayıtlar");
        DialogDataBaseRehberi.setContentView(R.layout.activity_kisi_kayit_goster);

        Button btn_rhbr_onay = (Button) DialogTelefonRehberi.findViewById(R.id.btn_secilen_onay);
        Button btn_db_onay = (Button) DialogDataBaseRehberi.findViewById(R.id.btn_db_onay);


        if (FrgTopluSMS.secilen_no.getText().toString().indexOf("#" + ogr.getRehbername().toString()) != -1)
            onoff.setChecked(true);

        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int indis = 0; indis < getCount(); indis++) {
                    satiroku = liste.get(indis);

                    if (onoff.isChecked())
                        if (position == indis) {
                            onoff.setChecked(true);

                            if (FrgTopluSMS.secilen_no.getText().toString().indexOf("#" + satiroku.getRehbername().toString()) == -1)
                                FrgTopluSMS.SecilenNo.add(new DgknSecilenNumaralar("#" + satiroku.getRehbername().toString(), "#" + satiroku.getRehbername().toString()));

                        }


                    if (!onoff.isChecked()) {

                        if (position == indis) {
                            onoff.setChecked(false);

                            for (int index = 0; index < FrgTopluSMS.SecilenNo.size(); index++) {
                                if (FrgTopluSMS.SecilenNo.get(index).getAliciname().equals("#" + satiroku.getRehbername().toString())) {
                                    FrgTopluSMS.SecilenNo.remove(index);

                                }
                            }
                        }

                    }
                }


            }
        });

        btn_rhbr_onay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogTelefonRehberi.onBackPressed();
            }
        });

        btn_db_onay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDataBaseRehberi.onBackPressed();
            }
        });

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    ListView rehber = (ListView) DialogTelefonRehberi.findViewById(R.id.listrehber);




                    adaprehber = new OzelAdaptorKisiler(activity, listgonder);
                    rehber.setAdapter(adaprehber);
                    DialogTelefonRehberi.show();
                }
                if (position == 1) {
                    v1 = new ClsVeriTabani(activity);

                    listView = (ListView) DialogDataBaseRehberi.findViewById(R.id.listkayitrehber);

                    try {


                        SQLiteDatabase db = v1.getReadableDatabase();
                        Cursor okunan = db.rawQuery("SELECT adi,soyadi,tel FROM KisilerTable order by upper(adi)  ", null);

                        listele.clear();

                        while (okunan.moveToNext()) {

                            listele.add(new DgknSmsGonder("", "", okunan.getString(okunan.getColumnIndex("adi")) + " " + okunan.getString(okunan.getColumnIndex("soyadi")), okunan.getString(okunan.getColumnIndex("tel")), "", "", ""));//arrayliste aktarim yapilmasi

                        }

                        adapter = new OzelAdaptorKisiler(activity, listele);
                        listView.setAdapter(adapter);
                    } catch (Exception e) {
                        Toast.makeText(activity, "Kayıt Bulunamadı.", Toast.LENGTH_LONG).show();
                    }
                    DialogDataBaseRehberi.show();
                }

            }
        });

        return row;
    }


}
