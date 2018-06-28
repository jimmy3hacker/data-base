package com.backyardev.class9;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

public class DBClass extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="localdb";
    private static final int DATABASE_VERSION=1;

    public DBClass(Context context) {
        super( context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER  (Name text, Password text,Phone text,Type text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS USER" );
        onCreate( db );
    }
    public boolean onAddData(String name,String password,String number,String type){
       SQLiteDatabase db= getWritableDatabase();

        ContentValues contentValues = new ContentValues(  );
        contentValues.put( "Name",name );
        contentValues.put( "Password",password );
        contentValues.put( "Phone",number );
        contentValues.put( "Type",type );

        db.insert( "USER",null,contentValues );
        contentValues.clear();
        return true;
    }
    public Cursor getData(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("USER",null,null,null,null,null,null);
        return cursor;
    }
    void onDelete(String name){
        SQLiteDatabase db = getWritableDatabase();
        String arr[]={" "};
        arr[0]=name;
        db.delete("USER","Name=?",arr);
    }
    public Cursor loginLogic(String name,String pass){
       SQLiteDatabase db= getReadableDatabase();
       Cursor c = db.rawQuery( "SELECT Name, Password,Type FROM USER WHERE Name=? AND Password=?",
               new String[] {name,pass} );
       return c;
    }
    boolean onUpdate(String name,String oldPass,String newPass){
        int check;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Name",name);
        cv.put("Password",newPass);
        String arr[]={" "," "};
        arr[0]=name;
        arr[1]= oldPass;
        check=db.update("USER",cv,"Name=? AND Password=?",arr);
        return (check>-1);
    }
    public Cursor getUserData(String name, String pass){
        SQLiteDatabase db= getReadableDatabase();
        Cursor c=db.query( "USER",null,"Name=? AND Password=?",
                new String[]{name, pass},null,null,null);
        return c;

    }


}