package sh.broy.lpuhms.Utils;

public class Constants {

    public static final String SHARED_PREF_NAME = "LPUHMS";
    public static final String DATABASE_NAME = "LPUHMS";

    //Patient Table
    public static final String PATIENT_TABLE = "patient";
    public static final String PATIENT_ID = "id";
    public static final String PATIENT_NAME = "name";
    public static final String PATIENT_EMAIL = "email";
    public static final String PATIENT_PASSWORD = "password";

    //Doctors Table
    public static final String DOCTOR_TABLE = "doctor";
    public static final String DOCTOR_ID = "id";
    public static final String DOCTOR_NAME = "name";
    public static final String DOCTOR_EMAIL = "email";
    public static final String DOCTOR_PASSWORD = "password";
    public static final String DOCTOR_SPCL = "specialization";

    public static final String PRESCRIPTION_TABLE = "prescription";

    public static final String APPOINTMENT_TABLE = "appointment";
    public static final String APPOINTMENT_ID = "id";
    public static final String APPOINTMENT_P_NAME = "p_name";
    public static final String APPOINTMENT_D_NAME = "d_name";
    public static final String APPOINTMENT_TIME = "time";
    public static final String APPOINTMENT_ISSUE = "issue";
    public static final String APPOINTMENT_ACTIVE = "active";


    public static final String QUERY_CREATE_TABLE_PATIENT = "CREATE TABLE " + PATIENT_TABLE + "(" + PATIENT_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + PATIENT_NAME + " TEXT," + PATIENT_EMAIL + " TEXT,"
            + PATIENT_PASSWORD + " TEXT)";
    public static final String QUERY_CREATE_TABLE_DOCTOR = "CREATE TABLE " + DOCTOR_TABLE + "(" + DOCTOR_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + DOCTOR_NAME + " TEXT," + DOCTOR_EMAIL + " TEXT,"
            + DOCTOR_PASSWORD + " TEXT," + DOCTOR_SPCL + " TEXT)";
    public static final String QUERY_CREATE_TABLE_APPOINTMENT = "CREATE TABLE " + APPOINTMENT_TABLE + "("
            + APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + APPOINTMENT_P_NAME + " TEXT,"
            + APPOINTMENT_D_NAME + " TEXT," + APPOINTMENT_ISSUE + " TEXT," + APPOINTMENT_TIME + " TEXT,"
            + APPOINTMENT_ACTIVE + " INTEGER)";

    public static final String QUERY_ALL_DOCTOR = "SELECT * FROM " + DOCTOR_TABLE;
    public static final String QUERY_FIND_DOCTOR = "SELECT * FROM " + DOCTOR_TABLE + " where email = ?";
    public static final String QUERY_FIND_PATIENT = "SELECT * FROM " + PATIENT_TABLE + " where email = ?";
    public static final String QUERY_FIND_P_APPO = "SELECT * FROM " + APPOINTMENT_TABLE + " where p_name = ?";
    public static final String QUERY_FIND_D_APPO = "SELECT * FROM " + APPOINTMENT_TABLE + " where d_name = ?";
}
