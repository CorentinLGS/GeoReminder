package georeminder;

import android.content.Intent;
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

        TextView title = v.findViewById(R.id.display_title);
        title.setText(reminder.getTitle());

        TextView text = v.findViewById(R.id.display_content);
        text.setText(reminder.getContent());

        TextView dateView = v.findViewById(R.id.display_time);
        Date date = new Date(reminder.getDate());
        dateView.setText(date.toString());

        TextView timeView = v.findViewById(R.id.display_date);
        switch (reminder.getClass().getName()){
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

        Button edit = v.findViewById(R.id.display_edit_button);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchEdit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Button done = v.findViewById(R.id.display_done_button);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminder.setDone(true);
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

    public static DisplayFragment newInstance(Reminder reminder) {
        return (new DisplayFragment(reminder));
    }

    private void launchEdit(){
        Intent intent;
        switch (reminder.getClass().getName())
        {
            case "models.MonthlyReminder":
                intent = new Intent(getContext(), AddRoutineActivity.class);
            case "models.WeeklyReminder":
                intent = new Intent(getContext(), AddRoutineActivity.class);
            case "models.DailyReminder":
                intent = new Intent(getContext(), AddRoutineActivity.class);
            case "models.GeoReminder":
                intent = new Intent(getContext(), AddGeoActivity.class);
            default:
                intent = new Intent(getContext(), AddNoteActivity.class);
                break;
        }
        intent.putExtras(reminder.takeData());
        startActivity(intent);
    }

    private void refresh(){
        switch (reminder.getClass().getName())
        {
            case "models.MonthlyReminder":
                MainActivity.dbmanager.retrieveMonthlyReminders();
            case "models.WeeklyReminder":
                MainActivity.dbmanager.retrieveWeeklyReminder();
            case "models.DailyReminder":
                MainActivity.dbmanager.retrieveDailyReminder();
            case "models.GeoReminder":
                MainActivity.dbmanager.retrieveGeoReminder();
            default:
                MainActivity.dbmanager.retrieveBasicReminder();
                break;
        }
    }

}
