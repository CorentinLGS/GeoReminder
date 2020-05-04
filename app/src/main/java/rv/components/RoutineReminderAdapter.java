package rv.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.georeminder.R;

import java.util.Vector;

import models.DailyReminder;
import models.GeoReminder;
import models.MonthlyReminder;
import models.Reminder;

public class RoutineReminderAdapter extends RecyclerView.Adapter<BasicReminderViewHolder>{

    private Vector<Reminder> reminders_;

    public RoutineReminderAdapter( Vector<Reminder> reminders){
        reminders_ = reminders;
    }

    @NonNull
    @Override
    public BasicReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.basic_recycler, parent, false);
        BasicReminderViewHolder basicVh = new BasicReminderViewHolder(itemView);
        return basicVh;
    }

    @Override
    public void onBindViewHolder(@NonNull BasicReminderViewHolder holder, int position) {
        if(reminders_ != null) {
            Reminder reminder = reminders_.get(position);
            holder.title_vh.setText(reminder.getTitle());
            holder.date_vh.setText(reminder.getDate().toString());
            holder.text_vh.setText(reminder.getContent());
            switch (reminder.getClass().getName()){
                case "models.MonthlyReminder":
                    holder.spec_vh.setText("Monthly reminder");
                    break;
                case "models.WeeklyReminder":
                    holder.spec_vh.setText("Weekly reminder");
                    break;
                case "models.DailyReminder":
                    holder.spec_vh.setText("Daily reminder");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(reminders_!=null)
            return reminders_.size();
        else return 0;
    }
}
