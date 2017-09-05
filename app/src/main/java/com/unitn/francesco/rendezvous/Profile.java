package com.unitn.francesco.rendezvous;


import java.util.ArrayList;
import java.util.List;

public class Profile {

    public static Profile mPROFILE = null;

    private String name;
    private String surname;
    private String birthday;
    private String position;
    private List<String> tags = new ArrayList<String>();
    private List<Event> fav_events = new ArrayList<Event>();

    public Profile() {
    }

    public Profile(String name, String surname, String birthday, String position) {

        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.position = position;
    }

    public Profile(String name, String surname, String birthday, String position, List<String> tags, List<Event> fav_events) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void addFavEvent(Event e){
        fav_events.add(e);
    }

    public void removeFavEvent(Event e){
        fav_events.remove(fav_events.indexOf(e));
    }

    public List<Event> getFav_events(){
        return fav_events;
    }

    public void setTags(List<String> tags){
        this.tags=tags;
    }

    public List<String> getTags(){
        return tags;
    }
}
