package com.example.newpr;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sklad extends Fragment {
SQLiteDatabase sqLiteDatabase;
dbhelp dbhelp;
public static FruitArrayAdapter fruitArrayAdapter;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.sklad, container, false);
        ListView listView = root.findViewById(R.id.listview);
dbhelp = new dbhelp(getContext());
sqLiteDatabase = dbhelp.getWritableDatabase();
        int fruitImgResId = getResources().getIdentifier("product", "drawable-v24", "com.javapapers.android.NewPr.app");
        Popular.setVajniy(fruitImgResId);

        @SuppressLint("ResourceType") LinearLayout layout = (LinearLayout) root.findViewById(R.id.lay25);

Methods.setlist(sqLiteDatabase, listView, fruitImgResId);

listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.updatekolvo, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());

        ContentValues contentValues = new ContentValues();

        mDialogBuilder.setView(promptsView);


        final EditText userInput = (EditText) promptsView.findViewById(R.id.newkolvo);


        userInput.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                if(userInput.getText().toString().isEmpty()){

                                    return;

                                }


                                contentValues.put(com.example.newpr.dbhelp.NAME_COLUMN, Methods.arrayList.get(position));
                                contentValues.put(com.example.newpr.dbhelp.KOLVO_COLUMN, userInput.getText().toString());
                                sqLiteDatabase.update(com.example.newpr.dbhelp.TABLE_NAME,  contentValues,"name = ?", new String[]{Methods.arrayList.get(position)});
                                Toast.makeText(getContext(), "Успешно обновлено!", Toast.LENGTH_LONG).show();
                              ListView  listView =  root.findViewById(R.id.listview);
                                Methods.setlist(sqLiteDatabase, listView, getContext(), Popular.getVajniy());

                            }
                        })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });


        AlertDialog alertDialog = mDialogBuilder.create();

        alertDialog.show();
        return false;
    }
});


        return root;
    }

}
