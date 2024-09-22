package com.example.retrofitrepository.Hilt.Module;

import android.content.Context;

import com.example.retrofitrepository.Retrofit.ApiService.Services;
import com.example.retrofitrepository.Retrofit.ModelRetrofit.GetDataModel;
import com.example.retrofitrepository.Retrofit.ServiceHelper.Helper;
import com.example.retrofitrepository.Room.DataBases.MyDatabase;
import com.example.retrofitrepository.Room.RoomHelper.HelperRo;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Call;

@Module
@InstallIn(SingletonComponent.class)
public class SingletonModule {
    //Retrofit Requeat
    @Provides
    @Singleton
    public Helper provideHelper() {
        return Services.getAPI().create(Helper.class);
    }

    @Provides
    @Singleton
    public Call<List<GetDataModel>> provideGetData(Helper helper) {
        return helper.getalldata();
    }
    //Rooom Request
    @Provides
    @Singleton
    public MyDatabase myDatabase(@ApplicationContext Context context){
        return MyDatabase.getDatabase(context);
    }
    @Provides
    @Singleton
    public HelperRo helperRo(MyDatabase myDatabase){
        return myDatabase.roomHelper();
    }





}
