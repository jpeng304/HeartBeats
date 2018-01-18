package com.example.jonathan.login;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageLoader.ImageContainer;

import android.util.Log;

import org.json.JSONObject;

import java.util.*;
/**
 * Created by Jonathan on 9/20/2016.
 */
public class App extends Application
{
    //FOR VOLLEY SINGLETON
    public static final String TAG = "VolleyPatterns";  //Log or request TAG
    private RequestQueue mRequestQueue;                 //Request queue for Volley
    private ImageLoader mImageLoader;                   //Image Loader for Volley
    private static App sInstance;     //Static Instance - this class is not instantiated

    //FOR POST REQUESTS
    public static String myToken = "";
    public static final String apiURL = "https://www.heartbeats.ml/api";
    private static final String spotifyURL = "https://api.spotify.com/v1";

    private static HashMap<String, JSONObject> jsonCache = new HashMap<String, JSONObject>();

    //VOLLEY SINGLETON SECTION

    @Override
    public void onCreate()
    {
        super.onCreate();
        sInstance = this;   //initialize the singleton
    }

    /**
     * @return App singleton instance
     */
    public static synchronized App getInstance()
    {
        return sInstance;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)      // initialize the queue when this method is called for the first time
        {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * @return An image loader, which will be created if it is null
     */
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag)
    {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);     // set the default tag if tag is empty
        //VolleyLog.d("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);                         //stick the request on the queue to be sent out
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req)
    {
        req.setTag(TAG);                // set the tag to the default tag
        getRequestQueue().add(req);     // stick the request on the queue to be sent out
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag)
    {
        if (mRequestQueue != null)
        {
            mRequestQueue.cancelAll(tag);   //cancel all requests under a certain tag name
        }
    }
}
