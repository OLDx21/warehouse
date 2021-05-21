package com.example.newpr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.newpr.ui.main.PlaceholderFragment;

import java.io.IOException;
import java.util.*;

public class Methods {
    public static ArrayList<Button> buttonArrayList = new ArrayList<>();
    static ArrayList<String> arrayList = new ArrayList<String>();
    public static ArrayList<InfoTovar> infoTovars = new ArrayList<InfoTovar>();
    public static int check(SQLiteDatabase sqLiteDatabase, String text, String tablrname, String namecolumn, String twocolumn) {

        String[] text1 = {text};
        int i = 0;
        Cursor cursor = sqLiteDatabase.query(tablrname,
                new String[]{namecolumn, twocolumn},
                namecolumn + " = ?",
                text1,
                null, null, null);

        if (cursor.moveToFirst()) {
            i++;

        }
        cursor.close();
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setlist(SQLiteDatabase sqLiteDatabase, ListView listView, int fruitImgResId) {
        arrayList.clear();
        Cursor cursor = sqLiteDatabase.query(dbhelp.TABLE_NAME, null, null, null, null, null, null);
         FruitArrayAdapter fruitArrayAdapter = new FruitArrayAdapter(listView.getContext(), R.layout.customlist);
         fruitArrayAdapter.setNotifyOnChange(true);
        Sklad.fruitArrayAdapter = fruitArrayAdapter;
        listView.setAdapter(fruitArrayAdapter);

        String name;
        String kolvo;

        if (cursor.moveToFirst()) {
            int nameid = cursor.getColumnIndex(dbhelp.NAME_COLUMN);
            int kolvotable = cursor.getColumnIndex(dbhelp.KOLVO_COLUMN);

            do {

                name = cursor.getString(nameid);
                kolvo = cursor.getString(kolvotable);
                arrayList.add(name);


                Fruit fruit = new Fruit(fruitImgResId,name,kolvo);
                fruitArrayAdapter.add(fruit);

            }

            while (cursor.moveToNext());
        }

        cursor.close();




    }

    public static void setlist(SQLiteDatabase sqLiteDatabase, ListView listView, Context context,int fruitImgResId) {
        FruitArrayAdapter.fruitList.clear();
        arrayList.clear();
        Cursor cursor = sqLiteDatabase.query(dbhelp.TABLE_NAME, null, null, null, null, null, null);
        FruitArrayAdapter fruitArrayAdapter = new FruitArrayAdapter(context, R.layout.customlist);
        fruitArrayAdapter.setNotifyOnChange(true);
        Sklad.fruitArrayAdapter = fruitArrayAdapter;
        listView.setAdapter(fruitArrayAdapter);

        String name;
        String kolvo;

        if (cursor.moveToFirst()) {
            int nameid = cursor.getColumnIndex(dbhelp.NAME_COLUMN);
            int kolvotable = cursor.getColumnIndex(dbhelp.KOLVO_COLUMN);

            do {

                name = cursor.getString(nameid);
                kolvo = cursor.getString(kolvotable);
                arrayList.add(name);


                Fruit fruit = new Fruit(fruitImgResId,name,kolvo);
                fruitArrayAdapter.add(fruit);

            }

            while (cursor.moveToNext());
        }

        cursor.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("WrongConstant")
    public static ScrollView artbuttons(SQLiteDatabase sqLiteDatabase, Context context, int widght) {
        infoTovars.clear();
        buttonArrayList.clear();
        Cursor cursor = sqLiteDatabase.query(dbhelp.TABLE_NAME2
                , null, null, null, null, null, null);
        String name;
        String recept;

        if (cursor.moveToFirst()) {
            int next = cursor.getColumnIndex(dbhelp.NAME_COLUMN2);
            int next2 = cursor.getColumnIndex(dbhelp.RECEPT);


            do {
                name = cursor.getString(next);
                recept = cursor.getString(next2);
                infoTovars.add(new InfoTovar(name, recept));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        GridLayout gridLayout = new GridLayout(context);
        gridLayout.setOrientation(0);
        gridLayout.setColumnCount(4);

        ScrollView scrollView = new ScrollView(context);


        for (int i = 0; i < Methods.infoTovars.size(); i++) {
            Button button = new Button(context);
            button.setHeight(widght);
            button.setWidth(widght);
            button.setText(Methods.infoTovars.get(i).getNam());

            buttonArrayList.add(button);

            gridLayout.addView(button);
        }
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.addView(gridLayout);


        return scrollView;
    }

    public static void arthistory(SQLiteDatabase sqLiteDatabase, ListView listView) {

        Cursor cursor2 = sqLiteDatabase.query(com.example.newpr.dbhelp.TABLE_NAME3, new String[]{com.example.newpr.dbhelp.COLUMN_NAME_OTMENA,
                        com.example.newpr.dbhelp.CULUMN_DATE, com.example.newpr.dbhelp.COLUMN_KODE},
                null,
                null, null, null, null);

        ArrayList<INfo> tester = new ArrayList<>();
        AdapterHistory fruitArrayAdapter = new AdapterHistory(listView.getContext(), R.layout.customhistory);
        fruitArrayAdapter.setNotifyOnChange(true);


        String name2;
        String kode;
        String data;


        if (cursor2.moveToFirst()) {
            int next = cursor2.getColumnIndex(com.example.newpr.dbhelp.CULUMN_DATE);
            int nex2 = cursor2.getColumnIndex(com.example.newpr.dbhelp.COLUMN_KODE);
            int nex3 = cursor2.getColumnIndex(com.example.newpr.dbhelp.COLUMN_NAME_OTMENA);

            do {
                name2 = cursor2.getString(nex3);
                data = cursor2.getString(next);
                kode = cursor2.getString(nex2);
                tester.add(new INfo(name2, data, kode));
                HistoryClas fruit = new HistoryClas("",data,name2);
                fruitArrayAdapter.add(fruit);


            }
            while (cursor2.moveToNext());
        }
        listView.setAdapter(fruitArrayAdapter);
        cursor2.close();
History.adapterHistory = fruitArrayAdapter;
AdapterHistory.arrayList.clear();
AdapterHistory.put(tester);


    }
    public static void deleteitem(SQLiteDatabase sqLiteDatabase, String kode, String name){

       Cursor cursor = sqLiteDatabase.query(dbhelp.TABLE_NAME2,
                new String[]{dbhelp.NAME_COLUMN2, dbhelp.RECEPT},
                "nametovar = ?",
                new String[]{name},
                null, null, null);
       String recept = "";

       if(cursor.moveToFirst()){
           int intrecept = cursor.getColumnIndex(dbhelp.RECEPT);

           recept = cursor.getString(intrecept);

       }

        sqLiteDatabase.delete(dbhelp.TABLE_NAME3, "kode = ?", new String[]{kode});


        ArrayList<String> arrayList = new ArrayList<>();

        int hr = 0;
        int start=0;
        while(hr<recept.length()){
            hr= recept.indexOf(';',hr+1);
            if(hr==-1||start==-1){ break; }
            arrayList.add(recept.substring(start+1,hr).trim());
            start = recept.indexOf(';',hr);
        }
        int dvetochki;

        int result;
        int next2;
        String kolvo;
        String allkolvo;
        ContentValues contentValues = new ContentValues();

        for(int i = 0; i<arrayList.size(); i++) {
            dvetochki = arrayList.get(i).indexOf(':');
            name = arrayList.get(i).substring(0, dvetochki).trim();
            kolvo = arrayList.get(i).substring(dvetochki+1, arrayList.get(i).length()).trim();

            cursor = sqLiteDatabase.query(dbhelp.TABLE_NAME,
                    new String[]{dbhelp.NAME_COLUMN, dbhelp.KOLVO_COLUMN},
                    "name = ?",
                    new String[]{name},
                    null, null, null);

            if (cursor.moveToFirst()) {

                next2 = cursor.getColumnIndex(dbhelp.KOLVO_COLUMN);

                allkolvo = cursor.getString(next2);



                    result = Integer.parseInt(allkolvo) + Integer.parseInt(kolvo);
                    contentValues.put(dbhelp.NAME_COLUMN, name);
                    contentValues.put(dbhelp.KOLVO_COLUMN, String.valueOf(result));
                    sqLiteDatabase.update(dbhelp.TABLE_NAME, contentValues, "name = ?", new String[]{name});




            }
            cursor.close();
        }



    }
    public static boolean updatesklad(SQLiteDatabase sqLiteDatabase, String recept){
        boolean bool = true;
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList <Boolean> booleans = new ArrayList<Boolean>();
        ArrayList<InfoTorarsRecept> infoTorarsRecepts = new ArrayList<>();

        int hr = 0;
        int start=0;
        while(hr<recept.length()){
            hr= recept.indexOf(';',hr+1);
            if(hr==-1||start==-1){ break; }
            arrayList.add(recept.substring(start+1,hr).trim());
            start = recept.indexOf(';',hr);
        }
        Cursor cursor;
        String name;
        int dvetochki;

int result;
        int next2;
        String kolvo;
        String allkolvo;
        ContentValues contentValues = new ContentValues();
        for(int i = 0; i<arrayList.size(); i++) {
            dvetochki = arrayList.get(i).indexOf(':');
            name = arrayList.get(i).substring(0, dvetochki).trim();
            kolvo = arrayList.get(i).substring(dvetochki+1, arrayList.get(i).length()).trim();

             cursor = sqLiteDatabase.query(dbhelp.TABLE_NAME,
                    new String[]{dbhelp.NAME_COLUMN, dbhelp.KOLVO_COLUMN},
                    "name = ?",
                    new String[]{name},
                    null, null, null);

            if (cursor.moveToFirst()) {

             next2 = cursor.getColumnIndex(dbhelp.KOLVO_COLUMN);

             allkolvo = cursor.getString(next2);
             if(Integer.parseInt(kolvo)>Integer.parseInt(allkolvo)){
                 bool=false;
                 booleans.add(false);
                 break;

             }
             else {


                 result = Integer.parseInt(allkolvo) - Integer.parseInt(kolvo);
                 infoTorarsRecepts.add(new InfoTorarsRecept(name, String.valueOf(result)));

             }


            }
            cursor.close();
        }

        if(!booleans.contains(false)) {
            for (int i = 0; i < infoTorarsRecepts.size(); i++) {
                contentValues.put(dbhelp.NAME_COLUMN, infoTorarsRecepts.get(i).getNameT());
                 contentValues.put(dbhelp.KOLVO_COLUMN,infoTorarsRecepts.get(i).getResult());
                 sqLiteDatabase.update(dbhelp.TABLE_NAME, contentValues, "name = ?", new String[]{infoTorarsRecepts.get(i).getNameT()});

            }
        }





        return bool;
    }
    public static void  updatesklad2(SQLiteDatabase sqLiteDatabase, String recept){

        ArrayList<String> arrayList = new ArrayList<>();

        int hr = 0;
        int start=0;
        while(hr<recept.length()){
            hr= recept.indexOf(';',hr+1);
            if(hr==-1||start==-1){ break; }
            arrayList.add(recept.substring(start+1,hr).trim());
            start = recept.indexOf(';',hr);
        }
        Cursor cursor;
        String name;
        int dvetochki;

        int result;
        int next2;
        String kolvo;
        String allkolvo;
        ContentValues contentValues = new ContentValues();
        for(int i = 0; i<arrayList.size(); i++) {
            dvetochki = arrayList.get(i).indexOf(':');
            name = arrayList.get(i).substring(0, dvetochki).trim();
            kolvo = arrayList.get(i).substring(dvetochki+1, arrayList.get(i).length()).trim();

            cursor = sqLiteDatabase.query(dbhelp.TABLE_NAME,
                    new String[]{dbhelp.NAME_COLUMN, dbhelp.KOLVO_COLUMN},
                    "name = ?",
                    new String[]{name},
                    null, null, null);

            if (cursor.moveToFirst()) {

                next2 = cursor.getColumnIndex(dbhelp.KOLVO_COLUMN);

                allkolvo = cursor.getString(next2);
                if(Integer.parseInt(kolvo)>Integer.parseInt(allkolvo)){


                }
                else {

                    result = Integer.parseInt(allkolvo) - Integer.parseInt(kolvo);
                    contentValues.put(dbhelp.NAME_COLUMN, name);
                    contentValues.put(dbhelp.KOLVO_COLUMN, String.valueOf(result));
                    sqLiteDatabase.update(dbhelp.TABLE_NAME, contentValues, "name = ?", new String[]{name});

                }


            }
            cursor.close();
        }

    }

}

