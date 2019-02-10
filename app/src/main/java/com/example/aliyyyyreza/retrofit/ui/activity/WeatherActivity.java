package com.example.aliyyyyreza.retrofit.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aliyyyyreza.retrofit.model.user.SignUp;
import com.example.aliyyyyreza.retrofit.model.user.Status;
import com.example.aliyyyyreza.retrofit.model.weather.Model;
import com.example.aliyyyyreza.retrofit.MyApp;
import com.example.aliyyyyreza.retrofit.R;
import com.example.aliyyyyreza.retrofit.api.RetrofitCallback;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = WeatherActivity.class.getSimpleName();
    Toolbar toolbar;
    EditText firstName, lastName;
    Button signUp;
    Button books;
    TextView spanTextView;

    RetrofitCallback<Model> modelRetrofitCallback = new RetrofitCallback<>(this, new Callback<Model>() {
        @Override
        public void onResponse(Call<Model> call, Response<Model> response) {
            Log.i(TAG, "onResponse: " + new Gson().toJson(response.body()));
            Model model = response.body();
            int x = (int) (Double.parseDouble(model.getMain().getTemp()) - 273);
            Snackbar.make(findViewById(R.id.cl1), "London : " + x + "C", Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<Model> call, Throwable t) {
            Log.i("TAG", "onFailure=" + t.getLocalizedMessage());
        }
    });
    RetrofitCallback<Status> boolianRetrofitCallback = new RetrofitCallback<>(this, new Callback<Status>() {
        @Override
        public void onResponse(Call<Status> call, Response<Status> response) {
            Log.i(TAG, "onResponse: " + new Gson().toJson(response.body()));
            Status boolian = response.body();
            Snackbar.make(findViewById(R.id.cl1), boolian.isSuccess() + " ", Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<Status> call, Throwable t) {
            Log.i("TAG", "onFailure=" + t.getLocalizedMessage());
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setupViews();
        MyApp.getApplication().setUpRetrofit();
        getWeatherInfo();
        setSpan(getResources().getString(R.string.app_name));
    }

    private void setSpan(String span) {
        Spannable spannable=new SpannableString(span);
        spannable.setSpan(new ForegroundColorSpan(Color.BLUE),0,5,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD),0,5,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanTextView.setText(spannable);
    }


    private void setupViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        spanTextView=findViewById(R.id.spanTextView);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        signUp = findViewById(R.id.signUp);
        books = findViewById(R.id.books);
        signUp.setOnClickListener(this);
        books.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUp:
                SignUp user = new SignUp();
                user.setFirstName(firstName.getText().toString());
                user.setLastName(lastName.getText().toString());
                JsonParser parser = new JsonParser();
                JsonObject o = parser.parse(new Gson().toJson(user)).getAsJsonObject();
                //Log.i(TAG, "onClick: " + o.toString());
                MyApp.myApi.saveUser(o).enqueue(boolianRetrofitCallback);
                firstName.getText().clear();
                lastName.getText().clear();
                break;
            case R.id.books:
                startActivity(new Intent(WeatherActivity.this,ArticleActivity.class));
                break;
        }
    }

    private void getWeatherInfo() {
        MyApp.myApi.getWeatherInfo().enqueue(modelRetrofitCallback);
    }
}

