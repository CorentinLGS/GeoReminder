package models;

import java.util.Date;

public class MonthlyReminder extends Reminder{

    public MonthlyReminder(String uid, String title, String content, Date date, Date deadline){
        super(uid, title, content, date, deadline);
    }

}
