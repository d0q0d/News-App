package com.example.aliyyyyreza.retrofit.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.example.aliyyyyreza.retrofit.R;

public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.setting_prefrences);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //boolean b=getPreferenceManager().getSharedPreferences().getBoolean("key1",false);
    }

    @Override
    public PreferenceManager getPreferenceManager() {
        return super.getPreferenceManager();

    }
}
