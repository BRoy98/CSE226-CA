package sh.broy.lpuhms.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import sh.broy.lpuhms.Models.Appointment;
import sh.broy.lpuhms.Models.Doctor;
import sh.broy.lpuhms.Utils.Constants;

import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseManager";

    public DatabaseManager(Context context) {
        super(context, Constants.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.QUERY_CREATE_TABLE_PATIENT);
        db.execSQL(Constants.QUERY_CREATE_TABLE_DOCTOR);
        db.execSQL(Constants.QUERY_CREATE_TABLE_APPOINTMENT);

        ContentValues doc1 = new ContentValues();
        doc1.put(Constants.DOCTOR_NAME, "Dr. Amit Guha");
        doc1.put(Constants.DOCTOR_EMAIL, "doc1@lpu.in");
        doc1.put(Constants.DOCTOR_PASSWORD, "12345");
        doc1.put(Constants.DOCTOR_SPCL, "Dentist");

        ContentValues doc2 = new ContentValues();
        doc2.put(Constants.DOCTOR_NAME, "Dr. Rajesh Mishra");
        doc2.put(Constants.DOCTOR_EMAIL, "doc2@lpu.in");
        doc2.put(Constants.DOCTOR_PASSWORD, "12345");
        doc2.put(Constants.DOCTOR_SPCL, "Neurologist");

        ContentValues doc3 = new ContentValues();
        doc3.put(Constants.DOCTOR_NAME, "Dr. Aman Singh");
        doc3.put(Constants.DOCTOR_EMAIL, "doc3@lpu.in");
        doc3.put(Constants.DOCTOR_PASSWORD, "12345");
        doc3.put(Constants.DOCTOR_SPCL, "Radiologist");

        db.insert(Constants.DOCTOR_TABLE, null, doc1);
        db.insert(Constants.DOCTOR_TABLE, null, doc2);
        db.insert(Constants.DOCTOR_TABLE, null, doc3);

        // Create patient profiles
        ContentValues patient1 = new ContentValues();
        patient1.put(Constants.PATIENT_NAME, "Amal Kumar");
        patient1.put(Constants.PATIENT_EMAIL, "patient1@lpu.in");
        patient1.put(Constants.PATIENT_PASSWORD, "12345");

        ContentValues patient2 = new ContentValues();
        patient2.put(Constants.PATIENT_NAME, "Bishnu Das");
        patient2.put(Constants.PATIENT_EMAIL, "patient2@lpu.in");
        patient2.put(Constants.PATIENT_PASSWORD, "12345");

        db.insert(Constants.PATIENT_TABLE, null, patient1);
        db.insert(Constants.PATIENT_TABLE, null, patient2);

        // Add appointments
        ContentValues appo1 = new ContentValues();
        appo1.put(Constants.APPOINTMENT_P_NAME, "Amal Kumar");
        appo1.put(Constants.APPOINTMENT_D_NAME, "Dr. Amit Guha");
        appo1.put(Constants.APPOINTMENT_ISSUE, "I am having teeth pain");
        appo1.put(Constants.APPOINTMENT_TIME, "12:00 PM");
        appo1.put(Constants.APPOINTMENT_ACTIVE, 1);

        // Add appointments
        ContentValues appo2 = new ContentValues();
        appo2.put(Constants.APPOINTMENT_P_NAME, "Bishnu Das");
        appo2.put(Constants.APPOINTMENT_D_NAME, "Dr. Amit Guha");
        appo2.put(Constants.APPOINTMENT_ISSUE, "Teeth pain on left side");
        appo2.put(Constants.APPOINTMENT_TIME, "01:00 PM");
        appo2.put(Constants.APPOINTMENT_ACTIVE, 1);

        db.insert(Constants.APPOINTMENT_TABLE, null, appo1);
        db.insert(Constants.APPOINTMENT_TABLE, null, appo2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private String[] checkAccount(String email, SQLiteDatabase db) {

        Cursor patient = db
                .rawQuery(Constants.QUERY_FIND_PATIENT, new String[]{email});
        Cursor doctor = db
                .rawQuery(Constants.QUERY_FIND_DOCTOR, new String[]{email});


        if (doctor.getCount() > 0 && doctor.moveToFirst()) {
            String[] data = new String[]{
                    "1",
                    String.valueOf(doctor.getInt(0)),
                    doctor.getString(1),
                    doctor.getString(2),
                    doctor.getString(3),
                    doctor.getString(4),
            };
            patient.close();
            doctor.close();
            return data;
        } else if (patient.getCount() > 0 && patient.moveToFirst()) {
            String[] data = new String[]{
                    "2",
                    String.valueOf(patient.getInt(0)),
                    patient.getString(1),
                    patient.getString(2),
                    patient.getString(3),
            };

            patient.close();
            doctor.close();
            return data;
        } else {
            return null;
        }
    }

    public int login(String email, String password, Context mContext) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] accountData = checkAccount(email, db);

        if (accountData == null) {
            return -1; //account does not exist
        }

        if (accountData[4].equals(password)) {
            SharedPreferences preferences = mContext
                    .getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("account_type", accountData[0]);
            editor.putString("id", accountData[1]);
            editor.putString("name", accountData[2]);
            editor.putString("email", accountData[3]);

            if (accountData[0].equals("1")) {
                editor.putString("specialization", accountData[5]);
            }

            editor.apply();

            return Integer.parseInt(accountData[0]);
        } else {
            return 0; //wrong password
        }
    }

    public void logout(Context mContext) {
        SharedPreferences preferences = mContext
                .getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("account_type", "");
        editor.putString("id", "");
        editor.putString("name", "");
        editor.putString("email", "");
        editor.putString("specialization", "");
        editor.apply();
        editor.commit();
    }

    public ArrayList<Appointment> getAppointments(String name, int accountType) {

        ArrayList<Appointment> appoList = new ArrayList<>();
        String query;
        if (accountType == 1) {
            query = Constants.QUERY_FIND_D_APPO;
        } else {
            query = Constants.QUERY_FIND_P_APPO;
        }
//
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor appos = db.rawQuery(query, new String[]{String.valueOf(name)});

        for (appos.moveToFirst(); !appos.isAfterLast(); appos.moveToNext()) {
            appoList.add(new Appointment(appos.getInt(0), appos.getString(1), appos.getString(2), appos.getString(3),
                    appos.getString(4), appos.getInt(5)));
        }

        appos.close();

        return appoList;
    }

    public ArrayList<Doctor> getDoctors() {
        ArrayList<Doctor> docList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor docs = db.rawQuery(Constants.QUERY_ALL_DOCTOR, new String[]{});
        for (docs.moveToFirst(); !docs.isAfterLast(); docs.moveToNext()) {
            docList.add(new Doctor(docs.getInt(0), docs.getString(1), docs.getString(2), docs.getString(3),
                    docs.getString(4)));
        }
        docs.close();

        return docList;
    }

    public void createAppointment(String pName, String dname, String time, String issue) {
        // Add appointments
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues appo2 = new ContentValues();
        appo2.put(Constants.APPOINTMENT_P_NAME, pName);
        appo2.put(Constants.APPOINTMENT_D_NAME, dname);
        appo2.put(Constants.APPOINTMENT_ISSUE, issue);
        appo2.put(Constants.APPOINTMENT_TIME, time);
        appo2.put(Constants.APPOINTMENT_ACTIVE, 1);

        db.insert(Constants.APPOINTMENT_TABLE, null, appo2);
    }
}
