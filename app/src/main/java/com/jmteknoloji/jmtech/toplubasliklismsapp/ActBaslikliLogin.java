package com.jmteknoloji.jmtech.toplubasliklismsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ActBaslikliLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baslikli_login);

        Button login = (Button) findViewById(R.id.btnlogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClsIletiMrkzAPI log=new ClsIletiMrkzAPI();
                log.login_and_bakiye("","");
                log.smsgonder("5458540897","Jmt66382183670.","5458540897","System Control","METRODERYA","");

            }
        });

    }

}
