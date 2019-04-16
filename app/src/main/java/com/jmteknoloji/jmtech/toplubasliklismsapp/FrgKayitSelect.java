package com.jmteknoloji.jmtech.toplubasliklismsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FrgKayitSelect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrgKayitSelect extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static String ID = "";
    static private int positionana;
    static ClsVeriTabani v1;
    static SwipeMenuCreator update_delete;
    static SwipeMenuCreator sms_add;

    ListView liste;
    Button ara;
    static SearchView arama;
    static OzelAdaptorShow adapter;
    static List<DgknKayitKisi> listele = new ArrayList<DgknKayitKisi>();

    static SwipeMenuListView swipeMenuListView;

    public FrgKayitSelect() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KisiSelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FrgKayitSelect newInstance(String param1, String param2) {
        FrgKayitSelect fragment = new FrgKayitSelect();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    static View view;

    protected SwipeActionAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.kisi_kayit_goster, container, false);
        v1 = new ClsVeriTabani(view.getContext());


        swipeMenuListView = (SwipeMenuListView) view.findViewById(R.id.list_item);
        arama = (SearchView) view.findViewById(R.id.arama2);


        arama.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(arama.getWindowToken(), 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SQLiteDatabase db = v1.getReadableDatabase();
                String searchStr = newText;

                listele.clear();


                Cursor okunan = db.rawQuery("SELECT id,res,adi,soyadi,tel,dyer,cns,dtarihi,itarihi,tc FROM KisilerTable WHERE upper(adi)  like '" + searchStr + "%' order by upper(adi)", null);
                if (okunan != null) {
                    if (okunan.moveToFirst()) {
                        do {

                            byte[] res = okunan.getBlob(okunan.getColumnIndex("res"));
                            listele.add(new DgknKayitKisi(okunan.getInt(okunan.getColumnIndex("id")), ClsUtility.getPhoto(res), okunan.getString(okunan.getColumnIndex("adi")), okunan.getString(okunan.getColumnIndex("soyadi")), okunan.getString(okunan.getColumnIndex("cns")), okunan.getString(okunan.getColumnIndex("tc")), okunan.getString(okunan.getColumnIndex("tel")), okunan.getString(okunan.getColumnIndex("dtarihi")), okunan.getString(okunan.getColumnIndex("itarihi")), okunan.getString(okunan.getColumnIndex("dyer"))));//arrayliste aktarim yapilmasi
                            menuolustur();

                        } while (okunan.moveToNext());
                    } else {
                        listele.clear();
                        menuolustur();
                    }

                }
                return false;
            }
        });

        menuolustur();
        bilgilerigoster();
        return view;
    }


    private void klavyeGizle() {
        //
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void bilgilerigoster() {
        try {

            SQLiteDatabase db = v1.getReadableDatabase();
            Cursor okunan = db.rawQuery("SELECT id,res,adi,soyadi,tel,dyer,cns,dtarihi,itarihi,tc FROM KisilerTable order by upper(adi)  ", null);

            listele.clear();

            while (okunan.moveToNext()) {

                byte[] res = okunan.getBlob(okunan.getColumnIndex("res"));

                listele.add(new DgknKayitKisi(okunan.getInt(okunan.getColumnIndex("id")), ClsUtility.getPhoto(res), okunan.getString(okunan.getColumnIndex("adi")), okunan.getString(okunan.getColumnIndex("soyadi")), okunan.getString(okunan.getColumnIndex("cns")), okunan.getString(okunan.getColumnIndex("tc")), okunan.getString(okunan.getColumnIndex("tel")), okunan.getString(okunan.getColumnIndex("dtarihi")), okunan.getString(okunan.getColumnIndex("itarihi")), okunan.getString(okunan.getColumnIndex("dyer"))));//arrayliste aktarim yapilmasi

            }
            Toast.makeText(view.getContext(), okunan.getCount() + " Kayıt Listelendi.", Toast.LENGTH_LONG).show();

            menuolustur();


        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Kayıt Bulunamadı.", Toast.LENGTH_LONG).show();
        }

    }

    static SwipeMenuItem openItem2;

    public static void menuolustur() {
        swipeMenuListView = (SwipeMenuListView) view.findViewById(R.id.list_item);

        swipeMenuListView.setSwipeDirection(swipeMenuListView.DIRECTION_LEFT);

        adapter = new OzelAdaptorShow(view.getContext(), listele);
        swipeMenuListView.setAdapter(adapter);

        update_delete = new SwipeMenuCreator() {


            @Override
            public void create(SwipeMenu menu) {
                // Create different menus depending on the view type

                SwipeMenuItem openItem = new SwipeMenuItem(view.getContext().getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.parseColor("#4FC3F7")));
                openItem.setWidth(170);
                openItem.setIcon(R.drawable.edit);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(view.getContext().getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(170);
                deleteItem.setIcon(R.drawable.del);
                menu.addMenuItem(deleteItem);
            }


        };

        swipeMenuListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
                swipeMenuListView.getChildAt(position).setBackgroundColor(Color.rgb(243, 246, 243));
            }

            @Override
            public void onMenuClose(int position) {
                swipeMenuListView.getChildAt(position).setBackgroundColor(Color.WHITE);
            }
        });

        swipeMenuListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
            }

            @Override
            public void onSwipeEnd(int position) {
            }
        });


        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(arama.getWindowToken(), 0);
                positionana = position;
                switch (index) {
                    case 0:
                        ID = String.valueOf(listele.get(positionana).getID());
                        Intent intent = new Intent(view.getContext(), ActKayitDuzenleActivity.class);
                        view.getContext().startActivity(intent);
                        break;
                    case 1:
                        AlertDialog alertMessage = new AlertDialog.Builder(view.getContext()).create();
                        alertMessage.setTitle("Kişi Siliniyor !");
                        alertMessage.setMessage("Kişiyi silmek istediğinizden emin misiniz?");

                        alertMessage.setButton("Evet", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String id = String.valueOf(listele.get(positionana).getID());
                                SQLiteDatabase db = v1.getReadableDatabase();
                                db.delete("KisilerTable", "id" + "=?", new String[]{id});
                                String p = String.valueOf(positionana);

                                listele.remove(positionana);
                                adapter.notifyDataSetChanged();
                                swipeMenuListView.setAdapter(adapter);
                                Toast.makeText(view.getContext(), "1 Kayıt Silindi.", Toast.LENGTH_LONG).show();
                            }
                        });
                        alertMessage.setButton2("Hayır", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertMessage.show();

                        break;

                }
                return false;
            }

        });
        swipeMenuListView.setMenuCreator(update_delete);

    }


}
