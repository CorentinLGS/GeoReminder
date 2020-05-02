package models;

import java.util.Date;

public class DailyReminder extends Reminder{

    public DailyReminder(String uid, String title, String content, Date date, Date deadline){
        super(uid, title, content, date, deadline);
    }

    //public boolean refreshDeadline(){return true}

}
