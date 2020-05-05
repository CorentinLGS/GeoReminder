package rv.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.georeminder.R;

import java.util.Date;
import java.util.Vector;

import georeminder.MainActivity;
import models.DailyReminder;
import models.GeoReminder;
import models.MonthlyReminder;
import models.Reminder;

public class RoutineReminderAdapter extends RecyclerView.Adapter<BasicReminderViewHolder>{

    private Vector<Reminder> reminders_;
    private Context context;

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
            final Reminder reminder = reminders_.get(position);
            holder.title_vh.setText(reminder.getTitle());
            Date date = new Date(reminder.getDate());
            holder.date_vh.setText(date.toString());
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
            holder.text_vh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity context1 = (MainActivity) context;
                    context1.showDisplayFragment(reminder);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(reminders_!=null)
            return reminders_.size();
        else return 0;
    }

    public void addContext(Context context){
        this.context = context;
    }
}
