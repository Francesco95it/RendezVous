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

import org.w3c.dom.Text;

import java.util.List;

import static com.unitn.francesco.rendezvous.Profile.mPROFILE;

public class My_tags extends AppCompatActivity {

    TextView mCurrTags;
    EditText mNewTags;
    Button submit_newTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tags);

        mCurrTags = (TextView) findViewById(R.id.curr_tags);
        mNewTags = (EditText) findViewById(R.id.edit_tags);
        submit_newTags = (Button) findViewById(R.id.subm_new_tags);

        List<String> current_tags = Profile.mPROFILE.getTags();

        String tags = "";
        for(String tag : current_tags){
            tags+="#"+tag+"\n";
        }
        mCurrTags.setText(tags);

        submit_newTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile.mPROFILE.setTags(TagToken.getListTags(mNewTags.getText().toString()));
                finish();
            }
        });
    }

}
