package com.nolanpirce.voicecontrol.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nolanpirce.voicecontrol.model.Command;

import java.util.ArrayList;

/**
 * Created by nolanprice on 8/18/17.
 */

public class CommandDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CommandSchema.TABLE_NAME + " (" +
                    CommandSchema._ID + " INTEGER PRIMARY KEY," +
                    CommandSchema.COLUMN_NAME_VOICE_COMMAND + " TEXT," +
                    CommandSchema.COLUMN_NAME_ACTION_URL + " TEXT," +
                    CommandSchema.COLUMN_NAME_JSON_PAYLOAD + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CommandSchema.TABLE_NAME;

    private static final String GET_ALL_COMMANDS =
            "SELECT * FROM " + CommandSchema.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Commands.db";

    public CommandDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void storeCommand(Command command) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CommandSchema.COLUMN_NAME_VOICE_COMMAND, command.getVoiceCommand());
        values.put(CommandSchema.COLUMN_NAME_ACTION_URL, command.getActionUrl());
        values.put(CommandSchema.COLUMN_NAME_JSON_PAYLOAD, command.getJsonPayload());

        db.insert(CommandSchema.TABLE_NAME, null, values);
    }

    public ArrayList<Command> getAllCommands() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(GET_ALL_COMMANDS, null);
        ArrayList<Command> commands = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Command command = new Command();

                String voiceCommand = cursor.getString(cursor.getColumnIndex(CommandSchema.COLUMN_NAME_VOICE_COMMAND));
                command.setVoiceCommand(voiceCommand);

                String actionUrl = cursor.getString(cursor.getColumnIndex(CommandSchema.COLUMN_NAME_ACTION_URL));
                command.setActionUrl(actionUrl);

                String jsonPayload = cursor.getString(cursor.getColumnIndex(CommandSchema.COLUMN_NAME_JSON_PAYLOAD));
                command.setJsonPayload(jsonPayload);

                commands.add(command);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return commands;
    }

    public ArrayList<String> getAllCommandNames() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(GET_ALL_COMMANDS, null);
        ArrayList<String> commandNames = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String voiceCommand = cursor.getString(cursor.getColumnIndex(CommandSchema.COLUMN_NAME_VOICE_COMMAND));

                commandNames.add(voiceCommand);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return commandNames;
    }

    public void deleteCommand(String commandName) {
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(CommandSchema.TABLE_NAME, CommandSchema.COLUMN_NAME_VOICE_COMMAND + "=\"" + commandName + "\"", null);
    }
}
