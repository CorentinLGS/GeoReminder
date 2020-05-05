package models;

import android.os.Bundle;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class GeoReminder extends Reminder{

    private String gps;

    public GeoReminder(){}
    public GeoReminder(String title, String content, long date, String gps){
        super(title, content, date, 0);
        this.gps = gps;
    }

    @Override
    public Bundle takeData() {
        Bundle bundle = super.takeData();
        bundle.putString("gps", gps);
        return bundle;
    }

    @Override
    public void populate(Bundle bundle) throws ParseException {
        super.populate(bundle);
        gps = bundle.getString("gps");
    }

    public String getGps() {
        return gps;
    }
}
