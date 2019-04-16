package com.jmteknoloji.jmtech.toplubasliklismsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;


/**
 * Created by JMTech on 6.06.2017.
 */

public class OzelAdaptorKisiler extends BaseAdapter {

    LayoutInflater lytsatir;
    List<DgknSmsGonder> liste;
    Context activity;
    private DgknSmsGonder kisiler;
    DgknSmsGonder satiroku;
    public OzelAdaptorKisiler(Context mactivity, List<DgknSmsGonder> mList) {
        activity = mactivity;
        lytsatir = (LayoutInflater) mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        liste = mList;

    }

    @Override
    public int getCount() {
        return liste.size();
    }

    @Override
    public Object getItem(int i) {
        return liste.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View row = lytsatir.inflate(R.layout.kisi_row_numaralar, viewGroup, false);


        TextView ad = (TextView) row.findViewById(R.id.txtrehbername);

        TextView tel = (TextView) row.findViewById(R.id.txtrehbertel);
        final Switch txtrehbersec = (Switch) row.findViewById(R.id.txtrehbersec);

        Button btn = (Button) row.findViewById(R.id.btnbuy);

        kisiler = liste.get(i);
        ad.setText(kisiler.getAliciname());
        tel.setText(kisiler.getAlici());



            if (FrgTopluSMS.secilen_no.getText().toString().indexOf(kisiler.getAliciname().toString()) != -1)
                txtrehbersec.setChecked(true);


        txtrehbersec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int indis = 0; indis < getCount(); indis++) {
                     satiroku = liste.get(indis);

                    if (txtrehbersec.isChecked())
                        if (i == indis) {
                            if (FrgTopluSMS.secilen_no.getText().toString().indexOf(satiroku.getAlici().toString()) == -1)
                                FrgTopluSMS.SecilenNo.add(new DgknSecilenNumaralar(satiroku.getAliciname().toString(), satiroku.getAlici().toString()));

                        }


                if (!txtrehbersec.isChecked()) {

                    if (i == indis) {
                        for (int index = 0; index < FrgTopluSMS.SecilenNo.size(); index++) {
                            if (FrgTopluSMS.SecilenNo.get(index).getAlicino() == satiroku.getAlici().toString()) {
                                FrgTopluSMS.SecilenNo.remove(index);
                            }
                        }
                    }

                }
                }
            }
        });


        return row;
    }
}