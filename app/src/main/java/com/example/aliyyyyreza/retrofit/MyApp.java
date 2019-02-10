package com.example.aliyyyyreza.retrofit;

import android.app.Application;
import androidx.room.Room;

import com.example.aliyyyyreza.retrofit.api.ApiClient;
import com.example.aliyyyyreza.retrofit.api.ApiService;

import retrofit2.Retrofit;

public class MyApp extends Application {
    public static MyApp myApp;
    public static Retrofit retrofit;
    public static ApiService myApi;

    public static MyApp getApplication() {
        if (myApp == null) {
            myApp = new MyApp();
        }
        return myApp;
    }

    public void setUpRetrofit() {
        retrofit = ApiClient.getClient();
        myApi = retrofit.create(ApiService.class);
    }
}
