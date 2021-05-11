package com.example.newpr;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.SparseArray;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.newpr.ui.main.PlaceholderFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
static int posotionpager;
    static  MainActivity mainActivity = new MainActivity();
dbhelp dbhelp;
    ListView listView ;
SQLiteDatabase sqLiteDatabase;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.view_pager);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), MainActivity.this);

        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        dbhelp = new dbhelp(this);
        sqLiteDatabase = dbhelp.getWritableDatabase();

viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
if(position==1){

    FruitArrayAdapter.updatelist(sqLiteDatabase);

}
        posotionpager = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
});






        ContentValues contentValues = new ContentValues();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (posotionpager) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, AddTovar.class);
                        startActivity(intent);
                        break;
                    case 1:
                        LayoutInflater li = LayoutInflater.from(MainActivity.this);
                        View promptsView = li.inflate(R.layout.dialogaddkomp, null);
                        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(MainActivity.this);


                        mDialogBuilder.setView(promptsView);


                        final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);
                        final EditText kolvoinput = promptsView.findViewById(R.id.input_text2);

                        kolvoinput.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                        mDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                              if(kolvoinput.getText().toString().isEmpty()|| userInput.getText().toString().isEmpty()){
                                                  Snackbar.make(view, "Не все поля заполнены!", Snackbar.LENGTH_LONG)
                                                          .setAction("Action", null).show();
                                                  return;

                                              }
                                                if (Methods.check(sqLiteDatabase, userInput.getText().toString(), com.example.newpr.dbhelp.TABLE_NAME,
                                                        com.example.newpr.dbhelp.NAME_COLUMN, com.example.newpr.dbhelp.KOLVO_COLUMN) == 1) {
                                                    Snackbar.make(view, "Компонент с таким названием уже существует!", Snackbar.LENGTH_LONG)
                                                            .setAction("Action", null).show();
                                                    return;
                                                }
                                                contentValues.put(dbhelp.NAME_COLUMN, userInput.getText().toString());
                                                contentValues.put(dbhelp.KOLVO_COLUMN, kolvoinput.getText().toString());
                                               sqLiteDatabase.insert(dbhelp.TABLE_NAME, null, contentValues);
                                                Toast.makeText(getBaseContext(), "Успешно сохранено!", Toast.LENGTH_LONG).show();
                                                listView =  findViewById(R.id.listview);
                                              Methods.setlist(sqLiteDatabase, listView, MainActivity.this, Popular.getVajniy());

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
                        break;
                    case 2:
                        break;
                }
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                     //   .setAction("Action", null).show();
            }
        });


    }


    public static class MyAdapter extends FragmentPagerAdapter {
        @StringRes
        private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
        private final Context mContext;


        MyAdapter(@NonNull FragmentManager fm, Context mContext) {
            super(fm);
            this.mContext = mContext;
        }




        @Override
        public int getCount() {
            return 3;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {


            switch (position) {
                case 0:

                    return new PlaceholderFragment(mContext);
                case 1:

                    return new Sklad();
                case 2:

                    return new History();

                default:

                    return  null;
            }
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            return mContext.getResources().getString(TAB_TITLES[position]);
        }

    }

}