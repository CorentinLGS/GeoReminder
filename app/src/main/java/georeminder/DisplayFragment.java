package georeminder;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.georeminder.R;

import java.util.Date;

import models.GeoReminder;
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
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        TextView title = v.findViewById(R.id.display_title);
        title.setHeight(height*1/14);
        title.setText(reminder.getTitle());

        TextView text = v.findViewById(R.id.display_content);
        text.setHeight(height*10/14);
        text.setText(reminder.getContent());

        TextView dateView = v.findViewById(R.id.display_time);
        dateView.setWidth(width*1/2);
        Date date = new Date(reminder.getDate());
        dateView.setText(date.toString());

        TextView timeView = v.findViewById(R.id.display_date);
        timeView.setWidth(width*1/2);
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


        LinearLayout dtView = v.findViewById(R.id.display_dt_constraint);
        dtView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height*1/14));

        ImageButton validate = v.findViewById(R.id.display_validate_button);
        validate.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height*1/14));
    }

    public static DisplayFragment newInstance(Reminder reminder) {
        return (new DisplayFragment(reminder));
    }


}
