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

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnPersEventListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */


public class MyPersEventsRecyclerViewAdapter extends RecyclerView.Adapter<MyPersEventsRecyclerViewAdapter.ViewHolder> {

    private final List<Event> mValues;
    private final OnPersEventListFragmentInteractionListener mListener;

    public MyPersEventsRecyclerViewAdapter(List<Event> items, OnPersEventListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_persevents, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        Log.d(TAG, "position: "+position+", mValues: "+mValues.get(position).id);
        holder.mImg.setImageBitmap(mValues.get(position).image);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mTagsView.setText(mValues.get(position).getStringTags());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {

                    Intent p_events_intent = new Intent(v.getContext(), EventPage.class);
                    p_events_intent.putExtra("event_id", mValues.get(position).id);
                    p_events_intent.putExtra("event_type", mValues.get(position).type);
                    v.getContext().startActivity(p_events_intent);
                }
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
            mNameView = (TextView) view.findViewById(R.id.content);
            mTagsView = (TextView) view.findViewById(R.id.tags);
            mImg = (ImageView) view.findViewById(R.id.eventImg);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
