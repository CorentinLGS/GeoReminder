package models;

import android.os.Bundle;

import com.google.type.DayOfWeek;

import java.util.ArrayList;
import java.util.Date;

public class DailyReminder extends Reminder{

    private String type= "Daily";

    public DailyReminder(){}

    public DailyReminder(String title, String content, long date, long deadline){
        super(title, content, date, deadline);
    }

    @Override
    public Bundle takeData() {
        Bundle data = super.takeData();
        data.putString("type",type);
        return data;
    }

}
