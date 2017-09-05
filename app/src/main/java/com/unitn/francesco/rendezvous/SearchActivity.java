package com.unitn.francesco.rendezvous;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

public class SearchActivity extends AppCompatActivity {

    EditText name;
    EditText tags;
    EditText position;

    RadioButton input_radio_pers;
    RadioButton input_radio_comm;

    ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        name = (EditText) findViewById(R.id.src_name_input);
        tags = (EditText) findViewById(R.id.src_tags_input);
        position = (EditText) findViewById(R.id.src_position_input);

        input_radio_comm = (RadioButton) findViewById(R.id.comm_radio_src);
        input_radio_pers = (RadioButton) findViewById(R.id.pers_radio_src);

        search = (ImageView) findViewById(R.id.src_img);

        //TODO: change id and implement search logic
        View view = findViewById(R.id.list_search);
        Context context = view.getContext();
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new SearchAdapter("-1","-1","-1","-1"));


        input_radio_pers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_radio_pers.setChecked(true);
                input_radio_comm.setChecked(false);
            }
        });

        input_radio_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_radio_pers.setChecked(false);
                input_radio_comm.setChecked(true);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_in = name.getText().toString();
                String tags_in = tags.getText().toString();
                String position_in = position.getText().toString();
                String type_in = "pers";
                if(input_radio_comm.isChecked()) type_in="comm";

                if(name_in.equals("")) name_in = "-1";
                if(tags_in.equals("")) tags_in = "-1";
                if(position_in.equals("")) position_in = "-1";
                recyclerView.setAdapter(new SearchAdapter(name_in,tags_in,type_in,position_in));
            }
        });
    }

}
