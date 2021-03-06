package com.example.newpr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class UpdateProduct extends AppCompatActivity {
    public static void setName(String name) {
        UpdateProduct.name = name;
    }
TextView textView;
    static String name;
    com.example.newpr.dbhelp dbhelp;
    SQLiteDatabase sqLiteDatabase;
    Button save;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateProduct.this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar2, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.savebtn2:
              save.performClick();

                break;


        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeat);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("???????????????????? ??????????????");

        textView = findViewById(R.id.tvText);
        textView.setText(name);
        save = new Button(this);
        save.setVisibility(-1);
        @SuppressLint("ResourceType") LinearLayout constraintLayout = (LinearLayout) findViewById(R.id.updatetovar);
        Display display = getWindowManager().getDefaultDisplay();
        int widght = display.getWidth();

        ArrayList<EditText> editTexts1 = new ArrayList<>();
        ArrayList<TextView> textViews1 = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<EditText> editTexts2 = new ArrayList<>();
        ArrayList<TextView> textViews2 = new ArrayList<>();
        GridLayout layout = new GridLayout(this);
        layout.setOrientation(0);
        layout.setColumnCount(2);
        ScrollView scrollView = new ScrollView(this);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(7);
        FloatingActionButton floatingActionButton = new FloatingActionButton(this);

        dbhelp = new dbhelp(this);
        sqLiteDatabase = dbhelp.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < Methods.arrayList.size(); i++) {
            EditText textView = new EditText(this);
            EditText editText = new EditText(this);
            textView.setText("   " + Methods.arrayList.get(i));
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setWidth(widght - (widght / 4));
            textView.setBackgroundResource((android.R.drawable.edit_text));
            textView.setEnabled(false);
            textView.setShadowLayer(5f, 2, 2, 0xFFFFFFFF);

            editText.setRawInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setWidth(widght / 4);
            editText.setFilters(filters);
            editText.setTextColor(Color.parseColor("#BAF011"));


            editText.setBackgroundResource((android.R.drawable.edit_text));

            editTexts1.add(editText);
            textViews1.add(textView);
            layout.addView(textView);
            layout.addView(editText);


        }
        layout.addView(save);
        scrollView.addView(layout);
        constraintLayout.addView(scrollView);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < textViews1.size(); i++) {

                    if (!editTexts1.get(i).getText().toString().isEmpty()) {
                        editTexts2.add(editTexts1.get(i));
                        textViews2.add(textViews1.get(i));
                    }

                }
             if(editTexts2.isEmpty()){
                 Snackbar.make(v, "???????????? ?????????????????? ?????????? ?? zero ??????????????????????!", Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show();
                 return;
             }
                for (int i = 0; i < textViews2.size(); i++) {


                    stringBuilder.append(textViews2.get(i).getText().toString() + " : " + editTexts2.get(i).getText().toString()+";");

                }

                contentValues.put(com.example.newpr.dbhelp.NAME_COLUMN2, name);
                contentValues.put(com.example.newpr.dbhelp.RECEPT, stringBuilder.toString());

                sqLiteDatabase.update(dbhelp.TABLE_NAME2, contentValues, "nametovar = ?", new String[]{name});

                Snackbar.make(v, "?????????????? ??????????????????!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();




            }
        });




    }
}
