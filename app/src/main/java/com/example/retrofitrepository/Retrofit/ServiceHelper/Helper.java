package com.example.retrofitrepository.Retrofit.ServiceHelper;


import com.example.retrofitrepository.Retrofit.ModelRetrofit.DeleteDataModel;
import com.example.retrofitrepository.Retrofit.ModelRetrofit.GetDataModel;
import com.example.retrofitrepository.Retrofit.ModelRetrofit.InsertDataModel;
import com.example.retrofitrepository.Retrofit.ModelRetrofit.UpdateDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Helper {

    //Insert Data
    @POST("insertimage.php")
    Call<Void> insertData(@Body InsertDataModel insertDataModel);

    //getData
    @GET("getdata.php")
    Call<List<GetDataModel>> getalldata();


    // if no image select
    @POST("updatedata.php")
    Call<Void> updatedata(@Body UpdateDataModel updateDataModel);

    // if new image select
    @POST("update2.php")
    Call<Void> updatedataimage(@Body UpdateDataModel updateDataModel);

    @POST("deletedata.php")
    Call<Void> deleteData(@Body DeleteDataModel deleteDataModel);
}
