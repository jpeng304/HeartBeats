package com.example.jonathan.login;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jonathan on 10/25/2016.
 */

public class CommentLine {

    public String date;
    public String time;
    public String UserN;
    public String ReceievedC;

    public CommentLine(String name, String comments)
    {
        Date postedAt = new Date();
        final SimpleDateFormat simpDate = new SimpleDateFormat("MM.dd.yyyy");
        final SimpleDateFormat simpTime = new SimpleDateFormat("HH:mm:ss");
        date = simpDate.format(postedAt);;
        time = simpTime.format(postedAt);;
        UserN = name;
        ReceievedC = comments;
    }
}