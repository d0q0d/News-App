package com.example.aliyyyyreza.retrofit.ui.fragment;

import androidx.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aliyyyyreza.retrofit.MyApp;
import com.example.aliyyyyreza.retrofit.R;
import com.example.aliyyyyreza.retrofit.api.RetrofitCallback;
import com.example.aliyyyyreza.retrofit.model.article.Articles;
import com.example.aliyyyyreza.retrofit.model.room.Article;
import com.example.aliyyyyreza.retrofit.model.room.Database;
import com.example.aliyyyyreza.retrofit.model.rvmodel.RvModel;
import com.example.aliyyyyreza.retrofit.ui.adapter.RecyclerAdapter;
import com.example.aliyyyyreza.retrofit.util.EndlessRecyclerViewScrollListener;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class MainFragment extends Fragment {
    private static final String DATABASE_NAME = "db";
    Database database;
    private static final String TAG = MainFragment.class.getSimpleName();
    public static RecyclerAdapter recyclerAdapter;
    private ArrayList<RvModel> models;
    private List<Article> roomModels;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private int pages = 1;
    private RecyclerView recyclerView_Main;
    private LinearLayoutManager linearLayoutManager;
    private CheckNetwork checkNetwork;
    List<Article> articles=new ArrayList<>();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            database.access().addArticles(roomModels);
            roomModels.clear();
        }
    };
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            articles=database.access().getUsers();
        }
    };

    private RetrofitCallback<Articles> listRetrofitCallback = new RetrofitCallback<>(getContext(), new Callback<Articles>() {
        @Override
        public void onResponse(Call<Articles> call, Response<Articles> response) {
            ++pages;
            Log.i(TAG, "onResponse: " + new Gson().toJson(response.body()));
            Articles articles = response.body();
            for (int i = 0; i < articles.getArticles().size(); i++) {
                RvModel rvModel = new RvModel();
                Article article = new Article();
                rvModel.setTitle(articles.getArticles().get(i).getTitle());
                rvModel.setContent(articles.getArticles().get(i).getContent());
                rvModel.setImageUrl(articles.getArticles().get(i).getUrlToImage());
               /* article.setTitle(articles.getArticles().get(i).getTitle());
                article.setContent(articles.getArticles().get(i).getContent());
                article.setImageUrl(articles.getArticles().get(i).getUrlToImage());*/
                models.add(rvModel);
//                roomModels.add(article);
            }
            recyclerAdapter.addArticles(models);
            //new Thread(runnable).start();
            models.clear();
            avLoadingIndicatorView.hide();
        }

        @Override
        public void onFailure(Call<Articles> call, Throwable t) {
            Log.i(TAG, "onResponse: " + t.getLocalizedMessage());
        }
    });

    @Override
    public void onStart() {
        super.onStart();
        checkNetwork = new CheckNetwork();
        getActivity().registerReceiver(checkNetwork, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(checkNetwork);
        super.onStop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp.getApplication().setUpRetrofit();
        /*database = Room.databaseBuilder(getActivity(), Database.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        new Thread(runnable2).start();*/
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews(view);
    }

    private void setUpViews(View view) {
        models = new ArrayList<>();
        roomModels = new ArrayList<>();
        recyclerView_Main = view.findViewById(R.id.recyclerBooks);
        avLoadingIndicatorView = view.findViewById(R.id.avi);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerAdapter = new RecyclerAdapter(getActivity());
        recyclerView_Main.setAdapter(recyclerAdapter);
        recyclerView_Main.setLayoutManager(linearLayoutManager);
        recyclerView_Main.setOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (isConnected()) {
                    getArticles(pages);
                } else {
                    avLoadingIndicatorView.hide();
                    Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getArticles(int page) {
        MyApp.myApi.getArticles(page).enqueue(listRetrofitCallback);
        avLoadingIndicatorView.show();
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        return isConnected;
    }

    private class CheckNetwork extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (isConnected()) {
                roomModels.clear();
                getArticles(pages);

            } else {
                avLoadingIndicatorView.hide();
                Toast.makeText(context, "No Internet", Toast.LENGTH_LONG).show();
              /*  ArrayList<RvModel> arrayList=new ArrayList<>();
                for (int i = 0; i <articles.size() ; i++) {
                    RvModel rvModel = new RvModel();
                    rvModel.setTitle(articles.get(i).getTitle());
                    rvModel.setContent(articles.get(i).getContent());
                    rvModel.setImageUrl(articles.get(i).getImageUrl());
                    arrayList.add(rvModel);
                }
                recyclerAdapter.addArticles(arrayList);*/
            }
        }
    }
}
