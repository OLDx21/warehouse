package com.example.newpr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbhelp extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "saversec";

   public static final String TABLE_NAME = "sklad";
   public static final int VERSION = 1;
   public static final  String NAME_COLUMN = "name";
   public static final  String KOLVO_COLUMN = "kolvo";

   public static final String TABLE_NAME2 = "tovar";
    public static final  String NAME_COLUMN2 = "nametovar";
    public static final  String RECEPT = "recept";

public static final  String TABLE_NAME3 = "Otmena";
    public static final  String COLUMN_NAME_OTMENA = "name";
    public static final  String CULUMN_DATE = "date";
    public static final  String COLUMN_KODE = "kode";

    public dbhelp(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "(" + NAME_COLUMN + " text," + KOLVO_COLUMN + " text"+")");
        db.execSQL("create table " + TABLE_NAME2 + "(" + NAME_COLUMN2 + " text," + RECEPT + " text"+")");
        db.execSQL("create table " + dbhelp.TABLE_NAME3 + "(" + dbhelp.COLUMN_NAME_OTMENA + " text," + dbhelp.CULUMN_DATE + " text, "+ com.example.newpr.dbhelp.COLUMN_KODE + " text " + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("drop table if exists " + TABLE_NAME);
        db.execSQL("drop table if exists " + TABLE_NAME2);
onCreate(db);
    }
}
