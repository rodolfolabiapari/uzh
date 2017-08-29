package com.uzh.tp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.uzh.tp.db.ScheduleContract;
import com.uzh.tp.db.ScheduleDatabaseHelper;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ScheduleDatabaseHelper scheduleDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleDatabaseHelper = new ScheduleDatabaseHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        validateDB();
    }

    private void validateDB() {
        SQLiteDatabase db = scheduleDatabaseHelper.getReadableDatabase();
        String[] projection = {
                ScheduleContract.Room._ID,
                ScheduleContract.Room.ROOM_ID,
                ScheduleContract.Room.ROOM_NAME};
        Cursor cursor = db.query(
                ScheduleContract.Room.ROOM_TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        Log.d(TAG, String.format(Locale.getDefault(), "%d", cursor.getCount()));
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            Log.d(TAG, String.format(Locale.getDefault(), "KEY: %d, ID: %s, NAME: %s",
                    cursor.getInt(cursor.getColumnIndexOrThrow(ScheduleContract.Room._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.Room.ROOM_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.Room.ROOM_NAME))));
            cursor.moveToNext();
        }
        cursor.close();

        projection = new String[]{
                ScheduleContract.Speakers._ID,
                ScheduleContract.Speakers.SPEAKERS_ID,
                ScheduleContract.Speakers.SPEAKERS_NAME,
                ScheduleContract.Speakers.SPEAKERS_BIO};
        cursor = db.query(
                ScheduleContract.Speakers.SPEAKERS_TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        Log.d(TAG, String.format(Locale.getDefault(), "%d", cursor.getCount()));
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            Log.d(TAG, String.format(Locale.getDefault(), "KEY: %d, ID: %s, NAME: %s, BIO: %s",
                    cursor.getInt(cursor.getColumnIndexOrThrow(ScheduleContract.Speakers._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.Speakers.SPEAKERS_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.Speakers.SPEAKERS_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.Speakers.SPEAKERS_BIO))));
            cursor.moveToNext();
        }
        cursor.close();

        projection = new String[]{
                ScheduleContract.Session._ID,
                ScheduleContract.Session.SESSION_ID,
                ScheduleContract.Session.SESSION_TITLE,
                ScheduleContract.Session.SESSION_DESCRIPTION,
                ScheduleContract.Session.SESSION_START_TIME,
                ScheduleContract.Session.SESSION_END_TIME,
                ScheduleContract.Session.SESSION_ROOM_ID};
        cursor = db.query(
                ScheduleContract.Session.SESSION_TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        Log.d(TAG, String.format(Locale.getDefault(), "%d", cursor.getCount()));
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            Log.d(TAG, String.format(Locale.getDefault(), "KEY: %d, ID: %s, TITLE: %s, DESC: %s, START: %s, END: %s, ROOM: %d",
                    cursor.getInt(cursor.getColumnIndexOrThrow(ScheduleContract.Session._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.Session.SESSION_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.Session.SESSION_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.Session.SESSION_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.Session.SESSION_START_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.Session.SESSION_END_TIME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(ScheduleContract.Session.SESSION_ROOM_ID))));
            cursor.moveToNext();
        }
        cursor.close();

        projection = new String[]{
                ScheduleContract.SessionSpeakers._ID,
                ScheduleContract.SessionSpeakers.SS_SESSION_ID,
                ScheduleContract.SessionSpeakers.SS_SPEAKER_ID};
        cursor = db.query(
                ScheduleContract.SessionSpeakers.SS_TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        Log.d(TAG, String.format(Locale.getDefault(), "%d", cursor.getCount()));
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            Log.d(TAG, String.format(Locale.getDefault(), "KEY: %d, SESSION: %d, SPEAKER: %d",
                    cursor.getInt(cursor.getColumnIndexOrThrow(ScheduleContract.SessionSpeakers._ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(ScheduleContract.SessionSpeakers.SS_SESSION_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(ScheduleContract.SessionSpeakers.SS_SPEAKER_ID))));
            cursor.moveToNext();
        }
        cursor.close();
    }
}
