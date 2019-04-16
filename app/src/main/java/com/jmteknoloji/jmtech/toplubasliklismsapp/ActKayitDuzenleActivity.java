package com.jmteknoloji.jmtech.toplubasliklismsapp;

import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class ActKayitDuzenleActivity extends AppCompatActivity {

    EditText Ad;
    EditText Tc;
    EditText Soyad;
    EditText Tel;
    Spinner DYeri;
    Button btnGuncelle;
    TextView Phone;
    EditText DTarihi, ITarihi;
    ImageView KisiimageView;
    ClsVeriTabani v1;
    Bitmap Resimage;
    RadioButton CinsiyetE;
    RadioButton CinsiyetK;
    ClsRoundImage roundedImage;
    int sec_il = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    private ArrayAdapter<String> dataAdapterForIller;
    String[] iller = {"ADANA", "ADIYAMAN", "AFYONKARAHİSAR", "AĞRI", "AMASYA", "ANKARA", "ANTALYA", "ARTVİN", "AYDIN", "BALIKESİR", "BİLECİK"
            , "BİNGÖL", "BİTLİS", "BOLU", "BURDUR", "BURSA", "ÇANAKKALE", "ÇANKIRI", "ÇORUM", "DENİZLİ", "DİYARBAKIR", "EDİRNE", "ELAZIĞ", "ERZİNCAN", "ERZURUM",
            "ESKİŞEHİR", "GAZİANTEP", "GİRESUN", "GÜMÜŞHANE", "HAKKARİ", "HATAY", "ISPARTA", "MERSİN", "İSTANBUL", "İZMİR", "KARS", "KASTAMONU", "KAYSERİ", "KIRKLARELİ",
            "KIRŞEHİR", "KOCAELİ", "KONYA", "KÜTAHYA", "MALATYA", "MANİSA", "KAHRAMANMARAŞ", "MARDİN", "MUĞLA", "MUŞ", "NEVŞEHİR", "NİĞDE", "ORDU", "RİZE", "SAKARYA",
            "SAMSUN", "SİİRT", "SİNOP", "SİVAS", "TEKİRDAĞ", "TOKAT", "TRABZON", "TUNCELİ", "ŞANLIURFA", "UŞAK", "VAN", "YOZGAT", "ZONGULDAK", "AKSARAY", "BAYBURT", "KARAMAN",
            "KIRIKKALE", "BATMAN", "ŞIRNAK", "BARTIN", "ARDAHAN", "IĞDIR", "YALOVA", "KARABÜK", "KİLİS", "OSMANİYE", "DÜZCE"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisi_kayit_duzenle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("Kişi Kayıt Düzenle");
        setSupportActionBar(toolbar);



        Phone = (TextView) findViewById(R.id.upkisitel);
        DYeri = (Spinner) findViewById(R.id.upkisidyeri);
        DTarihi = (EditText) findViewById(R.id.upkisidtarihi);
        ITarihi = (EditText) findViewById(R.id.upkisiitarihi);
        Ad = (EditText) findViewById(R.id.upkisiadi);
        Soyad = (EditText) findViewById(R.id.upkisisoyadi);
        Tc = (EditText) findViewById(R.id.upkisitc);
        Tel = (EditText) findViewById(R.id.upkisitel);
        btnGuncelle = (Button) findViewById(R.id.btnGuncelle);
        CinsiyetE = (RadioButton) findViewById(R.id.upkisibay);
        CinsiyetK = (RadioButton) findViewById(R.id.upkisibayan);
        KisiimageView = (ImageView) findViewById(R.id.upkisiimagem);

        v1 = new ClsVeriTabani(ActKayitDuzenleActivity.this);
        //İlleri Yükle
        dataAdapterForIller = new ArrayAdapter<String>(ActKayitDuzenleActivity.this, android.R.layout.simple_spinner_item, iller);
        dataAdapterForIller.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DYeri.setAdapter(dataAdapterForIller);


        Resimage = null;
        bilgilerigoster();
        KisiimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);

//Açılacak olan klasör belirleniyor.
                startActivityForResult(Intent.createChooser(intent, "Resim Seç"), RESULT_LOAD_IMAGE);
            }
        });

        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (Resimage == null) {
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.person);
                        Resimage = bm;
                    }

                    if (Ad.getText().toString().trim().equals("")) {
                        Toast.makeText(ActKayitDuzenleActivity.this, "Lütfen adı boş geçmeyiniz", Toast.LENGTH_LONG).show();
                    } else if (Soyad.getText().toString().trim().equals("")) {
                        Toast.makeText(ActKayitDuzenleActivity.this, "Lütfen soyadı boş geçmeyiniz", Toast.LENGTH_LONG).show();
                    } else if (Tel.getText().toString().trim().equals("")) {
                        Toast.makeText(ActKayitDuzenleActivity.this, "Lütfen telefonu boş geçmeyiniz", Toast.LENGTH_LONG).show();
                    } else {

                        kaydiGuncelle(FrgKayitSelect.ID, Resimage, Ad.getText().toString(), Soyad.getText().toString(), Tc.getText().toString(), Tel.getText().toString(), DTarihi.getText().toString(), ITarihi.getText().toString(), DYeri.getSelectedItem().toString());
                        FrgKayitSelect.adapter.notifyDataSetChanged();
                        FrgKayitSelect.menuolustur();
                      onBackPressed();

                    }
                } finally {
                    v1.close();
                }
            }
        });

        DTarihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
            }
        });

        ITarihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
            }

        });

        Phone.addTextChangedListener(new TextWatcher() {

            int length_before = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                length_before = s.length();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (length_before < s.length()) {
                    if (s.length() <= 1)
                        s.insert(0, "0 (");

                    if (s.length() == 6)
                        s.append(") ");

                    if (s.length() > 6) {
                        if (Character.isDigit(s.charAt(6)))
                            s.insert(6, " ");
                    }
                    if (s.length() > 11) {
                        if (Character.isDigit(s.charAt(11)))
                            s.insert(11, " ");
                    }
                }
            }
        });


    }



    public void bilgilerigoster() {
        try {
            String[] sutunlar = {"id", "res", "adi", "soyadi", "tel", "dyer", "cns", "dtarihi", "itarihi", "tc"};

            SQLiteDatabase db = v1.getReadableDatabase();
            Cursor okunan = db.rawQuery("SELECT id,res,adi,soyadi,tel,dyer,cns,dtarihi,itarihi,tc FROM KisilerTable WHERE ID='" + FrgKayitSelect.ID + "'", null);

            while (okunan.moveToNext()) {

                byte[] res = okunan.getBlob(okunan.getColumnIndex("res"));


                Ad.setText(okunan.getString(okunan.getColumnIndex("adi")));
                Soyad.setText(okunan.getString(okunan.getColumnIndex("soyadi")));

                if (okunan.getString(okunan.getColumnIndex("cns")) == "Bay")
                    CinsiyetE.isChecked();
                else
                    CinsiyetK.isChecked();

                Tc.setText(okunan.getString(okunan.getColumnIndex("tc")));
                Tel.setText(okunan.getString(okunan.getColumnIndex("tel")));
                DTarihi.setText(okunan.getString(okunan.getColumnIndex("dtarihi")));
                ITarihi.setText(okunan.getString(okunan.getColumnIndex("itarihi")));


                for (int i = 0; i < iller.length; i++) {
                    if (iller[i].toString() == okunan.getString(okunan.getColumnIndex("dyer"))) {
                        sec_il = i;
                        break;

                    }
                }
            }

            DYeri.setSelection(sec_il);

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.person);
            roundedImage = new ClsRoundImage(bm);
            KisiimageView.setImageDrawable(roundedImage);

        } catch (Exception e) {

        }

    }

    public void kaydiGuncelle(String ID, Bitmap Res, String Ad, String Soyad, String tc, String tel, String dtarih, String itarih, String dyer) {

        SQLiteDatabase db = v1.getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        if (Res != null)
            cv1.put("res", ClsUtility.getBytes(Res));

        if (CinsiyetE.isChecked())
            cv1.put("cns", "Bay");
        else
            cv1.put("cns", "Bayan");

        cv1.put("adi", Ad);
        cv1.put("soyadi", Soyad);
        cv1.put("tc", tc);
        cv1.put("tel", tel);
        cv1.put("dtarihi", dtarih);
        cv1.put("itarihi", itarih);
        cv1.put("dyer", dyer);
        db.update("KisilerTable", cv1, "ID" + "=?", new String[]{ID});
        db.close();

    }

    @Override //Galeriden resim yüklemekte kullandığımız metot
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null && data.getData() != null) {


            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                KisiimageView = (ImageView) findViewById(R.id.upkisiimagem);
                Resimage = bitmap;
                roundedImage = new ClsRoundImage(bitmap);
                KisiimageView.setImageDrawable(roundedImage);


            } catch (IOException e) {

            }
        }

    }

    public void onStart() {
        super.onStart();

        ITarihi = (EditText) findViewById(R.id.upkisiitarihi);
        ITarihi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    ClsDateDialog dialog = new ClsDateDialog(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });


        DTarihi = (EditText) findViewById(R.id.upkisidtarihi);
        DTarihi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    ClsDateDialog dialog = new ClsDateDialog(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });
    }
}
