package com.example.jonathan.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan on 9/23/2016.
 */

public class Rating extends Activity
{
    private String spotifyID;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState); //call super
        setContentView(R.layout.rating);    //allow us to find the UI elements

        //get the UI elements
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button postComment = (Button) findViewById(R.id.postComment);
        NetworkImageView songPic = (NetworkImageView) findViewById(R.id.imageView2);
        Intent intent = getIntent();
        try
        {
            JSONObject track = new JSONObject(intent.getExtras().getString("Chosen_Song"));
            spotifyID = track.getString("id");

            JSONObject album = track.getJSONObject("album");
            String albumName = album.getString("name");

            String artistList = "";
            JSONArray artists = track.getJSONArray("artists");
            for(int count = 0; count < artists.length(); count++)
            {
                JSONObject artist = artists.getJSONObject(count);
                artistList += artist.getString("name");
                if(count < artists.length() - 1)
                {
                    artistList += ", ";
                }
            }

            JSONObject image;
            String imageURL = "";
            JSONArray images = album.getJSONArray("images");
            if(images.length() > 0)
            {
                image = images.getJSONObject(0);
                imageURL = image.getString("url");
                ImageLoader imageLoader = App.getInstance().getImageLoader();
                songPic.setImageUrl(imageURL, imageLoader);



            }
        }
        catch(JSONException err)
        {

        }

        //add the event listeners
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                final String ratingS=String.valueOf(ratingBar.getRating());
                StringRequest myRequest = new StringRequest(
                        Request.Method.POST,
                        App.apiURL + "/song/rate",                       //send the request to our server at /song/rate
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                //response
                            }
                        },

                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                //error
                            }
                        })  {
                    @Override
                    protected Map<String, String> getParams()   //Pass a Map of parameters which will be appended to the URL
                    {
                        HashMap<String,String> myParams = new HashMap<String,String>();
                        myParams.put("idToken", App.myToken);
                        myParams.put("spotifyId", spotifyID);
                        myParams.put("rating", ratingS);
                        return myParams;
                    }
                };
                App.getInstance().addToRequestQueue(myRequest);   //add the request to the queue

            }
        });
        postComment.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View view)
            {
                String ratingS=String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(), ratingS, Toast.LENGTH_LONG).show();

            }

        });
    }
}