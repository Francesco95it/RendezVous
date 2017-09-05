package com.unitn.francesco.rendezvous;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unitn.francesco.rendezvous.PersEventsFragment.OnPersEventListFragmentInteractionListener;
import com.unitn.francesco.rendezvous.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private String name;
    private String tags;
    private String type;
    private String position;

    private List<Event> mValues = new ArrayList<Event>();

    public SearchAdapter(String name, String tags, String type, String position) {
        Log.d(TAG, "SearchAdapter, in here! name/tags/type/position" +name+tags+type+position);
        this.name = name;
        this.tags = tags;
        this.type = type;
        this.position = position;

        boolean filters = false;

        if(!name.equalsIgnoreCase("-1")){
            Log.d(TAG, "NAME!");
            filters = true;
            searchByName();
        }
        if(!tags.equalsIgnoreCase("-1")){
            Log.d(TAG, "TAGS!");
            filters = true;
            searchByTag();
        }
        if(!position.equalsIgnoreCase("-1")){
            Log.d(TAG, "POSITION!");
            filters = true;
            searchByPosition();
        }
        if(mValues.isEmpty() && !filters){
            Log.d(TAG, "EMPTY!!");
            for (Event e : Event.EVENTS) mValues.add(e);
            for (Event e : Event.EVENTS_COMM) mValues.add(e);
        }
        if(!type.equalsIgnoreCase("-1"))filterByType();
    }

    private void searchByName() {
        for (Event e : Event.EVENTS){
            if(e.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase())){
                mValues.add(e);
            }
        }
        for (Event e : Event.EVENTS_COMM){
            if(e.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase())){
                mValues.add(e);
            }
        }
    }
    private void searchByTag() {
        if(!mValues.isEmpty()){
            List<Event> filteredList = new ArrayList<Event>();
            for(Event e : mValues){
                boolean founded = false;
                for (String tag: e.getTags()) {
                    if(TagToken.getListTags(tags).contains(tag)) founded = true;
                }
                if (founded) filteredList.add(e);
            }
            mValues = filteredList;
        } else {
            for (Event e : Event.EVENTS){
                boolean founded = false;
                for (String tag: e.getTags()) {
                    if(TagToken.getListTags(tags).contains(tag)) founded = true;
                }
                if (founded) mValues.add(e);
            }
            for (Event e : Event.EVENTS_COMM){
                boolean founded = false;
                for (String tag: e.getTags()) {
                    if(TagToken.getListTags(tags).contains(tag)) founded = true;
                }
                if (founded) mValues.add(e);
            }

        }
    }
    private void searchByPosition() {
        if(!mValues.isEmpty()){
            List<Event> filteredList = new ArrayList<Event>();
            for(Event e : mValues){
                if(e.getPosition().equalsIgnoreCase(position)) filteredList.add(e);
            }
            mValues = filteredList;
        } else {
            for (Event e : Event.EVENTS){
                if (e.getPosition().equalsIgnoreCase(position)) mValues.add(e);
            }
            for (Event e : Event.EVENTS_COMM){
                if (e.getPosition().equalsIgnoreCase(position)) mValues.add(e);
            }

        }

    }
    private void filterByType() {

        Log.d(TAG, "FILTERING!");
        List<Event> filteredList = new ArrayList<Event>();
        for(Event e : mValues){
            if(e.getType().equalsIgnoreCase(type)) {
                Log.d(TAG, "FILTERED OK: " + e.getName());
                filteredList.add(e);
            }
        }

        mValues=filteredList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_search, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mImg.setImageBitmap(mValues.get(position).image);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mTagsView.setText(mValues.get(position).getStringTags());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p_events_intent = new Intent(v.getContext(), EventPage.class);
                p_events_intent.putExtra("event_id", mValues.get(position).id);
                p_events_intent.putExtra("event_type", mValues.get(position).type);
                v.getContext().startActivity(p_events_intent);
                }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mTagsView;
        public final ImageView mImg;
        public Event mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.content_src);
            mTagsView = (TextView) view.findViewById(R.id.tags_src);
            mImg = (ImageView) view.findViewById(R.id.eventImg_src);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }

}
