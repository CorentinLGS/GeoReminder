package rv.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.georeminder.R;

import java.util.Vector;

import models.Reminder;

public class BasicReminderAdapter extends RecyclerView.Adapter<BasicReminderViewHolder>{
    private Vector<Reminder> reminders_;

    public BasicReminderAdapter( Vector<Reminder> reminders){
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
        }
    }

    @Override
    public int getItemCount() {
        if(reminders_!=null)
            return reminders_.size();
        else return 0;
    }


}
