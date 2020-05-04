package models;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import georeminder.MainActivity;
import georeminder.RemindersFragment;

public class DataManager {

    private User user_;
    FirebaseFirestore db;

    public DataManager(){
        db  = FirebaseFirestore.getInstance();
    }

    public DataManager(User user){
        user_ = user;
        db  = FirebaseFirestore.getInstance();
    }

    public boolean addDataToBase(Reminder reminder){
        switch (reminder.getClass().getName())
        {
            case "models.MonthlyReminder":
                return addMonthlyReminder(reminder);
            case "models.WeeklyReminder":
                return addWeeklyReminder(reminder);
            case "models.DailyReminder":
                return addDailyReminder(reminder);
            case "models.Reminder":
                return addBasicReminder(reminder);
            case "models.GeoReminder":
                return addGeoReminder(reminder);
            default:
                return false;

        }
    }

    private boolean addMonthlyReminder(Reminder reminder){
        return db.collection("monthly")
                .document(user_.getUid_())
                .collection("Reminders")
                .add(reminder).isSuccessful();
    }

    private boolean addWeeklyReminder(Reminder reminder){
        return db.collection("weekly")
                .document(user_.getUid_())
                .collection("Reminders")
                .add(reminder).isSuccessful();
    }

    private boolean addDailyReminder(Reminder reminder){
        return db.collection("daily")
                .document(user_.getUid_())
                .collection("Reminders")
                .add(reminder).isSuccessful();
    }

    private boolean addBasicReminder(Reminder reminder){
        return db.collection("basic")
                .document(user_.getUid_())
                .collection("Reminders")
                .add(reminder).isSuccessful();
    }

    private boolean addGeoReminder(Reminder reminder){
        return db.collection("geo")
                .document(user_.getUid_())
                .collection("Reminders")
                .add(reminder).isSuccessful();
    }

    public void retrieveMonthlyReminders(){

        db.collection("monthly").document(user_.getUid_()).collection("Reminders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                MonthlyReminder reminder = document.toObject(MonthlyReminder.class);
                                user_.addMonthlyReminder(reminder);
                            }
                        }
                        RemindersFragment.basicRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    public void retrieveWeeklyReminder(){

        db.collection("weekly").document(user_.getUid_()).collection("Reminders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                WeeklyReminder reminder = document.toObject(WeeklyReminder.class);
                                user_.addWeeklyReminder(reminder);
                            }
                            RemindersFragment.basicRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
    }

    public void retrieveDailyReminder(){

        db.collection("daily").document(user_.getUid_()).collection("Reminders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                DailyReminder reminder = document.toObject(DailyReminder.class);
                                user_.addDailyReminder(reminder);
                            }
                            RemindersFragment.basicRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
    }

    public void retrieveBasicReminder(){

        db.collection("basic").document(user_.getUid_()).collection("Reminders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Reminder reminder = document.toObject(Reminder.class);
                                user_.addBasicReminder(reminder);
                            }
                            RemindersFragment.basicRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
    }

    public void retrieveGeoReminder(){

        db.collection("geo").document(user_.getUid_()).collection("Reminders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                GeoReminder reminder = document.toObject(GeoReminder.class);
                                user_.addGeoReminder(reminder);
                            }
                            RemindersFragment.basicRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
    }

    public User getUser_() {
        return user_;
    }

    public void retrieveAllReminders(){
        retrieveBasicReminder();
        retrieveDailyReminder();
        retrieveGeoReminder();
        retrieveMonthlyReminders();
        retrieveWeeklyReminder();
    }
}
