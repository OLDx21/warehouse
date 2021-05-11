package com.example.newpr.ui.main;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.newpr.*;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.newpr.dbhelp;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
 public static   Context context;

    Button button;
    com.example.newpr.dbhelp dbhelp;
    SQLiteDatabase sqLiteDatabase;
    boolean check;
    ContentValues contentValues10;

    private static final String ARG_SECTION_NUMBER = "section_number";
    public PlaceholderFragment (){


    }
public PlaceholderFragment (Context context){
    this.context = context;

}

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 dbhelp = new dbhelp(getContext());
 sqLiteDatabase = dbhelp.getWritableDatabase();

        int index = 2;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        int fruitImgResId = getResources().getIdentifier("product", "drawable-v24", "com.javapapers.android.NewPr.app");
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");

        @SuppressLint("ResourceType") LinearLayout constraintLayout = (LinearLayout) root.findViewById(R.id.constraintLayout);


        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int widght = display.getWidth()/4;

      ScrollView scrollView =  Methods.artbuttons(sqLiteDatabase, context, widght);


      constraintLayout.clearAnimation();
        constraintLayout.clearDisappearingChildren();

        constraintLayout.addView(scrollView);

        for(int i = 0; i<Methods.buttonArrayList.size(); i++){
            int finalI = i;
            Methods.buttonArrayList.get(i).setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(getContext(), UpdateProduct.class);
                    UpdateProduct.setName((String) Methods.buttonArrayList.get(finalI).getText());
                    startActivity(intent);
                    return false;
                }
            });

            Methods.buttonArrayList.get(i).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {

check= Methods.updatesklad(sqLiteDatabase, Methods.infoTovars.get(finalI).getRecept());

if(!check){   Snackbar.make(v, "Не хватает ингредиентов!", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();}


                    Cursor cursor2 = sqLiteDatabase.query(com.example.newpr.dbhelp.TABLE_NAME3, new String[]{com.example.newpr.dbhelp.COLUMN_NAME_OTMENA,
                                    com.example.newpr.dbhelp.CULUMN_DATE, com.example.newpr.dbhelp.COLUMN_KODE},
                            null,
                            null, null, null, null);

                    ArrayList<INfo> tester = new ArrayList<>();
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


                        }
                        while (cursor2.moveToNext());
                    }
                    cursor2.close();

                    String g = randomcode();

                    Date date = new Date();

                    tester.add(0, new INfo(Methods.buttonArrayList.get(finalI).getText().toString(), dateFormat.format(date), g));


                    contentValues10 = new ContentValues();


                    sqLiteDatabase.delete(com.example.newpr.dbhelp.TABLE_NAME3, null, null);
if(tester.size()>=10) {

    for (int i = 0; i < 10; i++) {


        contentValues10.put(com.example.newpr.dbhelp.COLUMN_NAME_OTMENA, tester.get(i).getName());
        contentValues10.put(com.example.newpr.dbhelp.CULUMN_DATE, tester.get(i).getDate());
        contentValues10.put(com.example.newpr.dbhelp.COLUMN_KODE, tester.get(i).getKode());
        sqLiteDatabase.insert(com.example.newpr.dbhelp.TABLE_NAME3, null, contentValues10);
        contentValues10.clear();

    }


}
else {
    for (int i = 0; i < tester.size(); i++) {


        contentValues10.put(com.example.newpr.dbhelp.COLUMN_NAME_OTMENA, tester.get(i).getName());
        contentValues10.put(com.example.newpr.dbhelp.CULUMN_DATE, tester.get(i).getDate());
        contentValues10.put(com.example.newpr.dbhelp.COLUMN_KODE, tester.get(i).getKode());
        sqLiteDatabase.insert(com.example.newpr.dbhelp.TABLE_NAME3, null, contentValues10);
        contentValues10.clear();

    }

}


tester = null;


                }
            });

        }



        return root;
    }
    public String randomcode(){
        int g = (int) (Math.random()*100000);


        return String.valueOf(g);

    }

}