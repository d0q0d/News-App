package com.example.aliyyyyreza.retrofit.api;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallback<T> implements Callback<T> {
    private static final String TAG = RetrofitCallback.class.getSimpleName();
    private Context mContext;
    private final Callback<T> mCallback;

    public RetrofitCallback(Context context, Callback<T> callback) {
        mContext = context;
        this.mCallback = callback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        mCallback.onResponse(call, response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        mCallback.onFailure(call, t);
    }
}