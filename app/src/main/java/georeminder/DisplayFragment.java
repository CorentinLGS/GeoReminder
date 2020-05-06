package georeminder;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.georeminder.R;

import java.util.Date;

import models.GeoReminder;
import models.MonthlyReminder;
import models.Reminder;

public class DisplayFragment extends Fragment {

    private View v;
    Reminder reminder;

    public DisplayFragment(){}
    public DisplayFragment(Reminder reminder){
        this.reminder = reminder;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)          {
        v = inflater.inflate(R.layout.fragment_display, container, false);
        initView();
        return v;
    }

    private void initView(){


        if(reminder != null) {
            TextView title = v.findViewById(R.id.display_title);
            title.setText(reminder.getTitle());

            TextView text = v.findViewById(R.id.display_content);
            text.setText(reminder.getContent());

            TextView dateView = v.findViewById(R.id.display_time);
            Date date = new Date(reminder.getDate());
            dateView.setText(date.toString());

            TextView timeView = v.findViewById(R.id.display_date);
            switch (reminder.getClass().getName()) {
                case "models.MonthlyReminder":
                    timeView.setText("Monthly");
                    break;
                case "models.WeeklyReminder":
                    timeView.setText("Weekly");
                    break;
                case "models.DailyReminder":
                    timeView.setText("Daily");
                    break;
                case "models.Reminder":
                    Date deadline = new Date(reminder.getDeadline());
                    timeView.setText(deadline.toString());
                    break;
                case "models.GeoReminder":
                    GeoReminder geo = (GeoReminder) reminder;
                    timeView.setText(geo.getGps());
                default:
                    break;
            }
            final ConstraintLayout constraintLayout = v.findViewById(R.id.display_constaint);
            if(reminder.getDone()) constraintLayout.setBackgroundColor(Color.parseColor("#005000"));

            Button edit = v.findViewById(R.id.display_edit_button);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchEdit();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });

            final  Button done = v.findViewById(R.id.display_done_button);
            if(reminder.getDone()) done.setText("Mark as not done");
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(reminder.getDone()){
                        reminder.setDone(false);
                        constraintLayout.setBackgroundColor(Color.parseColor("#73000000"));
                        done.setText("Mark as done");
                    }
                    else{
                        reminder.setDone(true);
                        constraintLayout.setBackgroundColor(Color.parseColor("#005000"));
                        done.setText("Mark as not done");
                    }
                    MainActivity.dbmanager.updateInBase(reminder);
                    refresh();
                }
            });

            Button delete = v.findViewById(R.id.display_delete_button);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.dbmanager.removeFromBase(reminder);
                    refresh();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }
    }

    public static DisplayFragment newInstance(Reminder reminder) {
        return (new DisplayFragment(reminder));
    }

    private void launchEdit(){
        Intent intent=null;
        switch (reminder.getClass().getName())
        {
            case "models.MonthlyReminder":
                intent = new Intent(getContext(), AddRoutineActivity.class);
                break;
            case "models.WeeklyReminder":
                intent = new Intent(getContext(), AddRoutineActivity.class);
                break;
            case "models.DailyReminder":
                intent = new Intent(getContext(), AddRoutineActivity.class);
                break;
            case "models.GeoReminder":
                intent = new Intent(getContext(), AddGeoActivity.class);
                break;
            case "models.Reminder":
                intent = new Intent(getContext(), AddNoteActivity.class);
                break;
            default:
                break;
        }
        if(intent!=null) {
            intent.putExtras(reminder.takeData());
            startActivity(intent);
        }
    }

    private void refresh(){
        switch (reminder.getClass().getName())
        {
            case "models.MonthlyReminder":
                MainActivity.dbmanager.retrieveMonthlyReminders();
                break;
            case "models.WeeklyReminder":
                MainActivity.dbmanager.retrieveWeeklyReminder();
                break;
            case "models.DailyReminder":
                MainActivity.dbmanager.retrieveDailyReminder();
                break;
            case "models.GeoReminder":
                MainActivity.dbmanager.retrieveGeoReminder();
                break;
            default:
                MainActivity.dbmanager.retrieveBasicReminder();
                break;
        }
    }

}
