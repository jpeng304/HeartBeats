package com.example.jonathan.login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Jonathan on 9/11/2016.
 */
public class Profile extends Activity {

    TextView lblName;
    TextView lblEmail;
    String user;
    String email;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        //receive username and email
        Intent intent = getIntent();
        user = intent.getExtras().getString("passName");
        email = intent.getExtras().getString("passEmail");

        lblName = (TextView) findViewById(R.id.lblName);
        lblEmail = (TextView) findViewById(R.id.lblEmail);

        lblName.setText("Name:" + user);
        lblEmail.setText("Email:" + email);

        final RadioButton byAll = (RadioButton) findViewById(R.id.rbByAll);
        final RadioButton byEmail = (RadioButton) findViewById(R.id.rbByEmail);
        final RadioButton byNone = (RadioButton) findViewById(R.id.rdByNone);

        final Button save = (Button) findViewById(R.id.btSave);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (byAll.isChecked()) {

                }
                else if (byEmail.isChecked()) {

                }
                else if (byNone.isChecked()) {

                }
            }
        });

    }




}