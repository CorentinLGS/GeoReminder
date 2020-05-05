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
import models.Reminder;

public class BasicReminderAdapter extends RecyclerView.Adapter<BasicReminderViewHolder>{
    private Vector<Reminder> reminders_;
    private Context context;
    private Reminder currentReminder;

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity context1 = (MainActivity) context;
            context1.showDisplayFragment(currentReminder);
        }
    };

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
            currentReminder = reminders_.get(position);
            holder.title_vh.setText(currentReminder.getTitle());
            holder.title_vh.setOnClickListener(onClickListener);
            Date date = new Date(currentReminder.getDate());
            holder.date_vh.setText(date.toString());
            holder.date_vh.setOnClickListener(onClickListener);
            holder.text_vh.setText(currentReminder.getContent());
            holder.text_vh.setOnClickListener(onClickListener);
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
