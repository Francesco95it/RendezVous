package com.unitn.francesco.rendezvous;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class EventPage extends AppCompatActivity {
    ImageView headerImg;
    TextView mTitle;
    TextView mPosition;
    TextView mDateTime;
    TextView mTags;
    TextView mDesc;
    Event mEvent;

    boolean control = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        Intent intent = getIntent();

        int iId = intent.getIntExtra("event_id", 0);
        String iType = intent.getStringExtra("event_type");

        Log.d("iType:", iType);
        if (iType.equalsIgnoreCase("pers")){
            Log.d("dentro if pers", "dentro if pers");
            for (Event e : Event.EVENTS){
                if(e.getId() == iId){
                    mEvent = e;
                }
            }
        } else {
            Log.d("dentro else", "dentro else");
            for (Event e : Event.EVENTS_COMM){
                if(e.getId() == iId){
                    mEvent = e;
                }
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        headerImg = (ImageView)findViewById(R.id.header_img);
        headerImg.setImageBitmap(mEvent.getImage());

        mTitle = (TextView)findViewById(R.id.title);
        //mTitle.setText(mEvent.getName());

        mPosition = (TextView)findViewById(R.id.position_event);
        mPosition.setText(mEvent.getPosition());

        mDateTime = (TextView)findViewById(R.id.dateTime_event);
        String time = Integer.valueOf(mEvent.getMinutes()).toString();
        if (time.equalsIgnoreCase("0")) time = "00";
        mDateTime.setText(mEvent.getDate() + ", " + mEvent.getHours()+":"+time);

        mTags = (TextView)findViewById(R.id.tags_event);
        String tags = "";
        List<String> event_tags = mEvent.getTags();
        for (String e : event_tags){
            tags+="#"+e+" ";
        }
        mTags.setText(tags);

        mDesc = (TextView)findViewById(R.id.description_event);
        mDesc.setText(mEvent.getDescription());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(mEvent.getName());

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if(Profile.mPROFILE != null) {
            Log.d("EventPage", "mProfile not null!");
            List<Event> mFavEvents = Profile.mPROFILE.getFav_events();
            if (mFavEvents.isEmpty()){
                Log.d("isEmptyIf()", "mFavEvents.isEmpty(): "+mFavEvents.isEmpty());
                fab.setImageResource(R.mipmap.ic_fav);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fab.setImageResource(R.mipmap.ic_fav_added);
                        if(getControl()) {
                            Profile.mPROFILE.addFavEvent(mEvent);
                            Snackbar.make(view, "Event added to favourites", Snackbar.LENGTH_LONG).show();
                            setControl();
                        } else {
                            Profile.mPROFILE.removeFavEvent(mEvent);
                            fab.setImageResource(R.mipmap.ic_fav);
                            Snackbar.make(view, "Event removed from favourites", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

            }
            boolean finded = false;
            for (Event e : mFavEvents) {
                Log.d("isEmptyIf()", "mFavEvents.isEmpty(): " + mFavEvents.isEmpty());
                if (e.getId() == mEvent.getId()) {
                    finded = true;
                    fab.setImageResource(R.mipmap.ic_fav_added);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Profile.mPROFILE.removeFavEvent(mEvent);
                            fab.setImageResource(R.mipmap.ic_fav);
                            Snackbar.make(view, "Event removed from favourites", Snackbar.LENGTH_LONG).show();
                        }
                    });

                }
            }
            if (!finded) {
                fab.setImageResource(R.mipmap.ic_fav);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fab.setImageResource(R.mipmap.ic_fav_added);
                        if(getControl()) {
                             Profile.mPROFILE.addFavEvent(mEvent);
                             Snackbar.make(view, "Event added to favourites", Snackbar.LENGTH_LONG).show();
                             setControl();
                        } else {
                            Profile.mPROFILE.removeFavEvent(mEvent);
                            fab.setImageResource(R.mipmap.ic_fav);
                            Snackbar.make(view, "Event removed from favourites", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

            }
        }
    }

    private void setControl(){
        this.control = !this.control;
    }

    private boolean getControl(){
        return this.control;
    }
}


