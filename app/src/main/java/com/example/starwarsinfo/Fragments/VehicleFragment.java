package com.example.starwarsinfo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starwarsinfo.ApiContants;
import com.example.starwarsinfo.Models.BaseVehicleObject;
import com.example.starwarsinfo.R;
import com.example.starwarsinfo.swService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VehicleFragment extends Fragment {
    RecyclerView rvVehicle;
    Retrofit retrofit;
    swService service;

    Callback<BaseVehicleObject> cbHandler = new Callback<BaseVehicleObject>() {
        @Override
        public void onResponse(Call<BaseVehicleObject> call, Response<BaseVehicleObject> response) {
            if(!response.isSuccessful()) return;

            BaseVehicleObject bvo = response.body();
            if(bvo == null || bvo.results == null) return;

            rvVehicle.setLayoutManager(new LinearLayoutManager(getContext()));
            rvVehicle.setAdapter(new VehicleAdapter(getContext(),bvo));
        }

        @Override
        public void onFailure(Call<BaseVehicleObject> call, Throwable t) {
            Log.e("TYAM",t.getMessage());
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vehicle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiContants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create((swService.class));

        rvVehicle = view.findViewById(R.id.rvFVehicle);

    }

    @Override
    public void onResume() {
        super.onResume();

        Call<BaseVehicleObject> foo = service.getVehicles();
        foo.enqueue((cbHandler));
    }
}

class VehicleAdapter extends RecyclerView.Adapter<VehicleViewHolder>{
    private final BaseVehicleObject baseObject;
    private final Context context;

    VehicleAdapter(Context context, BaseVehicleObject baseObject) {
        this.baseObject = baseObject;
        this.context = context;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vehicle,parent,false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        String name = baseObject.results.get(position).name;
        String model = baseObject.results.get(position).model;
        String manufacturer = baseObject.results.get(position).manufacturer;

        holder.bind(name,model,manufacturer);
    }

    @Override
    public int getItemCount() {
        return baseObject.results.size();
    }
}

class VehicleViewHolder extends RecyclerView.ViewHolder{
    private final TextView vehicle_name;
    private final TextView vehicle_model;
    private final TextView vehicle_manufacturer;


    public VehicleViewHolder(@NonNull View itemView) {
        super(itemView);

        vehicle_name = itemView.findViewById(R.id.tv_vehicle_name);
        vehicle_model = itemView.findViewById(R.id.tv_vehicle_model);
        vehicle_manufacturer = itemView.findViewById(R.id.tv_vehicle_manufacturer);
    }

    void bind (String name, String model, String manufacturer){
        vehicle_name.setText(name);
        vehicle_model.setText("Modelo: " + model);
        vehicle_manufacturer.setText("Fabricante: " + manufacturer);
    }
}
