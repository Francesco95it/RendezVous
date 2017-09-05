package com.unitn.francesco.rendezvous;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PersEventsFragment.OnPersEventListFragmentInteractionListener, CommEventsFragment.OnCommEventListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.background_franz_light));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (getIntent().getStringExtra("event_type")){
            case "p": {
                getSupportActionBar().setTitle("Personal events");
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new PersEventsFragment())
                        .commit(); break;
            }
            case "c": {
                getSupportActionBar().setTitle("Commercial events");
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new CommEventsFragment())
                        .commit(); break;
            }
            default: break;
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        TextView mNavProfName = (TextView)findViewById(R.id.nav_prof_name);
        TextView mNavProfPosition = (TextView)findViewById(R.id.nav_prof_position);

        mNavProfName.setText("Tell us");
        mNavProfPosition.setText("who you are!");

        if(Profile.mPROFILE != null){
            mNavProfName.setText(Profile.mPROFILE.getName() + " " + Profile.mPROFILE.getSurname());
            mNavProfPosition.setText(Profile.mPROFILE.getPosition());
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();
        //TODO: gestire cambio fragment a seconda dell'item schiacciato

        if (id == R.id.nav_create) {
            Intent create_event_intent = new Intent(Nav.this, CreateEvent.class);
            startActivity(create_event_intent);
        } else if (id == R.id.nav_pevents) {
            getSupportActionBar().setTitle("Personal Events");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new PersEventsFragment())
                    .commit();
        } else if (id == R.id.nav_cEvents) {
            getSupportActionBar().setTitle("Commercial Events");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new CommEventsFragment())
                    .commit();

        } else if (id == R.id.nav_account) {
            Intent account_intent = new Intent(Nav.this, Account.class);
            startActivity(account_intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPersEventListFragmentInteraction(Event string) {

    }

    @Override
    public void onCommEventListFragmentInteraction(Event item) {

    }
}
