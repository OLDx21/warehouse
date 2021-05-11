package com.example.newpr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newpr.ui.main.PlaceholderFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddTovar extends Activity {
    TextInputLayout text;
    Button button;
dbhelp dbhelp;
SQLiteDatabase sqLiteDatabase;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddTovar.this, MainActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"WrongConstant", "Range", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogaddtovar);


        text = findViewById(R.id.sec);
        button = findViewById(R.id.save);
        @SuppressLint("ResourceType") LinearLayout constraintLayout = (LinearLayout) findViewById(R.id.layout_root);
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

        scrollView.addView(layout);
        constraintLayout.addView(scrollView);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTexts2.clear();
                textViews2.clear();
                for (int i = 0; i < textViews1.size(); i++) {

                    if (!editTexts1.get(i).getText().toString().isEmpty()) {
                        editTexts2.add(editTexts1.get(i));
                        textViews2.add(textViews1.get(i));
                    }

                }
                if (text.getEditText().getText().toString().isEmpty()){
                    Snackbar.make(v, "Поле для названия не может быть пустым!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;

                }
                if(editTexts2.isEmpty()){
                    Snackbar.make(v, "Нельзя добавлять товар с zero компонентов!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;

                }

                if (Methods.check(sqLiteDatabase, text.getEditText().getText().toString(), com.example.newpr.dbhelp.TABLE_NAME2,
                        com.example.newpr.dbhelp.NAME_COLUMN2, com.example.newpr.dbhelp.RECEPT) == 1) {
                    Snackbar.make(v, "Товар с таким названием уже существует!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                for (int i = 0; i < textViews2.size(); i++) {


                    stringBuilder.append(textViews2.get(i).getText().toString() + " : " + editTexts2.get(i).getText().toString()+";");

                }
                contentValues.put(com.example.newpr.dbhelp.NAME_COLUMN2, text.getEditText().getText().toString());
                contentValues.put(com.example.newpr.dbhelp.RECEPT, stringBuilder.toString());
                sqLiteDatabase.insert(com.example.newpr.dbhelp.TABLE_NAME2, null, contentValues);
                Snackbar.make(v, "Успешно сохранено!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();



                stringBuilder.setLength(0);
                textViews2.clear();
                editTexts2.clear();
            }
        });

    }
}
