package com.roadassistance.roadassistanceapp;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;
import com.roadassistance.roadassistanceapp.presentation.searchinprogress.view.GmapFragment;

public class MainActivity extends AppCompatActivity {
    FragmentManager fM = getFragmentManager();
    SupportMapFragment sMF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sMF = SupportMapFragment.newInstance();
        fM.beginTransaction().replace(R.id.main_fragment, new GmapFragment()).commit();
    }
}
