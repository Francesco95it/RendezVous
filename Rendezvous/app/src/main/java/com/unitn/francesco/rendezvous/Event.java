package com.unitn.francesco.rendezvous;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import com.unitn.francesco.rendezvous.TagToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.unitn.francesco.rendezvous.TagToken.getListTags;


public class Event {

    public static final List<Event> EVENTS = new ArrayList<Event>();
    public static final List<Event> EVENTS_COMM = new ArrayList<Event>();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    int id;
    Bitmap image;
    String name;
    List<String> tags;
    String position;
    String type;
    String date;
    int hours;
    int minutes;
    String description;

    public Event(int id, String name, String tags, String position, String type, String description, String date, int hours, int minutes, Bitmap bitmap) {
        this.id = id;
        Log.d(TAG, "Event id: "+id);
        this.name = name;
        this.tags = getListTags(tags);
        this.position = position;
        this.type = type;
        this.description = description;
        this.image = bitmap;
        try {
            Date date1 = dateFormat.parse(date);
            this.date = dateFormat.format(date1);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.hours = hours;
        this.minutes = minutes;
    }

    public Event(int id, String name, String tags, String position, String type){
        this.id = id;
        this.name = name;
        this.tags = getListTags(tags);
        this.position = position;
        this.type = type;

    }

    public Event(int id, String name, String tags, String position){
        this.id = id;
        this.name = name;
        this.tags = getListTags(tags);
        this.position = position;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getStringTags(){
        String tokenString = "";
        for(String tag : this.tags){
            tokenString+="#"+tag+" ";
        }
        return tokenString;
    }

    public void setTags(String tags) {
        this.tags = getListTags(tags);
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    String getName(){
        return this.name;
    }

    void setName(String name){
        this.name=name;
    }
}
