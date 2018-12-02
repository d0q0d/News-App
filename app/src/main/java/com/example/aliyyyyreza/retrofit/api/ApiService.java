package com.example.aliyyyyreza.retrofit.api;

import com.example.aliyyyyreza.retrofit.Model.User.Status;
import com.example.aliyyyyreza.retrofit.Model.Weather.Model;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=cc41d19e7fefc3fcded36d2309230efe")
    Call<Model> getWeatherInfo();

    //REPLACE!!!!!!!!!!
    @POST("http://192.168.1.53/MyApp.dev/SaveUser.php")
    Call<Status> saveUser(@Body JsonObject jsonObject);
}

