package models;

import java.util.Date;

public class WeeklyReminder extends Reminder{

    public WeeklyReminder(){}
    public WeeklyReminder(String title, String content, Date date, Date deadline){
        super(title, content, date, deadline);
    }
}
