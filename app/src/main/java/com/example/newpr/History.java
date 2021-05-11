package com.example.newpr;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class History extends Fragment {
    SQLiteDatabase sqLiteDatabase;
    dbhelp dbhelp;
 static   AdapterHistory adapterHistory;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.history, container, false);
        ListView listView = root.findViewById(R.id.list2);
        dbhelp = new dbhelp(getContext());
        sqLiteDatabase = dbhelp.getWritableDatabase();


        Methods.arthistory(sqLiteDatabase,  listView);

        return root;
    }



}
