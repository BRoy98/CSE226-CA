package sh.broy.lpuhms.Patient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.broy.lpuhms.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.Objects;
import sh.broy.lpuhms.Activities.LoginActivity;
import sh.broy.lpuhms.Adapters.DoctorAdapter;
import sh.broy.lpuhms.Database.DatabaseManager;
import sh.broy.lpuhms.Models.Appointment;
import sh.broy.lpuhms.Models.Doctor;
import sh.broy.lpuhms.Utils.Constants;
import sh.broy.lpuhms.Utils.ListOnClick;

public class PatientDashboardActivity extends AppCompatActivity implements ListOnClick {

    private static final String TAG = "PatientDashboard";
    private Toolbar toolbar;
    private TextView appoCount;
    private SharedPreferences mPreferences;
    private DatabaseManager mDatabaseManager;
    RecyclerView appoListView;
    Spinner timeslot;
    TextInputEditText docname, issue;
    Button closeSheet, addAppo;

    LinearLayout layoutBottomSheet;
    BottomSheetBehavior<LinearLayout> sheetBehavior;
    FloatingActionButton addButton;

    String doctor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        toolbar = findViewById(R.id.toolbar);
        appoListView = findViewById(R.id.appo_list);
        appoCount = findViewById(R.id.pAppoCount);
        addButton = findViewById(R.id.addButton);
        timeslot = findViewById(R.id.timeslot);
        docname = findViewById(R.id.docname);
        addAppo = findViewById(R.id.addAppo);
        closeSheet = findViewById(R.id.closeSheet);
        issue = findViewById(R.id.issue);
        layoutBottomSheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        String[] times = new String[]{"10:00 AM", "11:00 AM", "12:00 PM", "05:00 PM", "06:00 PM", "07:00 PM"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, times);
        timeslot.setAdapter(arrayAdapter);

        mDatabaseManager = new DatabaseManager(this);
        mPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        setupToolbar();
        updateData();

        ArrayList<Doctor> docList = mDatabaseManager
                .getDoctors();

        DoctorAdapter doctorAdapter = new DoctorAdapter(this, docList, this);
        appoListView.setLayoutManager(new LinearLayoutManager(this));
        appoListView.setAdapter(doctorAdapter);

        addButton.setOnClickListener(view -> {
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                btnBottomSheet.setText("Close sheet");
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                btnBottomSheet.setText("Expand sheet");
            }
        });

        addAppo.setOnClickListener(view -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(issue.getText()).toString())) {
                Toast.makeText(this, "Please write your issue", Toast.LENGTH_SHORT).show();
            } else {
                mDatabaseManager.createAppointment(mPreferences.getString("name", "Patient Dashboard"), doctor,
                        timeslot.getSelectedItem().toString(), issue.getText().toString());
            }

            issue.setText("");
            issue.clearFocus();
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            Toast.makeText(this, "Appointment with " + doctor + " booked successfully!", Toast.LENGTH_LONG).show();
            updateData();



//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            assert imm != null;
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        });

        closeSheet.setOnClickListener(view -> {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
    }

    @Override
    public void onClick(String[] data) {
        docname.setText(data[0]);
        docname.setEnabled(false);
        doctor = data[0];
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    public void updateData() {
        ArrayList<Appointment> appoList = mDatabaseManager
                .getAppointments(mPreferences.getString("name", "Doctor Dashboard"), 2);
        Log.e(TAG, "" + appoList.size());
        appoCount.setText(String.valueOf(appoList.size()));
    }

    private void setupToolbar() {
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle(mPreferences.getString("name", "Patient Dashboard"));
        setSupportActionBar(toolbar);

        //logout on menu click
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_logout) {
                mDatabaseManager.logout(PatientDashboardActivity.this);
                Intent doc = new Intent(PatientDashboardActivity.this, LoginActivity.class);
                startActivity(doc);
                finish();
            }
            return false;
        });
    }


}

