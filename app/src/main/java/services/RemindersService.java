package services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.georeminder.R;

import java.sql.Time;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import broadcast.Alarm;
import broadcast.RestarterBroadcastReceiver;
import georeminder.MainActivity;
import models.Reminder;

public class RemindersService extends IntentService {

    private CustomReminder notificationContainer;

    public RemindersService() {
        super("ReminderService");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            Reminder reminder = new Reminder();
            try {
                reminder.populate(bundle);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            notificationContainer = new CustomReminder(reminder);
            notificationContainer.setNextNotificationTime();
            if (!reminder.getDone()
                    && notificationContainer.nextNotificationTime != null)
                sendPendingNotification();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendPendingNotification(){
        Intent intent = new Intent(this, Alarm.class);
        intent.putExtras(notificationContainer.reminder.takeData());
        if(hasExistingNotification(intent)){
            PendingIntent pendingIntent = PendingIntent.getService(this, notificationContainer.reminder.getUri().hashCode(), intent,0);
            AlarmManager am = (AlarmManager)getSystemService(IntentService.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, notificationContainer.nextNotificationTime.getTime() ,pendingIntent);
        }
    }

    private boolean hasExistingNotification(Intent i){
        if(PendingIntent.getBroadcast(this, notificationContainer.reminder.getUri().hashCode(), i, 0) != null)
            return true;
        else return false;
    }

    class CustomReminder{
        public Reminder reminder;
        public Date nextNotificationTime;

        public CustomReminder(Reminder reminder){
            this.reminder = reminder;
        }

        public void setNextNotificationTime() {
            long deadline = reminder.getDeadline();
            Calendar calendar = Calendar.getInstance();
            if(deadline - calendar.getTime().getTime() >0) {
                long diff = deadline - calendar.getTime().getTime() ;
                if(diff/2 >= 300000){
                    Date test = new Date(deadline);
                    nextNotificationTime = new Date( calendar.getTime().getTime() + diff/2 );
                }
                else if (diff < 300000) nextNotificationTime =null;
                else nextNotificationTime = new Date( deadline - 300000 );
            }

        }
    }
}
/*
    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, RestarterBroadcastReceiver.class);
        this.sendBroadcast(broadcastIntent);
    }
*/