package com.google.firebase.codelab.friendlychat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 13;
    private static final String DATABASE_NAME = "local_backup.db";

    // TableID and columns for messages table
    private static final String TABLE_MESSAGE = "messages";
    private static final String COLUMN_MESSAGEID = "msg_id";
    private static final String COLUMN_SENDER = "sender";
    private static final String COLUMN_MESSAGE = "message";

    public DatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_MESSAGE + "("
                + COLUMN_MESSAGEID + " TEXT PRIMARY KEY, "
                + COLUMN_SENDER + " TEXT NOT NULL, "
                + COLUMN_MESSAGE + " TEXT NOT NULL, ");";
        db.execSQL(query);
	}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        onCreate(db);
    }

    public void insertMessage(String messageID, String sender, String message) {
        ContentValues values = new ContentValues();
        value.put(COLUMN_MESSAGEID, messageID);
        values.put(COLUMN_SENDER, sender);
        values.put(COLUMN_MESSAGE, message);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MESSAGE, null, values);
        db.close();
    }

    public boolean checkDuplicateEntry(String messageID) {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT '" + COLUMN_MESSAGEID + "' FROM " + TABLE_MESSAGE + " WHERE '" + COLUMN_MESSAGEID + "' = '" + messageID + "'";
        if (Cursor cursor = db.rawQuery(query, null)) {
            return true;

        } else {
            return false;
        }

        db.close();
    }










