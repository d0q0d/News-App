package com.example.aliyyyyreza.retrofit.ui.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aliyyyyreza.retrofit.R;
import com.example.aliyyyyreza.retrofit.ui.fragment.MainFragment;
import com.example.aliyyyyreza.retrofit.ui.fragment.SettingFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

public class ArticleActivity extends AppCompatActivity {

    private static final String TAG = ArticleActivity.class.getSimpleName();
    androidx.appcompat.widget.Toolbar toolbar;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Runnable navigateNowplaying = new Runnable() {
        public void run() {
            Fragment fragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, fragment).commitAllowingStateLoss();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//        FirebaseCrash.report(new NullPointerException());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigateNowplaying.run();
        //xxx();
    }

    private void xxx() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer,new SettingFragment()).commit();
    }
}

