package com.example.retrofitrepository.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.retrofitrepository.Retrofit.ModelRetrofit.DeleteDataModel;
import com.example.retrofitrepository.Retrofit.ModelRetrofit.GetDataModel;
import com.example.retrofitrepository.R;
import com.example.retrofitrepository.Retrofit.ServiceHelper.Helper;
import com.example.retrofitrepository.Room.ModelRooom.Student;
import com.example.retrofitrepository.View.DataUpdate;
import com.example.retrofitrepository.View.DataVeiw;


import java.util.ArrayList;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdpater extends RecyclerView.Adapter<MyAdpater.ViewHolder>{



    ArrayList<Student> arrayList;
    Context context;



    @Inject
    Helper provideHelper;

    private DataVeiw dataView;

    public MyAdpater() {
    }


    public MyAdpater(ArrayList<Student> arrayList, Context context, Helper provideHelper, DataVeiw dataView) {
        this.arrayList = arrayList;
        this.context = context;
        this.provideHelper = provideHelper;
        this.dataView = dataView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,roll,reg,phone,subject,address,tv_uptade,tv_delete;
        CircleImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            roll = itemView.findViewById(R.id.roll);
            reg = itemView.findViewById(R.id.reg);
            phone = itemView.findViewById(R.id.phone);
            subject = itemView.findViewById(R.id.subject);
            address = itemView.findViewById(R.id.address);
            profile_image = itemView.findViewById(R.id.profile_image);
            tv_uptade = itemView.findViewById(R.id.tv_uptade);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Student dataModel = arrayList.get(position);


        String sname = dataModel.getName();
        String sroll = dataModel.getRoll();
        String srej = dataModel.getReg();
        String sphone = dataModel.getPhone();
        String ssub = dataModel.getSub();
        String sadd = dataModel.getAddress();

        holder.name.setText(sname);
        holder.roll.setText(sroll);
        holder.reg.setText(srej);
        holder.phone.setText(sphone);
        holder.subject.setText(ssub);
        holder.address.setText(sadd);

        String url = "https://maltinamax.quillncart.com/appsdta/test/retrofit/images/"+dataModel.getImage();

        String uri = dataModel.getImage();


        Glide.with(context).load(url)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .into(holder.profile_image);

        holder.tv_uptade.setOnClickListener( view -> {

            DataUpdate.ID = String.valueOf(position);
            DataUpdate.ADD= sadd;
            DataUpdate.NAME = sname;
            DataUpdate.ROLL = sroll;
            DataUpdate.REG = srej;
            DataUpdate.PHONE = sphone;
            DataUpdate.SUB = ssub;
            DataUpdate.URL = uri;
            context.startActivity(new Intent(context, DataUpdate.class));
        });

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer i_id = Integer.parseInt("10");


                provideHelper.deleteData(new DeleteDataModel(i_id)).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(context, "Data Update success", Toast.LENGTH_SHORT).show();

                        // Reload the data in DataView
                        if (context instanceof DataVeiw) {
                            ((DataVeiw) context).loadData(); // Reload the data in the main activity
                        }else {
                            Toast.makeText(context, "Data Deletion Failed", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(context, "Data Update Failed", Toast.LENGTH_SHORT).show();


                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
