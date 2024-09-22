package com.example.retrofitrepository.Room.RoomHelper;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.retrofitrepository.Retrofit.ModelRetrofit.GetDataModel;
import com.example.retrofitrepository.Room.ModelRooom.Student;

import java.util.List;

@Dao
public interface HelperRo {

    @Insert
    void insertDataRoom(Student student);

    @Query("SELECT * FROM student ORDER BY id DESC")
    List<Student> readAllData();

}
