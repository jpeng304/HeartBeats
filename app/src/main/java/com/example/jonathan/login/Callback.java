package com.example.jonathan.login;

import org.json.JSONObject;
import android.graphics.Bitmap;

/**
 * Created by micha on 9/21/2016.
 */
public class Callback
{
    void response(String result)
    {
        //it does nothing if this method is not implimented
    }
    void response(JSONObject result)
    {
        //it does nothing if this method is not implimented
    }

    void response(Bitmap result)
    {
        /*
        This does nothing until the method is implemented, but it's supposed to look like this:

        @Override
        public void response(Bitmap result)
        {
            //assuming imageView is an imageView object
            imageView.setImageBitmap(response.getBitmap());
        }
        */
    }
}
