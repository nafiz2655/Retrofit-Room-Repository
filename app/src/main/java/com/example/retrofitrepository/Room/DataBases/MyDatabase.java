package com.example.retrofitrepository.Room.DataBases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.retrofitrepository.Room.ModelRooom.Student;
import com.example.retrofitrepository.Room.RoomHelper.HelperRo;

@Database(entities = {Student.class},version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public abstract HelperRo roomHelper();

    public static MyDatabase getDatabase(Context context){
        return Room.databaseBuilder(context, MyDatabase.class,"studentInfo")
                .allowMainThreadQueries()
                .build();
    }

}
