package models;

import com.google.type.DayOfWeek;

import java.util.Date;

public class DailyReminder extends Reminder{

    public DailyReminder(){}
    public DailyReminder(String title, String content, Date date, Date deadline){
        super(title, content, date, deadline);
    }

    //public boolean refreshDeadline(){return true}

}
