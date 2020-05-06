package models;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import georeminder.RemindersFragment;
import services.GeoService;
import services.RemindersService;
import services.RoutineService;

public class DataManager {

    private User user_;
    private FirebaseFirestore db;

    private GeoService geoService;
    private Intent geoServiceIntent;
    private RemindersService remindersService;
    private Intent remindersServiceIntent;
    private RoutineService routineService;
    private Intent routineServiceIntent;

    private Context context;

    public DataManager(){
        db  = FirebaseFirestore.getInstance();
    }

    public DataManager(User user, Context context){
        user_ = user;
        this.context = context;
        db  = FirebaseFirestore.getInstance();
        initRemindersService();
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

    public boolean removeFromBase(Reminder reminder){
        switch (reminder.getClass().getName())
        {
            case "models.MonthlyReminder":
                return removeMonthlyReminder(reminder);
            case "models.WeeklyReminder":
                return removeWeeklyReminder(reminder);
            case "models.DailyReminder":
                return removeDailyReminder(reminder);
            case "models.Reminder":
                return removeBasicReminder(reminder);
            case "models.GeoReminder":
                return removeGeoReminder(reminder);
            default:
                return false;

        }
    }

    public boolean updateInBase(Reminder reminder){
        switch (reminder.getClass().getName())
        {
            case "models.MonthlyReminder":
                return updateMonthlyReminder(reminder);
            case "models.WeeklyReminder":
                return updateWeeklyReminder(reminder);
            case "models.DailyReminder":
                return updateDailyReminder(reminder);
            case "models.Reminder":
                return updateBasicReminder(reminder);
            case "models.GeoReminder":
                return updateGeoReminder(reminder);
            default:
                return false;

        }
    }

    private boolean addMonthlyReminder(Reminder reminder){
        if(reminder.getUri()!=null){
            return db.collection("monthly")
                    .document(user_.getUid_())
                    .collection("Reminders")
                    .document(reminder.getUri())
                    .set(reminder).isSuccessful();
        }
        else
        return db.collection("monthly")
                .document(user_.getUid_())
                .collection("Reminders")
                .add(reminder).isSuccessful();
    }

    private boolean addWeeklyReminder(Reminder reminder){
        if(reminder.getUri()!=null){
            return db.collection("weekly")
                    .document(user_.getUid_())
                    .collection("Reminders")
                    .document(reminder.getUri())
                    .set(reminder).isSuccessful();
        }
        else
        return db.collection("weekly")
                .document(user_.getUid_())
                .collection("Reminders")
                .add(reminder).isSuccessful();
    }

    private boolean addDailyReminder(Reminder reminder){
        if(reminder.getUri()!=null){
            return db.collection("daily")
                    .document(user_.getUid_())
                    .collection("Reminders")
                    .document(reminder.getUri())
                    .set(reminder).isSuccessful();
        }
        else
        return db.collection("daily")
                .document(user_.getUid_())
                .collection("Reminders")
                .add(reminder).isSuccessful();
    }

    private boolean addBasicReminder(Reminder reminder){
        if(reminder.getUri()!=null){
            return db.collection("basic")
                    .document(user_.getUid_())
                    .collection("Reminders")
                    .document(reminder.getUri())
                    .set(reminder).isSuccessful();
        }
        else
        return db.collection("basic")
                .document(user_.getUid_())
                .collection("Reminders")
                .add(reminder).isSuccessful();
    }

    private boolean addGeoReminder(Reminder reminder){
        if(reminder.getUri()!=null){
            return db.collection("geo")
                    .document(user_.getUid_())
                    .collection("Reminders")
                    .document(reminder.getUri())
                    .set(reminder).isSuccessful();
        }
        else
        return db.collection("geo")
                .document(user_.getUid_())
                .collection("Reminders")
                .add(reminder).isSuccessful();
    }

    private boolean removeMonthlyReminder(Reminder reminder){
            return db.collection("monthly")
                    .document(user_.getUid_())
                    .collection("Reminders")
                    .document(reminder.getUri()).delete().isSuccessful();
    }

    private boolean removeWeeklyReminder(Reminder reminder){
            return db.collection("weekly")
                    .document(user_.getUid_())
                    .collection("Reminders")
                    .document(reminder.getUri()).delete().isSuccessful();
    }

    private boolean removeDailyReminder(Reminder reminder){
            return db.collection("daily")
                    .document(user_.getUid_())
                    .collection("Reminders")
                    .document(reminder.getUri()).delete().isSuccessful();
    }

    private boolean removeBasicReminder(Reminder reminder){
            return db.collection("basic")
                    .document(user_.getUid_())
                    .collection("Reminders")
                    .document(reminder.getUri()).delete().isSuccessful();

    }

    private boolean removeGeoReminder(Reminder reminder){
            return db.collection("geo")
                    .document(user_.getUid_())
                    .collection("Reminders")
                    .document(reminder.getUri()).delete().isSuccessful();
    }

    private boolean updateMonthlyReminder(Reminder reminder){
        return db.collection("monthly")
                .document(user_.getUid_())
                .collection("Reminders")
                .document(reminder.getUri()).set(reminder).isSuccessful();
    }

    private boolean updateWeeklyReminder(Reminder reminder){
        return db.collection("weekly")
                .document(user_.getUid_())
                .collection("Reminders")
                .document(reminder.getUri()).set(reminder).isSuccessful();
    }

    private boolean updateDailyReminder(Reminder reminder){
        return db.collection("daily")
                .document(user_.getUid_())
                .collection("Reminders")
                .document(reminder.getUri()).set(reminder).isSuccessful();
    }

    private boolean updateBasicReminder(Reminder reminder){
        return db.collection("basic")
                .document(user_.getUid_())
                .collection("Reminders")
                .document(reminder.getUri()).set(reminder).isSuccessful();

    }

    private boolean updateGeoReminder(Reminder reminder){
        return db.collection("geo")
                .document(user_.getUid_())
                .collection("Reminders")
                .document(reminder.getUri()).set(reminder).isSuccessful();
    }

    public void retrieveMonthlyReminders(){

        db.collection("monthly").document(user_.getUid_()).collection("Reminders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            user_.clearMonthly();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                MonthlyReminder reminder = document.toObject(MonthlyReminder.class);
                                reminder.setUri(document.getId());
                                user_.addMonthlyReminder(reminder);
                            }
                            user_.updateRoutines();
                            RemindersFragment.basicRecyclerView.getAdapter().notifyDataSetChanged();
                        }
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
                            user_.clearWeekly();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                WeeklyReminder reminder = document.toObject(WeeklyReminder.class);
                                reminder.setUri(document.getId());
                                user_.addWeeklyReminder(reminder);
                            }
                            user_.updateRoutines();
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
                            user_.clearDaily();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                DailyReminder reminder = document.toObject(DailyReminder.class);
                                reminder.setUri(document.getId());
                                user_.addDailyReminder(reminder);
                            }
                            user_.updateRoutines();
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
                            user_.clearBasic();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Reminder reminder = document.toObject(Reminder.class);
                                reminder.setUri(document.getId());
                                user_.addBasicReminder(reminder);
                                startRemindersService(reminder);
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
                            user_.clearGeo();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                GeoReminder reminder = document.toObject(GeoReminder.class);
                                reminder.setUri(document.getId());
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

    public FirebaseFirestore getDb() {
        return db;
    }

    public void retrieveAllReminders(){
        retrieveBasicReminder();
        retrieveDailyReminder();
        retrieveGeoReminder();
        retrieveMonthlyReminders();
        retrieveWeeklyReminder();
    }


    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }

    private void initRemindersService(){
        remindersService = new RemindersService();

    }

    private void startRemindersService(Reminder reminder){
        remindersServiceIntent = new Intent(context, RemindersService.class);
        remindersServiceIntent.putExtras(reminder.takeData());
        context.startService(remindersServiceIntent);
    }
}
