package sh.broy.lpuhms.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.broy.lpuhms.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;
import java.util.Objects;
import sh.broy.lpuhms.Animation.LoginAnimation;
import sh.broy.lpuhms.Animation.ResizeAnimation;
import sh.broy.lpuhms.Database.DatabaseManager;
import sh.broy.lpuhms.Doctor.DocDashboardActivity;
import sh.broy.lpuhms.Patient.PatientDashboardActivity;
import sh.broy.lpuhms.Utils.Constants;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private ImageView logo;
    private LinearLayout title;
    private LoginAnimation mLoginAnimation;
    private ConstraintLayout root, logoParent;
    private TextInputEditText email, password;
    private DatabaseManager mDatabaseManager;
    private SharedPreferences mPreferences;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logo = findViewById(R.id.hms_logo);
        title = findViewById(R.id.title);
        logoParent = findViewById(R.id.logo_parent);
        root = findViewById(R.id.root);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        progressBar = findViewById(R.id.progressBar);
        final Button buttonLogin = findViewById(R.id.button_login);

        mDatabaseManager = new DatabaseManager(this);
        mPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mLoginAnimation = new LoginAnimation();

        animateLaunch();

        buttonLogin.setOnClickListener((click) -> {
            if (!TextUtils.isEmpty(Objects.requireNonNull(email.getText()).toString()) && !TextUtils.isEmpty(
                    Objects.requireNonNull(password.getText()).toString())) {
                int status = mDatabaseManager.login(email.getText().toString(), password.getText().toString(), this);

                Log.e(TAG, String.valueOf(status));

                switch (status) {
                    case -1:
                        Snackbar.make(root, "This email is not registered.", Snackbar.LENGTH_LONG)
//                                .setAction(
//                                        "REGISTER", view -> {
//
//                                        }
//                                )
                                .show();
                        break;
                    case 1:
                        Intent doc = new Intent(LoginActivity.this, DocDashboardActivity.class);
                        startActivity(doc);
                        finish();
                        break;
                    case 2:
                        Intent patient = new Intent(LoginActivity.this, PatientDashboardActivity.class);
                        startActivity(patient);
                        finish();
                        break;
                    case 0:
                        Snackbar.make(root, "Email or password is incorrect", Snackbar.LENGTH_LONG).show();
                        break;
                }
            } else {
                Snackbar.make(root, "Please enter email and password", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void animateLaunch() {

        Animation logoFadeIn = mLoginAnimation.FadeInAnimation(300);
        Animation titleFadeIn = mLoginAnimation.FadeInAnimation(700);

        logo.startAnimation(logoFadeIn);
        logo.setVisibility(View.VISIBLE);
        title.startAnimation(titleFadeIn);
        title.setVisibility(View.VISIBLE);

        String isLoggedIn = mPreferences.getString("account_type", null);


        if (isLoggedIn != null && !TextUtils.isEmpty(isLoggedIn)) {
            progressBar.setVisibility(View.VISIBLE);
            switch (isLoggedIn) {
                case "1":
                    new Handler().postDelayed(() -> {
                        Intent patient = new Intent(LoginActivity.this, DocDashboardActivity.class);
                        startActivity(patient);
                        finish();
                    }, 2000);
                    break;
                case "2":
                    new Handler().postDelayed(() -> {
                        Intent patient = new Intent(LoginActivity.this, PatientDashboardActivity.class);
                        startActivity(patient);
                        finish();
                    }, 2000);
                    break;
            }
        } else {
            // get display height
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;

            // resize layout to half of the screen
            ResizeAnimation resizeAnimation = new ResizeAnimation(
                    logoParent,
                    height / 2,
                    height
            );

            resizeAnimation.setDuration(500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    logoParent.startAnimation(resizeAnimation);
                }
            }, 1000);

        }


    }
}
