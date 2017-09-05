package com.unitn.francesco.rendezvous;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.unitn.francesco.rendezvous.Profile.mPROFILE;

public class PersonalInfo extends AppCompatActivity {

    EditText name;
    EditText surname;
    EditText birthday;
    EditText position;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        name = (EditText)findViewById(R.id.input_prof_name);
        surname = (EditText)findViewById(R.id.input_prof_surname);
        birthday = (EditText)findViewById(R.id.input_prof_birthday);
        position = (EditText)findViewById(R.id.input_prof_position);

        submit = (Button)findViewById(R.id.prof_submit);

        if(mPROFILE != null){
            name.setText(mPROFILE.getName());
            surname.setText(mPROFILE.getSurname());
            birthday.setText(mPROFILE.getBirthday());
            position.setText(mPROFILE.getPosition());
        } else {
            mPROFILE = new Profile();
        }


        birthday.setHint("dd/mm/yyyy");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString() != "") mPROFILE.setName(name.getText().toString());
                if (surname.getText().toString() != "") mPROFILE.setSurname(surname.getText().toString());
                if (birthday.getText().toString() != "") mPROFILE.setBirthday(birthday.getText().toString());
                if (position.getText().toString() != "") mPROFILE.setPosition(position.getText().toString());
                Toast.makeText(getBaseContext(), "Profile succesfully updated", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

}
