package com.example.android.habitstracker.data;

import android.provider.BaseColumns;

/**
 * Created by avishai on 9/23/2017.
 */

public class HabitContract {

    private HabitContract(){}

    public static final class HabitEntry implements BaseColumns{

        public final static String TABLE_NAME = "Habits";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABIT_DESC ="description";
        public final static String COLUMN_HABIT_STATUS = "status";

        public static  final int STATUS_TODO = 0;
        public static  final int STATUS_HALF_DONE = 0;
        public static  final int STATUS_DONE = 0;

        public Boolean isValidStatus(int status){
            if(status == STATUS_TODO || status == STATUS_HALF_DONE || status == STATUS_DONE){
                return true;
            }
            else{
                return false;
            }
        }
    }
}
