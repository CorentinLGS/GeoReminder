package models;


import android.os.Bundle;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class Reminder {

    private String title ="";
    private String content ="";
    private long date;
    private long deadline;
    private String uri;
    private boolean done;

    public Reminder(){};

    public Reminder(String title, String content, long date, long deadline){
        this.title = title;
        this.content = content;
        this.date = date;
        this.deadline = deadline;
        this.done = false;
    }

    public Reminder(String title, String content, long date, long deadline, String uri){
        this.title = title;
        this.content = content;
        this.date = date;
        this.deadline = deadline;
        this.uri = uri;
        this.done = false;
    }

    public Reminder(String title, String content, long date, long deadline, boolean done){
        this.title = title;
        this.content = content;
        this.date = date;
        this.deadline = deadline;
        this.done = done;
    }

    public Reminder(String title, String content, long date, long deadline, String uri, boolean done){
        this.title = title;
        this.content = content;
        this.date = date;
        this.deadline = deadline;
        this.uri = uri;
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getDate() {
        return date;
    }

    public long getDeadline() {
        return deadline;
    }

    public String getUri(){
        return uri;
    }

    public void setUri(String uri){
        this.uri = uri;
    }

    public Bundle takeData(){
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("text", content);
        bundle.putLong("date", date);
        bundle.putLong("deadline", deadline);
        bundle.putString("uri", uri);
        bundle.putBoolean("done", done);

        return bundle;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean getDone() {
        return done;
    }

    public void populate( Bundle bundle) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(" dd/MM/yy HH:mm");
        this.title = bundle.getString("title");
        this.content = bundle.getString("text");
        this.date = bundle.getLong("date");
        this.deadline = bundle.getLong("deadline");
        this.uri = bundle.getString("uri");
        this.done = bundle.getBoolean("done");
    }
}
