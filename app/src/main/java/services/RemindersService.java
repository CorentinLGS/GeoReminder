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

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            startMyOwnForeground();
        }

        else {
            startForeground(1, new Notification());
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground()
    {
        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("Content")
                .build();
        startForeground(2, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendPendingNotification(){
        Intent intent = new Intent(getBaseContext(), Alarm.class);
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
            if(deadline- calendar.getTime().getTime() <0) {
                long diff = calendar.getTime().getTime() - deadline;
                if(diff/2 >= 600000){
                    nextNotificationTime = new Date( calendar.getTime().getTime() + diff/2 );
                }
                else if (diff < 60000) nextNotificationTime =null;
                else nextNotificationTime = new Date( deadline - 60000 );
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