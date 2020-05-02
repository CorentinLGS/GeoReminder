package models;

import java.util.List;

public class User {
    private String uid_;
    private String username_;
    private List<Reminder> basic_;
    private List<Reminder> monthly_;
    private List<Reminder> weekly_;
    private List<Reminder> daily_;

    public User(){};

    public User(String uid, String username){

        uid_ = uid;
        username_ = username;
    }

    public void addMonthlyReminder( MonthlyReminder reminder){
        if(!monthly_.contains(reminder)){
            monthly_.add(reminder);
        }
    }

    public void addWeeklyReminder( WeeklyReminder reminder){
        if(!weekly_.contains(reminder)){
            weekly_.add(reminder);
        }
    }

    public void addDailyReminder( DailyReminder reminder){
        if(!daily_.contains(reminder)){
            daily_.add(reminder);
        }
    }

    public void addBasicReminder( Reminder reminder){
        if(!basic_.contains(reminder)){
            basic_.add(reminder);
        }
    }

    public String getUid_() {
        return uid_;
    }
}
