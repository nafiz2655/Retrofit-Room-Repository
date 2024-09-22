package com.example.retrofitrepository.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.retrofitrepository.Adapter.MyAdpater;
import com.example.retrofitrepository.Repository.DataRepository;
import com.example.retrofitrepository.Retrofit.ModelRetrofit.GetDataModel;
import com.example.retrofitrepository.R;
import com.example.retrofitrepository.Retrofit.ServiceHelper.Helper;
import com.example.retrofitrepository.Room.ModelRooom.Student;
import com.example.retrofitrepository.Room.RoomHelper.HelperRo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;


@AndroidEntryPoint
public class DataVeiw extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdpater myAdpater;
    ArrayList<Student> arrayList;
    ProgressBar progress;
    ImageView add;
    SwipeRefreshLayout swipeRefreshLayout;


    @Inject
    Call<List<GetDataModel>> provideGetData;
    @Inject
    Helper provideHelper;
    @Inject
    HelperRo helperRo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dataveiw);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

// Initialize views
        arrayList = new ArrayList<Student>();
        recyclerView = findViewById(R.id.recyclerView);
        progress = findViewById(R.id.progress);
        add = findViewById(R.id.add);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);

        // Setup RecyclerView with LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Handle add button click to navigate to InsertData activity
        add.setOnClickListener(view -> {
            startActivity(new Intent(DataVeiw.this, DataInsert.class));
            Toast.makeText(DataVeiw.this, "Redirecting to add data", Toast.LENGTH_SHORT).show();
        });

        // Swipe to refresh setup
        swipeRefreshLayout.setOnRefreshListener(this::loadData); // Call loadData on swipe

        // Initial data load
        loadData();
    }

    public void loadData() {
      /*  // Show progress while loading data
        progress.setVisibility(View.VISIBLE);

        // Create a new Call instance each time loadData is called
        Call<List<GetDataModel>> getDataCall = provideHelper.getalldata();

        getDataCall.enqueue(new Callback<List<GetDataModel>>() {
            @Override
            public void onResponse(Call<List<GetDataModel>> call, Response<List<GetDataModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    arrayList.clear(); // Clear existing data
                    arrayList.addAll(response.body()); // Add new data

                    // Set adapter or notify existing one of the data change
                    if (myAdpater == null) {
                        myAdpater = new MyAdpater(arrayList, DataVeiw.this, provideHelper,DataVeiw.this);
                        recyclerView.setAdapter(myAdpater);
                    } else {
                        myAdpater.notifyDataSetChanged();
                    }

                    Toast.makeText(DataVeiw.this, "Data loaded: " + arrayList.size() + " items", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DataVeiw.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                }

                // Hide progress and stop refreshing animation
                progress.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<GetDataModel>> call, Throwable throwable) {
                Toast.makeText(DataVeiw.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

*/

        DataRepository dataRepository = new DataRepository(DataVeiw.this,helperRo,provideGetData);
        dataRepository.getData(new DataRepository.DataCallback() {
            @Override
            public void onSuccess(List<Student> studentList) {
                arrayList.addAll(studentList);

                // Set adapter or notify existing one of the data change
                if (myAdpater == null) {
                    myAdpater = new MyAdpater(arrayList, DataVeiw.this, provideHelper, DataVeiw.this);
                    recyclerView.setAdapter(myAdpater);
                    progress.setVisibility(View.GONE);
                } else {
                    myAdpater.notifyDataSetChanged();
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(DataVeiw.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }
        });

    }
}
