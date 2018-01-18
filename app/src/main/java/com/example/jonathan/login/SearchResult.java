package com.example.jonathan.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Created by Giuliana on 10/13/16.
 */

public class SearchResult extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);         //call super
        setContentView(R.layout.search_result);    //allow us to access the UI elements

        ArrayList<SearchLine> searchLines = new ArrayList<SearchLine>();

        //SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), myList, R.layout.searchlist_item, keys, views);
        CustomListAdapter adapter = new CustomListAdapter(this, searchLines);



        Log.d("CAT!", "CAT!  Got here!");
        try
        {
            JSONObject myJSON = new JSONObject(getIntent().getStringExtra("SEARCH_RESULTS"));
            JSONArray tracks = myJSON.getJSONObject("tracks").getJSONArray("items");
            for(int index = 0; index < tracks.length(); index++)
            {
                JSONObject track = tracks.getJSONObject(index);
                String trackName = track.getString("name");
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
                }

                Log.d("CAT!", "CAT!  " + trackName + albumName + artistList + imageURL);
                searchLines.add(new SearchLine(trackName, albumName, artistList, imageURL));
            }
            /*
            JSONArray albums = myJSON.getJSONObject("albums").getJSONArray("items");
            for(int index = 0; index < albums.length(); index++)
            {
                JSONObject album = albums.getJSONObject(index);
                String name = album.getString("name");
                JSONArray images = album.getJSONArray("images");
                if(images.length() > 0)
                {
                    JSONObject image = images.getJSONObject(0);
                    String url = image.getString("url");
                    searchLines.add(new SearchLine(name, "", url));
                    Log.d("Cats!", "WE GOT " + name + " AND " + url);
                }

            }*/
        }
        catch(JSONException err)
        {
            Log.d("CAT!", "CAT!  " + err.toString());
            //can't read the JSON
        }




        //let the adapter know that the data has changed
        adapter.notifyDataSetChanged();

        //get the UI elements we want
        ListView searchList = (ListView) findViewById(R.id.searchList);

        //use the adapter we created
        searchList.setAdapter(adapter);

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Log.d("Clicked", "searchList");
            }

        });
    }



    private class CustomListAdapter extends BaseAdapter
    {
        private Activity activity;
        private LayoutInflater inflater;
        private List<SearchLine> searchItems;
        ImageLoader imageLoader = App.getInstance().getImageLoader();

        public CustomListAdapter(Activity inActivity, List<SearchLine> inSearchItems)
        {
            activity = inActivity;
            searchItems = inSearchItems;
        }

        @Override
        public int getCount() {
            return searchItems.size();
        }

        @Override
        public Object getItem(int location) {
            return searchItems.get(location);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (inflater == null)
            {
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null)
            {
                convertView = inflater.inflate(R.layout.searchlist_item, null);
            }

            if (imageLoader == null)
            {
                imageLoader = App.getInstance().getImageLoader();
            }
            NetworkImageView image = (NetworkImageView) convertView.findViewById(R.id.image);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            TextView description2 = (TextView) convertView.findViewById(R.id.description2);

            Log.d("CAT!", "CAT!  Test");

            // getting movie data for the row
            SearchLine row = searchItems.get(position);

            // image
            image.setImageUrl(row.picURL, imageLoader);

            // title
            title.setText(row.title);

            // description
            description.setText(row.description);

            // description 2
            description2.setText(row.description2);

            return convertView;
        }

    }




    /*
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
    */
}