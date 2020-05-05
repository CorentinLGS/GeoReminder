package models;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;

public class MonthlyReminder extends Reminder{

    private String type= "Monthly";
    public MonthlyReminder(){}

    public MonthlyReminder(String title, String content, long date, long deadline){
        super(title, content, date, deadline);
    }

    @Override
    public Bundle takeData() {
        Bundle data = super.takeData();
        data.putString("type",type);
        return data;
    }

}
