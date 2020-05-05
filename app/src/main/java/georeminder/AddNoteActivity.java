package georeminder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.annotation.Nullable;

import com.example.georeminder.R;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import models.DataManager;
import models.Reminder;

public class AddNoteActivity extends Activity {

    private EditText title;
    private EditText text;
    private EditText dateView;
    private EditText timeview;
    private Calendar calendar;
    private ImageButton validate;
    private ImageButton cancel;
    private LinearLayout vcView;
    private LinearLayout dtView;
    private DatePickerDialog.OnDateSetListener date;
    private TimePickerDialog.OnTimeSetListener time;
    private String uri;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        title = findViewById(R.id.basic_title);
        text = findViewById(R.id.basic_content);
        dateView = findViewById(R.id.basic_date);
        timeview = findViewById(R.id.basic_time);
        calendar = new GregorianCalendar();
        validate = findViewById(R.id.validate_button);
        cancel = findViewById(R.id.cancel_button);
        dtView = findViewById(R.id.dt_constraint);
        vcView = findViewById(R.id.vc_constraint);

        initDate();
        initTime();
        initButtons();
        if(getIntent().getExtras()!=null) {
            Bundle data = getIntent().getExtras();
            title.setText(data.getString("title"));
            text.setText(data.getString("text"));
            uri = data.getString("uri");

            DateFormat f = new SimpleDateFormat("dd/MM/yy hh:mm");
            Date d = new Date(data.getLong("deadline"));
            DateFormat date = new SimpleDateFormat("dd/MM/yy");
            DateFormat time = new SimpleDateFormat("hh:mm");
            if (d != null) {
                dateView.setText(date.format(d));
                timeview.setText(time.format(d));
            }
        }

    }



    private void updateDateLabel(){
        String myFormat = "dd/MM/YY";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        dateView.setText(sdf.format(calendar.getTime()));
    }

    private void updateTimeLabel(){
        String myFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        timeview.setText(sdf.format(calendar.getTime().getTime()));
    }

    private void initDate(){
        date= new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }

        };

        dateView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddNoteActivity.this, date, calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void initTime(){
        time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateTimeLabel();
            }
        };

        timeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddNoteActivity.this, time, calendar.get(Calendar.HOUR_OF_DAY)
                        , calendar.get(Calendar.MINUTE), true).show();
            }
        });
    }

    private void initButtons(){
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDate = dateView.getText().toString() + " " + timeview.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm");
                java.util.Date date = null;
                try {
                    date = format.parse(sDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                Reminder reminder = new Reminder(title.getText().toString(), text.getText().toString(), c.getTime().getTime(), date.getTime());
                reminder.setUri(uri);
                MainActivity.dbmanager.addDataToBase(reminder);
                MainActivity.dbmanager.retrieveBasicReminder();
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
