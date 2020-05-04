package models;

import java.util.Date;

public class MonthlyReminder extends Reminder{

    public MonthlyReminder(){}
    public MonthlyReminder(String title, String content, Date date, Date deadline){
        super(title, content, date, deadline);
    }

}
