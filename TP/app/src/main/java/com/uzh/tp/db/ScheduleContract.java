package com.uzh.tp.db;

import android.provider.BaseColumns;

//===========================================================================
// Create: brenokeller 
// Date: 8/28/17
// Package: com.uzh.tp.db
// Project: TP
//===========================================================================
public class ScheduleContract {

    private ScheduleContract() {
    }

    public static class Room implements BaseColumns {
        public static final String ROOM_TABLE_NAME = "room";
        public static final String ROOM_ID = "room_id";
        public static final String ROOM_NAME = "room_name";
    }

    public static class Session implements BaseColumns {
        public static final String SESSION_TABLE_NAME = "session";
        public static final String SESSION_ID = "session_id";
        public static final String SESSION_TITLE = "session_title";
        public static final String SESSION_DESCRIPTION = "session_description";
        public static final String SESSION_START_TIME = "session_start_time";
        public static final String SESSION_END_TIME = "session_end_time";
        public static final String SESSION_ROOM_ID = "session_room_id";
    }

    public static class Speakers implements BaseColumns {
        public static final String SPEAKERS_TABLE_NAME = "speakers";
        public static final String SPEAKERS_ID = "speakers_id";
        public static final String SPEAKERS_NAME = "speakers_name";
        public static final String SPEAKERS_BIO = "speakers_bio";
    }

    public static class SessionSpeakers implements BaseColumns {
        public static final String SS_TABLE_NAME = "session_speakers";
        public static final String SS_SESSION_ID = "session_speakers_session_id";
        public static final String SS_SPEAKER_ID = "session_speakers_speakers_id";
    }
}
