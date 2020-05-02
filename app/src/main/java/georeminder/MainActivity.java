package georeminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.georeminder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Date;

import models.DataManager;
import models.Reminder;
import models.User;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser fUser;
    private User user ;
    private DataManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        user = new User(fUser.getUid(), fUser.getDisplayName());



        dbmanager = new DataManager(user);

    }
}
