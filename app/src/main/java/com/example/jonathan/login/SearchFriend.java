package com.example.jonathan.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SearchFriend extends AppCompatActivity  {
    String username;
    String email;
    String umlPic;
    private ListView friendListView;
    private ArrayList<SearchLine> friendList;
    private CustomListAdapter adapter;

    EditText edSearch;
    ArrayAdapter<String> m_adapter;
    ArrayList<String> m_listItems = new ArrayList<String>();

    private static final int RC_GET_TOKEN = 9002;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_friend);

        edSearch = (EditText) findViewById(R.id.edFriendSearch);

        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    final String searchString = edSearch.getText().toString();
                    final StringRequest myRequest = new StringRequest(
                            Request.Method.POST,
                            App.apiURL + "/lookupUsers",                       //send the request to our server at /googlesiginin
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //response

                                    update(true, response);
                                }
                            },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //erro
                                    Log.d("Search: ", "Fail");
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams()   //Pass a Map of parameters which will be appended to the URL
                        {
                            HashMap<String, String> myParams = new HashMap<String, String>();
                            myParams.put("idToken", App.myToken);
                            myParams.put("searchTerm", searchString);
                            return myParams;
                        }
                    };
                    App.getInstance().addToRequestQueue(myRequest);   //add the request to the queue
                }
                return true;

            }
        });

        friendListView = (ListView) findViewById(R.id.friendListView);
        friendList = new ArrayList<SearchLine>();
        adapter = new CustomListAdapter(this, friendList);


        final StringRequest myRequest = new StringRequest(
                Request.Method.POST,
                App.apiURL + "/user/listFriends",                       //send the request to our server at /googlesiginin
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //response

                        try
                        {
                            JSONArray friendArray = new JSONArray(response);
                            for(int index = 0; index < friendArray.length(); index++)
                            {
                                JSONObject friend = friendArray.getJSONObject(index);

                                username = friend.getString("name");
                                 umlPic = friend.getString("picture");
                                String status = friend.getString("status");
                                friendList.add(new SearchLine(username, status, "", umlPic, friend.toString()));
                            }
                        }
                        catch(JSONException err)
                        {

                        }



                        adapter.notifyDataSetChanged();
                        Log.d("SearchFriend InitReq", response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error
                        Log.d("SearchFriend InitReq", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams()   //Pass a Map of parameters which will be appended to the URL
            {
                HashMap<String, String> myParams = new HashMap<String, String>();
                myParams.put("idToken", App.myToken);
                return myParams;
            }
        };
        App.getInstance().addToRequestQueue(myRequest);   //add the request to the queue




        adapter.notifyDataSetChanged();
        friendListView.setAdapter(adapter);
        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Log.d("Clicked", "searchList" + id);

                Intent intent = new Intent(SearchFriend.this, FriendResult.class);
                intent.putExtra("Chosen_friend", friendList.get(position).extraDataString);
                startActivity(intent);
                friendList.remove(position);
                adapter.notifyDataSetChanged();
            }

        });

    }




    private void update(boolean isLookUp, String response)
    {
        if (isLookUp)
        {
            Intent intent = new Intent(SearchFriend.this,FriendResult.class);

            //pass the gmail username and email
            intent.putExtra("FriendInfo", response);                            //pass the cache index in the intent
            startActivity(intent);
        }
        else
        {
            Log.d("search", "fail");
        }
    }



}