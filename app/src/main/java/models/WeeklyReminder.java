package models;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;

public class WeeklyReminder extends Reminder{

    private String type= "Weekly";

    public WeeklyReminder(){}
    public WeeklyReminder(String title, String content, long date, long deadline){
        super(title, content, date, deadline);
    }

    @Override
    public Bundle takeData() {
        Bundle data = super.takeData();
        data.putString("type",type);
        return data;
    }
}
