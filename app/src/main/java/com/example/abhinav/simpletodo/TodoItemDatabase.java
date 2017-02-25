package com.example.abhinav.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.name;

/**
 * Created by janabhi on 2/22/17.
 */

public class TodoItemDatabase extends SQLiteOpenHelper {

    private static TodoItemDatabase sInstance;
    // Database Info
    private static String DATABASE_NAME = "todoItems";
    private static int DATABASE_VERSION = 2;

    // Table name
    private static String TABLE_ITEMS="items";

    // Items Table columns
    private static String KEY_ITEMS_ID="id";
    private static String KEY_ITEMS_TEXT ="text";
    private static String KEY_ITEMS_PRIORITY="priority";
    private static String KEY_ITEMS_DATE="date";

    public static synchronized TodoItemDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TodoItemDatabase(context.getApplicationContext());
        }
        return sInstance;
    }

    public TodoItemDatabase(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE "+ TABLE_ITEMS +
                "("
                + KEY_ITEMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_ITEMS_TEXT + " TEXT, "
                + KEY_ITEMS_PRIORITY + " TEXT, "
                + KEY_ITEMS_DATE +" TEXT" +")";
        sqLiteDatabase.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            onCreate(sqLiteDatabase);
        }
    }

    public long addItem(Item listItem){
        SQLiteDatabase db  = getWritableDatabase();
        db.beginTransaction();
        long itemsId=0;
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_ITEMS_TEXT,listItem.text);
            values.put(KEY_ITEMS_PRIORITY, listItem.priority);
            itemsId=db.insertOrThrow(TABLE_ITEMS,null, values);
            Log.i("id",String.valueOf(itemsId));
            db.setTransactionSuccessful();
        }catch(Exception e){
            Log.d(e.getMessage(),"Error while trying to add item to database");
        }finally {
            db.endTransaction();
            return itemsId;
        }
    }
    public int update(Item listItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ITEMS_TEXT, listItem.text);
        values.put(KEY_ITEMS_PRIORITY,listItem.priority);
        return db.update(TABLE_ITEMS,values, KEY_ITEMS_ID+" = ? ",new String[] { String.valueOf(listItem.id) });
    }
    public ArrayList<Item> getAllItems(){
        ArrayList<Item> itemsList = new ArrayList<>();
        String ITEMS_SELECT_QUERY = String.format("SELECT * FROM %s", TABLE_ITEMS);
        SQLiteDatabase db= getReadableDatabase();
        Cursor cursor = db.rawQuery(ITEMS_SELECT_QUERY,null);
        try{
            if(cursor.moveToFirst()){
                do{
                    Item item = new Item();
                    item.text = cursor.getString(cursor.getColumnIndex(KEY_ITEMS_TEXT));
                    item.id= cursor.getInt(cursor.getColumnIndex(KEY_ITEMS_ID));
                    item.priority= cursor.getString(cursor.getColumnIndex(KEY_ITEMS_PRIORITY));
//                    item.dueDate= new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(cursor.getColumnIndex(KEY_ITEMS_DATE)));
                    itemsList.add(item);
                    Log.i("Text",item.text);
                    Log.i("id",String.valueOf(item.id));
                    Log.i("pri", item.priority);

                }while(cursor.moveToNext());
            }
        }catch(Exception e ){
            Log.d(e.getMessage(), "can't read the items");
        } finally{
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return itemsList;
    }

    public void deleteItem(Item item)
    {   SQLiteDatabase db= this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ITEMS_TEXT + "=?" , new String[]{String.valueOf(item.text)});
    }

}
