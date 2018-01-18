package com.example.jonathan.login;

/**
 * Created by micha on 10/17/2016.
 */

import java.util.ArrayList;

public class SearchLine {
    public String title;
    public String description;
    public String description2;
    public String picURL;
    public String extraDataString;

    public SearchLine(String name, String inPic){
        title = name;
        picURL = inPic;
    }
    public SearchLine(String inTitle, String inDescription, String inDescription2, String inPicURL, String inExtraDataString)
    {
        title = inTitle;
        description = inDescription;
        description2 = inDescription2;
        picURL = inPicURL;
        extraDataString = inExtraDataString;
    }
}
