package com.uzh.tp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//===========================================================================
// Create: brenokeller 
// Date: 8/28/17
// Package: com.uzh.tp.db
// Project: TP
//===========================================================================
public class ScheduleDatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "ScheduleDatabaseHelper";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Schedule.db";

    //ROOM
    private static final String ROOM_CREATE_TABLE =
            "CREATE TABLE " + ScheduleContract.Room.ROOM_TABLE_NAME + " (" +
                    ScheduleContract.Room._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ScheduleContract.Room.ROOM_ID + " TEXT," +
                    ScheduleContract.Room.ROOM_NAME + " TEXT)";
    private static final String ROOM_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + ScheduleContract.Room.ROOM_TABLE_NAME;

    //SESSION
    private static final String SESSION_CREATE_TABLE =
            "CREATE TABLE " + ScheduleContract.Session.SESSION_TABLE_NAME + " (" +
                    ScheduleContract.Session._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ScheduleContract.Session.SESSION_ID + " TEXT," +
                    ScheduleContract.Session.SESSION_TITLE + " TEXT," +
                    ScheduleContract.Session.SESSION_DESCRIPTION + " TEXT," +
                    ScheduleContract.Session.SESSION_START_TIME + " TEXT," +
                    ScheduleContract.Session.SESSION_END_TIME + " TEXT," +
                    ScheduleContract.Session.SESSION_ROOM_ID + " INTEGER REFERENCES " + ScheduleContract.Room.ROOM_TABLE_NAME + "(" + ScheduleContract.Room._ID + ")" + ")";
    private static final String SESSION_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + ScheduleContract.Session.SESSION_TABLE_NAME;

    //SPEAKERS
    private static final String SPEAKERS_CREATE_TABLE =
            "CREATE TABLE " + ScheduleContract.Speakers.SPEAKERS_TABLE_NAME + " (" +
                    ScheduleContract.Speakers._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ScheduleContract.Speakers.SPEAKERS_ID + " TEXT," +
                    ScheduleContract.Speakers.SPEAKERS_NAME + " TEXT," +
                    ScheduleContract.Speakers.SPEAKERS_BIO + " TEXT)";
    private static final String SPEAKERS_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + ScheduleContract.Speakers.SPEAKERS_TABLE_NAME;


    //SESSION_SPEAKERS (SS)
    private static final String SS_CREATE_TABLE =
            "CREATE TABLE " + ScheduleContract.SessionSpeakers.SS_TABLE_NAME + " (" +
                    ScheduleContract.SessionSpeakers._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ScheduleContract.SessionSpeakers.SS_SESSION_ID + " INTEGER REFERENCES " + ScheduleContract.Session.SESSION_TABLE_NAME + "(" + ScheduleContract.Session._ID + ")," +
                    ScheduleContract.SessionSpeakers.SS_SPEAKER_ID + " INTEGER REFERENCES " + ScheduleContract.Speakers.SPEAKERS_TABLE_NAME + "(" + ScheduleContract.Speakers._ID + "))";
    private static final String SS_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + ScheduleContract.Speakers.SPEAKERS_TABLE_NAME;


    public ScheduleDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ROOM_CREATE_TABLE);
        sqLiteDatabase.execSQL(SESSION_CREATE_TABLE);
        sqLiteDatabase.execSQL(SPEAKERS_CREATE_TABLE);
        sqLiteDatabase.execSQL(SS_CREATE_TABLE);

        populateDB(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(ROOM_DELETE_TABLE);
        sqLiteDatabase.execSQL(SESSION_DELETE_TABLE);
        sqLiteDatabase.execSQL(SPEAKERS_DELETE_TABLE);
        sqLiteDatabase.execSQL(SS_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    private void populateDB(SQLiteDatabase sqLiteDatabase) {
        ContentValues values = new ContentValues();
        values.put(ScheduleContract.Room.ROOM_ID, "Room 1");
        values.put(ScheduleContract.Room.ROOM_NAME, "Room Alpha");
        long roomAlfa = sqLiteDatabase.insert(ScheduleContract.Room.ROOM_TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ScheduleContract.Room.ROOM_ID, "Room 2");
        values.put(ScheduleContract.Room.ROOM_NAME, "Room Beta");
        long roomBeta = sqLiteDatabase.insert(ScheduleContract.Room.ROOM_TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ScheduleContract.Speakers.SPEAKERS_ID, "Speaker 1");
        values.put(ScheduleContract.Speakers.SPEAKERS_NAME, "Speaker Alfa");
        values.put(ScheduleContract.Speakers.SPEAKERS_BIO, "Alfa");
        long speakerAlfa = sqLiteDatabase.insert(ScheduleContract.Speakers.SPEAKERS_TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ScheduleContract.Speakers.SPEAKERS_ID, "Speaker 2");
        values.put(ScheduleContract.Speakers.SPEAKERS_NAME, "Speaker Beta");
        values.put(ScheduleContract.Speakers.SPEAKERS_BIO, "Beta");
        long speakerBeta = sqLiteDatabase.insert(ScheduleContract.Speakers.SPEAKERS_TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ScheduleContract.Session.SESSION_ID, "Session 1");
        values.put(ScheduleContract.Session.SESSION_TITLE, "Session Alfa");
        values.put(ScheduleContract.Session.SESSION_DESCRIPTION, "Alfa");
        values.put(ScheduleContract.Session.SESSION_START_TIME, "2017-08-28T00:00:00Z");
        values.put(ScheduleContract.Session.SESSION_END_TIME, "2017-08-28T00:30:00Z");
        values.put(ScheduleContract.Session.SESSION_ROOM_ID, roomAlfa);
        long sessionAlfa = sqLiteDatabase.insert(ScheduleContract.Session.SESSION_TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ScheduleContract.Session.SESSION_ID, "Session 2");
        values.put(ScheduleContract.Session.SESSION_TITLE, "Session Beta");
        values.put(ScheduleContract.Session.SESSION_DESCRIPTION, "Beta");
        values.put(ScheduleContract.Session.SESSION_START_TIME, "2017-08-28T00:00:00Z");
        values.put(ScheduleContract.Session.SESSION_END_TIME, "2017-08-28T00:30:00Z");
        values.put(ScheduleContract.Session.SESSION_ROOM_ID, roomBeta);
        long sessionBeta = sqLiteDatabase.insert(ScheduleContract.Session.SESSION_TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ScheduleContract.SessionSpeakers.SS_SESSION_ID, sessionAlfa);
        values.put(ScheduleContract.SessionSpeakers.SS_SPEAKER_ID, speakerAlfa);
        sqLiteDatabase.insert(ScheduleContract.SessionSpeakers.SS_TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ScheduleContract.SessionSpeakers.SS_SESSION_ID, sessionAlfa);
        values.put(ScheduleContract.SessionSpeakers.SS_SPEAKER_ID, speakerBeta);
        sqLiteDatabase.insert(ScheduleContract.SessionSpeakers.SS_TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(ScheduleContract.SessionSpeakers.SS_SESSION_ID, sessionBeta);
        values.put(ScheduleContract.SessionSpeakers.SS_SPEAKER_ID, speakerBeta);
        sqLiteDatabase.insert(ScheduleContract.SessionSpeakers.SS_TABLE_NAME, null, values);
    }
}
