package rv.components;

import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.georeminder.R;

import georeminder.MainActivity;

public class BasicReminderViewHolder extends RecyclerView.ViewHolder {

    public TextView title_vh, text_vh, date_vh, spec_vh;

    public BasicReminderViewHolder(@NonNull View itemView) {
        super(itemView);

        title_vh = (TextView) itemView.findViewById(R.id.title_vh);
        text_vh = (TextView) itemView.findViewById(R.id.text_vh);
        date_vh = (TextView) itemView.findViewById(R.id.date_vh);
        spec_vh = (TextView) itemView.findViewById(R.id.spec);
    }
}
