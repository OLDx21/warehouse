package com.example.newpr;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class AdapterHistory extends ArrayAdapter<HistoryClas> {
    SQLiteDatabase sqLiteDatabase;
    dbhelp dbhelp;
    ListView listView;

    private static final String TAG = "FruitArrayAdapter";
    private List<HistoryClas> fruitList = new ArrayList<HistoryClas>();
    static ArrayList<INfo> arrayList = new ArrayList<>();

    static class FruitViewHolder {
        TextView fruitImg;
        TextView fruitName;
        ImageButton calories;
    }

    static void put(ArrayList<INfo> arrayList2) {
        AdapterHistory.arrayList.addAll(arrayList2);

    }

    public AdapterHistory(Context context, int textViewResourceId) {
        super(context, textViewResourceId);

    }

    @Override
    public void add(HistoryClas object) {
        fruitList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.fruitList.size();
    }

    @Override
    public HistoryClas getItem(int index) {
        return this.fruitList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        FruitViewHolder viewHolder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.customhistory, parent, false);

            viewHolder = new FruitViewHolder();
            viewHolder.fruitImg = (TextView) row.findViewById(R.id.NAmeZ);
            viewHolder.fruitName = (TextView) row.findViewById(R.id.Time);
            viewHolder.calories = (ImageButton) row.findViewById(R.id.btnotmena);


            row.setTag(viewHolder);
        } else {
            viewHolder = (FruitViewHolder) row.getTag();
        }
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.history, parent, false);
        listView = root.findViewById(R.id.list2);
        AdapterHistory fruitArrayAdapter = History.adapterHistory;
        HistoryClas fruit = getItem(position);

        viewHolder.calories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbhelp = new dbhelp(v.getContext());
                sqLiteDatabase = dbhelp.getWritableDatabase();
                Methods.deleteitem(sqLiteDatabase, arrayList.get(position).getKode(),arrayList.get(position).getName());

                fruitList.remove(position);
                fruitArrayAdapter.remove(new HistoryClas("", arrayList.get(position).getDate(), arrayList.get(position).getName()));
                fruitArrayAdapter.notifyDataSetChanged();
                arrayList.remove(position);

   }
        });
        viewHolder.fruitName.setText(fruit.getFruitName());
        viewHolder.fruitImg.setText(fruit.getCalories());
        return row;
    }


}
