package models;

import java.util.Vector;
import java.util.Vector;

public class User {
    private String uid_;
    private String username_;
    private Vector<Reminder> basic_;
    private Vector<MonthlyReminder> monthly_;
    private Vector<WeeklyReminder> weekly_;
    private Vector<DailyReminder> daily_;
    private Vector<GeoReminder> geo_;
    private Vector<Reminder> routines_;

    public User(){
        basic_ = new Vector<Reminder>();
        monthly_ = new Vector<MonthlyReminder>();
        weekly_ = new Vector<WeeklyReminder>();
        daily_ = new Vector<DailyReminder>();
        geo_ = new Vector<GeoReminder>();
        routines_ = new Vector<Reminder>();
    };

    public User(String uid, String username){
        uid_ = uid;
        username_ = username;
        basic_ = new Vector<Reminder>();
        monthly_ = new Vector<MonthlyReminder>();
        weekly_ = new Vector<WeeklyReminder>();
        daily_ = new Vector<DailyReminder>();
        geo_ = new Vector<GeoReminder>();
        routines_ = new Vector<Reminder>();
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

    public void addBasicReminder(Reminder reminder){
        if(!basic_.contains(reminder)){
            basic_.add(reminder);
        }
    }

    public void addGeoReminder(GeoReminder reminder){
        if(!geo_.contains(reminder)){
            geo_.add(reminder);
        }
    }

    public String getUsername_() {
        return username_;
    }

    public Vector<Reminder> getBasic_() {
        return basic_;
    }

    public Vector<MonthlyReminder> getMonthly_() {
        return monthly_;
    }

    public Vector<WeeklyReminder> getWeekly_() {
        return weekly_;
    }

    public Vector<DailyReminder> getDaily_() {
        return daily_;
    }

    public Vector<GeoReminder> getGeo_() {
        return geo_;
    }

    public String getUid_() {
        return uid_;
    }

    public Vector<Reminder> getRoutines(){
        return routines_;
    }

    public void clearBasic(){
        basic_.clear();
    }

    public void clearGeo(){
        geo_.clear();
    }

    public void clearDaily(){
        daily_.clear();
    }

    public void clearWeekly(){
        weekly_.clear();
    }

    public void clearMonthly(){
        monthly_.clear();
    }

    public void updateRoutines(){
        routines_.clear();
        routines_.addAll(getDaily_());
        routines_.addAll(getWeekly_());
        routines_.addAll(getMonthly_());
    }



}
