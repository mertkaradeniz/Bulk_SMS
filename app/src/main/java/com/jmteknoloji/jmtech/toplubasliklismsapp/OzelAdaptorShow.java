package com.jmteknoloji.jmtech.toplubasliklismsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JMTech on 29.05.2017.
 */

public class OzelAdaptorShow extends BaseAdapter {

    private static final String EXTRA_MESSAGE ="" ;
    LayoutInflater lytsatir;
    List<DgknKayitKisi> liste;
    Context activity;
    private DgknKayitKisi ogr;
    ClsRoundImage roundedImage;

    public OzelAdaptorShow(Context mactivity,List<DgknKayitKisi> mList)
    {
        activity =mactivity ;
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
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View row=lytsatir.inflate(R.layout.kisi_row_kayitlar,parent,false);

        ImageView image= (ImageView) row.findViewById(R.id.imageres);
        TextView ad= (TextView) row.findViewById(R.id.txtadi);

        TextView tel= (TextView) row.findViewById(R.id.txttel);
        TextView dyeri= (TextView) row.findViewById(R.id.txtdyer);

        Button btn= (Button) row.findViewById(R.id.btnbuy);

        ogr=liste.get(position);
        ad.setText(ogr.getAd().toString()+" "+ogr.getSoy().toString());
        tel.setText(ogr.getTelefon().toString());
        dyeri.setText(ogr.getDyer().toString());
        ogr.setID(ogr.getID());


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClsIletiMrkzAPI log=new ClsIletiMrkzAPI();
              //  log.login_and_bakiye("","");
                log.smsgonder("5458540897","Jmt66382183670.","5458540897","System Control","METRODERYA","");


            }
        });

        try {
               roundedImage = new ClsRoundImage(ogr.getImagepath());
               image.setImageDrawable(roundedImage);
       }
        catch (Exception e) {
           image.setImageResource(R.drawable.person);
        }
    return row;
}
}