package com.iitevents.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlHelper extends SQLiteOpenHelper{
    // Database Version
    private static final int DATABASE_VERSION = 3;
    // Database Name
    private static final String DATABASE_NAME = "EventsDB";
    
    // Sports table name
    private static final String TABLE_EVENTS = "events";

    // Sports Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_URL = "url";

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create sports table
    	// rating and imageName columns added to table sports
    	String CREATE_SPORTS_TABLE = "CREATE TABLE events ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "name TEXT, "+
                "url TEXT )";

 
        // create sports table
        db.execSQL(CREATE_SPORTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older sports table if existed
        db.execSQL("DROP TABLE IF EXISTS events");
 
        // create fresh sports table 
        this.onCreate(db);
    }
  
    /*CRUD operations (create "add", read "get", update, delete) */
  
     public void addSports(Sports sport){
    	Log.i("adding sports", "Swaroop");
    	Log.d("addSports", sport.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, sport.getName()); // get title 
        values.put(KEY_URL, sport.getUrl()); // get author
        // 3. insert
        db.insert(TABLE_EVENTS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/values

        // 4. Close dbase
        db.close(); 
    }
 
    // Get All Sports
    public List<Sports> getAllSports() {
        List<Sports> sports = new LinkedList<Sports>();
 
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_EVENTS;
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build sport and add it to list
        Sports sport = null;
        if (cursor.moveToFirst()) {
            do {
            	sport = new Sports();
            	sport.setId(Integer.parseInt(cursor.getString(0)));
            	sport.setName(cursor.getString(1));
            	sport.setUrl(cursor.getString(2));
                
                sports.add(sport);
            } while (cursor.moveToNext());
        }
        Log.d("getAllSports()", sports.toString());
         
        return sports; // return sports
    }
     // Updating single sport
    public int updateSport(Sports sport, String newName, String newUrl) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", newName); // get title 
        values.put("url", newUrl); // get author
        // 3. updating row
        int i = db.update(TABLE_EVENTS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(sport.getId()) }); //selection args 
        // 4. close dbase
        db.close();
        Log.d("UpdateSport", sport.toString());
        return i;
 
    }
 
    // Deleting single sport
    public void deleteSport(Sports sport) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_EVENTS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(sport.getId()) });
 
        // 3. close
        db.close();
        Log.d("deleteSport", sport.toString());
     }
    
	public int getIds(Sports sport) {
		String selectQuery = "SELECT id FROM events";
		SQLiteDatabase database = this.getReadableDatabase();
		Cursor c = database.rawQuery(selectQuery, null);
		c.moveToFirst();
		int total = c.getCount();

		Log.d("count of records in the database", String.valueOf(total));
		return total;
	}
	
	public Set<String> getName() {
  	  Set<String> set = new HashSet<String>();
  	  String selectQuery = "select * from " + TABLE_EVENTS;
  	  SQLiteDatabase db = this.getReadableDatabase();
  	  Cursor cursor = db.rawQuery(selectQuery, null);
  	  if (cursor.moveToFirst()) {
  	   do {
  	   set.add(cursor.getString(1));  //get title column's value
  	   } while (cursor.moveToNext());
  	  }
  	  cursor.close();
  	  db.close();
  	  return set;
  	 }   
public String getUrl(String name) {
      StringBuilder s = new StringBuilder();
	  String selectQuery = "select * from " + TABLE_EVENTS + " where name=?";
	  SQLiteDatabase db = this.getReadableDatabase();
	  Cursor cursor = db.rawQuery(selectQuery,new String[] {name});
	  if (cursor.moveToFirst()) {
	   do {
	   s.append(cursor.getString(2));  //get author's value
	   } while (cursor.moveToNext());
	  }
	  cursor.close();
	  db.close();
	  return s.toString();
	 }   

}
