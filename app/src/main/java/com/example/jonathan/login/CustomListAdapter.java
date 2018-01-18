package com.example.jonathan.login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by micha on 11/7/2016.
 */

public class CustomListAdapter extends BaseAdapter {
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
