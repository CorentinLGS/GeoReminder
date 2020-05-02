package models;

import java.util.Date;

public class GeoReminder extends Reminder{

    private String gps;

    public GeoReminder(String uid, String title, String content, Date date, Date deadline, String gps){
        super(uid, title, content, date, deadline);
        this.gps = gps;
    }

    public String getGps() {
        return gps;
    }
}
