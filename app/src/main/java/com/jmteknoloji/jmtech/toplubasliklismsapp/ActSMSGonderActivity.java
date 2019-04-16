package com.jmteknoloji.jmtech.toplubasliklismsapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.jmteknoloji.jmtech.toplubasliklismsapp.FrgTopluSMS.NUMARALAR;

/**
 * Created by JMTech on 15.06.2017.
 */

public class ActSMSGonderActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    int BASARILI_SMS_I = 0;
    boolean SMS_GONDERILIYOR = false;
    boolean SMS_BITTI = false;
    boolean SMS_BEKLET = false;
    boolean SMS_DURDU = false;
    int progressStatus = 1;
    int FOR_I = 0;
    private Handler handler = new Handler();
    ProgressBar progressBar1, progressBar2;
    int NUMARALAR_SIZE = 0;
    List<String> list = new ArrayList<String>();
    ListView listView;

    ArrayAdapter<String> adapter;

    @Override
    public void onBackPressed() {
        if (!SMS_DURDU) {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Uyarı !")
                    .setMessage("İşlemi iptal etmek istediğinize emin misiniz?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!SMS_DURDU) {
                                SMS_DURDU = true;
                                progressBar1.setVisibility(View.GONE);
                                progressBar2.setProgress(NUMARALAR.split(",").length);
                                AlertDialog.Builder alertMessage = new AlertDialog.Builder(ActSMSGonderActivity.this);
                                alertMessage.setTitle("İşlem Sonlandırıldı");
                                alertMessage.setMessage("Toplu SMS gönderme işlemi sonlandırıldı.Toplamda sadece " + BASARILI_SMS_I + " SMS gönderildi.");


                                alertMessage.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Tamam butonuna basılınca yapılacaklar
                                    }
                                });
                                alertMessage.show();
                            }


                        }
                    }).setNegativeButton("Hayır", null).show();
        } else {
            ActSMSGonderActivity.this.onBackPressed();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_sms_gonderiliyor);


        progressBar1 = (ProgressBar) findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        listView = (ListView) findViewById(R.id.list_sms_gon_numaralar);
        progressBar2.setMax(NUMARALAR.split(",").length);
        progressBar2.setProgress(0);
        progressBar2.setIndeterminate(false);
        BASARILI_SMS_I = 0;
        NUMARALAR_SIZE = NUMARALAR.split(",").length;
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        SMS_BASLAT().start();
    }

    private Thread SMS_BASLAT() {
        return new Thread(new Runnable() {
            public void run() {


                while (FOR_I < NUMARALAR_SIZE) {


                    while (SMS_BEKLET) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    if (SMS_DURDU) return;

                    handler.post(new Runnable() {
                        public void run() {


                            progressBar2.setProgress(progressStatus);
                            progressStatus += 1;

                            //!!!!!!!!!!!!!!!!!!!!!!!! SMS GONDERİLİYOR /////////////////////////////77
                            FrgTopluSMS.AFTER_NUMBER = NUMARALAR.split(",")[FOR_I];
                            FOR_I += 1;

                            sendText();
                            if (!(FOR_I < NUMARALAR.split(",").length - 1)) {
                                progressBar1.setVisibility(View.GONE);
                                AlertDialog.Builder alertMessage = new AlertDialog.Builder(ActSMSGonderActivity.this);
                                alertMessage.setTitle("İşlem Tamamlandı");
                                alertMessage.setMessage("Toplu SMS gönderme işlemi tamamlandı.Toplamda " + BASARILI_SMS_I + " SMS gönderildi.");


                                alertMessage.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Tamam butonuna basılınca yapılacaklar
                                        SMS_DURDU=true;
                                    }
                                });
                                alertMessage.show();
                            }
                        }
                    });
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void sendText() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
            return;
        }
        String Number = FrgTopluSMS.AFTER_NUMBER;

        if (Number != null && !Number.isEmpty()) {


            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Number, null, FrgTopluSMS.MESAJ.getText().toString(), null, null);

            BASARILI_SMS_I = BASARILI_SMS_I + 1;
            list.add(FOR_I + ".) " + Number);
            listView.setAdapter(adapter);


            // Toast.makeText(this, String.valueOf(BASARILI_SMS_I) + ". Başarılı SMS", Toast.LENGTH_LONG).show();
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission for sending SMS was granted", Toast.LENGTH_LONG).show();

                    sendText();

                } else {
                    Toast.makeText(this, "Permisssion for sending SMS was denied.", Toast.LENGTH_LONG).show();
                }

                return;
            }
        }
    }

}
