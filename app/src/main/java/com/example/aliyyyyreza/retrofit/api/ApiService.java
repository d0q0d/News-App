package com.example.aliyyyyreza.retrofit.api;

import com.example.aliyyyyreza.retrofit.model.article.Articles;
import com.example.aliyyyyreza.retrofit.model.rvmodel.RvModel;
import com.example.aliyyyyreza.retrofit.model.user.Status;
import com.example.aliyyyyreza.retrofit.model.weather.Model;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=cc41d19e7fefc3fcded36d2309230efe")
    Call<Model> getWeatherInfo();

    @GET("https://newsapi.org/v2/top-headlines?country=us&apiKey=1e2e32b9d6994319b4e344304bf8bf5f")
    Call<Articles> getArticles(@Query("page") int page);

    //REPLACE!!!!!!!!!!
    @POST("http://192.168.1.53/MyApp.dev/SaveUser.php")
    Call<Status> saveUser(@Body JsonObject jsonObject);
}

