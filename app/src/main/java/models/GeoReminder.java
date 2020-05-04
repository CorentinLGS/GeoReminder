package models;

import java.util.Date;

public class GeoReminder extends Reminder{

    private String gps;

    public GeoReminder(){}
    public GeoReminder(String title, String content, Date date, String gps){
        super(title, content, date, null);
        this.gps = gps;
    }

    public String getGps() {
        return gps;
    }
}
