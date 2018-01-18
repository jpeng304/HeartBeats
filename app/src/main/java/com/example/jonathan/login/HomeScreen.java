package com.example.jonathan.login;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

import static com.example.jonathan.login.R.id.text;
import static com.example.jonathan.login.R.id.textView;

public class HomeScreen extends AppCompatActivity {


    TextView textView5;
    String username;
    String useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);


        Intent intent = getIntent();
        username = intent.getExtras().getString("getinfo");
        useremail = intent.getExtras().getString("getemail");


        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        final Button btSearch = (Button) findViewById(R.id.btSearch);
        final ImageButton ibHome = (ImageButton) findViewById(R.id.ibHome);
        final ImageButton ibRate = (ImageButton) findViewById(R.id.ibRate);
        final ImageButton ibFriend = (ImageButton) findViewById(R.id.ibFriend);
        final ImageButton ibSetting = (ImageButton) findViewById(R.id.ibSetting);
        TextView textView5 = (TextView) findViewById(R.id.textView5);

        ibFriend.setOnClickListener( new View.OnClickListener(){

            public void onClick(View V){
                Intent intent = new Intent(HomeScreen.this, SearchFriend.class);
                startActivity(intent);
            }
        });

        ibSetting.setOnClickListener( new View.OnClickListener(){

            public void onClick(View V){
                Intent intent = new Intent(HomeScreen.this, Profile.class);
                intent.putExtra("passName", username);
                intent.putExtra("passEmail", useremail);
                startActivity(intent);
            }
        });

        ibRate.setOnClickListener( new View.OnClickListener(){

            public void onClick(View V){
                Intent intent = new Intent(HomeScreen.this, Rating.class);
                startActivity(intent);
            }
        });
        
        btSearch.setOnClickListener( new View.OnClickListener(){

            public void onClick(View V){
                final String searchString = etSearch.getText().toString();
                JsonObjectRequest myRequest = new JsonObjectRequest(
                        Request.Method.GET,                                                                 //send a get request
                        "https://api.spotify.com/v1/search?q=" + searchString + "&type=track",        //send it to this URL
                        null,                                                                               //no JSON info to submit
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response)         //when the response is called...
                            {
                                Log.d("HomeScreen: Search", "Recieved stuff back");
                                Intent intent = new Intent(HomeScreen.this, SearchResult.class);
                                intent.putExtra("SEARCH_RESULTS", response.toString());                            //pass the cache index in the intent
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)  //if there is an error...
                            {
                                Log.d("HomeScreen: Search", "Recieved error");
                            }
                        })  ;
                App.getInstance().addToRequestQueue(myRequest);   //add the request to the queue
            }
        });


        textView5.setText(username);

    }
}