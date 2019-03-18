package org.d3ifcool.tel_uevent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

    //Database Name
    public static final String DATABASE_NAME = "db_event";

    //Database Versi
    public static final int DATABASE_VERSION = 1;

    //Table Name
    public static final String TABLE_USER = "users";
    public static final String TABLE_EVENT = "event";

    //Table User Columns
    //ID colum @PrimaryKey
    public static final String KEY_ID = "nim";

    //COLUMN username
    public static final String KEY_USER_NAME = "username";

    //COLUMN Jenis Kelamin
    public static final String KEY_JENIS_KELAMIN = "jk";

    //COLUMN No HP
    public static final String KEY_HP = "no_hp";

    //COLUMN Alamat Bandung
    public static final String KEY_ALAMAT_BANDUNG = "alamat_bandung";

    //COLUMN Status User
    public static final String KEY_STATUS = "status";

    //COLUMN Hak Akses
    public static final String KEY_HAK_AKSES = "hak_akses";

    //COLUMN Nama Lengkap
    public static final String KEY_USER_FULL_NAME = "nama_lengkap";

    //COLUMN email
    public static final String KEY_EMAIL = "email";

    //COLUMN password
    public static final String KEY_PASSWORD = "password";

    //End User Column

    //Buat Table user
    public static final String SQL_TABLE_USER = " CREATE TABLE " + TABLE_USER
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT ,"
            + KEY_PASSWORD + " TEXT ,"
            + KEY_USER_FULL_NAME + " TEXT ,"
            + KEY_ALAMAT_BANDUNG + " TEXT ,"
            + KEY_EMAIL + " TEXT ,"
            + KEY_HP + " TEXT ,"
            + KEY_JENIS_KELAMIN + " TEXT ,"
            + KEY_STATUS + " TEXT ,"
            + KEY_HAK_AKSES + " TEXT  ) ";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Buat Table ketika di panggil

        sqLiteDatabase.execSQL(SQL_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Drop table dan buat baru jika versi berubah

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER);
    }

    public void addUser(User user){
        //Ambil variable DB
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put Value
        values.put(KEY_ID, user.getNim());
        values.put(KEY_USER_NAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_USER_FULL_NAME, user.getNama_lengkap());
        values.put(KEY_JENIS_KELAMIN, user.getJk());
        values.put(KEY_HP, user.getNo_hp());
        values.put(KEY_STATUS, user.getStatus());
        values.put(KEY_HAK_AKSES, user.getHak_akses());
        values.put(KEY_ALAMAT_BANDUNG, user.getAlamat_bandung());

        //Insert Table
        long todo_id = db.insert(TABLE_USER, null, values);

    }

    public User Authenticate (User user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[] {KEY_ID,KEY_USER_NAME,KEY_PASSWORD,KEY_EMAIL,KEY_USER_FULL_NAME,KEY_JENIS_KELAMIN,KEY_HP,KEY_STATUS,KEY_HAK_AKSES,KEY_ALAMAT_BANDUNG},
                KEY_USER_NAME + "=?",
                new String[]{user.getUsername()},
                null,null,null);

        if(cursor != null && cursor.moveToFirst() && cursor.getCount()>0){
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));

            if (user.getPassword().equalsIgnoreCase(user1.getPassword())){
                return user1;
            }
        }
        return null;
    }

    public boolean isUsernameExists(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[] {KEY_ID,KEY_USER_NAME,KEY_PASSWORD,KEY_EMAIL,KEY_USER_FULL_NAME,KEY_JENIS_KELAMIN,KEY_HP,KEY_STATUS,KEY_HAK_AKSES,KEY_ALAMAT_BANDUNG},
                KEY_USER_NAME + "=?",
                new String[]{username}, //Where Clause
                null,null,null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            return true;
        }
        return false;
    }
}
