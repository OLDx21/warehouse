package com.example.newpr;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FruitArrayAdapter extends ArrayAdapter<Fruit> {
    private static final String TAG = "FruitArrayAdapter";
	public static List<Fruit> fruitList = new ArrayList<Fruit>();

    static class FruitViewHolder {
        ImageView fruitImg;
        TextView fruitName;
        TextView calories;
    }

    public FruitArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

	@Override
	public void add(Fruit object) {
		fruitList.add(object);
		super.add(object);
	}

    @Override
	public int getCount() {
		return this.fruitList.size();
	}

    @Override
	public Fruit getItem(int index) {
		return this.fruitList.get(index);
	}

    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
        FruitViewHolder viewHolder;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.customlist, parent, false);
            viewHolder = new FruitViewHolder();
            viewHolder.fruitImg = (ImageView) row.findViewById(R.id.fruitImg);
            viewHolder.fruitName = (TextView) row.findViewById(R.id.fruitName);
            viewHolder.calories = (TextView) row.findViewById(R.id.calories);


            row.setTag(viewHolder);
		} else {
            viewHolder = (FruitViewHolder)row.getTag();
        }
		Fruit fruit = getItem(position);


        row.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {

        return false;
    }
});
        viewHolder.fruitName.setText(fruit.getFruitName());
        viewHolder.calories.setText(fruit.getCalories());
		return row;
	}
public static void updatelist(SQLiteDatabase sqLiteDatabase){

        fruitList.clear();
    Cursor cursor = sqLiteDatabase.query(com.example.newpr.dbhelp.TABLE_NAME, null, null, null, null, null, null);



    String name;
    String kolvo;

    if (cursor.moveToFirst()) {
        int nameid = cursor.getColumnIndex(com.example.newpr.dbhelp.NAME_COLUMN);
        int kolvotable = cursor.getColumnIndex(com.example.newpr.dbhelp.KOLVO_COLUMN);

        do {

            name = cursor.getString(nameid);
            kolvo = cursor.getString(kolvotable);



            Fruit fruit = new Fruit(Popular.getVajniy(),name,kolvo);
            fruitList.add(fruit);

        }

        while (cursor.moveToNext());
    }

    cursor.close();

    Sklad.fruitArrayAdapter.notifyDataSetChanged();

}


}
