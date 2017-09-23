package com.example.android.habitstracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.habitstracker.data.HabitContract.HabitEntry;
import com.example.android.habitstracker.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new HabitDbHelper(this);
    }

    private void displayDatabaseInfo(){

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_DESC,
                HabitEntry.COLUMN_HABIT_STATUS};

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        //get text view from activity
        TextView txtView = (TextView)findViewById(R.id.main_text);

        try{

            txtView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
            txtView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT_DESC+ " - " +
                    HabitEntry.COLUMN_HABIT_STATUS+ "\n" );

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int descColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DESC);
            int statusColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_STATUS);

            while(cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                String currentDesc = cursor.getString(descColumnIndex);
                String currentStatus = cursor.getString(statusColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                txtView.append(("\n" + currentID + " - " +
                        currentDesc + " - " +
                        currentStatus));
            }

        }catch(Exception ex){

        }finally {
            cursor.close();
        }
    }

    private void insertHabit(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(HabitEntry.COLUMN_HABIT_DESC, "habit desctiption");
        cv.put(HabitEntry.COLUMN_HABIT_STATUS, HabitEntry.STATUS_TODO);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, cv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertHabit();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
