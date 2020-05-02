package models;

import java.util.Date;

public class WeeklyReminder extends Reminder{

    public WeeklyReminder(String uid, String title, String content, Date date, Date deadline){
        super(uid, title, content, date, deadline);
    }
}
