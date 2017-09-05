package com.unitn.francesco.rendezvous;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Date;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView search_event = (ImageView) findViewById(R.id.search_main);
        LinearLayout p_events = (LinearLayout) findViewById(R.id.pers_events);
        LinearLayout c_events = (LinearLayout) findViewById(R.id.c_events);
        LinearLayout account = (LinearLayout) findViewById(R.id.account);
        /*LinearLayout settings = (LinearLayout) findViewById(R.id.settings);*/

        search_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search_intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(search_intent);
            }
        });

        p_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p_events_intent = new Intent(MainActivity.this, Nav.class);
                p_events_intent.putExtra("event_type", "p");
                startActivity(p_events_intent);
            }
        });
        c_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c_events_intent = new Intent(MainActivity.this, Nav.class);
                c_events_intent.putExtra("event_type", "c");
                startActivity(c_events_intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent account_intent = new Intent(MainActivity.this, Account.class);
                startActivity(account_intent);
            }
        });

        /*settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings_intent = new Intent(MainActivity.this, EventPage.class);
                startActivity(settings_intent);
            }
        });*/

        Bitmap party_img = BitmapFactory.decodeResource(getResources(), R.mipmap.foam_party);
        Bitmap drl_img = BitmapFactory.decodeResource(getResources(), R.mipmap.drl_black);
        Bitmap montecatini_img = BitmapFactory.decodeResource(getResources(), R.mipmap.montecatini_mori);
        Bitmap renon_img = BitmapFactory.decodeResource(getResources(), R.mipmap.renon);

        String date = "17/09/2017";
        int hours = 8;
        int minutes = 0;
        String date1 = "06/09/2017";

        Event.EVENTS.add(new Event(2, "Gita al renon", "Camminare, natura, amicizia", "Renon", "pers", "Ciao a tutti! Per questo weekend vorrei andare a fare una camminata al renon: nulla di troppo faticoso! Passo lento per godersi tutta la natura in completo relax. Ritrovo alle 8:00 alla funivia del Renon! Tutti benvenuti!", date, hours, minutes, renon_img));
        Event.EVENTS.add(new Event(3, "Esplorazione Montecatini!", "Abbandono, esplorazione, distruzione, pericolo", "Mori", "pers", "Ragazzi domani sera pensavo di andare a fare un'esplorazione dell'abbandonata Montecatini a Mori! Sono ormai un veterano dell'esplorazione dei luoghi abbandonati ma questo mi manca alla lista. Chi volesse venire è benvenuto, più siamo meglio è! Non si sa mai.. Ritrovo alle 20:30 al ponte appena a nord dell'edificio!", date1, 20, 30, montecatini_img));

        Event.EVENTS_COMM.add(new Event(4, "Party all night", "Birra, Festa", "Merano", "comm", "Al parco di Merano la FORST beer presenta l'evento dell'anno! Birra a 1€ sabato 11/09, entrata gratuita!", "11/09/2017", 18, 30, party_img));
        Event.EVENTS_COMM.add(new Event(5, "DRL #race7", "Droni, Gare, DRL", "Cortaccia", "comm", "Cortaccia ospita per la prima volta la DRL. Gara 7 della Drone Racing League si terrà a 3km dal centro dove le strade verranno appositamente allestite. Tutto gratuito! Inizio gara ore 10:00", date, 10, 0, drl_img));

    }
}
