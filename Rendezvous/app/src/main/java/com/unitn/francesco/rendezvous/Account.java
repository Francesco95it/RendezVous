package com.unitn.francesco.rendezvous;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Account extends AppCompatActivity {

    Button personalInfo;
    Button fav_events;
    Button mod_tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        fav_events = (Button)findViewById(R.id.myevents_btn);

        fav_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Account.this, FavEvents.class);
                startActivity(i);
            }
        });

        mod_tags = (Button)findViewById(R.id.mod_tags_btn);

        mod_tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Account.this, My_tags.class);
                startActivity(i);
            }
        });

        personalInfo = (Button)findViewById(R.id.mod_pers_info_btn);

        personalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Account.this, PersonalInfo.class);
                startActivity(i);
            }
        });

    }

}
