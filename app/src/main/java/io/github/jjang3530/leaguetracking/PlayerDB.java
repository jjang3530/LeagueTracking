package io.github.jjang3530.leaguetracking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerDB {
    // database constants
    public static final String DB_NAME = "team.sqlite";
    public static final int    DB_VERSION = 1;
    public ArrayList<HashMap<String, String>> data;

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            db.execSQL("CREATE TABLE players (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , name VARCHAR NOT NULL , wins INTEGER NOT NULL  DEFAULT 0, losses INTEGER NOT NULL  DEFAULT 0, ties INTEGER NOT NULL  DEFAULT 0)");
         }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE \"players\"");
            Log.d("Task list", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);
            onCreate(db);
        }
    }

    // database and database helper objects
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public PlayerDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        openWriteableDB();
        closeDB();
    }
    // private methods
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    ArrayList<HashMap<String, String>> getTeams(){
        data = new ArrayList<HashMap<String, String>>();
        openReadableDB();
        Cursor cursor = db.rawQuery("SELECT id, name, wins, losses, ties FROM players ORDER BY wins desc, ties desc",null );
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", cursor.getString(0));
            map.put("name", cursor.getString(1));
            map.put("wins", cursor.getString(2));
            map.put("losses", cursor.getString(3));
            map.put("ties", cursor.getString(4));
            data.add(map);
        }
        if (cursor != null)
            cursor.close();
        closeDB();

        return data;
    }

    public HashMap<String, String> getTeam(int index) {
        return data.get(index);
    }

    void insertTeam(String sName)throws Exception {
        openWriteableDB();
        ContentValues content = new ContentValues();
        content.put("name", sName);
        long nResult = db.insert("players",null, content);
        if(nResult == -1) throw new Exception("no data");
        closeDB();
    }

    void updateTeam(Integer id, String uName)throws Exception {
        openWriteableDB();
        ContentValues content = new ContentValues();
        content.put("name", uName);
        long uResult = db.update("Players", content, "id =?", new String[] {Integer.toString(id)});
        if(uResult == -1) throw new Exception("no data");
        closeDB();
    }

    void updateWinScore(Integer id, Integer wins)throws Exception {
        openWriteableDB();
        ContentValues content = new ContentValues();
        content.put("wins", wins);
        long uResult = db.update("Players", content, "id =?", new String[] {Integer.toString(id)});
        if(uResult == -1) throw new Exception("no data");
        closeDB();
    }

    void updateLossScore(Integer id, Integer losses)throws Exception {
        openWriteableDB();
        ContentValues content = new ContentValues();
        content.put("losses", losses);
        long uResult = db.update("Players", content, "id =?", new String[] {Integer.toString(id)});
        if(uResult == -1) throw new Exception("no data");
        closeDB();
    }

    void updateTieScore(Integer id, Integer ties)throws Exception {
        openWriteableDB();
        ContentValues content = new ContentValues();
        content.put("ties", ties);
        long uResult = db.update("Players", content, "id =?", new String[] {Integer.toString(id)});
        if(uResult == -1) throw new Exception("no data");
        closeDB();
    }

    void deleteTeam(Integer id)throws Exception {
        openWriteableDB();
        long uResult = db.delete("Players", "id =?", new String[] {Integer.toString(id)});
        if(uResult == -1) throw new Exception("no data");
        closeDB();
    }
}
