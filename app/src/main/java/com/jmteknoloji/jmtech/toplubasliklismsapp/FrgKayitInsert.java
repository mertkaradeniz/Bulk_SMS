package com.jmteknoloji.jmtech.toplubasliklismsapp;

import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FrgKayitInsert#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrgKayitInsert extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_OK = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText Ad;
    EditText Tc;
    EditText Soyad;
    EditText Tel;
    Spinner DYeri;
    Button btnkayit,btnvazgec;
    TextView Phone;
    EditText DTarihi, ITarihi;
    ImageView KisiimageView;
    ClsVeriTabani v1;
    Bitmap Resimage;
    RadioButton Cinsiyet;
    ClsRoundImage roundedImage;

    List<DgknKayitKisi> listele = new ArrayList<DgknKayitKisi>();
    private static final int RESULT_LOAD_IMAGE = 1;
    private ArrayAdapter<String> dataAdapterForIller;
    View view;
    String[] iller = {"ADANA", "ADIYAMAN", "AFYONKARAHİSAR", "AĞRI", "AMASYA", "ANKARA", "ANTALYA", "ARTVİN", "AYDIN", "BALIKESİR", "BİLECİK"
            , "BİNGÖL", "BİTLİS", "BOLU", "BURDUR", "BURSA", "ÇANAKKALE", "ÇANKIRI", "ÇORUM", "DENİZLİ", "DİYARBAKIR", "EDİRNE", "ELAZIĞ", "ERZİNCAN", "ERZURUM",
            "ESKİŞEHİR", "GAZİANTEP", "GİRESUN", "GÜMÜŞHANE", "HAKKARİ", "HATAY", "ISPARTA", "MERSİN", "İSTANBUL", "İZMİR", "KARS", "KASTAMONU", "KAYSERİ", "KIRKLARELİ",
            "KIRŞEHİR", "KOCAELİ", "KONYA", "KÜTAHYA", "MALATYA", "MANİSA", "KAHRAMANMARAŞ", "MARDİN", "MUĞLA", "MUŞ", "NEVŞEHİR", "NİĞDE", "ORDU", "RİZE", "SAKARYA",
            "SAMSUN", "SİİRT", "SİNOP", "SİVAS", "TEKİRDAĞ", "TOKAT", "TRABZON", "TUNCELİ", "ŞANLIURFA", "UŞAK", "VAN", "YOZGAT", "ZONGULDAK", "AKSARAY", "BAYBURT", "KARAMAN",
            "KIRIKKALE", "BATMAN", "ŞIRNAK", "BARTIN", "ARDAHAN", "IĞDIR", "YALOVA", "KARABÜK", "KİLİS", "OSMANİYE", "DÜZCE"};

    public FrgKayitInsert() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KisiEkleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FrgKayitInsert newInstance(String param1, String param2) {
        FrgKayitInsert fragment = new FrgKayitInsert();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void ekleme(Bitmap Res, String Ad, String Soyad, String tc, String tel, String dtarih, String itarih, String dyer) {
        SQLiteDatabase db = v1.getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        if (Res != null)
            cv1.put("res", ClsUtility.getBytes(Res));

        if (Cinsiyet.isChecked())
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
        db.insertOrThrow("KisilerTable", null, cv1);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.kisi_kayit_ekle, container, false);


        //super.onCreate(savedInstanceState);
        Phone = (TextView) view.findViewById(R.id.kisitel);
        DYeri = (Spinner) view.findViewById(R.id.kisidyeri);
        DTarihi = (EditText) view.findViewById(R.id.kisidtarihi);
        ITarihi = (EditText) view.findViewById(R.id.kisiitarihi);
        Ad = (EditText) view.findViewById(R.id.kisiadi);
        Soyad = (EditText) view.findViewById(R.id.kisisoyadi);
        Tc = (EditText) view.findViewById(R.id.kisitc);
        Tel = (EditText) view.findViewById(R.id.kisitel);
        btnkayit = (Button) view.findViewById(R.id.btnkayit);
        btnvazgec = (Button) view.findViewById(R.id.btn_vazgec);

        Cinsiyet = (RadioButton) view.findViewById(R.id.kisibay);
        KisiimageView = (ImageView) view.findViewById(R.id.kisiimagem);

        v1 = new ClsVeriTabani(view.getContext());
        //İlleri Yükle
        dataAdapterForIller = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, iller);
        dataAdapterForIller.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DYeri.setAdapter(dataAdapterForIller);


        Resimage = null;

        btnvazgec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrgKayitSelect anaMenuFragment = new FrgKayitSelect();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.relativelayout, anaMenuFragment).commit();
            }
        });

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
        btnkayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (Resimage == null) {
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.person);
                        Resimage = bm;
                    }

                    if (Ad.getText().toString().trim().equals("")) {
                        Toast.makeText(view.getContext(), "Lütfen adı boş geçmeyiniz", Toast.LENGTH_LONG).show();
                    } else if (Soyad.getText().toString().trim().equals("")) {
                        Toast.makeText(view.getContext(), "Lütfen soyadı boş geçmeyiniz", Toast.LENGTH_LONG).show();
                    } else if (Tel.getText().toString().trim().equals("")) {
                        Toast.makeText(view.getContext(), "Lütfen telefonu boş geçmeyiniz", Toast.LENGTH_LONG).show();
                    } else {

                        ekleme(Resimage, Ad.getText().toString(), Soyad.getText().toString(), Tc.getText().toString(), Tel.getText().toString(), DTarihi.getText().toString(), ITarihi.getText().toString(), DYeri.getSelectedItem().toString());
                        Toast.makeText(view.getContext(), "Kayıt Yapıldı", Toast.LENGTH_LONG).show();
                        Ad.setText("");
                        Soyad.setText("");
                        Tc.setText("");
                        Tel.setText("");
                        DTarihi.setText("");
                        ITarihi.setText("");
                        Cinsiyet.isChecked();
                        DYeri.setSelection(0);

                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.person);
                        roundedImage = new ClsRoundImage(bm);
                        KisiimageView.setImageDrawable(roundedImage);

                        FrgKayitSelect anaMenuFragment = new FrgKayitSelect();
                        FragmentManager manager = getFragmentManager();
                        manager.beginTransaction().replace(R.id.relativelayout, anaMenuFragment).commit();
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

        return view;

    }

    @Override //Galeriden resim yüklemekte kullandığımız metot
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null && data.getData() != null) {


            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                KisiimageView = (ImageView) view.findViewById(R.id.kisiimagem);
                Resimage = bitmap;
                roundedImage = new ClsRoundImage(bitmap);
                KisiimageView.setImageDrawable(roundedImage);


            } catch (IOException e) {

            }
        }

    }

    public void onStart() {
        super.onStart();

        ITarihi = (EditText) view.findViewById(R.id.kisiitarihi);
        ITarihi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    ClsDateDialog dialog = new ClsDateDialog(view);
                    FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });


        DTarihi = (EditText) view.findViewById(R.id.kisidtarihi);
        DTarihi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    ClsDateDialog dialog = new ClsDateDialog(view);
                    FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });
    }
}
