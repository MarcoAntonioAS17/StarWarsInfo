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
import com.example.starwarsinfo.Models.BasePlanetObject;
import com.example.starwarsinfo.R;
import com.example.starwarsinfo.swService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlanetFragment extends Fragment {
    RecyclerView rvPlanet;
    Retrofit retrofit;
    swService service;

    Callback <BasePlanetObject> bcHandler = new Callback<BasePlanetObject>() {
        @Override
        public void onResponse(Call<BasePlanetObject> call, Response<BasePlanetObject> response) {
            if(!response.isSuccessful()) return;

            BasePlanetObject bpo = response.body();
            if(bpo == null || bpo.results == null) return;

            rvPlanet.setLayoutManager(new LinearLayoutManager(getContext()));
            rvPlanet.setAdapter(new PlanetAdapter(getContext(),bpo));
        }

        @Override
        public void onFailure(Call<BasePlanetObject> call, Throwable t) {
            Log.e("TYAM",t.getMessage());
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_planet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiContants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(swService.class);

        rvPlanet = view.findViewById(R.id.rvFPlanet);

    }

    @Override
    public void onResume() {
        super.onResume();

        Call<BasePlanetObject> foo = service.getPlanets();
        foo.enqueue(bcHandler);
    }
}

class PlanetAdapter extends RecyclerView.Adapter<PlanetViewHolder>{
    private final BasePlanetObject baseObject;
    private final Context context;

    PlanetAdapter(Context context, BasePlanetObject baseObject){
        this.baseObject = baseObject;
        this.context = context;
    }

    @NonNull
    @Override
    public PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_planet,parent,false);
        return  new PlanetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanetViewHolder holder, int position) {
        String name = baseObject.results.get(position).name;
        String diameter = baseObject.results.get(position).diameter;
        String population = baseObject.results.get(position).population;

        holder.bind(name,diameter,population);

    }

    @Override
    public int getItemCount() {
        return baseObject.results.size();
    }
}

class PlanetViewHolder extends RecyclerView.ViewHolder{
    private final TextView planet_name;
    private final TextView planet_diameter;
    private final TextView planet_population;

    public PlanetViewHolder(@NonNull View itemView) {
        super(itemView);

        planet_name = itemView.findViewById(R.id.tv_planet_name);
        planet_diameter = itemView.findViewById(R.id.tv_planet_diameter);
        planet_population = itemView.findViewById(R.id.tv_planet_population);
    }

    void bind(String name, String diameter, String population){
        planet_name.setText(name);
        planet_diameter.setText("Diametro: " + diameter);
        planet_population.setText("Poblacion " + population);
    }
}
