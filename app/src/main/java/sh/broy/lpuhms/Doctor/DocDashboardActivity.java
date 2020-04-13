package sh.broy.lpuhms.Doctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.broy.lpuhms.R;
import java.util.ArrayList;
import sh.broy.lpuhms.Activities.LoginActivity;
import sh.broy.lpuhms.Adapters.AppointmentAdapter;
import sh.broy.lpuhms.Database.DatabaseManager;
import sh.broy.lpuhms.Models.Appointment;
import sh.broy.lpuhms.Utils.Constants;

public class DocDashboardActivity extends AppCompatActivity {

    private static final String TAG = "DocDashboardActivity";
    private Toolbar toolbar;
    private TextView appoCount;
    private SharedPreferences mPreferences;
    private DatabaseManager mDatabaseManager;
    RecyclerView appoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_dashboard);

        toolbar = findViewById(R.id.toolbar);
        appoListView = findViewById(R.id.appo_list);
        appoCount = findViewById(R.id.pAppoCount);

        mDatabaseManager = new DatabaseManager(this);
        mPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        // Setup toolbar
        setupToolbar();

        ArrayList<Appointment> appoList = mDatabaseManager
                .getAppointments(mPreferences.getString("name", "Doctor Dashboard"), 1);

        appoCount.setText(String.valueOf(appoList.size()));
        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(this, appoList, true);
        appoListView.setLayoutManager(new LinearLayoutManager(this));
        appoListView.setAdapter(appointmentAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    private void setupToolbar() {
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setSubtitleTextColor(Color.argb(150, 255, 255, 255));
        toolbar.setTitle(mPreferences.getString("name", "Doctor Dashboard"));
        toolbar.setSubtitle(mPreferences.getString("specialization", "Dentist"));
        setSupportActionBar(toolbar);

        //logout on menu click
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_logout) {
                mDatabaseManager.logout(DocDashboardActivity.this);
                Intent doc = new Intent(DocDashboardActivity.this, LoginActivity.class);
                startActivity(doc);
                finish();
            }
            return false;
        });
    }
}
