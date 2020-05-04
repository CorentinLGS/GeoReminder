package georeminder;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.georeminder.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import models.DailyReminder;
import models.GeoReminder;
import models.MonthlyReminder;
import models.WeeklyReminder;

public class AddRoutineActivity extends Activity {

    private EditText title;
    private EditText text;
    private ImageButton validate;
    private ImageButton cancel;
    private LinearLayout dtView;
    private LinearLayout vcView;
    private CheckBox dailyCB, weeklyCB, monthlyCB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addroutine);

        title = findViewById(R.id.routine_title);
        text = findViewById(R.id.routine_content);
        validate = findViewById(R.id.validate_button_routine);
        cancel = findViewById(R.id.cancel_button_routine);
        vcView = findViewById(R.id.vc_constraint_routine);
        dtView = findViewById(R.id.dt_constraint_routine);
        dailyCB = findViewById(R.id.daily_routine_check);
        weeklyCB = findViewById(R.id.weekly_routine_check);
        monthlyCB = findViewById(R.id.monthly_routine_check);

        initViews();
        initCheckBoxes();
        initButtons();
    }

    private void initViews() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        title.setHeight(height * 1 / 14);
        text.setHeight(height * 10 / 14);
        vcView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height * 1 / 14));
        dtView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height * 1 / 14));
        validate.setLayoutParams(new LinearLayout.LayoutParams(width * 1 / 2, height * 1 / 14));
        cancel.setLayoutParams(new LinearLayout.LayoutParams(width * 1 / 2, height * 1 / 14));
    }

    private void initCheckBoxes(){
        dailyCB.setChecked(true);
        dailyCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    weeklyCB.setChecked(false);
                    monthlyCB.setChecked(false);
                }
            }
        });

        weeklyCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dailyCB.setChecked(false);
                    monthlyCB.setChecked(false);
                }
            }
        });

        monthlyCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dailyCB.setChecked(false);
                    weeklyCB.setChecked(false);
                }
            }
        });
    }

    private void initButtons(){
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Date currentTime = c.getTime();
                SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yy");
                Date deadline = null;
                String deadHour = "23:59:59";

                if(dailyCB.isChecked()){
                    String dLine = dFormat.format(currentTime) +" "+ deadHour;
                    SimpleDateFormat fFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    try {
                        deadline = fFormat.parse(dLine);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    DailyReminder reminder = new DailyReminder(title.getText().toString(), text.getText().toString(), currentTime, deadline);
                    MainActivity.dbmanager.addDataToBase(reminder);
                    MainActivity.dbmanager.retrieveDailyReminder();
                }

                else if(weeklyCB.isChecked()){
                    c.set(Calendar.DAY_OF_WEEK, 7);
                    Date lastDay = c.getTime();
                    String dLine = dFormat.format(lastDay) +" "+ deadHour;
                    SimpleDateFormat fFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    try {
                        deadline = fFormat.parse(dLine);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    WeeklyReminder reminder = new WeeklyReminder(title.getText().toString(), text.getText().toString(),currentTime, deadline);
                    MainActivity.dbmanager.addDataToBase(reminder);
                    MainActivity.dbmanager.retrieveWeeklyReminder();
                }

                else if(monthlyCB.isChecked()){
                    int lastDayMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
                    c.set(Calendar.DAY_OF_MONTH, lastDayMonth);
                    Date lastDay = c.getTime();
                    String dLine = dFormat.format(lastDay) +" "+ deadHour;
                    SimpleDateFormat fFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    try {
                        deadline = fFormat.parse(dLine);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    MonthlyReminder reminder = new MonthlyReminder(title.getText().toString(), text.getText().toString(), currentTime, deadline);
                    MainActivity.dbmanager.addDataToBase(reminder);
                    MainActivity.dbmanager.retrieveMonthlyReminders();
                }

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
