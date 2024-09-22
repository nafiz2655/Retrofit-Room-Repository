package com.example.retrofitrepository.Repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.retrofitrepository.Retrofit.ModelRetrofit.GetDataModel;
import com.example.retrofitrepository.Room.ModelRooom.Student;
import com.example.retrofitrepository.Room.RoomHelper.HelperRo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private Context context;
    @Inject
    HelperRo helperRo;
    @Inject
    Call<List<GetDataModel>> provideGetData;

    public DataRepository(Context context, HelperRo helperRo, Call<List<GetDataModel>> provideGetData) {
        this.context = context;
        this.helperRo = helperRo;
        this.provideGetData = provideGetData;
    }

    // Define a callback interface
    public interface DataCallback {
        void onSuccess(List<Student> studentList);
        void onFailure(String errorMessage);
    }

    // Fetch data from either network or Room and use the callback to return the data
    public void getData(DataCallback callback) {
        try {
            if (checkInternet()) {
                // Fetch data from Retrofit (API)
                fetchFromNetwork(callback);
            } else {
                // Fetch data from Room (Local DB)
                List<Student> studentList = helperRo.readAllData();
                if (studentList != null && !studentList.isEmpty()) {
                    callback.onSuccess(studentList); // Data available locally
                } else {
                    callback.onFailure("No data available locally.");
                }
            }
        } catch (Exception e) {
            callback.onFailure(e.getMessage());
        }
    }

    private void fetchFromNetwork(DataCallback callback) {
        provideGetData.enqueue(new Callback<List<GetDataModel>>() {
            @Override
            public void onResponse(Call<List<GetDataModel>> call, Response<List<GetDataModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Student> studentList = new ArrayList<>();
                    for (GetDataModel getDataModel : response.body()) {
                        Student student = new Student();
                        student.setRetrofit_id(getDataModel.getId());
                        student.setName(getDataModel.getName());
                        student.setRoll(getDataModel.getRoll());
                        student.setReg(getDataModel.getRej());
                        student.setSub(getDataModel.getSub());
                        student.setPhone(getDataModel.getPhone());
                        student.setAddress(getDataModel.getAddress());
                        student.setImage(getDataModel.getImage());

                        helperRo.insertDataRoom(student);  // Save data locally
                        studentList.add(student);  // Add to list
                    }
                    callback.onSuccess(studentList);  // Return fetched data
                } else {
                    callback.onFailure("Failed to retrieve data from server.");
                }
            }

            @Override
            public void onFailure(Call<List<GetDataModel>> call, Throwable throwable) {
                callback.onFailure(throwable.getMessage()); // Handle failure
            }
        });
    }

    private boolean checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
