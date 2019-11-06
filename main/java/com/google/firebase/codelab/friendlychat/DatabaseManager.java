package com.google.firebase.codelab.friendlychat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "local_backup.db";

    // TableID and columns for messages table
    private static final String TABLE_MESSAGE = "messages";
    private static final String COLUMN_MESSAGEID = "msg_id";
    private static final String COLUMN_SENDER = "sender";
    private static final String COLUMN_RECEIVER = "receiver";
    private static final String COLUMN_MESSAGE = "message";
    private static final String COLUMN_SYMKEY = "symetric_key";

    // TableID and columns for account table
    private static final String TABLE_ACCOUNT = "account";
    private static final String COLUMN_ACCOUNTID = "account_id";
    private static final String COLUMN_FIREBASEID = "firebase_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PVTKEY = "private_key";

    public DatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_MESSAGE + "("
                + COLUMN_MESSAGEID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SENDER + " TEXT NOT NULL, "
                + COLUMN_RECEIVER + " TEXT NOT NULL, "
                + COLUMN_MESSAGE + " TEXT NOT NULL, "
                + COLUMN_SYMKEY + " TEXT NOT NULL" + ");";
        db.execSQL(query);

        query = new String();
        query = "CREATE TABLE " + TABLE_ACCOUNT + "("
                + COLUMN_ACCOUNTID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FIREBASEID + " TEXT NOT NULL, "
                + COLUMN_USERNAME + " TEXT NOT NULL, "
                + COLUMN_EMAIL + " TEXT NOT NULL, "
                + COLUMN_PASSWORD + " TEXT NOT NULL, "
                + COLUMN_PVTKEY + " TEXT NOT NULL" + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        onCreate(db);
    }

    public void insertMessage(String sender, String receiver, String message, String key) { //TODO: change to add row into message table
        ContentValues values = new ContentValues();
        values.put(COLUMN_SENDER, sender);
        values.put(COLUMN_RECEIVER, receiver);
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_SYMKEY, key);


        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT e.*, MAX(m.'msg_id') "
                        + "FROM " + TABLE_MESSAGE + " e, " + TABLE_MESSAGE + " m "
                        + "WHERE e.'" + COLUMN_MESSAGEID + "' = m.'" + COLUMN_MESSAGEID + "'";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        int maxID = cursor.getInt(cursor.getColumnIndex(COLUMN_MESSAGEID));
        String existingSender = cursor.getString(cursor.getColumnIndex(COLUMN_SENDER));
        String existingReceiver = cursor.getString(cursor.getColumnIndex(COLUMN_RECEIVER));
        String existingMessage = cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE));
        String existingKey = cursor.getString(cursor.getColumnIndex(COLUMN_SYMKEY));

        if (existingSender.equals(sender) && existingReceiver.equals(receiver) &&
                existingMessage.equals(message) && existingKey.equals(key)) {
            db.close();

        } else {
            db.insert(TABLE_MESSAGE, null, values);
        }

        db.close();
    }

    public void insertAccount(String firebaseID, String username, String email, String password, String privateKey) { //TODO: Add inserts for account
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIREBASEID, firebaseID);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PVTKEY, privateKey);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ACCOUNT, null, values);
        db.close();
    }

    public String retreivePrivateKey(String firebaseID) {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_PVTKEY + " FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_FIREBASEID + "='" + firebaseID + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        dbString += cursor.getString(cursor.getColumnIndex("private_key"));

        db.close();
        return dbString;
    }

}










