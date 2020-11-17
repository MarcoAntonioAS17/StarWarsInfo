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
import com.example.starwarsinfo.Models.BasePeopleObject;
import com.example.starwarsinfo.R;
import com.example.starwarsinfo.swService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PeopleFragment extends Fragment {
    RecyclerView rvPeople;
    Retrofit retrofit;
    swService service;

    Callback<BasePeopleObject> cbHandler = new Callback<BasePeopleObject>() {
        @Override
        public void onResponse(Call<BasePeopleObject> call, Response<BasePeopleObject> response) {
            Log.e("TYAM","Web Response");
            if (!response.isSuccessful()) {
                Log.e("TYAM","Error "+ response.isSuccessful());
                return;
            }

            BasePeopleObject bpo = response.body();
            if (bpo == null || bpo.results == null) return;

            Log.e("TYAM","Dibujando datos");

            rvPeople.setLayoutManager(new LinearLayoutManager(getContext()));
            rvPeople.setAdapter(new PeopleAdapter(getContext(),bpo));
        }

        @Override
        public void onFailure(Call<BasePeopleObject> call, Throwable t) {
            Log.e("TYAM", t.getMessage());
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_people, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiContants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(swService.class);

        rvPeople = view.findViewById(R.id.rvFPeople);
        Log.e("TYAM","onViewCreated Exit");
    }

    @Override
    public void onResume() {
        super.onResume();

        Call<BasePeopleObject> foo = service.getPeople();
        foo.enqueue(cbHandler);
        Log.e("TYAM","onResume Finished");
    }
}


class PeopleAdapter extends RecyclerView.Adapter<PeopleViewHolder>{
    private final BasePeopleObject baseObject;
    private final Context context;

    PeopleAdapter(Context context, BasePeopleObject baseObject){
        this.baseObject = baseObject;
        this.context = context;
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate (R.layout.item_people, parent, false);
        return new  PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, int position) {
        String name =baseObject.results.get(position).name;
        String height =baseObject.results.get(position).height;
        String year_birth =baseObject.results.get(position).birth_year;

        holder.bind(name,height,year_birth);
    }

    @Override
    public int getItemCount() {
        return  baseObject.results.size();
    }
}

class PeopleViewHolder extends RecyclerView.ViewHolder {
    private final TextView people_name;
    private final TextView people_height;
    private final TextView people_birth_year;

    public PeopleViewHolder(@NonNull View itemView) {
        super(itemView);

        people_name = itemView.findViewById(R.id.tv_people_name);
        people_height = itemView.findViewById(R.id.tv_people_height);
        people_birth_year = itemView.findViewById(R.id.tv_people_yearbirth);
    }

    void bind (String name, String height, String birth){
        people_name.setText(name);
        people_height.setText("Altura: " + height);
        people_birth_year.setText("Nacimiento: " + birth);
    }
}
