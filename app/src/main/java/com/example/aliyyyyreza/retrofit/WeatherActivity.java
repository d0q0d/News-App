package com.example.aliyyyyreza.retrofit;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aliyyyyreza.retrofit.Model.User.Status;
import com.example.aliyyyyreza.retrofit.Model.Weather.Model;
import com.example.aliyyyyreza.retrofit.Model.User.SignUp;
import com.example.aliyyyyreza.weather7.R;
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
    RetrofitCallback<Model> modelRetrofitCallback = new RetrofitCallback<>(this, new Callback<Model>() {
        @Override
        public void onResponse(Call<Model> call, Response<Model> response) {
            //Log.i(TAG, "onResponse:"+response.body());
            Model model = response.body();
            int x = (int) (Double.parseDouble(model.getMain().getTemp()) - 273);
            Snackbar.make(findViewById(R.id.cl1), "London : "+x + "C", Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<Model> call, Throwable t) {
            Log.i("TAG", "onFailure=" + t.getLocalizedMessage());
        }
    });
    RetrofitCallback<Status> boolianRetrofitCallback = new RetrofitCallback<>(this, new Callback<Status>() {
        @Override
        public void onResponse(Call<Status> call, Response<Status> response) {
            //Log.i(TAG, "onResponse: " + response.body());
            Status boolian = response.body();
            Snackbar.make(findViewById(R.id.cl1), boolian.isSuccess() + " ", Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<Status> call, Throwable t) {
            Log.i("TAG", "onFailure=" + t.getLocalizedMessage());
        }
    });
    //private CheckNetwork checkNetwork;

    @Override
    protected void onStart() {
        super.onStart();
      /*  checkNetwork = new CheckNetwork();
        registerReceiver(checkNetwork, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setupViews();
        MyApp.getApplication().setUpRetrofit();
        getWeatherInfo();
    }

    @Override
    protected void onStop() {
        //unregisterReceiver(checkNetwork);
        super.onStop();
    }


    private void setupViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
      /*  getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(this);
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
        }
    }

    private void getWeatherInfo() {
        MyApp.myApi.getWeatherInfo().enqueue(modelRetrofitCallback);
    }

   /* private class CheckNetwork extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
            if (isConnected) {
            } else {

            }
        }
    }*/
}

