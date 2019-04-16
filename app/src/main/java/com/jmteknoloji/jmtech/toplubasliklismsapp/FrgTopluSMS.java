package com.jmteknoloji.jmtech.toplubasliklismsapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FrgTopluSMS#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrgTopluSMS extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<DgknTelRehber> rehber = new ArrayList<DgknTelRehber>();
    ;;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View FrgRehber;
    public static TextView secilen_no;
    public static List<DgknSecilenNumaralar> SecilenNo = new ArrayList<DgknSecilenNumaralar>();
    List<DgknSmsGonder> listgonder = new ArrayList<DgknSmsGonder>();
    List<DgknSmsGonder> list_veritabani = new ArrayList<DgknSmsGonder>();
    ProgressDialog progressDialog;
    Dialog DialogSMSGonderiliyor;
    Dialog DlgRehberler;
    CheckedTextView rehber_ek_no;
    public static TextView MESAJ;
    public static String AFTER_NUMBER;
    public static String BASARILI_SMS;
    public static String NUMARALAR;
    OzelAdaptorRehber adapter;
    ClsVeriTabani v1;
    ListView list_rehber;
    private Handler handler = new Handler();

    public FrgTopluSMS() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopluSmsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FrgTopluSMS newInstance(String param1, String param2) {
        FrgTopluSMS fragment = new FrgTopluSMS();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FrgRehber = inflater.inflate(R.layout.ana_menu_sms_gonder, container, false);

        rehber_ek_no = (CheckedTextView) FrgRehber.findViewById(R.id.chk_rehber_ek_no);
        secilen_no = (TextView) FrgRehber.findViewById(R.id.secilen_no);


        final Button SMSGONDER = (Button) FrgRehber.findViewById(R.id.btn_sms_gonder);

        SMSGONDER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SMSGONDER_P();
            }
        });

        rehber_ek_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DlgRehberler = new Dialog(FrgRehber.getContext());
                DlgRehberler.setContentView(R.layout.kisi_tel_rehberler);
                list_rehber = (ListView) DlgRehberler.findViewById(R.id.listrehber);

                DlgRehberler.setTitle("Rehber yada Numara Seç");


                DlgRehberler.show();
               // rehberler();
               new BackgroundTaskRehber().execute((Void) null);


            }
        });
        return FrgRehber;
    }

    public void veritabani_list() {
        try {

            v1 = new ClsVeriTabani(getActivity());

            SQLiteDatabase db = v1.getReadableDatabase();
            Cursor okunan = db.rawQuery("SELECT adi,soyadi,tel FROM KisilerTable order by upper(adi)  ", null);

            list_veritabani.clear();

            while (okunan.moveToNext()) {

                list_veritabani.add(new DgknSmsGonder("", "", okunan.getString(okunan.getColumnIndex("adi")) + " " + okunan.getString(okunan.getColumnIndex("soyadi")), okunan.getString(okunan.getColumnIndex("tel")), "", "", ""));//arrayliste aktarim yapilmasi

            }


        } catch (Exception e) {

        }

    }

    public void rehberler() {

        rehber.clear();

        rehber.add(new DgknTelRehber("Telefon Rehberi", MainActivity.REHBER_SIZE));


        ClsVeriTabani v1 = new ClsVeriTabani(DlgRehberler.getContext());
        String[] sutunlar = {"id"};
        SQLiteDatabase db = v1.getReadableDatabase();
        Cursor okunan = db.query("KisilerTable", sutunlar, null, null, null, null, null);
        String kayitli_size = String.valueOf(okunan.getCount());
        okunan.close();

        rehber.add(new DgknTelRehber("Kaydedilen Numaralar", kayitli_size));

        rehber.add(new DgknTelRehber("Dışardan Eklenen Numaralar", "0"));

        final Button btn_tm = (Button) DlgRehberler.findViewById(R.id.btn_tm);


        String[] yazili_kisiler = secilen_no.getText().toString().split(",");
        for (int i = 0; i < yazili_kisiler.length; i++) {
            SecilenNo.add(new DgknSecilenNumaralar(yazili_kisiler[i], yazili_kisiler[i]));
        }

        FrgTopluSMS.SecilenNo.clear();

        btn_tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rehber_ek_no.setChecked(false);

                DlgRehberler.onBackPressed();
                if (SecilenNo.size() > 0) {
                    for (int i = 0; i < SecilenNo.size(); i++) {
                        if (secilen_no.getText().toString().indexOf(SecilenNo.get(i).getAliciname()) == -1)
                            secilen_no.append(SecilenNo.get(i).getAlicino() + ",");
                }
                }

                for (int i = 0; i < SecilenNo.size(); i++) {
                    if (secilen_no.getText().toString().indexOf(SecilenNo.get(i).getAlicino()) != -1) {
                        rehber_ek_no.setChecked(true);

                    } else
                        rehber_ek_no.setChecked(false);

                }

            }
        });

        adapter = new OzelAdaptorRehber(DlgRehberler.getContext(), rehber);
        list_rehber.setAdapter(adapter);
    }

    public void SMSGONDER_P() {

        MESAJ = (TextView) FrgRehber.findViewById(R.id.txt_mesaj);

        if (MESAJ.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "Lütfen mesaj giriniz.", Toast.LENGTH_LONG).show();
            return;
        } else if (secilen_no.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "Lütfen alıcı ekleyiniz.", Toast.LENGTH_LONG).show();
            return;
        }


        AlertDialog alertMessage = new AlertDialog.Builder(getContext()).create();
        alertMessage.setTitle("SMS Gönderiliyor !");
        alertMessage.setMessage("Seçilen Numara yada gruplara SMS göndermek istediğinize emin misiniz?");

        alertMessage.setButton("Evet", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                new BackgroundTaskSMSGonder().execute((Void) null);


            }
        });
        alertMessage.setButton2("Hayır", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertMessage.show();
    }

    public void Numara_analiz() {
        NUMARALAR = "";
        String txt_sec_cop = secilen_no.getText().toString();


        if ((secilen_no.getText().toString().indexOf("#Telefon Rehberi")) != -1) {
            for (int i = 0; i < OzelAdaptorRehber.listgonder.size(); i++) {
                NUMARALAR += OzelAdaptorRehber.listgonder.get(i).getAlici() + ",";
            }
        }
        veritabani_list();

        if ((secilen_no.getText().toString().indexOf("#Kaydedilen Numaralar")) != -1) {
            for (int i = 0; i < list_veritabani.size(); i++) {
                NUMARALAR += list_veritabani.get(i).getAlici() + ",";
            }
        }

        if ((secilen_no.getText().toString().indexOf("#Dışardan Eklenen Numaralar")) != -1) {

        }

        String[] S_N = secilen_no.getText().toString().split(",");

        for (int i = 0; i < S_N.length; i++) {
            if ((S_N[i].indexOf("#Telefon Rehberi")) == -1)
                if ((S_N[i].indexOf("#Kaydedilen Numaralar")) == -1)
                    if ((S_N[i].indexOf("#Dışardan Eklenen Numaralar")) == -1)
                        NUMARALAR += S_N[i] + ",";


        }



        Intent ıntent = new Intent(getActivity(), ActSMSGonderActivity.class);
        startActivity(ıntent);
    }

    public class BackgroundTaskRehber extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getContext(), "", "Yükleniyor...");

        }

        @Override
        protected Void doInBackground(Void... params) {


            rehberler();

            try {
                Thread.sleep(100);
                progressDialog.dismiss();

            } catch (InterruptedException e) {
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
            progressDialog.dismiss();
        }
    }

    public class BackgroundTaskSMSGonder extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getContext(), "Lütfen Bekleyiniz !", "SMS Gönderme işlemi başlıyor...");

        }

        @Override
        protected Void doInBackground(Void... params) {

            Numara_analiz();

            for (int i = 0; i < 101; i = i + 10) {
                try {
                    publishProgress(i);
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Integer currentProgress = values[0];
            progressDialog.setProgress(currentProgress);
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
            progressDialog.dismiss();
        }
    }

}