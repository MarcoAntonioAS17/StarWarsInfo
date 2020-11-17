package com.example.starwarsinfo;

import com.example.starwarsinfo.Models.BasePeopleObject;
import com.example.starwarsinfo.Models.BasePlanetObject;
import com.example.starwarsinfo.Models.BaseVehicleObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface swService {

    @GET (ApiContants.PEOPLE_URL)
    Call<BasePeopleObject> getPeople();

    @GET (ApiContants.PLANETS_URL)
    Call<BasePlanetObject> getPlanets();

    @GET (ApiContants.VEHICLES_URL)
    Call<BaseVehicleObject> getVehicles();
}
