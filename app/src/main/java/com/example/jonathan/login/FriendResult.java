package com.example.jonathan.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FriendResult extends AppCompatActivity {
    private ArrayList<CommentLine> addedFriendList;
    AddedListAdapter adapterA;

    String name;

    String umlPicture;
    String useremail;
    TextView txt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_result);
        addedFriendList = new ArrayList<CommentLine>();

        adapterA = new AddedListAdapter(this, addedFriendList);

        Log.d("CAT!", "CAT!  Got here!");
        try
        {
            JSONObject myJSON = new JSONObject(getIntent().getStringExtra("Chosen_friend"));
            name = myJSON.getString("name");
            umlPicture = myJSON.getString("picture");

            Log.d("CAT!", "CAT!  " + name + umlPicture);
            addedFriendList.add(new CommentLine(name, umlPicture));

        }
        catch(JSONException err)
        {
            Log.d("CAT!", "CAT!  " + err.toString());
            //can't read the JSON
        }

        adapterA.notifyDataSetChanged();

        final ListView addFriendListView = (ListView) findViewById(R.id.addedFriendListView);

        addFriendListView.setAdapter(adapterA);

    }
    private class AddedListAdapter extends BaseAdapter
    {
        private Activity activityA;
        private LayoutInflater inflaterA;
        private List<CommentLine> addFriend;
        ImageLoader imageLoader = App.getInstance().getImageLoader();

        public AddedListAdapter(Activity inActivity, List<CommentLine> inSearchItems)
        {
            activityA = inActivity;
            addFriend = inSearchItems;
        }

        @Override
        public int getCount() {
            return addFriend.size();
        }

        @Override
        public Object getItem(int location) {
            return addFriend.get(location);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (inflaterA == null)
            {
                inflaterA = (LayoutInflater) activityA.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null)
            {
                convertView = inflaterA.inflate(R.layout.searchlist_item, null);
            }

            if (imageLoader == null)
            {
                imageLoader = App.getInstance().getImageLoader();
            }
            NetworkImageView image = (NetworkImageView) convertView.findViewById(R.id.image);
            TextView friendName = (TextView) convertView.findViewById(R.id.title);
            Log.d("CAT!", "CAT!  Test");

            // getting movie data for the row
            CommentLine row = addFriend.get(position);

            // friend's image
            image.setImageUrl(row.ReceievedC, imageLoader);

            // friend's name
            friendName.setText(row.UserN);

            return convertView;
        }

    }

};